package eu.ha3.dataswimming.treemapping

/**
 * (Default template)
 * Created on 2017-09-20
 *
 * @author Ha3
 */
class Treemapping<T> {
    val left: Treemapping<T>?
    val right: Treemapping<T>?
    val element: T?

    constructor(elements: List<T>, weighFunction: (T) -> Long) {
        val splitting = Splitting(elements, weighFunction)
        if (splitting.right.isEmpty()) {
            left = null
            right = null
            element = splitting.left[0]

        } else {
            left = Treemapping(splitting.left, weighFunction)
            right = Treemapping(splitting.right, weighFunction)
            element = null
        }
    }

    fun isLeaf(): Boolean {
        return element != null
    }
}