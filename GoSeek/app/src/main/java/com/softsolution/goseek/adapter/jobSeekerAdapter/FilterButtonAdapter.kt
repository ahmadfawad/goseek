package com.softsolution.goseek.adapter.jobSeekerAdapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.softsolution.goseek.R
import com.softsolution.goseek.model.jobSeekerModel.FilterButtonData
import java.util.*

class FilterButtonAdapter(
    private val list: ArrayList<FilterButtonData>,
    private val context: Context): RecyclerView.Adapter<FilterButtonAdapter.ViewHolder>() {
    var row_index = 90
    var i = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.button_vh, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(filterButtonData: FilterButtonData) {

            var btn: MaterialButton = itemView.findViewById(R.id.btn) as MaterialButton

            btn.text = filterButtonData.btn

            itemView.setOnClickListener {
                row_index = adapterPosition
                notifyDataSetChanged()
            }

            if (row_index == adapterPosition) {
                btn.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.pink))
                btn.setTextColor(Color.parseColor("#FFFFFFFF"))
            } else {
                btn.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.grey009))
                btn.setTextColor(Color.parseColor("#FF000000"))
            }

        }
    }

}