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
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.PoseLandmark
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appmovements.R
import dev.sertan.android.ram.appmovements.databinding.FragmentPoseDetectionBinding
import dev.sertan.android.ram.core.ui.util.viewBinding

@AndroidEntryPoint
internal class PoseDetectionFragment :
    Fragment(R.layout.fragment_pose_detection),
    TextureView.SurfaceTextureListener {

    private val binding by viewBinding(FragmentPoseDetectionBinding::bind)

    private lateinit var camera: Camera
    private var isFrontCameraActive = true
    private var isDetectionActive = false

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

        poseDetector.process(image)
            .addOnSuccessListener { pose ->
                binding.landmarkGraphView.clear()
                val nose = pose.getPoseLandmark(PoseLandmark.NOSE)
                val leftEyeInner = pose.getPoseLandmark(PoseLandmark.LEFT_EYE_INNER)
                val leftEye = pose.getPoseLandmark(PoseLandmark.LEFT_EYE)
                val rightEyeInner = pose.getPoseLandmark(PoseLandmark.RIGHT_EYE_INNER)
                val rightEye = pose.getPoseLandmark(PoseLandmark.RIGHT_EYE)
                val leftMouth = pose.getPoseLandmark(PoseLandmark.LEFT_MOUTH)
                val rightMouth = pose.getPoseLandmark(PoseLandmark.RIGHT_MOUTH)
                val leftShoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER)
                val rightShoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER)
                val leftElbow = pose.getPoseLandmark(PoseLandmark.LEFT_ELBOW)
                val rightElbow = pose.getPoseLandmark(PoseLandmark.RIGHT_ELBOW)
                val leftWrist = pose.getPoseLandmark(PoseLandmark.LEFT_WRIST)
                val rightWrist = pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST)
/*                val leftPinky = pose.getPoseLandmark(PoseLandmark.LEFT_PINKY)
                val rightPinky = pose.getPoseLandmark(PoseLandmark.RIGHT_PINKY)
                val leftIndex = pose.getPoseLandmark(PoseLandmark.LEFT_INDEX)
                val rightIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_INDEX)
                val leftThumb = pose.getPoseLandmark(PoseLandmark.LEFT_THUMB)
                val rightThumb = pose.getPoseLandmark(PoseLandmark.RIGHT_THUMB)*/
                val leftHip = pose.getPoseLandmark(PoseLandmark.LEFT_HIP)
                val rightHip = pose.getPoseLandmark(PoseLandmark.RIGHT_HIP)
/*                val leftKnee = pose.getPoseLandmark(PoseLandmark.LEFT_KNEE)
                val rightKnee = pose.getPoseLandmark(PoseLandmark.RIGHT_KNEE)
                val leftAnkle = pose.getPoseLandmark(PoseLandmark.LEFT_ANKLE)
                val rightAnkle = pose.getPoseLandmark(PoseLandmark.RIGHT_ANKLE)
                val leftHeel = pose.getPoseLandmark(PoseLandmark.LEFT_HEEL)
                val rightHeel = pose.getPoseLandmark(PoseLandmark.RIGHT_HEEL)
                val leftFootIndex = pose.getPoseLandmark(PoseLandmark.LEFT_FOOT_INDEX)
                val rightFootIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_FOOT_INDEX)*/

                with(binding.landmarkGraphView) {
                    addLine(leftEyeInner, nose)
                    addLine(leftEye, leftEyeInner)
                    addLine(rightEyeInner, nose)
                    addLine(rightEye, rightEyeInner)
                    addLine(leftMouth, rightMouth)
                    addLine(leftShoulder, rightShoulder)
                    addLine(rightShoulder, rightElbow)
                    addLine(leftShoulder, leftElbow)
                    addLine(rightElbow, rightWrist)
                    addLine(leftElbow, leftWrist)
                    addLine(rightShoulder, rightHip)
                    addLine(leftShoulder, leftHip)
                    addLine(leftHip, rightHip)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("RAM_LOG_TAG", "Pose detection failed: ${exception.message}")
            }
    }
}


