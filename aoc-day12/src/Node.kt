data class Node(val id: String) {
    private val paths = mutableSetOf<Node>()
    fun addPath(node: Node?) {
        node?.also {
            if (it.id != "start") {
                this.paths.add(it)
            }
            if (this.id != "start") {
                it.paths.add(this)
            }
        }
    }
    fun exits(): Set<Node> = paths
    override fun equals(other: Any?): Boolean {
        return other is Node && other.id == this.id
    }
    fun isSmall() = id.all { it.isLowerCase() }

    override fun hashCode() = this.id.hashCode()
    override fun toString(): String {
        return "$id: ${paths.map { it.id }}"
    }
}