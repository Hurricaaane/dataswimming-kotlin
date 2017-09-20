package eu.ha3.dataswimming.tesselation

import eu.ha3.dataswimming.treemapping.Treemapping

/**
 * (Default template)
 * Created on 2017-09-20
 *
 * @author Ha3
 */
class Tesselation<T>(val item: T, val x: Double, val y: Double, val w: Double, val h: Double) {
    companion object {
        fun <T> from(treemap: Treemapping<T>, initialBreadth: Int): List<Tesselation<T>> {
            return internalTesselation(treemap, 0.0, 0.0, 1.0, 1.0, initialBreadth)
        }

        private fun <T> internalTesselation(treemap: Treemapping<T>, xx: Double, yy: Double, ww: Double, hh: Double, breadth: Int): List<Tesselation<T>> {
            if (treemap.isLeaf()) {
                return listOf(Tesselation(treemap.element!!, xx, yy, ww, hh))

            } else {
                if (breadth % 2 == 0) {
                    return listOf(
                            internalTesselation(
                                    treemap.left!!,
                                    xx, yy, ww * treemap.split, hh,
                                    breadth + 1
                            ),
                            internalTesselation(
                                    treemap.right!!,
                                    ww * treemap.split + xx, yy, ww * treemap.oppositeSplit(), hh,
                                    breadth + 1
                            )
                    ).flatMap { it }
                } else {
                    return listOf(
                            internalTesselation(
                                    treemap.left!!,
                                    xx, yy, ww, hh * treemap.split,
                                    breadth + 1
                            ),
                            internalTesselation(
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

        other as Tesselation<*>

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
        return "Tesselation(item=$item, x=$x, y=$y, w=$w, h=$h)"
    }
}
