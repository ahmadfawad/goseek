package com.softsolution.goseek.fragments.jobSeeker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softsolution.goseek.R
import com.softsolution.goseek.adapter.jobSeekerAdapter.DashbordAdapter
import com.softsolution.goseek.databinding.FragmentDashbordBinding
import com.softsolution.goseek.model.jobSeekerModel.DashbordData
import java.util.ArrayList


class DashbordFragment : Fragment() {
    private var binding: FragmentDashbordBinding? = null
    private var dashbordList: ArrayList<DashbordData>?=null
    private var layoutManager: RecyclerView.LayoutManager?=null
    private var adapter: DashbordAdapter?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashbord, container, false)
        binding!!.setFragment(this)

        dashbordList= ArrayList<DashbordData>()
        layoutManager= LinearLayoutManager(requireActivity())
        adapter= DashbordAdapter(dashbordList!!,requireActivity())

        binding!!.recyclerView.layoutManager=layoutManager
        binding!!.recyclerView.adapter=adapter

        loadData()

        return binding!!.getRoot()

    }

    private fun loadData() {
        for (i in 0..16){
            val dashbordData= DashbordData()
            dashbordData.designation="Catering Hospitality"
            dashbordData.designation_desc="Waiter Help Wanted"
            dashbordData.location="Wandsworth, Uk"
            dashbordData.time="10 Jun 2021"
            dashbordData.rate="$3"
            dashbordList!!.add(dashbordData)
        }
        adapter!!.notifyDataSetChanged()
    }





}