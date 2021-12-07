package com.softsolution.goseek.adapter.jobPosterAdapter

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.widget.CompoundButtonCompat
import androidx.recyclerview.widget.RecyclerView
import com.softsolution.goseek.Interface.CallFragmentInterface
import com.softsolution.goseek.R
import com.softsolution.goseek.activities.MainActivity
import com.softsolution.goseek.model.jobPosterModel.PostedDashbordData
import com.softsolution.goseek.model.jobPosterModel.PostedData
import java.util.ArrayList

class PostedAdapter (
    private val list: ArrayList<PostedData>,
    private val context: Context): RecyclerView.Adapter<PostedAdapter.ViewHolder>(){

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

        fun bind(dashbordData: PostedData){

            var designation_description:TextView=itemView.findViewById(R.id.designation_description) as TextView
            var calender_text:TextView=itemView.findViewById(R.id.calender_text) as TextView
            val btn:Button=itemView.findViewById(R.id.btn) as Button

            designation_description.text=dashbordData.designation_desc
            calender_text.text=dashbordData.time
            btn.text=dashbordData.btn_text

            if(dashbordData.btn_text =="Pause"){
                btn.background.setTint(ContextCompat.getColor(context, R.color.pause_bg))
                btn.setTextColor(Color.parseColor("#F1A73E"))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    btn.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#F1A73E")))
                }

               /* ViewCompat.setBackgroundTintList(
                    btn,
                    ColorStateList.valueOf(Color.parseColor("#F1A73E")))*/
              //  btn.setSupportButtonTintList(ContextCompat.getColorStateList(context, R.color.pause_bg));
             /*   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    btn.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#F1A73E")))
                }*/
            }else if(dashbordData.btn_text =="Expired"){
                btn.background.setTint(ContextCompat.getColor(context, R.color.exp_bg))
                btn.setTextColor(Color.parseColor("#B0B0B0"))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    btn.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#B0B0B0")))
                }
            }

            itemView.setOnClickListener {
                //  callbackInterface.passResultCallback()
                listener = context as CallFragmentInterface
                listener?.passFragmentCallback("postedJobDetail")

            }


        }
    }


}