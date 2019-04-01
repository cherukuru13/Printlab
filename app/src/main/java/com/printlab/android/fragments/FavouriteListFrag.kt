package com.printlab.android.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.printlab.android.R
import com.printlab.android.activities.MainActivity
import com.printlab.android.adapters.CategoriesAdapter
import com.printlab.android.adapters.RecentProductsAdapter
import com.printlab.android.constants.AppConstants
import com.printlab.android.listeners.OnRecyclerItemClick
import com.printlab.android.model.CartProductsModel
import com.printlab.android.model.GroceryModel
import com.printlab.android.utils.RecyclerViewUtils
import kotlinx.android.synthetic.main.frag_favourite_list.view.*


class FavouriteListFrag : BaseFragment(), OnRecyclerItemClick<Any> {


    companion object {

        val mTag = "FavouriteListFrag"

        val mGroceryTag = "GroceryTag"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_favourite_list, container, false)

    }

    override fun onResume() {
        super.onResume()

        val cartImg = (activity as MainActivity).findViewById<ImageView>(R.id.action_cart)
        val favImg = (activity as MainActivity).findViewById<ImageView>(R.id.action_favorite)
        cartImg.visibility = View.VISIBLE
        favImg.visibility = View.GONE
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)

    }

    private fun init(view: View) {

        setAdapter(view)
    }


    private fun setAdapter(view: View) {

        view.list_favorites.layoutManager = RecyclerViewUtils.getLinearLayoutManager(
            activity!!,
            LinearLayoutManager.VERTICAL
        )

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

        view.list_favorites.adapter = CategoriesAdapter(activity!!, "grocery", mGroceryList, this)

    }


    override fun onItemClick(pos: Int, viewId: Int, t: Any) {


        if (viewId == 0) {
            val mProductDetailsFrag = ProductDetailsFrag.getInstance()

            val mBundle = Bundle(1)
            mBundle.putParcelable(AppConstants.FragBundleKeys.FRAG_PRODUCT_DETAILS, t as GroceryModel)
            mProductDetailsFrag.arguments = mBundle

            (activity as MainActivity).setContainerFrag(mProductDetailsFrag, ProductDetailsFrag.mTag)
        } else {

//            if (!CartProductsModel.getCartProductsList().contains(t))
            CartProductsModel.setCartProductsList(t)

        }

    }


}