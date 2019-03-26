package com.printlab.android.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.printlab.android.R
import kotlinx.android.synthetic.main.adapter_productforsale.view.*

import com.printlab.android.listeners.OnRecyclerItemClick


class RecentProductsAdapter(
    var context: Context,
    var listType: String,
    var mList: ArrayList<Any>,
    val listener: OnRecyclerItemClick<Any>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var inflater: LayoutInflater? = null

    init {

        inflater = LayoutInflater.from(context)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ProductsViewHolder(inflater!!.inflate(R.layout.adapter_productforsale, parent, false))
    }

    override fun getItemCount(): Int {

        return mList.size
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
    }


    inner class ProductsViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView), View.OnClickListener {


        override fun onClick(v: View?) {

        }


    }
}