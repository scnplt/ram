/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.data.service.speechtotext

interface SpeechToTextService {
    fun start()
    fun stop()
    fun shutdown()
    fun setListener(listener: Listener)

    interface Listener {
        fun onResult(textList: List<String>) {}
        fun onError(error: Int) {}
    }
}
