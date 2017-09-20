package eu.ha3.dataswimming.tesselation

import eu.ha3.dataswimming.treemapping.Treemapping
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.Test

/**
 * (Default template)
 * Created on 2017-09-20
 *
 * @author Ha3
 */
internal class TesselationTest {
    @Test
    fun tesselating() {
        // FIXME: Difficult to write tests due to equals/hashCode not making a lot of sense with doubles
        assertThat(
                Tesselation.from(Treemapping(listOf(1, 1), { it.toLong() }), 0).toSet(),
                `is`(setOf(
                        Tesselation(1, 0.0, 0.0, 0.5, 1.0),
                        Tesselation(1, 0.5, 0.0, 0.5, 1.0)
                ))
        )
        assertThat(
                Tesselation.from(Treemapping(listOf(1, 1), { it.toLong() }), 1).toSet(),
                `is`(setOf(
                        Tesselation(1, 0.0, 0.0, 1.0, 0.5),
                        Tesselation(1, 0.0, 0.5, 1.0, 0.5)
                ))
        )
        assertThat(
                Tesselation.from(Treemapping(listOf(2, 1, 1), { it.toLong() }), 0).toSet(),
                `is`(setOf(
                        Tesselation(2, 0.0, 0.0, 0.5, 1.0),
                        Tesselation(1, 0.5, 0.0, 0.5, 0.5),
                        Tesselation(1, 0.5, 0.5, 0.5, 0.5)
                ))
        )
    }
}