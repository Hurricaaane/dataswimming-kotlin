package eu.ha3.dataswimming.tree

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
    }
}

