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
    fun pathable() {
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
        assertThat(
                Tree.from(listOf(TTPathable("usr/root/index"))),
                `is`(Tree(listOf(Tree("usr", listOf(Tree("root", setOf(TTPathable("usr/root/index"))))))))
        )
    }

    @Test
    fun mappingSubtree() {
        assertThat(
                Tree.from(listOf(TTPathable("data"))).subtree(),
                `is`(listOf(TTPathable("data")))
        )
        assertThat(
                Tree.from(listOf(TTPathable("bin/thing"), TTPathable("bin/diamond"))).subtree(),
                `is`(listOf(TTPathable("bin/thing"), TTPathable("bin/diamond")))
        )
        assertThat(
                Tree.from(listOf(TTWeighed("data", 1))).mapSubtree({ it.weight }).reduce(Int::plus),
                `is`(1)
        )
        assertThat(
                Tree.from(listOf(TTWeighed("data", 1), TTWeighed("bin", 2))).mapSubtree({ it.weight }).reduce(Int::plus),
                `is`(3)
        )
        assertThat(
                Tree.from(listOf(TTWeighed(".something", 10), TTWeighed("bin/thing", 1), TTWeighed("bin/diamond", 2))).mapSubtree({ it.weight }).reduce(Int::plus),
                `is`(13)
        )
        assertThat(
                Tree.from(listOf(
                        TTWeighed(".something", 10),
                        TTWeighed("bin/thing", 1),
                        TTWeighed("bin/diamond", 2),
                        TTWeighed("usr/root/index", 7)
                )).mapSubtree({ it.weight }).reduce(Int::plus),
                `is`(20)
        )
        assertThat(
                Tree.from(listOf(
                        TTPathable(".something"),
                        TTPathable("bin/thing"),
                        TTPathable("bin/diamond"),
                        TTPathable("usr/root/index")
                )).mapSubtree({ it.lastBit() }).toSet(),
                `is`(setOf(".something", "thing", "diamond", "index"))
        )
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


class TTWeighed(path: String, val weight: Int) : Pathable {
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
