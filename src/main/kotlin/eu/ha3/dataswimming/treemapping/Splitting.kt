package eu.ha3.dataswimming.treemapping

/**
 * (Default template)
 * Created on 2017-09-19
 *
 * @author Ha3
 */
class Splitting<out T>(val left: List<T>, val right: List<T>) {
    companion object {
        private fun <T> cutInHalf(elements: List<T>, total: Long, weighFunction: (T) -> Long): Int {
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

        fun <T> of(elements: List<T>, weighFunction: (T) -> Long): Splitting<T> {
            if (elements.isEmpty()) {
                throw TreemappingRequiresAnElementException()
            }

            if (elements.size == 1) {
                return Splitting(elements, emptyList());

            } else if (elements.size == 2) {
                return Splitting(listOf(elements[0]), listOf(elements[1]));

            } else {
                val total = elements
                        .map { weighFunction(it) }
                        .reduce(Long::plus)

                if (total != 0L) {
                    val cut = cutInHalf(elements, total, weighFunction)

                    return Splitting(elements.subList(0, cut), elements.subList(cut, elements.size));

                } else {
                    val leftAdvantage = elements.size / 2 + elements.size % 2

                    return Splitting(elements.subList(0, leftAdvantage), elements.subList(0, leftAdvantage));
                }
            }
        }
    }

}