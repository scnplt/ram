/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcommunication.ui.bodyparts

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appcommunication.R
import dev.sertan.android.ram.appcommunication.databinding.FragmentBodyPartsBinding
import dev.sertan.android.ram.core.ui.util.extension.viewBinding

@AndroidEntryPoint
internal class BodyPartsFragment : Fragment(R.layout.fragment_body_parts) {

    private val binding by viewBinding(FragmentBodyPartsBinding::bind)

/*    private val steps = mapOf(
        R.drawable.full_body to listOf(
            ,
            BodyPart("Sol eli göster", 0.255f, 0.44f, 0.54f, 0.5f),
            BodyPart("Sağ eli göster", 0.82f, 0.4f, 0.975f, 0.47f),
            BodyPart("Sol ayağı göster", 0.3f, 0.93f, 0.53f, 1f),
            BodyPart("Sağ ayağı göster", 0.62f, 0.92f, 0.955f, 0.98f),
        ),
    )*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bodyImageView.setPart(BodyPart("Kafayı göster", 0.4785f, 0.044f, 0.7689f, 0.1385f))

/*
        steps.forEach { (resourceId, parts) ->
            //binding.bodyImageView.setImageResource(resourceId)
            parts.forEach { part -> binding.bodyImageView.insertPart(part) }
        }*/

    }

}
