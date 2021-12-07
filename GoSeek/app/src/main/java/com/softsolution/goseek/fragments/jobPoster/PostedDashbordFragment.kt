package com.softsolution.goseek.fragments.jobPoster

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softsolution.goseek.R
import com.softsolution.goseek.adapter.jobPosterAdapter.PostedAdapter
import com.softsolution.goseek.adapter.jobPosterAdapter.PostedDashbordAdapter
import com.softsolution.goseek.databinding.FragmentPostedDashbordBinding
import com.softsolution.goseek.model.jobPosterModel.PostedDashbordData
import com.softsolution.goseek.model.jobPosterModel.PostedData
import java.util.ArrayList

class PostedDashbordFragment : Fragment() {
    private var binding: FragmentPostedDashbordBinding? = null
    private var dashbordList: ArrayList<PostedData>?=null
    private var layoutManager: RecyclerView.LayoutManager?=null
    private var adapter: PostedAdapter?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_posted_dashbord, container, false)
        binding!!.setFragment(this)

        dashbordList= ArrayList<PostedData>()
        layoutManager= LinearLayoutManager(requireActivity())
        adapter= PostedAdapter(dashbordList!!,requireActivity())

        binding!!.recyclerView.layoutManager=layoutManager
        binding!!.recyclerView.adapter=adapter

        loadData()

        return binding!!.getRoot()

    }

    private fun loadData() {
        dashbordList!!.add(PostedData("10 Jun 2021","Waiter Help Wanted","Pause"))
        dashbordList!!.add(PostedData("10 Jun 2021","Waiter Help Wanted","Active"))
        dashbordList!!.add(PostedData("10 Jun 2021","Waiter Help Wanted","Expired"))
        dashbordList!!.add(PostedData("10 Jun 2021","Waiter Help Wanted","Pause"))
        dashbordList!!.add(PostedData("10 Jun 2021","Waiter Help Wanted","Active"))
        dashbordList!!.add(PostedData("10 Jun 2021","Waiter Help Wanted","Expired"))
        dashbordList!!.add(PostedData("10 Jun 2021","Waiter Help Wanted","Pause"))
        dashbordList!!.add(PostedData("10 Jun 2021","Waiter Help Wanted","Active"))
        dashbordList!!.add(PostedData("10 Jun 2021","Waiter Help Wanted","Expired"))
       /* for (i in 0..16){
            val dashbordData= PostedData()
            dashbordData.designation_desc="Waiter Help Wanted"
            dashbordData.time="10 Jun 2021"
            dashbordList!!.add(dashbordData)
        }
        adapter!!.notifyDataSetChanged()*/
    }

}