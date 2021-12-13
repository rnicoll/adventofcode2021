data class Graph(val start: Node) {
    companion object {
        fun build(vertices: Collection<Vertex>): Graph {
            val allNodes: Map<String, Node> = (vertices.map { it.from } + vertices.map { it.to })
                .toSet().associateWith { Node(it) }
            vertices.forEach {
                allNodes[it.from]?.addPath(allNodes[it.to])
            }
            return Graph(allNodes["start"]!!)
        }
    }
}
