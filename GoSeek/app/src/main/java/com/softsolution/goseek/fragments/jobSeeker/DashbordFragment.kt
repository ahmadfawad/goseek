package com.softsolution.goseek.fragments.jobSeeker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softsolution.goseek.R
import com.softsolution.goseek.adapter.jobSeekerAdapter.DashbordAdapter
import com.softsolution.goseek.base.BaseFragment
import com.softsolution.goseek.base.generateList
import com.softsolution.goseek.databinding.FragmentDashbordBinding
import com.softsolution.goseek.model.jobPosterModel.PostedData
import com.softsolution.goseek.model.jobSeekerModel.DashbordData
import com.softsolution.goseek.network.NetworkClass
import com.softsolution.goseek.network.Response
import com.softsolution.goseek.network.URLApi
import java.util.ArrayList


class DashbordFragment : BaseFragment() {
    private var binding: FragmentDashbordBinding? = null
    private var dashbordList: ArrayList<DashbordData>?=null
    private var layoutManager: RecyclerView.LayoutManager?=null
    private var adapter: DashbordAdapter?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashbord, container, false)
        binding!!.fragment = this

        dashbordList= ArrayList<DashbordData>()
        layoutManager= LinearLayoutManager(requireActivity())
        adapter= DashbordAdapter(dashbordList!!,requireActivity())

        binding!!.recyclerView.layoutManager=layoutManager
        binding!!.recyclerView.adapter=adapter

        loadData()

        return binding!!.root

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

    private fun jobListing(memberId: String, status: Int, page: Int) {
        showLoading()
        NetworkClass.callApi(URLApi.companyJobList(memberId, status, page), object : Response {
            override fun onSuccessResponse(response: String?, message: String) {
                hideLoading()
                val data = generateList(response.toString(), Array<PostedData>::class.java)
                dashbordList?.clear()
//                dashbordList?.addAll(data)
                adapter?.notifyDataSetChanged()
            }

            override fun onErrorResponse(error: String?, response: String?) {
                hideLoading()
                Toast.makeText(mActivity, error ?: "", Toast.LENGTH_SHORT).show()
            }

        })
    }



}