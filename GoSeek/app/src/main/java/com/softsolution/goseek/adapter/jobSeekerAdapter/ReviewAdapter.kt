package com.softsolution.goseek.adapter.jobSeekerAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.softsolution.goseek.R
import com.softsolution.goseek.model.jobSeekerModel.ReviewModel
import me.zhanghai.android.materialratingbar.MaterialRatingBar
import java.util.*

class ReviewAdapter(
    private val reviewlist: ArrayList<ReviewModel>,
    private val viewPager2: ViewPager2,
    private val context: Context
): RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.review_vh, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reviewlist[position])
    }

    override fun getItemCount(): Int {
        return reviewlist.size
    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(reviewModel: ReviewModel) {

            var review_date: TextView = itemView.findViewById(R.id.calender_text) as TextView
            var review: TextView = itemView.findViewById(R.id.review) as TextView
            var reviewWriteName: TextView = itemView.findViewById(R.id.review_writer_name) as TextView
            var startRating: MaterialRatingBar = itemView.findViewById(R.id.rating) as MaterialRatingBar

            review_date.text=reviewModel.review_date
            reviewWriteName.text=reviewModel.review_writer_name
            review.text=reviewModel.review
            startRating.numStars= reviewModel.rating!!

            if (position == reviewlist.size - 2) {
                viewPager2.post(runnable)
            }

        }
    }

    private val runnable = Runnable { reviewlist.addAll(reviewlist) }
}