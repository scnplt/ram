/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

@file:Suppress("DEPRECATION")

package dev.sertan.android.ram.appmovements.posedetection

import android.graphics.ImageFormat
import android.hardware.Camera
import android.hardware.Camera.CameraInfo.CAMERA_FACING_BACK
import android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.View
import androidx.fragment.app.Fragment
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.PoseLandmark
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.android.ram.appmovements.R
import dev.sertan.android.ram.appmovements.databinding.FragmentPoseDetectionBinding
import dev.sertan.android.ram.core.ui.util.viewBinding
import java.util.concurrent.Executors

@AndroidEntryPoint
internal class PoseDetectionFragment :
    Fragment(R.layout.fragment_pose_detection),
    SurfaceHolder.Callback,
    Camera.PreviewCallback {

    private val binding by viewBinding(FragmentPoseDetectionBinding::bind)

    private lateinit var camera: Camera
    private var isFrontCameraActive = true
    private var isDetectionActive = false

    private val poseExecutor = Executors.newSingleThreadExecutor()

    private val poseDetector by lazy {
        val options = PoseDetectorOptions.Builder()
            .setDetectorMode(PoseDetectorOptions.STREAM_MODE)
            .setExecutor(poseExecutor)
            .build()

        PoseDetection.getClient(options)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpComponents()
    }

    private fun setUpComponents() = with(binding) {
        cameraView.holder.addCallback(this@PoseDetectionFragment)
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

    private fun setUpCamera() {
        val cameraMode = if (isFrontCameraActive) CAMERA_FACING_FRONT else CAMERA_FACING_BACK
        camera = Camera.open(cameraMode)
        camera.setDisplayOrientation(90)
        camera.setPreviewDisplay(binding.cameraView.holder)
        camera.setPreviewCallback(this)
        camera.parameters.previewFormat = ImageFormat.NV21
        camera.parameters = camera.parameters.apply {
            val supportedSize = supportedPreviewSizes.getOrNull(2) ?: return
            setPreviewSize(supportedSize.width, supportedSize.height)
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        setUpCamera()
        camera.startPreview()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        camera.stopPreview()
        camera.release()
    }

    @Deprecated("Deprecated in Java")
    override fun onPreviewFrame(data: ByteArray?, camera: Camera?) {
        if (!isDetectionActive || data == null || camera == null) return

        val image = InputImage.fromByteArray(
            data,
            camera.parameters.previewSize.width, camera.parameters.previewSize.height, 180,
            ImageFormat.NV21
        )

        poseDetector.process(image).addOnSuccessListener { pose ->
            binding.landmarkGraphView.clear()
            getLandmarkPairs(pose).forEach { (start, stop) ->
                binding.landmarkGraphView.addLine(start, stop)
            }
        }
    }

    private fun getLandmarkPairs(pose: Pose): List<Pair<PoseLandmark?, PoseLandmark?>> {
        val nose = pose.getPoseLandmark(PoseLandmark.NOSE)
        val leftEyeInner = pose.getPoseLandmark(PoseLandmark.LEFT_EYE_INNER)
        val leftEye = pose.getPoseLandmark(PoseLandmark.LEFT_EYE)
        val leftEyeOuter = pose.getPoseLandmark(PoseLandmark.LEFT_EYE_OUTER)
        val rightEyeInner = pose.getPoseLandmark(PoseLandmark.RIGHT_EYE_INNER)
        val rightEye = pose.getPoseLandmark(PoseLandmark.RIGHT_EYE)
        val rightEyeOuter = pose.getPoseLandmark(PoseLandmark.RIGHT_EYE_OUTER)
        val leftEar = pose.getPoseLandmark(PoseLandmark.LEFT_EAR)
        val rightEar = pose.getPoseLandmark(PoseLandmark.RIGHT_EAR)
        val leftMouth = pose.getPoseLandmark(PoseLandmark.LEFT_MOUTH)
        val rightMouth = pose.getPoseLandmark(PoseLandmark.RIGHT_MOUTH)
        val leftShoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER)
        val rightShoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER)
        val leftElbow = pose.getPoseLandmark(PoseLandmark.LEFT_ELBOW)
        val rightElbow = pose.getPoseLandmark(PoseLandmark.RIGHT_ELBOW)
        val leftWrist = pose.getPoseLandmark(PoseLandmark.LEFT_WRIST)
        val rightWrist = pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST)
        val leftPinky = pose.getPoseLandmark(PoseLandmark.LEFT_PINKY)
        val rightPinky = pose.getPoseLandmark(PoseLandmark.RIGHT_PINKY)
        val leftIndex = pose.getPoseLandmark(PoseLandmark.LEFT_INDEX)
        val rightIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_INDEX)
        val leftThumb = pose.getPoseLandmark(PoseLandmark.LEFT_THUMB)
        val rightThumb = pose.getPoseLandmark(PoseLandmark.RIGHT_THUMB)
        val leftHip = pose.getPoseLandmark(PoseLandmark.LEFT_HIP)
        val rightHip = pose.getPoseLandmark(PoseLandmark.RIGHT_HIP)
        val leftKnee = pose.getPoseLandmark(PoseLandmark.LEFT_KNEE)
        val rightKnee = pose.getPoseLandmark(PoseLandmark.RIGHT_KNEE)
        val leftAnkle = pose.getPoseLandmark(PoseLandmark.LEFT_ANKLE)
        val rightAnkle = pose.getPoseLandmark(PoseLandmark.RIGHT_ANKLE)
        val leftHeel = pose.getPoseLandmark(PoseLandmark.LEFT_HEEL)
        val rightHeel = pose.getPoseLandmark(PoseLandmark.RIGHT_HEEL)
        val leftFootIndex = pose.getPoseLandmark(PoseLandmark.LEFT_FOOT_INDEX)
        val rightFootIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_FOOT_INDEX)

        return listOf(
            nose to leftEyeInner,
            leftEyeInner to leftEyeOuter,
            leftEye to leftEyeInner,
            leftEyeOuter to leftEar,
            nose to rightEyeInner,
            rightEyeInner to rightEyeOuter,
            rightEye to rightEyeInner,
            rightEyeOuter to rightEar,
            leftMouth to rightMouth,
            leftShoulder to rightShoulder,
            leftShoulder to leftElbow,
            leftElbow to leftWrist,
            leftWrist to leftPinky,
            leftWrist to leftIndex,
            leftWrist to leftThumb,
            leftIndex to leftPinky,
            rightShoulder to rightElbow,
            rightElbow to rightWrist,
            rightWrist to rightPinky,
            rightWrist to rightIndex,
            rightWrist to rightThumb,
            rightIndex to rightPinky,
            leftShoulder to leftHip,
            leftHip to leftKnee,
            leftKnee to leftAnkle,
            leftAnkle to leftHeel,
            leftHeel to leftFootIndex,
            leftAnkle to leftFootIndex,
            rightShoulder to rightHip,
            rightHip to rightKnee,
            rightKnee to rightAnkle,
            rightAnkle to rightHeel,
            rightHeel to rightFootIndex,
            rightAnkle to rightFootIndex,
        )
    }
}


