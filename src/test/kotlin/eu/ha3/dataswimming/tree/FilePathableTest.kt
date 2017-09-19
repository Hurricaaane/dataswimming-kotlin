package eu.ha3.dataswimming.tree

import eu.ha3.dataswimming.file.FilePathable
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime

/**
 * (Default template)
 * Created on 2017-09-19
 *
 * @author Ha3
 */
internal class FilePathableTest {
    @Test
    fun splitting() {
        val created = ZonedDateTime.of(LocalDateTime.of(2000, 12, 29, 23, 50, 50), ZoneOffset.UTC)
        val modified = ZonedDateTime.of(LocalDateTime.of(2017, 9, 19, 22, 34, 4), ZoneOffset.UTC)

        assertThat(FilePathable("/usr/root/index", 5, created, modified).asPathableItems(), `is`(listOf("", "usr", "root", "index")))
        assertThat(FilePathable("C:/Windows/System32/mapi32.dll", 109_568, created, modified).asPathableItems(), `is`(listOf("C:", "Windows", "System32", "mapi32.dll")))
    }

    @Test
    fun equality() {
        val created = ZonedDateTime.of(LocalDateTime.of(2000, 12, 29, 23, 50, 50), ZoneOffset.UTC)
        val modified = ZonedDateTime.of(LocalDateTime.of(2017, 9, 19, 22, 34, 4), ZoneOffset.UTC)

        assertThat(FilePathable("/usr/root/index", 5, created, modified),
                `is`(FilePathable("/usr/root/index", 5, created, modified)))
    }
}
