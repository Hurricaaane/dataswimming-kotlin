package eu.ha3.dataswimming.treemapping

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.hamcrest.number.IsCloseTo.closeTo
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

/**
 * (Default template)
 * Created on 2017-09-20
 *
 * @author Ha3
 */
internal class TreemappingTest {
    @Test
    fun treemapping() {
        val fn: (Int) -> Long = Int::toLong

        assertThrows(TreemappingRequiresAnElementException::class.java, { Treemapping.of(listOf(), fn) })
        assertThat(Treemapping.of(listOf(1), fn).isLeaf, `is`(true))
        assertThat(Treemapping.of(listOf(0), fn).isLeaf, `is`(true))
        assertThat(Treemapping.of(listOf(1, 1), fn).isLeaf, `is`(false))

        assertThat(Treemapping.of(listOf(1, 2), fn).asBranch().left.isLeaf, `is`(true))
        assertThat(Treemapping.of(listOf(1, 2), fn).asBranch().left.asLeaf().element, `is`(1))
        assertThat(Treemapping.of(listOf(1, 2), fn).asBranch().right.asLeaf().element, `is`(2))

        assertThat(Treemapping.of(listOf(1, 1, 2), fn).isLeaf, `is`(false))
        assertThat(Treemapping.of(listOf(1, 1, 2), fn).asBranch().left.isLeaf, `is`(false))
        assertThat(Treemapping.of(listOf(1, 2, 3), fn).asBranch().left.asBranch().left.asLeaf().element, `is`(1))
        assertThat(Treemapping.of(listOf(1, 2, 3), fn).asBranch().left.asBranch().right.asLeaf().element, `is`(2))
        assertThat(Treemapping.of(listOf(1, 2, 3), fn).asBranch().right.asLeaf().element, `is`(3))
    }

    @Test
    fun split() {
        val fn: (Int) -> Long = Int::toLong

        assertThat(Treemapping.of(listOf(1, 1), fn).split, closeTo(0.5, 0.01))
        assertThat(Treemapping.of(listOf(1, 1, 1), fn).split, closeTo(0.66, 0.1))
        assertThat(Treemapping.of(listOf(50, 7), fn).split, closeTo(0.87, 0.1))
        assertThat(Treemapping.of(listOf(50, 7, 7, 7), fn).split, closeTo(0.70, 0.1))
    }
}