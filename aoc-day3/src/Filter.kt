interface Filter {
    fun filter(input: List<List<Int>>, idx: Int): List<List<Int>>
}

class OxygenGenerator: Filter {
    override fun filter(input: List<List<Int>>, idx: Int): List<List<Int>> {
        val ones = countBits(input, 1)
        val zeroes = countBits(input, 0)
        return input.filter {
            val value: Int = it[idx]
            val zeroCount = zeroes[idx]
            val oneCount = ones[idx]
            // Keep entries where ones are more common, or equally common
            val match: Int = if (oneCount >= zeroCount) {
                1
            } else {
                0
            }
            value == match
        }
    }
}

class CO2Scrubber: Filter {
    override fun filter(input: List<List<Int>>, idx: Int): List<List<Int>> {
        val ones = countBits(input, 1)
        val zeroes = countBits(input, 0)
        return input.filter {
            val value: Int = it[idx]
            val zeroCount = zeroes[idx]
            val oneCount = ones[idx]
            // Match zero if zero is least or equally common
            val match: Int = if (oneCount < zeroCount) {
                1
            } else {
                0
            }
            value == match
        }
    }
}