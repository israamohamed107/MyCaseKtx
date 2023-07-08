package com.example.mycasektx.ui.requests

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.mycasektx.R
import com.example.mycasektx.databinding.FragmentAllRequestsBinding
import com.example.mycasektx.ui.USER_PHONE
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllRequestsFragment : Fragment() {


    private lateinit var binding: FragmentAllRequestsBinding
    private lateinit var pagerAdapter: PagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllRequestsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPager()

        binding.btnNewRequest.setOnClickListener{
            findNavController().navigate(AllRequestsFragmentDirections.actionAllRequestsFragmentToNewRequestFragment())
        }

    }



    private fun initPager() {
        pagerAdapter = PagerAdapter(childFragmentManager, lifecycle)
        binding.apply {
            tabLayout.addTab(tabLayout.newTab().setText("المرسلة"))
            tabLayout.addTab(tabLayout.newTab().setText("قيد المراجعة"))
            tabLayout.addTab(tabLayout.newTab().setText("المنتهية"))

            pager.adapter = pagerAdapter

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab != null) {
                        pager.currentItem = tab.position
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

            })

            pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    tabLayout.selectTab(tabLayout.getTabAt(position))
                }
            })
        }


    }


}