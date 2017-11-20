package eu.ha3.dataswimming.treemapping

/**
 * (Default template)
 * Created on 2017-09-20
 *
 * @author Ha3
 */
open class Treemapping<T>(val isLeaf: Boolean, val split: Double) {
    fun oppositeSplit(): Double {
        return 1 - split
    }

    fun asLeaf(): TreemappingLeaf<T> {
        return this as TreemappingLeaf<T>;
    }

    fun asBranch(): TreemappingBranch<T> {
        return this as TreemappingBranch<T>;
    }

    companion object {
        fun <T> of(elements: List<T>, weighFunction: (T) -> Long): Treemapping<T> {
            val splitting = Splitting.of(elements, weighFunction)
            if (splitting.right.isEmpty()) {
                return TreemappingLeaf(splitting.left[0])

            } else {
                return TreemappingBranch(of(splitting.left, weighFunction), of(splitting.right, weighFunction), calculateSplit(splitting, weighFunction))
            }
        }

        private fun <T> calculateSplit(splitting: Splitting<T>, weighFunction: (T) -> Long): Double {
            val ls = splitting.left.map(weighFunction).reduce(Long::plus)
            val rs = splitting.right.map(weighFunction).reduce(Long::plus)
            return ls / (ls * 1.0 + rs)
        }
    }
    init {
    }
}

class TreemappingLeaf<T>(val element: T) : Treemapping<T>(true, 0.0)

class TreemappingBranch<T>(val left: Treemapping<T>, val right: Treemapping<T>, split: Double) : Treemapping<T>(false, split)
