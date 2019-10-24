package com.example.testapp.adapter

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.testapp.R
import com.example.testapp.model.Data
import com.squareup.picasso.Picasso

class DataListAdapter(var context:Context,var dataList:ArrayList<Data>):RecyclerView.Adapter<DataListAdapter.DataViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DataViewHolder {
        return DataViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_data_card_item,p0,false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(p0: DataViewHolder, p1: Int) {
        p0.bind(dataList[p1],context)
    }

    class DataViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        fun bind(data: Data, context: Context) {
            val placeName= itemView.findViewById<TextView>(R.id.place_name)
            val placeImage= itemView.findViewById<ImageView>(R.id.place_image)
            placeName.text = data.place
            Picasso.with(context).load(Uri.parse(data.url)).into(placeImage)
        }

    }
}