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
import com.softsolution.goseek.network.LocalPreference
import com.softsolution.goseek.network.NetworkClass
import com.softsolution.goseek.network.Response
import com.softsolution.goseek.network.URLApi
import java.util.ArrayList


class DashbordFragment : BaseFragment() {
    private var binding: FragmentDashbordBinding? = null
    private var dashbordList: ArrayList<PostedData>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: DashbordAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashbord, container, false)
        binding!!.fragment = this

        dashbordList = ArrayList<PostedData>()
        layoutManager = LinearLayoutManager(requireActivity())
        adapter = DashbordAdapter(dashbordList!!, requireActivity())

        binding!!.recyclerView.layoutManager = layoutManager
        binding!!.recyclerView.adapter = adapter

//        loadData()
        jobListing(
            LocalPreference.shared.user?.MemberId.toString(),
            LocalPreference.shared.user?.status ?: 0,
            0
        )

        binding?.refreshView?.setOnRefreshListener {
            jobListing(
                LocalPreference.shared.user?.MemberId.toString(),
                LocalPreference.shared.user?.status ?: 0,
                0
            )
        }
        return binding!!.root

    }

    override fun onResume() {
        super.onResume()
        jobListing(
            LocalPreference.shared.user?.MemberId.toString(),
            LocalPreference.shared.user?.status ?: 0,
            0
        )
    }

    private fun jobListing(memberId: String, status: Int, page: Int) {
       binding?.refreshView?.isRefreshing = true
        NetworkClass.callApi(URLApi.companyJobList(memberId, status, page), object : Response {
            override fun onSuccessResponse(response: String?, message: String) {
                val data = generateList(response.toString(), Array<PostedData>::class.java)
                dashbordList?.clear()
                dashbordList?.addAll(data)
                adapter?.notifyDataSetChanged()
                binding?.refreshView?.isRefreshing = false
            }

            override fun onErrorResponse(error: String?, response: String?) {
                Toast.makeText(mActivity, error ?: "", Toast.LENGTH_SHORT).show()
                binding?.refreshView?.isRefreshing = false
            }

        })
    }


}