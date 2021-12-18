import java.lang.StringBuilder
import java.math.BigInteger
import java.util.ArrayDeque
import java.util.Deque

interface Node {
    var parent: Node?
    companion object {
        fun parse(input: String): Node {
            val chars = input.toCharArray().toList()
            val queue: Deque<Char> = ArrayDeque(input.length)
            queue.addAll(chars)
            return parse(queue)
        }
        fun parse(input: Deque<Char>) = if (input.first().isDigit()) {
            NumberNode.parse(input)
        } else {
            PairNode.parse(input)
        }
    }
}

data class PairNode(val left: Node, val right: Node): Node {
    companion object {
        fun parse(input: Deque<Char>): Node {
            require(input.removeFirst() == '[')
            val left = Node.parse(input)
            require(input.removeFirst() == ',')
            val right = Node.parse(input)
            require(input.removeFirst() == ']')
            val node = PairNode(left, right)
            left.parent = node
            right.parent = node
            return node
        }
    }
    override var parent: Node? = null
    override fun equals(other: Any?) = (other is PairNode) && other.left == this.left && other.right == this.right
    override fun hashCode(): Int {
        var hash = 1
        hash = hash * 31 + this.left.hashCode()
        hash = hash * 31 + this.right.hashCode()
        return hash
    }
    override fun toString() = "[$left,$right]"
}

data class NumberNode(val value: BigInteger): Node {
    companion object {
        fun parse(input: Deque<Char>): NumberNode {
            val builder = StringBuilder()
            while (input.isNotEmpty() && input.first().isDigit()) {
                builder.append(input.removeFirst())
            }
            return NumberNode(BigInteger(builder.toString()))
        }
    }
    override var parent: Node? = null
    override fun equals(other: Any?) = (other is NumberNode) && other.value == this.value
    override fun hashCode() = this.value.hashCode()
    override fun toString() = value.toString()
}