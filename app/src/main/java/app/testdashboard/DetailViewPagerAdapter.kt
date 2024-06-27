package app.testdashboard

import android.app.Activity
import android.content.Context
import android.graphics.drawable.RippleDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetailViewPagerAdapter(
    private val context: Activity,
    list: ArrayList<DataModel.HomeViewPager>,
    private val clickListener: OnContainerClick) :
    RecyclerView.Adapter<DetailViewPagerAdapter.ViewHolder>() {
    private val mList = list
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int): DetailViewPagerAdapter.ViewHolder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view: View = inflater.inflate(R.layout.detail_vp_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])

        holder.container.setOnClickListener {
            clickListener.onContainerClick(recyclerView as View, position)
        }
    }

    private var recyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = null
    }

    fun triggerRippleEffect(position: Int) {
        val viewHolder = recyclerView?.findViewHolderForAdapterPosition(position) as? ViewHolder
        viewHolder?.let {
            val background = it.container.background
            if (background is RippleDrawable) {
                CoroutineScope(Dispatchers.Main).launch {
                    delay(200)
                    background.state = intArrayOf(android.R.attr.state_pressed, android.R.attr.state_enabled)
                    delay(350)
                    it.container.background.state = intArrayOf()
                }
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val title: TextView = view.findViewById(R.id.detail_vp_item_text)
        private val power: ImageView = view.findViewById(R.id.detail_vp_item_running)
        val container: LinearLayout = view.findViewById(R.id.detail_vp_running_container)

        fun bind(dao: DataModel.HomeViewPager) {
            title.text = dao.title
            power.visibility = if (dao.isPowered) View.VISIBLE else View.GONE
        }
    }
}