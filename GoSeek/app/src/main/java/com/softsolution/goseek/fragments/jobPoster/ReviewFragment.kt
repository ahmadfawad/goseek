package com.softsolution.goseek.fragments.jobPoster

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softsolution.goseek.R
import com.softsolution.goseek.adapter.jobPosterAdapter.ReviewAdapterData
import com.softsolution.goseek.databinding.FragmentReviewBinding
import com.softsolution.goseek.model.jobSeekerModel.ReviewModel
import java.util.*

class ReviewFragment : Fragment() {
    private var binding: FragmentReviewBinding? = null
    private var dashbordList: ArrayList<ReviewModel>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: ReviewAdapterData? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_review, container, false)
        binding!!.setFragment(this)


        dashbordList = ArrayList<ReviewModel>()
        layoutManager = LinearLayoutManager(requireActivity())
        adapter = ReviewAdapterData(dashbordList!!, requireActivity())

        binding!!.recyclerView.layoutManager = layoutManager
        binding!!.recyclerView.adapter = adapter

        loadData()

        return binding!!.getRoot()
    }

    fun onClick(view: View) {
        when (view?.id) {
            R.id.back -> {
                this.findNavController().popBackStack()
            }
        }
    }

    private fun loadData() {

        for (i in 0..16) {
            dashbordList!!.add(
                ReviewModel(
                    "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using Content here, content here, making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for lorem ipsum will uncover many web sites still in their infancy.",
                    5,
                    "10 Jun,2021",
                    "By: John Doe"
                )
            )
        }
    }

}