import java.lang.RuntimeException
import java.math.BigInteger

interface Packet {
    companion object {
        const val TYPE_SUM = 0
        const val TYPE_PRODUCT = 1
        const val TYPE_MIN = 2
        const val TYPE_MAX = 3
        const val TYPE_LITERAL = 4
        const val TYPE_GREATER_THAN = 5
        const val TYPE_LESS_THAN = 6
        const val TYPE_EQUAL = 7
    }

    val version: Int
    val versions: Int
    val eval: BigInteger
}

data class LiteralPacket(override val version: Int, val value: Int) : Packet {
    companion object {
        fun parse(input: ArrayDeque<Byte>, version: Int): LiteralPacket {
            val value = readLiteral(input)
            return LiteralPacket(version, value)
        }

        private fun readLiteral(input: ArrayDeque<Byte>): Int {
            val bits = ArrayDeque<Byte>()
            var last = false
            while (!last) {
                if (input.removeFirst() < 1) {
                    last = true
                }
                for (i in 0..3) {
                    bits.add(input.removeFirst())
                }
            }
            return bits.toInt()
        }
    }

    override fun toString() = value.toString()
    override val versions: Int
        get() = this.version
    override val eval: BigInteger
        get() = this.value.toBigInteger()
}

data class OperatorPacket(override val version: Int, val type: Int, val subpackets: List<Packet>) : Packet {
    companion object {
        fun parse(input: ArrayDeque<Byte>, version: Int, type: Int): OperatorPacket {
            val lengthType = input.removeFirst()
            val subpackets = mutableListOf<Packet>()
            when (lengthType.toInt()) {
                0 -> {
                    val length = input.removeFirst(15).toInt()
                    val end = input.size - length
                    while (input.size > end) {
                        subpackets.add(readPacket(input))
                    }
                }
                else -> {
                    val packets = input.removeFirst(11).toInt()
                    for (i in 0 until packets) {
                        subpackets.add(readPacket(input))
                    }
                }
            }
            return OperatorPacket(version, type, subpackets)
        }
    }

    override val eval: BigInteger
        get() = when (this.type) {
            Packet.TYPE_SUM -> {
                this.subpackets.sumOf { it.eval }
            }
            Packet.TYPE_PRODUCT -> {
                var product = BigInteger.ONE
                subpackets.forEach {
                    product *= it.eval
                }
                product
            }
            Packet.TYPE_MIN -> {
                this.subpackets.minOf { it.eval }
            }
            Packet.TYPE_MAX -> {
                this.subpackets.maxOf { it.eval }
            }
            Packet.TYPE_GREATER_THAN -> {
                if (this.subpackets.first().eval > this.subpackets.last().eval) {
                    BigInteger.ONE
                } else {
                    BigInteger.ZERO
                }
            }
            Packet.TYPE_LESS_THAN -> {
                if (this.subpackets.first().eval < this.subpackets.last().eval) {
                    BigInteger.ONE
                } else {
                    BigInteger.ZERO
                }
            }
            Packet.TYPE_EQUAL -> {
                if (this.subpackets.first().eval == this.subpackets.last().eval) {
                    BigInteger.ONE
                } else {
                    BigInteger.ZERO
                }
            }
            else -> throw RuntimeException("Illegal type $type")
        }

    override fun toString() = when (this.type) {
        Packet.TYPE_SUM -> {
            "(" + this.subpackets.joinToString(" + ") { it.toString() } + ")"
        }
        Packet.TYPE_PRODUCT -> {
            "(" + this.subpackets.joinToString(" * ") { it.toString() } + ")"
        }
        Packet.TYPE_MIN -> {
            "min(${subpackets.joinToString(",") { it.toString() }})"
        }
        Packet.TYPE_MAX -> {
            "max(${subpackets.joinToString(",") { it.toString() }})"
        }
        Packet.TYPE_GREATER_THAN -> {
            "(" + "${subpackets[0]} > ${subpackets[1]}" + ")"
        }
        Packet.TYPE_LESS_THAN -> {
            "(" + "${subpackets[0]} < ${subpackets[1]}" + ")"
        }
        Packet.TYPE_EQUAL -> {
            "(" + "${subpackets[0]} == ${subpackets[1]}" + ")"
        }
        else -> throw RuntimeException("Illegal type $type")
    }

    override val versions: Int
        get() = this.version + this.subpackets.sumOf { it.versions }
}