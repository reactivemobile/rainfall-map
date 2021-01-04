package com.reactivemobile.rainfall.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commitNow
import com.reactivemobile.rainfall.R
import com.reactivemobile.rainfall.presentation.ui.map.StationsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RainfallActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rainfall)

        val fragment = StationsFragment.newInstance()
        supportFragmentManager.commitNow {
            replace(
                R.id.rootView,
                fragment,
                StationsFragment.TAG
            )
        }
    }
}