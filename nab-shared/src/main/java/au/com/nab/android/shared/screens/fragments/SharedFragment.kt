package au.com.nab.android.shared.screens.fragments

import android.os.Bundle
import android.view.View
import au.com.nab.android.shared.screens.common.UIBehaviour
import dagger.android.support.DaggerFragment
import org.jetbrains.anko.support.v4.longToast

open class SharedFragment : DaggerFragment(), UIBehaviour {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onSyncViews(savedInstanceState)
        onSyncEvents()
        onSyncData()
    }

    override fun onSyncViews(savedInstanceState: Bundle?) {

    }

    override fun onSyncEvents() {

    }

    override fun onSyncData() {

    }

    open fun stopSelf() {

    }

    override fun showLoading(isLoading: Boolean) {

    }

    override fun showError(message: String?) {
        longToast(message ?: "")
    }

    open fun showLoading() = showLoading(true)

    open fun dismissLoading() = showLoading(false)

    override fun onDestroyView() {
        dismissLoading()
        super.onDestroyView()
    }
}
