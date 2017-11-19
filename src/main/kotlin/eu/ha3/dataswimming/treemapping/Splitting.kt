package eu.ha3.dataswimming.treemapping

/**
 * (Default template)
 * Created on 2017-09-19
 *
 * @author Ha3
 */
class Splitting<out T>(elements: List<T>, weighFunction: (T) -> Long) {
    val left: List<T>
    val right: List<T>

    private fun cutInHalf(elements: List<T>, total: Long, weighFunction: (T) -> Long): Int {
        var rollingTotal = 0.0
        val iter = elements.iterator()
        var i = 0
        while (iter.hasNext() && rollingTotal < total / 2.0) {
            rollingTotal += weighFunction(iter.next()).toDouble()
            i++
        }
        if (i == elements.size) {
            // Always keep at least 1 element to the right
            i--
        }
        return i
    }

    init {
        if (elements.isEmpty()) {
            throw TreemappingRequiresAnElementException()
        }
        if (elements.size == 1) {
            left = elements
            right = emptyList()

        } else if (elements.size == 2) {
            left = listOf(elements[0])
            right = listOf(elements[1])

        } else {
            val total = elements
                    .map { weighFunction(it) }
                    .reduce(Long::plus)

            if (total != 0L) {
                val cut = cutInHalf(elements, total, weighFunction)
                left = elements.subList(0, cut)
                right = elements.subList(cut, elements.size)

            } else {
                val leftAdvantage = elements.size / 2 + elements.size % 2
                left = elements.subList(0, leftAdvantage)
                right = elements.subList(leftAdvantage, elements.size)
            }
        }
    }
}