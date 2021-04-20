package co.id.kedai.kedaiapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import co.id.kedai.kedaiapp.adapter.ViewPagerAdapter
import co.id.kedai.kedaiapp.databinding.FragmentBerandaBinding
import com.google.android.material.tabs.TabLayoutMediator

class BerandaFragment : Fragment() {
    private var _binding: FragmentBerandaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBerandaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ViewPagerAdapter(activity!!.supportFragmentManager, activity!!.lifecycle)

        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "TERKINI"
                1 -> tab.text = "PROGRAM"
                2 -> tab.text = "JARINGAN"
                3 -> tab.text = "MULTIMEDIA"
                4 -> tab.text = "SYSADMIN"
                5 -> tab.text = "HARDWARE"
            }
        }.attach()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}