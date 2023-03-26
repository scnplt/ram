/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.common.log

interface RamLogger {
    fun debugInit()
    fun e(exception: Throwable)
    fun i(message: String?)
    fun d(message: String?)
    fun v(message: String?)
    fun w(message: String?)
}
