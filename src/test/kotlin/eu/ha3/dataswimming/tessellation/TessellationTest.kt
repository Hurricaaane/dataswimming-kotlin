package eu.ha3.dataswimming.tessellation

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
internal class TessellationTest {
    @Test
    fun tessellating() {
        // FIXME: Difficult to write tests due to equals/hashCode not making a lot of sense with doubles
        assertThat(
                Tessellation.from(Treemapping.of(listOf(1, 1), { it.toLong() }), 0).toSet(),
                `is`(setOf(
                        Tessellation(1, 0.0, 0.0, 0.5, 1.0),
                        Tessellation(1, 0.5, 0.0, 0.5, 1.0)
                ))
        )
        assertThat(
                Tessellation.from(Treemapping.of(listOf(1, 1), { it.toLong() }), 1).toSet(),
                `is`(setOf(
                        Tessellation(1, 0.0, 0.0, 1.0, 0.5),
                        Tessellation(1, 0.0, 0.5, 1.0, 0.5)
                ))
        )
        assertThat(
                Tessellation.from(Treemapping.of(listOf(2, 1, 1), { it.toLong() }), 0).toSet(),
                `is`(setOf(
                        Tessellation(2, 0.0, 0.0, 0.5, 1.0),
                        Tessellation(1, 0.5, 0.0, 0.5, 0.5),
                        Tessellation(1, 0.5, 0.5, 0.5, 0.5)
                ))
        )
    }
}