/*
 * RAM (c) by Sertan Canpolat
 *
 * RAM is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 *
 * You should have received a copy of the license along with this work.
 * If not, see <http://creativecommons.org/licenses/by-nc/4.0/>.
 */

package dev.sertan.android.ram.core.ui.util

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun <T> Fragment.getNavResult(key: String, callback: (T) -> Unit) {
    findNavController().currentBackStackEntry?.savedStateHandle?.run {
        callback(get<T>(key) ?: return)
        remove<T>(key)
    }
}

fun <T> Fragment.setNavResult(key: String, data: T) {
    findNavController().previousBackStackEntry?.savedStateHandle?.run { set<T>(key, data) }
}
