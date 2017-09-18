package eu.ha3.dataswimming.tree

/**
 * (Default template)
 * Created on 2017-09-18
 *
 * @author Ha3
 */
class Tree<T> {
    companion object {
        fun <T : Pathable> from(pathables: List<T>) : Tree<T> {
            throw TreeRequiresAnElementException()
        }
    }
}

interface Pathable {
    fun getPathableItems(): List<String>
}
