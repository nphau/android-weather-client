package au.com.nab.android.shared.screens.common

import android.os.Bundle

interface UIBehaviour {

    fun onSyncViews(savedInstanceState: Bundle?)

    fun onSyncEvents()

    fun onSyncData()

    fun doNotCare() = Unit

    fun showError(message: String?)

    fun showLoading(isLoading: Boolean = true)

}
