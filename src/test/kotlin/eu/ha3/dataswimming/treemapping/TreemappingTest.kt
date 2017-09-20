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

        assertThrows(TreemappingRequiresAnElementException::class.java, { Treemapping(listOf<Int>(), fn) })
        assertThat(Treemapping(listOf(1), fn).isLeaf(), `is`(true))
        assertThat(Treemapping(listOf(0), fn).isLeaf(), `is`(true))
        assertThat(Treemapping(listOf(1, 1), fn).isLeaf(), `is`(false))

        assertThat(Treemapping(listOf(1, 2), fn).left!!.isLeaf(), `is`(true))
        assertThat(Treemapping(listOf(1, 2), fn).left!!.element, `is`(1))
        assertThat(Treemapping(listOf(1, 2), fn).right!!.element, `is`(2))

        assertThat(Treemapping(listOf(1, 1, 2), fn).isLeaf(), `is`(false))
        assertThat(Treemapping(listOf(1, 1, 2), fn).left!!.isLeaf(), `is`(false))
        assertThat(Treemapping(listOf(1, 2, 3), fn).left!!.left!!.element, `is`(1))
        assertThat(Treemapping(listOf(1, 2, 3), fn).left!!.right!!.element, `is`(2))
        assertThat(Treemapping(listOf(1, 2, 3), fn).right!!.element, `is`(3))
    }

    @Test
    fun split() {
        val fn: (Int) -> Long = Int::toLong

        assertThat(Treemapping(listOf(1, 1), fn).split, closeTo(0.5, 0.01))
        assertThat(Treemapping(listOf(1, 1, 1), fn).split, closeTo(0.66, 0.1))
        assertThat(Treemapping(listOf(50, 7), fn).split, closeTo(0.87, 0.1))
        assertThat(Treemapping(listOf(50, 7, 7, 7), fn).split, closeTo(0.70, 0.1))
    }
}