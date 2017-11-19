package eu.ha3.dataswimming.treemapping

/**
 * (Default template)
 * Created on 2017-09-20
 *
 * @author Ha3
 */
class Treemapping<T>(elements: List<T>, weighFunction: (T) -> Long) {
    val left: Treemapping<T>?
    val right: Treemapping<T>?
    val element: T?
    val split: Double

    private fun calculateSplit(splitting: Splitting<T>, weighFunction: (T) -> Long): Double {
        val ls = splitting.left.map(weighFunction).reduce(Long::plus)
        val rs = splitting.right.map(weighFunction).reduce(Long::plus)
        return ls / (ls * 1.0 + rs)
    }

    fun isLeaf(): Boolean {
        return element != null
    }

    fun oppositeSplit(): Double {
        return 1 - split
    }

    init {
        val splitting = Splitting(elements, weighFunction)
        if (splitting.right.isEmpty()) {
            left = null
            right = null
            split = 0.0
            element = splitting.left[0]

        } else {
            left = Treemapping(splitting.left, weighFunction)
            right = Treemapping(splitting.right, weighFunction)
            split = calculateSplit(splitting, weighFunction)
            element = null
        }
    }
}