/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appmemory.data.datasource.remote

import androidx.annotation.Keep

@Keep
internal data class NetworkGapFillingQuestion(
    val uid: String? = null,
    val correctAnswerUid: String? = null,
    val content: String? = null,
    val materialUidList: List<String>? = null,
    val contentMaterialUid: String? = null
)
