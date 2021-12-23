package com.softsolution.goseek.fragments.jobPoster

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softsolution.goseek.R
import com.softsolution.goseek.adapter.jobPosterAdapter.PostedAdapter
import com.softsolution.goseek.base.BaseFragment
import com.softsolution.goseek.base.generateList
import com.softsolution.goseek.databinding.FragmentPostedDashbordBinding
import com.softsolution.goseek.model.jobPosterModel.PostedData
import com.softsolution.goseek.network.LocalPreference
import com.softsolution.goseek.network.NetworkClass
import com.softsolution.goseek.network.Response
import com.softsolution.goseek.network.URLApi
import java.util.*

class PostedDashbordFragment : BaseFragment() {
    private var binding: FragmentPostedDashbordBinding? = null
    private var dashbordList: ArrayList<PostedData>? = null
    private var adapter: PostedAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_posted_dashbord, container, false)
        binding!!.fragment = this

        dashbordList = ArrayList<PostedData>()
        adapter = PostedAdapter(dashbordList!!, mActivity)
        binding?.recyclerView?.layoutManager =
            LinearLayoutManager(mActivity, RecyclerView.VERTICAL, false)
        binding?.recyclerView?.adapter = adapter

        jobListing(
            LocalPreference.shared.user?.MemberId.toString(),
            LocalPreference.shared.user?.status ?: 0,
            0
        )

        return binding!!.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.refreshView?.setOnRefreshListener {
            jobListing(
                LocalPreference.shared.user?.MemberId.toString(),
                LocalPreference.shared.user?.status ?: 0,
                0
            )
        }
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
                hideLoading()
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
