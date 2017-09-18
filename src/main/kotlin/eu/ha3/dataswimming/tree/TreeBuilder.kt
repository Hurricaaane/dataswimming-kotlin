package eu.ha3.dataswimming.tree

data class TreeBuilder<T : Pathable>(val bit: String, var branches: MutableList<TreeBuilder<T>>, val leaves: MutableSet<T>) {
    constructor() : this("", mutableListOf(), mutableSetOf())
    constructor(bit: String) : this(bit, mutableListOf(), mutableSetOf())

    fun freeze(): Tree<T> {
        if (branches.isEmpty() && leaves.isEmpty()) {
            throw TreeRequiresAnElementException()
        }

        return Tree(bit, branches.map { it.freeze() }.toList(), leaves.toSet())
    }

    fun getting(item: String): TreeBuilder<T> {
        return branches.stream()
                .filter({ it.bit == item })
                .findAny()
                .orElseGet({
                    val newBranch = TreeBuilder<T>(item)
                    branches.add(newBranch)
                    newBranch
                })
    }
}