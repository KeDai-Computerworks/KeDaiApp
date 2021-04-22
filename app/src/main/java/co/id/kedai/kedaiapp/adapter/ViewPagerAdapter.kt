package co.id.kedai.kedaiapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import co.id.kedai.kedaiapp.fragment.BlogFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 6

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BlogFragment("all")
            1 -> BlogFragment("program")
            2 -> BlogFragment("network")
            3 -> BlogFragment("multimedia")
            4 -> BlogFragment("sysadmin")
            5 -> BlogFragment("hardware")
            else -> Fragment()
        }
    }
}