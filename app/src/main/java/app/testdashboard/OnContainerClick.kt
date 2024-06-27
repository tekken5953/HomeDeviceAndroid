package app.testdashboard

import android.view.View

interface OnContainerClick {
    fun onContainerClick(view: View, position: Int) {
        view.performClick()
    }
}