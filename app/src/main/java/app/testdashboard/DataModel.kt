package app.testdashboard

import android.graphics.drawable.Drawable

class DataModel {
    data class Home(
        val value: Int,
        val unit: String,
        val title: String,
        val subTitle: String,
        val icon: Drawable?
    )

    data class HomeViewPager(
        val title: String,
        val isPowered: Boolean
    )
}