package com.softsolution.goseek.adapter.jobSeekerAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.softsolution.goseek.Interface.CallFragmentInterface
import com.softsolution.goseek.R
import com.softsolution.goseek.model.jobSeekerModel.DashbordData
import java.util.ArrayList

class DashbordAdapter (
    private val list: ArrayList<DashbordData>,
    private val context: Context): RecyclerView.Adapter<DashbordAdapter.ViewHolder>(){

    var listener: CallFragmentInterface? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.dashbord_vh,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(dashbordData: DashbordData){

            var designation:TextView=itemView.findViewById(R.id.designation) as TextView
            var designation_description:TextView=itemView.findViewById(R.id.designation_description) as TextView
            var calender_text:TextView=itemView.findViewById(R.id.calender_text) as TextView
            var location_text:TextView=itemView.findViewById(R.id.location_text) as TextView
            var rate:TextView=itemView.findViewById(R.id.rate) as TextView
            var heart:ImageView=itemView.findViewById(R.id.heart) as ImageView

            designation.text= dashbordData.designation
            designation_description.text=dashbordData.designation_desc
            calender_text.text=dashbordData.time
            location_text.text=dashbordData.location
            rate.text=dashbordData.rate

            heart.setOnClickListener {
                heart.setImageResource(R.drawable.ic_fill_heart)
            }

            itemView.setOnClickListener {
              //  callbackInterface.passResultCallback()
                listener = context as CallFragmentInterface
                listener?.passFragmentCallback("JobDescription")

            }


        }
    }


}