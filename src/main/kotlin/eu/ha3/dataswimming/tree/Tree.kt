package eu.ha3.dataswimming.tree

import java.util.*
import java.util.function.Consumer
import java.util.function.Predicate

/**
 * (Default template)
 * Created on 2017-09-18
 *
 * @author Ha3
 */
class Tree<T : Pathable>(val bit :String, val branches: List<Tree<T>>, val leaves: Set<T>) {
    constructor(leaves: Set<T>): this("", emptyList(), leaves)
    constructor(branches: List<Tree<T>>): this("", branches.sortedBy { it.bit }, emptySet())
    constructor(branches: List<Tree<T>>, leaves: Set<T>): this("", branches.sortedBy { it.bit }, leaves)
    constructor(bit: String, leaves: Set<T>): this(bit, emptyList(), leaves)
    constructor(bit: String, branches: List<Tree<T>>): this(bit, branches.sortedBy { it.bit }, emptySet())

    init {
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
                mergeInto(root, pathable, false)
            }

            return root.freeze()
        }

        fun <T : Pathable> merge(root: Tree<T>, override: Tree<T>): Tree<T> {
            val root = TreeBuilder(root)

            for (pathable in override.subtree()) {
                mergeInto(root, pathable, true)
            }

            return root.freeze()
        }

        fun <T : Pathable> mergeInto(alpha: Tree<T>, pathables: List<T>): Tree<T> {
            val root = TreeBuilder(alpha)

            for (pathable in pathables) {
                mergeInto(root, pathable, true)
            }

            return root.freeze()
        }

        private fun <T : Pathable> mergeInto(root: TreeBuilder<T>, pathable: T, overrideMode: Boolean) {
            val items = pathable.asPathableItems()
            if (items.size == 1) {
                root.leaves.add(pathable)

            } else {
                var current = root
                for (item in items.subList(0, items.size - 1)) {
                    current = current.getting(item)
                }

                if (overrideMode) {
                    current.leaves.stream()
                            .filter(Predicate { it.lastBit() == pathable.lastBit() })
                            .findAny()
                            .ifPresent(Consumer {
                                current.leaves.remove(it)
                            })
                }

                current.leaves.add(pathable)
            }
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

    fun subtree(): List<T> {
        return subtreeSequence().toList()
    }

    fun subtreeSequence(): Sequence<T> {
        // TODO: Review this functional idiom
        return sequenceOf(
                branches.asSequence().map { it.subtreeSequence() }.flatMap { it },
                leaves.asSequence()
        ).flatMap { it }
    }

    fun <V> mapSubtree(mappingFunction: (T) -> V): List<V> {
        return subtreeSequence().map(mappingFunction).toList()
    }

    private val bitToBranch: Map<String, Tree<T>> by lazy { branches.associateBy({ it.bit }, { it }) }
    private val bitToLeaf: Map<String, T> by lazy { leaves.associateBy({ it.lastBit() }, { it }) }

    fun get(bit: String): Tree<T>? {
        return bitToBranch[bit]
    }

    fun leaf(bit: String): T? {
        return bitToLeaf[bit]
    }
}

interface Pathable {
    fun asPathableItems(): List<String>
    fun lastBit() : String = asPathableItems().last()
}
