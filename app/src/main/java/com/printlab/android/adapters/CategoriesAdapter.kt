package com.printlab.android.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.printlab.android.R
import com.printlab.android.listeners.OnRecyclerItemClick
import com.printlab.android.model.GroceryModel
import kotlinx.android.synthetic.main.adpter_fav_list.view.*

class CategoriesAdapter(
    var context: Context, var listType: String,
    private var mList: ArrayList<Any>,
    val listener: OnRecyclerItemClick<Any>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var inflator: LayoutInflater? = null

    init {
        inflator = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view = inflator!!.inflate(R.layout.adpter_fav_list, viewGroup, false)

        return CategoriesAdapterViewHolder(view)

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, pos: Int) {

        val obj = mList[pos] as GroceryModel

        initHolder(viewHolder, pos, obj)
    }


    inner class CategoriesAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {

        }


    }

    private fun initHolder(viewHolder: RecyclerView.ViewHolder, pos: Int, obj: Any?) {

        val obj = obj as GroceryModel
        viewHolder.itemView.title_fav.text = obj.name
        viewHolder.itemView.brand_fav.text = obj.brand

        viewHolder.itemView.add_to_cart_fav.setOnClickListener {

            listener.onItemClick(pos, R.id.add_to_cart_fav, obj)

        }
        viewHolder.itemView.setOnClickListener {
            listener.onItemClick(pos, 0, obj)

        }


    }

}