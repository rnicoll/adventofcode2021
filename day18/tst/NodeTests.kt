import org.junit.jupiter.api.Test
import java.lang.StringBuilder
import kotlin.test.assertEquals

class NodeTests {
    companion object {
        val SAMPLE_NUMBERS = arrayListOf<String>(
            "[1,2]",
            "[[1,2],3]",
            "[9,[8,7]]",
            "[[1,9],[8,5]]",
            "[[[[1,2],[3,4]],[[5,6],[7,8]]],9]",
            "[[[9,[3,8]],[[0,9],6]],[[[3,7],[4,9]],3]]",
            "[[[[1,3],[5,3]],[[1,3],[8,7]]],[[[4,9],[6,9]],[[8,2],[7,3]]]]"
        )
    }    

    @Test
    fun parse() {
        SAMPLE_NUMBERS.forEach { expected ->
            val actual = Node.parse(expected).toString()
            assertEquals(expected, actual)
        }
    }
}