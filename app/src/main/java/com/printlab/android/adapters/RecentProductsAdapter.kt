package com.printlab.android.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v4.content.ContextCompat
import com.printlab.android.R
import kotlinx.android.synthetic.main.adapter_productforsale.view.*


import com.printlab.android.listeners.OnRecyclerItemClick
import com.printlab.android.model.GroceryModel


class RecentProductsAdapter(
    var context: Context,
    var listType: String,
    private var mList: ArrayList<Any>,
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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, pos: Int) {

        val obj = mList[pos] as GroceryModel

        initHolder(holder, pos, obj.name, obj.sale_price, null, obj)
    }


    inner class ProductsViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView), View.OnClickListener {


        init {


        }


        override fun onClick(v: View?) {

        }


    }


    private fun initHolder(
        holder: RecyclerView.ViewHolder,
        pos: Int,
        txt: String,
        path: Any,
        imgVersion: Any? = null,
        obj: Any?
    ) {

        holder.itemView.title_pos.text = txt


    }
}