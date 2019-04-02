package com.printlab.android.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.printlab.android.R
import com.printlab.android.activities.MainActivity
import com.printlab.android.adapters.RecentProductsAdapter
import com.printlab.android.listeners.OnRecyclerItemClick

import com.printlab.android.model.GroceryModel
import com.printlab.android.utils.RecyclerViewUtils
import kotlinx.android.synthetic.main.frag_landing_screen.view.*


class LandingScreen : BaseFragment(), View.OnClickListener, OnRecyclerItemClick<Any> {



    companion object {

        var mInstance: LandingScreen? = null
        const val tag = "LandingScreen"

        fun getInstance(): LandingScreen {

            if (mInstance == null) {

                return LandingScreen()
            }
            return mInstance!!
        }

    }



    override fun onItemClick(pos: Int, viewId: Int, t: Any) {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.frag_landing_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)

    }

    override fun onResume() {
        super.onResume()
        val cartImg = (activity as MainActivity).findViewById<ImageView>(R.id.action_cart)
        val favImg = (activity as MainActivity).findViewById<ImageView>(R.id.action_favorite)
        cartImg.visibility = View.VISIBLE
        favImg.visibility = View.VISIBLE

    }

    private fun init(view: View) {


        setAdapter(view)

        setClickEvents(view)

    }

    private fun setAdapter(view: View) {

        view.recent_products_list.layoutManager =
            RecyclerViewUtils.getLinearLayoutManager(activity!!, LinearLayoutManager.HORIZONTAL)

        val mGroceryList = ArrayList<Any>()

        val desc =
            "Basmati means 'the queen of smell or the perfumed one'. India Gate Dubar Basmati Rice is smelled, nutlike flavor and smell can be attributed to the fact that the grain is aged, to reduce its moisture content. When cooked, it swells only sideways, thereby resulting in long slight grains that are dry, divide and fluffy. India Gate Basmati as no other rice in the world has these characteristics in mixture. It has an unforgettable aroma and flavor that will linger in your mind forever. "
        val groceryModel = GroceryModel("Indian gate Basmati Rice", 99.0, "Basmati", "bas102", desc)
        val groceryModel1 = GroceryModel("Indian gate Basmati Rice", 99.0, "Basmati", "bas102", desc)
        val groceryModel2 = GroceryModel("Indian gate Basmati Rice", 99.0, "Basmati", "bas102", desc)
        val groceryModel3 = GroceryModel("Indian gate Basmati Rice", 99.0, "Basmati", "bas102", desc)
        val groceryModel4 = GroceryModel("Indian gate Basmati Rice", 99.0, "Basmati", "bas102", desc)

        mGroceryList.add(groceryModel)
        mGroceryList.add(groceryModel1)
        mGroceryList.add(groceryModel2)
        mGroceryList.add(groceryModel3)
        mGroceryList.add(groceryModel4)
        mGroceryList.add(groceryModel)
        mGroceryList.add(groceryModel)

        view.recent_products_list.adapter = RecentProductsAdapter(activity!!, "grocery", mGroceryList, this)

        view.products_on_sale_list.layoutManager =
            RecyclerViewUtils.getLinearLayoutManager(activity!!, LinearLayoutManager.HORIZONTAL)
        view.products_on_sale_list.adapter = RecentProductsAdapter(activity!!, "grocery", mGroceryList, this)

        view.popular_list.layoutManager =
            RecyclerViewUtils.getLinearLayoutManager(activity!!, LinearLayoutManager.HORIZONTAL)
        view.popular_list.adapter = RecentProductsAdapter(activity!!, "grocery", mGroceryList, this)

    }

    private fun setClickEvents(view: View) {

        view.grocery.setOnClickListener(this)
        view.frozen.setOnClickListener(this)
        view.meat.setOnClickListener(this)
        view.misc.setOnClickListener(this)
        view.produce.setOnClickListener(this)
        view.dairy.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        (activity as MainActivity).setContainerFrag(FavouriteListFrag(), FavouriteListFrag.mGroceryTag)

    }

}