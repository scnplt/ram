/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmovements

import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.core.ui.fragment.texttospeechprovider.TextToSpeechProviderFragment

@AndroidEntryPoint
internal class HomeFragment : TextToSpeechProviderFragment(R.layout.fragment_home) {
    override fun onTextToSpeechStateChanged(isActive: Boolean) {}
}
