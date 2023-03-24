/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmovements.posedetection

import android.graphics.SurfaceTexture
import android.hardware.Camera
import android.hardware.Camera.CameraInfo.CAMERA_FACING_BACK
import android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT
import android.os.Bundle
import android.util.Log
import android.view.TextureView
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appmovements.R
import dev.sertan.android.ram.appmovements.databinding.FragmentPoseDetectionBinding
import dev.sertan.android.ram.core.ui.util.viewBinding
import java.util.concurrent.Executors

@AndroidEntryPoint
internal class PoseDetectionFragment :
    Fragment(R.layout.fragment_pose_detection),
    TextureView.SurfaceTextureListener {

    private val binding by viewBinding(FragmentPoseDetectionBinding::bind)
    private val viewModel by viewModels<PoseDetectionViewModel>()

    private lateinit var camera: Camera
    private var isFrontCameraActive = true
    private var isDetectionActive = false

    private val poseExecutor = Executors.newSingleThreadExecutor()

    private val poseDetector by lazy {
        val options = PoseDetectorOptions.Builder()
            .setDetectorMode(PoseDetectorOptions.STREAM_MODE)
            .build()

        PoseDetection.getClient(options)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpComponents()
    }

    private fun setUpComponents() = with(binding) {
        cameraTextureView.surfaceTextureListener = this@PoseDetectionFragment
        changeCamera.setOnClickListener {
            isFrontCameraActive = !isFrontCameraActive
            setUpCamera()
        }
        startStopDetection.setOnClickListener {
            if (isDetectionActive) {
                startStopDetection.text = "Stop Detection"
                isDetectionActive = false
                landmarkGraphView.clear()
            } else {
                startStopDetection.text = "Start Detection"
                isDetectionActive = true
            }
        }
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
        setUpCamera()
        camera.startPreview()
    }

    private fun setUpCamera() {
        val cameraMode = if (isFrontCameraActive) CAMERA_FACING_FRONT else CAMERA_FACING_BACK
        camera = Camera.open(cameraMode)
        camera.setDisplayOrientation(90)
        camera.setPreviewTexture(binding.cameraTextureView.surfaceTexture)
        camera.parameters = camera.parameters.apply {
            val supportedSize = supportedPreviewSizes.getOrNull(2) ?: return
            setPreviewSize(supportedSize.width, supportedSize.height)
        }
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {}

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
        camera.stopPreview()
        camera.release()
        return true
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
        if (!isDetectionActive) return
        val image = InputImage.fromBitmap(binding.cameraTextureView.bitmap ?: return, 0)
        Log.d("RAM_LOG_TAG", "onSurfaceTextureUpdated: ${Thread.currentThread().name}}")
        poseDetector.process(image).continueWith(poseExecutor) { task ->
            val pose = task.result ?: return@continueWith
            viewModel.processPoseWithPairs(pose) { posePairs ->
                binding.landmarkGraphView.clear()
                posePairs.forEach { (start, stop) ->
                    binding.landmarkGraphView.addLine(start, stop)
                }
            }
        }
    }
}


