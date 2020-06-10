package dev.skymansandy.base.extension

import androidx.fragment.app.Fragment

fun Fragment.isEmptyBackStack(): Boolean = childFragmentManager.backStackEntryCount == 0
