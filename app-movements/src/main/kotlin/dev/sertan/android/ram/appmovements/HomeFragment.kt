/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmovements

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appmovements.HomeFragmentDirections.Companion.actionHomeFragmentToDrawingFragment
import dev.sertan.android.ram.appmovements.HomeFragmentDirections.Companion.actionHomeFragmentToPoseDetectionFragment
import dev.sertan.android.ram.appmovements.databinding.FragmentHomeBinding
import dev.sertan.android.ram.core.ui.fragment.texttospeechprovider.TextToSpeechProviderFragment
import dev.sertan.android.ram.core.ui.util.doIfPermissionGranted
import dev.sertan.android.ram.core.ui.util.labelWithoutPrefix
import dev.sertan.android.ram.core.ui.util.navTo
import dev.sertan.android.ram.core.ui.util.navigateToOssLicenses
import dev.sertan.android.ram.core.ui.util.viewBinding

@AndroidEntryPoint
internal class HomeFragment : TextToSpeechProviderFragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val requestPermissionLauncher = registerForActivityResult(RequestPermission()) {
        if (it) {
            navTo(actionHomeFragmentToPoseDetectionFragment())
            return@registerForActivityResult
        }

        // TASK: Refactor - Use custom dialog
        Toast.makeText(requireContext(), "Camera permission denied!", Toast.LENGTH_SHORT).show()
    }

    override fun onTextToSpeechStateChanged(isActive: Boolean) {
        binding.changeVoiceSupportButton.isActivated = isActive
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            titleTextView.text = requireContext().labelWithoutPrefix
            changeVoiceSupportButton.setOnClickListener { changeTextToSpeechState() }
            aboutButton.setOnClickListener { navigateToOssLicenses() }
            startButton.setOnClickListener {
                doIfPermissionGranted(
                    resultLauncher = requestPermissionLauncher,
                    permission = android.Manifest.permission.CAMERA,
                    block = { navTo(actionHomeFragmentToPoseDetectionFragment()) }
                )
            }
            drawingButton.setOnClickListener { navTo(actionHomeFragmentToDrawingFragment()) }
        }
    }
}
