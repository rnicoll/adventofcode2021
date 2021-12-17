import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.math.BigInteger

internal class Day16KtTest {

    @Test
    fun toInt5() {
        val input = "101".toCharArray().map { it.digitToInt().toByte() }
        val expected = 5
        val actual = input.toInt()
        assertEquals(expected, actual)
    }

    @Test
    fun toInt8() {
        val input = "1000".toCharArray().map { it.digitToInt().toByte() }
        val expected = 8
        val actual = input.toInt()
        assertEquals(expected, actual)
    }

    @Test
    fun toInt2021() {
        val input = "011111100101".toCharArray().map { it.digitToInt().toByte() }
        val expected = 2021
        val actual = input.toInt()
        assertEquals(expected, actual)
    }

    @Test
    fun evalSum() {
        val input = "C200B40A82"
        val packet = readPacket(parseHexToBinary(input))
        val expected = BigInteger("3")
        val actual = packet.eval
        assertEquals(expected, actual)
        assertEquals("(1 + 2)", packet.toString())
    }

    @Test
    fun evalProduct() {
        val input = "04005AC33890"
        val packet = readPacket(parseHexToBinary(input))
        val expected = BigInteger("54")
        val actual = packet.eval
        assertEquals(expected, actual)
        assertEquals("(6 * 9)", packet.toString())
    }

    @Test
    fun evalMax() {
        val input = "CE00C43D881120"
        val packet = readPacket(parseHexToBinary(input))
        val expected = BigInteger("9")
        val actual = packet.eval
        assertEquals(expected, actual)
        assertEquals("max(7,8,9)", packet.toString())
    }

    @Test
    fun evalMaxSingle() {
        val packet = OperatorPacket(2, Packet.TYPE_MAX, listOf(LiteralPacket(2, BigInteger("256058865"))))
        val expected = BigInteger("256058865")
        val actual = packet.eval
        assertEquals(expected, actual)
        assertEquals("max(256058865)", packet.toString())
    }

    @Test
    fun evalMin() {
        val input = "880086C3E88112"
        val packet = readPacket(parseHexToBinary(input))
        val expected = BigInteger("7")
        val actual = packet.eval
        assertEquals(expected, actual)
        assertEquals("min(7,8,9)", packet.toString())
    }

    @Test
    fun evalLess() {
        val input = "D8005AC2A8F0"
        val packet = readPacket(parseHexToBinary(input))
        val expected = BigInteger.ONE
        val actual = packet.eval
        assertEquals(expected, actual)
        assertEquals("(5 < 15)", packet.toString())
    }

    @Test
    fun evalGreater() {
        val input = "F600BC2D8F"
        val packet = readPacket(parseHexToBinary(input))
        val expected = BigInteger.ZERO
        val actual = packet.eval
        assertEquals(expected, actual)
        assertEquals("(5 > 15)", packet.toString())
    }

    @Test
    fun evalEqual() {
        val input = "9C005AC2F8F0"
        val packet = readPacket(parseHexToBinary(input))
        val expected = BigInteger.ZERO
        val actual = packet.eval
        assertEquals(expected, actual)
        assertEquals("(5 == 15)", packet.toString())
    }

    @Test
    fun evalComplex() {
        val input = "9C0141080250320F1802104A08"
        val packet = readPacket(parseHexToBinary(input))
        val expected = BigInteger.ONE
        val actual = packet.eval
        assertEquals(expected, actual)
        assertEquals("((1 + 3) == (2 * 2))", packet.toString())
    }
}