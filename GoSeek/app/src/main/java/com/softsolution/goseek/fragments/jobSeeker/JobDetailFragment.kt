package com.softsolution.goseek.fragments.jobSeeker

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.softsolution.goseek.R
import com.softsolution.goseek.adapter.jobSeekerAdapter.MoreNearbyJobsAdapter
import com.softsolution.goseek.adapter.jobSeekerAdapter.ReviewAdapter
import com.softsolution.goseek.databinding.FragmentJobDetailBinding
import com.softsolution.goseek.fragments.MapsFragment
import com.softsolution.goseek.model.jobSeekerModel.DashbordData
import com.softsolution.goseek.model.jobSeekerModel.ReviewModel
import com.softsolution.goseek.utils.Constants.Companion.login
import java.util.*

class JobDetailFragment : Fragment() {

    private var binding: FragmentJobDetailBinding? = null
    val reviewList = ArrayList<ReviewModel>()
    private var adapter: ReviewAdapter?=null
    private val sliderHandler = Handler()
    var cross: Button? = null
    var confirm: Button? = null
    var review: MaterialButton? = null
    var dialog: BottomSheetDialog? = null
    private var dashbordList: ArrayList<DashbordData>?=null
    private var layoutManager: RecyclerView.LayoutManager?=null
    private var adapterNearBy: MoreNearbyJobsAdapter?=null
    var i = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_job_detail, container, false)
        binding!!.setFragment(this)

        childFragmentManager.beginTransaction().replace(R.id.container_review, MapsFragment()).commit()

        loadData()
        init()

        return binding!!.getRoot()
    }



    fun onClick(view: View) {
        when (view?.id) {
            R.id.back -> {
                this.findNavController().popBackStack()
            }

            R.id.apply -> {
                if (login == true) {
                    userApply()
                } else {
                    val navController = findNavController()
                    navController.navigate(R.id.action_jobDetailFragment_to_optionsFragment)
                }
                i++

            }

            R.id.add_review -> {
                showBottomSheetDialog()
            }
        }
    }

    private fun userApply() {

        val messageBoxView = LayoutInflater.from(activity).inflate(
            R.layout.writter_help_needed_vh,
            null
        )
        val messageBoxBuilder = AlertDialog.Builder(requireActivity()).setView(messageBoxView)
        val confirm = messageBoxView.findViewById<Button>(R.id.confirm)
        val cross = messageBoxView.findViewById<Button>(R.id.cross)
        //show dialog
        val  messageBoxInstance = messageBoxBuilder.show()


        //set Listener
        cross.setOnClickListener() {
            //close dialog
            messageBoxInstance.dismiss()

        }

        confirm.setOnClickListener(){

            messageBoxInstance.dismiss()

            val messageBoxView = LayoutInflater.from(activity).inflate(
                R.layout.add_review_nofile,
                null
            )
            val messageBoxBuilder = AlertDialog.Builder(requireActivity()).setView(messageBoxView)
            val confirm = messageBoxView.findViewById<Button>(R.id.confirm_button)
            val cross = messageBoxView.findViewById<Button>(R.id.cross)

            //show dialog
            val  messageBoxInstance = messageBoxBuilder.show()

            confirm.setOnClickListener {
                messageBoxInstance.dismiss()

                val navController = findNavController()
                navController.navigate(R.id.action_jobDetailFragment_to_sucessfullFragment)
            }

            cross.setOnClickListener {
                //close dialog
                messageBoxInstance.dismiss()

            }
        }
    }

    private fun init() {

        //Setting recyclerView

        dashbordList= ArrayList<DashbordData>()
        layoutManager= LinearLayoutManager(requireActivity())
        adapterNearBy= MoreNearbyJobsAdapter(dashbordList!!, requireActivity())

        binding!!.recyclerView.layoutManager=layoutManager
        binding!!.recyclerView.adapter=adapterNearBy

        moreNearByJobLoadData()

        //Setting heart btn

        binding!!.cardview.setOnClickListener {
            if (i % 2 == 0) {
                binding!!.imagebtn.setImageResource(R.drawable.ic_fill_heart)
            } else {
                binding!!.imagebtn.setImageResource(R.drawable.ic_heart)
            }
            i++
        }

        //Setting View Pager with animation

        binding!!.viewPagerImageSlider.setAdapter(reviewList?.let {
            ReviewAdapter(
                it,
                binding!!.viewPagerImageSlider,
                requireActivity()
            )
        })

        binding!!.viewPagerImageSlider.clipToPadding = false
        binding!!.viewPagerImageSlider.clipChildren = false
        binding!!.viewPagerImageSlider.offscreenPageLimit = 3 //3

        binding!!.viewPagerImageSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40)) //40

        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - Math.abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }
        binding!!.viewPagerImageSlider.setPageTransformer(compositePageTransformer)

        binding!!.viewPagerImageSlider.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(slideRunnable)
                sliderHandler.postDelayed(slideRunnable, 3000)
            }
        })
    }
    private val slideRunnable =
        Runnable { binding!!.viewPagerImageSlider.currentItem = binding!!.viewPagerImageSlider.currentItem + 1 }

    private fun loadData() {

        for (i in 0..16){
            reviewList.add(
                ReviewModel(
                    "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using Content here, content here, making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for lorem ipsum will uncover many web sites still in their infancy.",
                    5,
                    "10 Jun,2021",
                    "By: John Doe"
                )
            )
        }
    }


     override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(slideRunnable)
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(slideRunnable, 3000)
    }

    private fun showBottomSheetDialog() {
        val view: View = layoutInflater.inflate(R.layout.add_review_vh, null)
        dialog = BottomSheetDialog(requireActivity(), R.style.DialogCustomTheme)
        dialog?.setContentView(view)
        dialog?.show()
        review = dialog?.findViewById(R.id.post_review)
        review!!.setOnClickListener{


        }
    }

    private fun moreNearByJobLoadData() {
        for (i in 0..5){
            val dashbordData= DashbordData()
            dashbordData.designation="Catering Hospitality"
            dashbordData.designation_desc="Waiter Help Wanted"
            dashbordData.location="Wandsworth, Uk"
            dashbordData.time="10 Jun 2021"
            dashbordData.rate="$3"
            dashbordList!!.add(dashbordData)
        }
        adapterNearBy!!.notifyDataSetChanged()
    }
}