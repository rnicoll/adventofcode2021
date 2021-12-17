import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day17KtTest {
    companion object {
        data class LaunchTest(val xVelocity: Int, val yVelocity: Int, val valid: Boolean)

        val launchScenarios = arrayOf(
            LaunchTest(7, 2, true),
            LaunchTest(6, 3, true),
            LaunchTest(6, 9, true),
            LaunchTest(6, 10, false),
            LaunchTest(9, 0, true),
            LaunchTest(17, -4, false)
        )
    }

    @Test
    fun launch() {
        val target = TargetArea(Range(20, 30), Range(-10, -5))
        launchScenarios.forEach { scenario ->
            val expected = if (scenario.valid) {
                ProbeTrajectory(0, 0, scenario.xVelocity, scenario.yVelocity)
            } else {
                null
            }
            val actual = Day17.launch(target, scenario.xVelocity, scenario.yVelocity)
            assertEquals(expected, actual)
        }
    }
}
