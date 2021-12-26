interface Processor<T> {
    val startIdx: Int
    fun get(register: Register): T
    fun set(register: Register, value: T)
}