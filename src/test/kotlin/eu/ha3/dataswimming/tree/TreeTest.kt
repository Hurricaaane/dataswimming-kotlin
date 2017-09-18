package eu.ha3.dataswimming.tree

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

/**
 * (Default template)
 * Created on 2017-09-18
 *
 * @author Ha3
 */
internal class TreeTest {
    @Test
    fun suite() {
        assertThrows(TreeRequiresAnElementException::class.java, { Tree.from(listOf<Pathable>()) })
        assertThat(Tree.from(listOf(TTPathable("data"))), `is`(Tree(setOf(TTPathable("data")))))
        assertThat(
                Tree.from(listOf(TTPathable("data"), TTPathable("bin"))),
                `is`(Tree(setOf(TTPathable("data"), TTPathable("bin"))))
        )
        assertThat(
                Tree.from(listOf(TTPathable("bin"), TTPathable("data"))),
                `is`(Tree(setOf(TTPathable("data"), TTPathable("bin"))))
        )
        assertThat(
                Tree.from(listOf(TTPathable("bin/thing"))),
                `is`(Tree(listOf(Tree("bin", setOf(TTPathable("bin/thing"))))))
        )
        assertThat(
                Tree.from(listOf(TTPathable("bin/thing"), TTPathable("bin/diamond"))),
                `is`(Tree(listOf(Tree("bin", setOf(TTPathable("bin/thing"), TTPathable("bin/diamond"))))))
        )
        assertThat(
                Tree.from(listOf(TTPathable("bin/diamond"), TTPathable("bin/thing"))),
                `is`(Tree(listOf(Tree("bin", setOf(TTPathable("bin/thing"), TTPathable("bin/diamond"))))))
        )
        assertThat(
                Tree.from(listOf(TTPathable("bin/diamond"), TTPathable("bin/thing"), TTPathable(".something"))),
                `is`(Tree(listOf(Tree("bin", setOf(TTPathable("bin/thing"), TTPathable("bin/diamond")))), setOf(TTPathable(".something"))))
        )
        assertThrows(TreeConflictingBitException::class.java, {
            Tree.from(listOf(TTPathable("bin"), TTPathable("bin/thing")))
        })
    }
}

class TTPathable(path: String) : Pathable {
    override fun asPathableItems(): List<String> = pathableItems

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TTPathable

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

