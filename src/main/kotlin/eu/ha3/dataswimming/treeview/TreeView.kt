package eu.ha3.dataswimming.treeview

import eu.ha3.dataswimming.tree.Pathable
import eu.ha3.dataswimming.tree.Tree

/**
 * (Default template)
 * Created on 2017-09-20
 *
 * @author Ha3
 */
class TreeView<B, L : Pathable>(val branches: List<B>, val leaves: Set<L>) {
    companion object {
        fun <B, L : Pathable> of(subject: Tree<L>, transformer: (Tree<L>) -> B): TreeView<B, L> {
            return TreeView(subject.branches.map(transformer), subject.leaves)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TreeView<*, *>

        if (branches != other.branches) return false
        if (leaves != other.leaves) return false

        return true
    }

    override fun hashCode(): Int {
        var result = branches.hashCode()
        result = 31 * result + leaves.hashCode()
        return result
    }

    override fun toString(): String {
        return "TreeView(branches=$branches, leaves=$leaves)"
    }
}
