package eu.ha3.dataswimming.file

import eu.ha3.dataswimming.tree.Pathable
import java.time.ZonedDateTime

/**
 * (Default template)
 * Created on 2017-09-19
 *
 * @author Ha3
 */
data class FilePathable (
        val path: String,
        private val size: Long,
        val created: ZonedDateTime,
        val modified: ZonedDateTime
) : Pathable, Weighted {
    override fun getSize(): Long = size

    private val pathableItems: List<String> by lazy {
        path.split("/")
    }

    override fun asPathableItems(): List<String> {
        return pathableItems
    }
}