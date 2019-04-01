package com.printlab.android.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.printlab.android.R
import com.printlab.android.listeners.OnRecyclerItemClick
import com.printlab.android.model.GroceryModel
import kotlinx.android.synthetic.main.emptylayout.view.*
import kotlinx.android.synthetic.main.shoppingcartcard.view.*


class ShoppingCartAdapter(
    var context: Context,
    var listType: String,
    var mList: ArrayList<Any>,
    val listener: OnRecyclerItemClick<Any>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var inflator: LayoutInflater? = null


    init {
        inflator = LayoutInflater.from(context)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view = inflator!!.inflate(R.layout.emptylayout, viewGroup, false)

        val emptyView = view.test

        val cellView = inflator!!.inflate(R.layout.shoppingcartcard, null)

        emptyView.addView(cellView)

        return ShopCartViewHolder(view)

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, pos: Int) {

        val obj = mList[pos] as GroceryModel

        initHolder(viewHolder, pos, obj)
    }


    inner class ShopCartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        init {


        }

    }

    private fun initHolder(viewHolder: RecyclerView.ViewHolder, pos: Int, obj: Any?) {

        val obj = obj as GroceryModel
        viewHolder.itemView.title.text = obj.name
        viewHolder.itemView.brand.text = obj.brand
        viewHolder.itemView.price.text = obj.sale_price.toString()
        viewHolder.itemView.unit.text = "1 kg"

        var mCountTxt = (viewHolder.itemView.cart_count.text.toString())
        var mCount = mCountTxt.toInt()

        viewHolder.itemView.add.setOnClickListener {

            listener.onItemClick(pos, R.id.add, obj)

            mCount += 1
            viewHolder.itemView.cart_count.text = "$mCount"
            viewHolder.itemView.price.text = "${obj.sale_price * mCount}"//(obj.sale_price * mCount).toString()
            viewHolder.itemView.unit.text = "$mCount kg"
        }

        viewHolder.itemView.sub.setOnClickListener {
            if (mCount > 1)
                mCount -= 1

            listener.onItemClick(pos, R.id.sub, obj)
            viewHolder.itemView.cart_count.text = "$mCount"
            viewHolder.itemView.price.text = "${obj.sale_price * mCount}"
            viewHolder.itemView.unit.text = "$mCount kg"

        }

    }

}