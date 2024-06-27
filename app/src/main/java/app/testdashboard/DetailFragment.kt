package app.testdashboard

import android.content.Context
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import app.testdashboard.databinding.FragmentDetailBinding

class DetailFragment : Fragment(), OnContainerClick {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var parentActivity: MainActivity

    private val vpList = ArrayList<DataModel.HomeViewPager>()

    private val vpAdapter by lazy { DetailViewPagerAdapter(requireActivity(), vpList,this) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) parentActivity = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)

        binding.detailAuto.setOnClickListener {
            applySelected(binding.detailAuto,binding.detailAway,binding.detailHome)
        }

        binding.detailAway.setOnClickListener {
            applySelected(binding.detailAway,binding.detailAuto,binding.detailHome)
        }

        binding.detailHome.setOnClickListener {
            applySelected(binding.detailHome,binding.detailAuto,binding.detailAway)
        }

        binding.detailBack.setOnClickListener {
            parentActivity.changeFragment(HomeFragment())
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        applySelected(binding.detailHome,binding.detailAuto,binding.detailAway)

        binding.detailVp.adapter = vpAdapter

        vpList.add(DataModel.HomeViewPager("First Floor", true))
        vpList.add(DataModel.HomeViewPager("Second Floor", false))
        vpList.add(DataModel.HomeViewPager("Third Floor", true))
        vpList.add(DataModel.HomeViewPager("Fourth Floor", false))

        vpAdapter.notifyDataSetChanged()

        binding.detailVp.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (vpList[position].isPowered && vpAdapter != null) vpAdapter.triggerRippleEffect(position)
            }
        })
    }


    private fun applySelected(selected: TextView, tv1: TextView, tv2: TextView) {
        selected.isSelected = true
        tv2.isSelected = false
        tv1.isSelected = false
        selected.setTextColor(requireContext().getColor(R.color.main_green))
        tv1.setTextColor(requireContext().getColor(R.color.main_white))
        tv2.setTextColor(requireContext().getColor(R.color.main_white))
    }


}