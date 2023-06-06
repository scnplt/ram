/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmemory.domain.model

internal data class GapFillingQuestionDto(
    val uid: String,
    val content: String,
    val materialUidList: List<String>,
    val correctMaterialUid: String,
    val contentMaterialUid: String
)
