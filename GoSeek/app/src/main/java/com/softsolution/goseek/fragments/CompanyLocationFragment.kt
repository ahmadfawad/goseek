package com.softsolution.goseek.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softsolution.goseek.R
import com.softsolution.goseek.adapter.jobPosterAdapter.CompanyLocationAdapter
import com.softsolution.goseek.adapter.jobSeekerAdapter.DashbordAdapter
import com.softsolution.goseek.databinding.FragmentAppliedBinding
import com.softsolution.goseek.databinding.FragmentCompanyLocationBinding
import com.softsolution.goseek.model.jobPosterModel.CompanyLocation
import com.softsolution.goseek.model.jobSeekerModel.DashbordData
import java.util.ArrayList

class CompanyLocationFragment : Fragment() {
    private var binding: FragmentCompanyLocationBinding? = null
    private var dashbordList: ArrayList<CompanyLocation>?=null
    private var layoutManager: RecyclerView.LayoutManager?=null
    private var adapter: CompanyLocationAdapter?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_company_location, container, false)


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_company_location, container, false)
        binding!!.fragment = this

        dashbordList= ArrayList<CompanyLocation>()
        layoutManager= LinearLayoutManager(requireActivity())
        adapter= CompanyLocationAdapter(dashbordList!!,requireActivity())

        binding!!.recyclerView.layoutManager=layoutManager
        binding!!.recyclerView.adapter=adapter

        loadData()

        return binding!!.root

    }
    private fun loadData() {
        for (i in 0..16){
            val dashbordData= CompanyLocation()
           dashbordData.desc="Abdalian Cooperative Housing Society is a housing\n" +
                   "estate located within Lahore, Punjab, Pakistan."
            dashbordData.time="Gulberg Office"
            dashbordList!!.add(dashbordData)
        }
        adapter!!.notifyDataSetChanged()
    }

    fun onClick(view: View) {
        when (view?.id) {
            R.id.back -> {
                this.findNavController().popBackStack()
                //   listener!!.passFragmentCallback("uploadResume")
            }
        }
    }
}