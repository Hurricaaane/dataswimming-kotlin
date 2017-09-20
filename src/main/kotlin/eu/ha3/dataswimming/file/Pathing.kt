package eu.ha3.dataswimming.file

import eu.ha3.dataswimming.tree.Pathable

/**
 * (Default template)
 * Created on 2017-09-21
 *
 * @author Ha3
 */
internal class Pathing(path: String) : Pathable {
    override fun asPathableItems(): List<String> = pathableItems

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Pathing

        if (pathableItems != other.pathableItems) return false

        return true
    }

    override fun hashCode(): Int {
        return pathableItems.hashCode()
    }

    override fun toString(): String {
        return "[" + pathableItems.joinToString("/") + "]"
    }

    private var pathableItems: List<String> = path.split('/')
}
