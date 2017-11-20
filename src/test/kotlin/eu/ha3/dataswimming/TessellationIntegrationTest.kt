package eu.ha3.dataswimming

import eu.ha3.dataswimming.file.FilePathable
import eu.ha3.dataswimming.file.Pathing
import eu.ha3.dataswimming.file.VirtualDirectory
import eu.ha3.dataswimming.file.Weighted
import eu.ha3.dataswimming.tessellation.Tessellation
import eu.ha3.dataswimming.tree.Tree
import eu.ha3.dataswimming.treemapping.Treemapping
import eu.ha3.dataswimming.treeview.TreeView
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime

/**
 * (Default template)
 * Created on 2017-09-20
 *
 * @author Ha3
 */
internal class TessellationIntegrationTest {
    @Test
    fun chaining() {
        val created = ZonedDateTime.of(LocalDateTime.of(2000, 12, 29, 23, 50, 50), ZoneOffset.UTC)
        val modified = ZonedDateTime.of(LocalDateTime.of(2017, 9, 19, 22, 34, 4), ZoneOffset.UTC)

        val files = listOf(
                FilePathable("/com/example/index.html", 54, created, modified),
                FilePathable("/com/example/favicon.ico", 17_225, created, modified),
                FilePathable("/com/example/logo.png", 11_500, created, modified),
                FilePathable("/com/example/css/styles.css", 120, created, modified),
                FilePathable("/com/example/css/base.css", 340, created, modified),
                FilePathable("/com/example/css/temp/.htaccess", 34, created, modified),
                FilePathable("/com/example/js/bootstrap.js", 3_000, created, modified),
                FilePathable("/com/example/js/angular.js", 10_000, created, modified),
                FilePathable("/com/example/js/derp.js", 2_400, created, modified)
        )

        val tree = Tree.from(files)
        val tessellations = tessellateTree(tree.navigate(Pathing("/com/example"))!!)
        tessellations.forEach { print(it) }
        assertThat(tessellations.size, `is`(5))
        assertThat(tessellations.filter { it.item is VirtualDirectory }.size, `is`(2))
        assertThat(tessellations.filter { it.item is FilePathable }.size, `is`(3))
        assertThat((tessellations.find { it.item.getSize() == 494L }?.item as VirtualDirectory).bit, `is`("css"))
    }

    private fun tessellateTree(tree: Tree<FilePathable>): List<Tessellation<Weighted>> {
        val treeView = TreeView.of(tree) {
            VirtualDirectory(it.bit, it.subtreeSequence().map { it.getSize() }.reduce(Long::plus), it.subtreeSequence().count().toLong())
        }

        val weights = sequenceOf(treeView.branches.asSequence(), treeView.leaves.asSequence())
                .flatMap { it }
                .sortedBy { -it.getSize() }
                .toList()

        val treemap = Treemapping.of(weights, { it.getSize() })

        return Tessellation.from(treemap, 0)
    }
}