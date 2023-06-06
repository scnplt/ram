/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.appnumber.data.datasource.remote

import androidx.annotation.Keep

@Keep
internal data class NetworkSection(
    val uid: String? = null,
    val minNumber: Int? = null,
    val maxNumber: Int? = null,
    val step: Int? = null
)
