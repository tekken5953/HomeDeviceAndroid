package app.testdashboard

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import app.testdashboard.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var parentActivity: MainActivity

    private val rvList = ArrayList<DataModel.Home>()
    private val rvAdapter by lazy { HomeRvAdapter(requireContext(), rvList)}

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) parentActivity = context
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homeRv.adapter = rvAdapter

        binding.homeYear.setOnClickListener {
            binding.homeMonth.isSelected = false
            binding.homeYear.isSelected = true
            binding.homeYear.setTextColor(requireContext().getColor(R.color.main_blue))
            binding.homeMonth.setTextColor(requireContext().getColor(R.color.main_white))
        }

        binding.homeMonth.setOnClickListener {
            binding.homeYear.isSelected = false
            binding.homeMonth.isSelected = true
            binding.homeYear.setTextColor(requireContext().getColor(R.color.main_white))
            binding.homeMonth.setTextColor(requireContext().getColor(R.color.main_blue))
        }

        rvAdapter.setOnItemClickListener(object : OnAdapterItemSingleClick() {
            override fun onSingleClick(v: View?, position: Int) {
                (activity as? MainActivity)?.changeFragment(DetailFragment())
            }
        })

        binding.homeMonth.isSelected = true
        binding.homeMonth.setTextColor(requireContext().getColor(R.color.main_blue))
        applySpan(binding.homeSubTitle, "David!")

        rvList.add(DataModel.Home(1345, "KWH", "Thermostat", "Heating and Ventilation",getR(R.drawable.speed)))
        rvList.add(DataModel.Home(23, "KWH", "Electricity", "12 outlets points",getR(R.drawable.speed2)))
        rvList.add(DataModel.Home(23, "KWH", "Security", "16 cameras",getR(R.drawable.cam)))
        rvList.add(DataModel.Home(980, "KWH", "CO2 Sensor", "Ventilation",getR(R.drawable.flug)))

        rvAdapter.notifyDataSetChanged()
    }

    private fun applySpan(tv: TextView, s: String) {
        kotlin.runCatching {
            val span = SpannableStringBuilder(tv.text.toString())
            val containsIndex = tv.text.toString().indexOf(s)
            if (containsIndex != -1)
                span.setSpan(ForegroundColorSpan(requireContext().getColor(R.color.main_blue)),
                    containsIndex, containsIndex + s.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            tv.text = span
        }.exceptionOrNull()?.stackTraceToString()
    }

    private fun getR(id: Int): Drawable? = ResourcesCompat.getDrawable(resources,id,null)
}