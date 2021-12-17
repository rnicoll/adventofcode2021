data class ProbeTrajectory(val xPos: Int, val yPos: Int, val xVelocity: Int, val yVelocity: Int) {
    // Return - 1 if the probe has not yet reached the target, 0 if it's on target, 1 if it's missed the target
    fun compare(target: TargetArea): Int {
        if (yPos < target.yRange.min) {
            return 1;
        }
        if (yPos > target.yRange.max) {
            return -1;
        }
        if (xPos >= target.xRange.min && xPos <= target.xRange.max) {
            return 0
        }

        // TODO: We could be a lot smarter about whether the probe _could_ hit the target

        return -1
    }

    fun tick(): ProbeTrajectory = if (xVelocity > 0) {
        ProbeTrajectory(xPos + xVelocity, yPos + yVelocity, xVelocity - 1, yVelocity - 1)
    } else {
        ProbeTrajectory(xPos + xVelocity, yPos + yVelocity, xVelocity + 1, yVelocity - 1)
    }

    override fun toString() = "$xPos,$yPos -> $xVelocity,$yVelocity"
}