package com.usfaa.cvbuilder.ui.base

import android.view.View
import androidx.fragment.app.Fragment
import com.usfaa.cvbuilder.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {
    
    override fun onResume() {
        super.onResume()
        activity.takeIf { it is MainActivity }?.let {
            (it as MainActivity).run {
                setBottomNavigationVisibility(
                    if (shouldShowBottomNavigation()) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
                )
            }
        }
    }

    protected abstract fun shouldShowBottomNavigation(): Boolean
}
