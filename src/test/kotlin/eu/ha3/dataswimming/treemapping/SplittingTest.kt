package eu.ha3.dataswimming.treemapping

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

/**
 * (Default template)
 * Created on 2017-09-19
 *
 * @author Ha3
 */
internal class SplittingTest {
    @Test
    fun splitting() {
        val fn: (Int) -> Long = Int::toLong

        assertThrows(TreemappingRequiresAnElementException::class.java, { Splitting(listOf(), fn) })
        assertThat(Splitting(listOf(1), fn).left, `is`(listOf(1)))
        assertThat(Splitting(listOf(1, 2), fn).left, `is`(listOf(1)))
        assertThat(Splitting(listOf(1, 2), fn).right, `is`(listOf(2)))
        assertThat(Splitting(listOf(100, 4, 4), fn).left, `is`(listOf(100)))
        assertThat(Splitting(listOf(2, 2, 2, 3, 3), fn).left, `is`(listOf(2, 2, 2)))
        assertThat(Splitting(listOf(1, 1, 100), fn).left, `is`(listOf(1, 1)))
        assertThat(Splitting(listOf(0, 0, 0, 0), fn).left, `is`(listOf(0, 0)))
        assertThat(Splitting(listOf(1, 1, 1, 1), fn).left, `is`(listOf(1, 1)))
        assertThat(Splitting(listOf(1, 1, 1, 1, 1), fn).left, `is`(listOf(1, 1, 1))) // This will fail if rolling total is a Long (due division)
        assertThat(Splitting(listOf(0, 0, 0, 0, 0), fn).left, `is`(listOf(0, 0, 0)))
        assertThat(Splitting(listOf(1, 1, 1, 1, 2, 2), fn).left, `is`(listOf(1, 1, 1, 1)))
        assertThat(Splitting(listOf(1, 1, 1, 1, 2, 2), fn).right, `is`(listOf(2, 2)))
        assertThat(Splitting(listOf(0, 100), fn).left, `is`(listOf(0)))
        assertThat(Splitting(listOf(100, 0, 100), fn).left, `is`(listOf(100)))
        assertThat(Splitting(listOf(100, 0, 0, 0, 100), fn).left, `is`(listOf(100)))
    }
}

