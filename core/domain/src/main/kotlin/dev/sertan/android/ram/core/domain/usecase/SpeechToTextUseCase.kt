/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.domain.usecase

import dev.sertan.android.ram.core.data.service.speechtotext.SpeechToTextService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpeechToTextUseCase @Inject constructor(
    private val speechToTextService: SpeechToTextService
) {

    operator fun invoke(
        onComplete: (text: String) -> Unit = {},
        onError: (errorCode: Int) -> Unit = {}
    ): Unit = with(speechToTextService) {
        setListener(object : SpeechToTextService.Listener {
            override fun onResult(textList: List<String>): Unit = onComplete(textList.last())
            override fun onError(error: Int): Unit = onError(error)
        })
        speechToTextService.start()
    }

    fun stop(): Unit = speechToTextService.stop()

    fun shutdown(): Unit = speechToTextService.shutdown()
}
