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
import android.util.Log
import android.view.Display
import android.view.SurfaceHolder
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions
import java.util.concurrent.Executors

private const val DETECTOR_MODE = 1
private const val DEFAULT_DEGREE = 90
private const val DEFAULT_ROTATION_DEGREE = 180
private const val START_PER_FRAME = 4

class PoseDetector(display: Display, private val overlayView: OverlayView) {

    private var isDetectionActive = false

    private var camera: Camera? = null
    private var cameraWidth = 0
    private var cameraHeight = 0

    private lateinit var detector: com.google.mlkit.vision.pose.PoseDetector

    private val executor = Executors.newSingleThreadExecutor()
    private var previewCount = 0

    private val previewCallback = Camera.PreviewCallback { data, _ ->
        if (data == null || camera == null || !isDetectionActive) return@PreviewCallback
        previewCount++

        if (previewCount < START_PER_FRAME) return@PreviewCallback
        previewCount = 0

        val image = InputImage.fromByteArray(
            data,
            cameraWidth,
            cameraHeight,
            DEFAULT_ROTATION_DEGREE,
            ImageFormat.NV21
        )

        detector.process(image)
            .addOnSuccessListener(overlayView::updatePose)
            .addOnFailureListener { Log.d(TAG, "Process - exception: ${it.message}") }
    }

    private val surfaceHolderCallback = object : SurfaceHolder.Callback {

        override fun surfaceCreated(holder: SurfaceHolder) {
            camera?.run {
                setPreviewDisplay(holder)
                camera?.setPreviewCallback(previewCallback)
                startPreview()
            }
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            camera?.run {
                stopPreview()
                setPreviewDisplay(holder)
                setPreviewCallback(previewCallback)
                setDisplayOrientation(DEFAULT_DEGREE)
                setPreviewSize()
                startPreview()
            }
            overlayView.updateRatio(
                xRatio = display.width.toFloat() / cameraHeight,
                yRatio = display.height.toFloat() / cameraWidth
            )
        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {
            camera?.stopPreview()
            camera?.release()
        }
    }

    init {
        detector = PoseDetection.getClient(
            PoseDetectorOptions.Builder()
                .setDetectorMode(DETECTOR_MODE)
                .setExecutor(executor)
                .build()
        )
        camera = getCamera()
        overlayView.setHolderCallback(surfaceHolderCallback)
    }

    private fun getCamera(): Camera {
        val cameraInfo = Camera.CameraInfo()
        for (id in 0 until Camera.getNumberOfCameras()) {
            Camera.getCameraInfo(id, cameraInfo)
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) return Camera.open(id)
        }
        error("Front camera not found!")
    }

    private fun setPreviewSize() {
        val cameraParameters = camera?.parameters ?: return
        val cameraSize = cameraParameters.supportedPreviewSizes?.firstOrNull() ?: return
        cameraWidth = cameraSize.width
        cameraHeight = cameraSize.height
        cameraParameters.setPreviewSize(cameraWidth, cameraHeight)
        camera?.parameters = cameraParameters
    }

    fun start() {
        isDetectionActive = true
    }

    companion object {
        const val TAG = "PoseDetection:DebugTag"
    }
}
