package com.softsolution.goseek.adapter.jobPosterAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.softsolution.goseek.Interface.CallFragmentInterface
import com.softsolution.goseek.R
import com.softsolution.goseek.activities.MainActivity
import com.softsolution.goseek.model.jobPosterModel.PostedDashbordData
import java.util.ArrayList

class PostedDashbordAdapter (
    private val list: ArrayList<PostedDashbordData>,
    private val context: Context): RecyclerView.Adapter<PostedDashbordAdapter.ViewHolder>(){

    var listener: CallFragmentInterface? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.posted_dashbord_vh,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(dashbordData: PostedDashbordData){

            var designation_description:TextView=itemView.findViewById(R.id.designation_description) as TextView
            var calender_text:TextView=itemView.findViewById(R.id.calender_text) as TextView

            designation_description.text=dashbordData.designation_desc
            calender_text.text=dashbordData.time


            itemView.setOnClickListener {
                //  callbackInterface.passResultCallback()
                listener = context as CallFragmentInterface
                listener?.passFragmentCallback("postedJobDetail")

            }


        }
    }


}