package eu.ha3.dataswimming.tree

data class TreeBuilder<T : Pathable>(val bit: String, var branches: MutableList<TreeBuilder<T>>, val leaves: MutableSet<T>) {
    constructor() : this("", mutableListOf(), mutableSetOf())
    constructor(bit: String) : this(bit, mutableListOf(), mutableSetOf())
    constructor(tree: Tree<T>) : this(tree.bit, tree.branches.map { TreeBuilder(it) }.toMutableList(), tree.leaves.toMutableSet())

    fun freeze(): Tree<T> {
        if (branches.isEmpty() && leaves.isEmpty()) {
            throw TreeRequiresAnElementException()
        }

        return Tree(bit, branches.map { it.freeze() }.toList(), leaves.toSet())
    }

    fun getting(item: String): TreeBuilder<T> {
        return branches.asSequence()
                .filter({ it.bit == item })
                .firstOrNull() ?: createBranch(item)
    }

    private fun createBranch(item: String): TreeBuilder<T> {
        val newBranch = TreeBuilder<T>(item)
        branches.add(newBranch)
        return newBranch
    }
}