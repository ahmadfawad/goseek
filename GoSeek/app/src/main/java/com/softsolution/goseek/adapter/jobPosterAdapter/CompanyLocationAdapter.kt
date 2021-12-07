package com.softsolution.goseek.adapter.jobPosterAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.softsolution.goseek.Interface.CallFragmentInterface
import com.softsolution.goseek.R
import com.softsolution.goseek.fragments.MapsFragment
import com.softsolution.goseek.model.jobPosterModel.CompanyLocation
import java.util.*


class CompanyLocationAdapter (
    private val list: ArrayList<CompanyLocation>,
    private val context: Context): RecyclerView.Adapter<CompanyLocationAdapter.ViewHolder>(){

    var listener: CallFragmentInterface? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.companylocation_vh,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(dashbordData: CompanyLocation){

            var frame:FrameLayout=itemView.findViewById(R.id.frameLayout) as FrameLayout
            var text:TextView=itemView.findViewById(R.id.calender_text) as TextView
            var desc:TextView=itemView.findViewById(R.id.desc) as TextView

            itemView.setOnClickListener {
                //  callbackInterface.passResultCallback()


            }

         /*   val transaction: FragmentTransaction =
                (context as FragmentActivity).supportFragmentManager.beginTransaction()
            transaction.replace(itemView.findViewById(R.id.frameLayout) , MapsFragment())
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction.addToBackStack(null)
            transaction.commit()*/


        }
    }


}