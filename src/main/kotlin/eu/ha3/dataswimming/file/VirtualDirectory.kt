package eu.ha3.dataswimming.file

/**
 * (Default template)
 * Created on 2017-09-20
 *
 * @author Ha3
 */
data class VirtualDirectory(val bit: String, private val size: Long, val count: Long) : Weighted {
    override fun getSize() = size
}
