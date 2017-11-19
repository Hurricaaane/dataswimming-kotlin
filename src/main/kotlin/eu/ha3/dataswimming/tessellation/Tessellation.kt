package eu.ha3.dataswimming.tessellation

import eu.ha3.dataswimming.treemapping.Treemapping

/**
 * (Default template)
 * Created on 2017-09-20
 *
 * @author Ha3
 */
class Tessellation<out T>(val item: T, val x: Double, val y: Double, val w: Double, val h: Double) {
    companion object {
        fun <T> from(treemap: Treemapping<T>, initialBreadth: Int): List<Tessellation<T>> {
            return internalTessellation(treemap, 0.0, 0.0, 1.0, 1.0, initialBreadth)
        }

        private fun <T> internalTessellation(treemap: Treemapping<T>, xx: Double, yy: Double, ww: Double, hh: Double, breadth: Int): List<Tessellation<T>> {
            if (treemap.isLeaf()) {
                return listOf(Tessellation(treemap.element!!, xx, yy, ww, hh))

            } else {
                if (breadth % 2 == 0) {
                    return listOf(
                            internalTessellation(
                                    treemap.left!!,
                                    xx, yy, ww * treemap.split, hh,
                                    breadth + 1
                            ),
                            internalTessellation(
                                    treemap.right!!,
                                    ww * treemap.split + xx, yy, ww * treemap.oppositeSplit(), hh,
                                    breadth + 1
                            )
                    ).flatMap { it }
                } else {
                    return listOf(
                            internalTessellation(
                                    treemap.left!!,
                                    xx, yy, ww, hh * treemap.split,
                                    breadth + 1
                            ),
                            internalTessellation(
                                    treemap.right!!,
                                    xx, hh * treemap.split + yy, ww, hh * treemap.oppositeSplit(),
                                    breadth + 1
                            )
                    ).flatMap { it }
                }
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Tessellation<*>

        if (item != other.item) return false
        if (x != other.x) return false
        if (y != other.y) return false
        if (w != other.w) return false
        if (h != other.h) return false

        return true
    }

    override fun hashCode(): Int {
        var result = item?.hashCode() ?: 0
        result = 31 * result + x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + w.hashCode()
        result = 31 * result + h.hashCode()
        return result
    }

    override fun toString(): String {
        return "Tessellation(item=$item, x=$x, y=$y, w=$w, h=$h)"
    }
}
