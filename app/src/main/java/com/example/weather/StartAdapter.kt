package com.example.weather

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.weather.fragments.StartFragment

class StartAdapter(
    fragment: FragmentActivity,
    private val startViewList: MutableList<StartViewData>,
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return startViewList.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = StartFragment()
        fragment.arguments = bundleOf("start" to startViewList[position])
        return fragment
    }


}
