package eu.ha3.dataswimming.tree

import java.util.*

/**
 * (Default template)
 * Created on 2017-09-18
 *
 * @author Ha3
 */
class Tree<T : Pathable> {
    private val leaves: Set<T>
    private var bit: String
    private var branches: List<Tree<T>>

    constructor(leaves: Set<T>) {
        this.bit = ""
        this.branches = emptyList()
        this.leaves = leaves
        checkWeakRules()
    }

    constructor(bit: String, leaves: Set<T>) {
        this.bit = bit
        this.branches = emptyList()
        this.leaves = leaves
        checkWeakRules()
    }

    constructor(branches: List<Tree<T>>) {
        this.bit = ""
        this.branches = branches.sortedBy { it.bit }
        this.leaves = emptySet()
        checkWeakRules()
    }

    constructor(branches: List<Tree<T>>, leaves: Set<T>) {
        this.bit = ""
        this.branches = branches.sortedBy { it.bit }
        this.leaves = leaves
        checkWeakRules()
    }

    constructor(bit: String, branches: List<Tree<T>>, leaves: Set<T>) {
        this.bit = bit
        this.branches = branches.sortedBy { it.bit }
        this.leaves = leaves
        checkWeakRules()
    }

    private fun checkWeakRules() {
        // Note: We do not check if Tree bits are identical.
        if (branches.isEmpty() && leaves.isEmpty()) {
            throw TreeRequiresAnElementException()
        }

        if (!Collections.disjoint(branches.map { it.bit }, leaves.map { it.lastBit() })) {
            throw TreeConflictingBitException()
        }
    }

    companion object {
        fun <T : Pathable> from(pathables: List<T>) : Tree<T> {
            if (pathables.isEmpty()) {
                throw TreeRequiresAnElementException()
            }

            val root = TreeBuilder<T>()

            for (pathable in pathables) {
                val items = pathable.asPathableItems()
                if (items.size == 1) {
                    root.leaves.add(pathable)

                } else {
                    var current = root
                    for (item in items.subList(0, items.size - 1)) {
                        current = root.getting(item)
                    }
                    current.leaves.add(pathable)
                }
            }

            return root.freeze()
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Tree<*>

        if (leaves != other.leaves) return false
        if (bit != other.bit) return false
        if (branches != other.branches) return false

        return true
    }

    override fun hashCode(): Int {
        var result = leaves.hashCode()
        result = 31 * result + bit.hashCode()
        result = 31 * result + branches.hashCode()
        return result
    }

    override fun toString(): String {
        return "Tree(leaves=$leaves, bit='$bit', branches=$branches)"
    }
}

interface Pathable {
    fun asPathableItems(): List<String>
    fun lastBit() : String = asPathableItems().last()
}
