package au.com.nab.android.shared.screens.activities

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import au.com.nab.android.shared.R
import au.com.nab.android.shared.common.extensions.hideKeyboardIfNeed
import au.com.nab.android.shared.screens.common.UIBehaviour
import dagger.android.support.DaggerAppCompatActivity
import org.jetbrains.anko.longToast

open class SharedActivity : DaggerAppCompatActivity(), UIBehaviour {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    override fun showLoading(isLoading: Boolean) {

    }

    open fun showLoading() = showLoading(true)

    open fun dismissLoading() = showLoading(false)

    override fun startActivity(intent: Intent?) {
        try {
            super.startActivity(intent)
            overridePendingTransitionEnter()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun startActivityForResult(intent: Intent?, requestCode: Int) {
        try {
            super.startActivityForResult(intent, requestCode)
            overridePendingTransitionEnter()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun doNotCare() {
        // Empty method
    }

    override fun showError(message: String?) {
        longToast(message ?: "")
    }

    override fun onPause() {
        super.onPause()
        hideKeyboardIfNeed()
    }

    override fun finish() {
        super.finish()
        overridePendingTransitionExit()
    }

    private fun overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.anim_slide_from_right, R.anim.anim_slide_to_left)
    }

    private fun overridePendingTransitionExit() {
        overridePendingTransition(R.anim.anim_slide_from_left, R.anim.anim_slide_to_right)
    }

    open fun allowUserDismissKeyboardWhenClickOutSide() = false

    override fun dispatchTouchEvent(motionEvent: MotionEvent?): Boolean {
        if (allowUserDismissKeyboardWhenClickOutSide())
            hideKeyboardIfNeed()
        return super.dispatchTouchEvent(motionEvent)
    }

}
