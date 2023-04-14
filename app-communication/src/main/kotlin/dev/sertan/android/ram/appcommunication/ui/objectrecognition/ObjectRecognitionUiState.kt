/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appcommunication.ui.objectrecognition

import dev.sertan.android.ram.feature.material.ui.Material

internal data class ObjectRecognitionUiState(
    val material: Material?,
    val isEmptyListMessageVisible: Boolean,
    val isMicButtonVisible: Boolean,
    val isFinished: Boolean
) {

    companion object {

        fun initialState() = ObjectRecognitionUiState(
            material = null,
            isEmptyListMessageVisible = false,
            isMicButtonVisible = false,
            isFinished = false
        )

        fun getState(materials: List<Material>, index: Int) = ObjectRecognitionUiState(
            material = materials.getOrNull(index),
            isEmptyListMessageVisible = materials.isEmpty(),
            isMicButtonVisible = materials.isNotEmpty(),
            isFinished = index >= materials.size
        )
    }
}
