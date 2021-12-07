package com.softsolution.goseek.adapter.jobPosterAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.softsolution.goseek.R
import com.softsolution.goseek.model.jobSeekerModel.ReviewModel
import me.zhanghai.android.materialratingbar.MaterialRatingBar
import java.util.ArrayList

class ReviewAdapterData (
    private val reviewlist: ArrayList<ReviewModel>,
    private val context: Context
): RecyclerView.Adapter<ReviewAdapterData.ViewHolder>() {

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


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapterData.ViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.review_vh_all, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewAdapterData.ViewHolder, position: Int) {
        holder.bind(reviewlist[position])
    }

    override fun getItemCount(): Int {
        return reviewlist.size
    }


}