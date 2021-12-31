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
import com.softsolution.goseek.databinding.FragmentAppliedBinding
import com.softsolution.goseek.model.jobPosterModel.PostedData
import com.softsolution.goseek.model.jobSeekerModel.DashbordData
import com.softsolution.goseek.network.NetworkClass
import com.softsolution.goseek.network.Response
import com.softsolution.goseek.network.URLApi
import org.json.JSONObject
import java.util.ArrayList

class AppliedFragment : Fragment() {
    private var binding: FragmentAppliedBinding? = null
    private var dashbordList: ArrayList<PostedData>?=null
    private var layoutManager: RecyclerView.LayoutManager?=null
    private var adapter: DashbordAdapter?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_applied, container, false)
        binding!!.fragment = this

        dashbordList= ArrayList<PostedData>()
        layoutManager= LinearLayoutManager(requireActivity())
        adapter= DashbordAdapter(dashbordList!!,requireActivity())

        binding!!.recyclerView.layoutManager=layoutManager
        binding!!.recyclerView.adapter=adapter

        loadData()

        return binding!!.root
    }
    private fun loadData() {
        for (i in 0..16){
            val dashbordData= PostedData()
            dashbordData.JobTitle="Catering Hospitality"
            dashbordData.JobDescription="Waiter Help Wanted"
            dashbordData.Location="Wandsworth, Uk"
            dashbordData.CreatedDate="10 Jun 2021"
            dashbordData.Wages="$3"
            dashbordList!!.add(dashbordData)
        }
        adapter!!.notifyDataSetChanged()
    }

    private fun appliedJobs(memberId: String , status : Int , page : Int){
        binding?.refreshView?.isRefreshing = true
        NetworkClass.callApi(URLApi.companyJobList(memberId, status, page),object : Response {
            override fun onSuccessResponse(response: String?, message: String) {
                val json = JSONObject(response ?: "")
                binding?.refreshView?.isRefreshing = false
            }

            override fun onErrorResponse(error: String?, response: String?) {
                Toast.makeText(requireContext(), error ?: "", Toast.LENGTH_SHORT).show()
                binding?.refreshView?.isRefreshing = false
            }

        })
    }
}