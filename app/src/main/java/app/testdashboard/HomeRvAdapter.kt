package app.testdashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * @author : Lee Jae Young
 * @since : 2023-03-28 오전 11:52
 **/
class HomeRvAdapter(private val context: Context, list: ArrayList<DataModel.Home>) :
    RecyclerView.Adapter<HomeRvAdapter.ViewHolder>() {
    private val mList = list

    private lateinit var onClickListener: OnAdapterItemSingleClick

    fun setOnItemClickListener(listener: OnAdapterItemSingleClick) {
        this.onClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeRvAdapter.ViewHolder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view: View = inflater.inflate(R.layout.list_item_home_rv, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val valueTv: TextView = itemView.findViewById(R.id.listItemHomeRvValue)
        private val unitTv: TextView = itemView.findViewById(R.id.listItemHomeRvUnit)
        private val titleTv: TextView = itemView.findViewById(R.id.listItemHomeRvTitle)
        private val subTitleTv: TextView = itemView.findViewById(R.id.listItemHomeRvSubTitle)
        private val icon: ImageView = itemView.findViewById(R.id.listItemHomeRvIcon)

        fun bind(dao: DataModel.Home) {
            valueTv.text = dao.value.toString()
            unitTv.text = dao.unit
            titleTv.text = dao.title
            subTitleTv.text = dao.subTitle
            dao.icon?.let { icon.setImageDrawable(it) }

            val position = adapterPosition

            itemView.setOnClickListener {
                if (position != RecyclerView.NO_POSITION) onClickListener.onItemClick(it, position)
            }
        }
    }
}