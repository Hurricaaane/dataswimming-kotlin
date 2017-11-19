package eu.ha3.dataswimming.treeview

import eu.ha3.dataswimming.tree.Pathable
import eu.ha3.dataswimming.tree.Tree
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.Test

/**
 * (Default template)
 * Created on 2017-09-20
 *
 * @author Ha3
 */
internal class TreeViewTest {
    @Test
    fun transforming() {
        val items = listOf(TTWeighed("usr/root/thing", 5), TTWeighed("usr/root/thing", 2), TTWeighed("usr/.something", 10))
        val sumItemsInBranchFn: (Tree<TTWeighed>) -> TTBranchView<TTWeighed> = { TTBranchView(it, it.bit, it.mapSubtree { it.weight }.reduce(Int::plus)) }

        val expectedTransformation = TTBranchView(Tree("root", setOf(TTWeighed("usr/root/thing", 5), TTWeighed("usr/root/thing", 2))), "root", 7)
        val expectedLeaves = setOf(TTWeighed("usr/.something", 10))

        assertThat(
                TreeView.of(Tree.from(items).get("usr")!!, sumItemsInBranchFn),
                `is`(TreeView(listOf(expectedTransformation), expectedLeaves))
        )
    }
}

internal class TTBranchView<T : Pathable>(val tree: Tree<T>, val bit: String, val weight: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TTBranchView<*>

        if (tree != other.tree) return false
        if (bit != other.bit) return false
        if (weight != other.weight) return false

        return true
    }

    override fun hashCode(): Int {
        var result = tree.hashCode()
        result = 31 * result + bit.hashCode()
        result = 31 * result + weight
        return result
    }

    override fun toString(): String {
        return "TTBranchView(tree=$tree, bit='$bit', weight=$weight)"
    }
}

internal class TTWeighed(path: String, val weight: Int) : Pathable {
    override fun asPathableItems(): List<String> = pathableItems

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TTWeighed

        if (weight != other.weight) return false
        if (pathableItems != other.pathableItems) return false

        return true
    }

    override fun hashCode(): Int {
        var result = weight
        result = 31 * result + pathableItems.hashCode()
        return result
    }

    override fun toString(): String {
        return "[${pathableItems.joinToString("/")}:$weight]"
    }

    private var pathableItems: List<String> = path.split('/')
}
