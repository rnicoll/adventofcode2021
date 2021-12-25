data class Instruction(val x: IntRange, val y: IntRange, val z: IntRange, val on: Boolean) {
    val onString: String
        get() = if (on) {
            "on"
        } else {
            "off"
        }
    override fun toString() = "$onString x=$x,y=$y,z=$z"
}