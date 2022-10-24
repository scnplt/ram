/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcolor.screen

import android.speech.tts.TextToSpeech
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appcolor.R
import javax.inject.Inject

@AndroidEntryPoint
internal class MainActivity : AppCompatActivity(R.layout.activity_main) {

    @Inject
    lateinit var textToSpeech: TextToSpeech
}
