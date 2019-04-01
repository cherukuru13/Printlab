package com.printlab.android.fragments

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.printlab.android.R
import com.printlab.android.activities.LoginOptionsActivity
import com.printlab.android.activities.MainActivity
import com.printlab.android.adapters.ShoppingCartAdapter
import com.printlab.android.constants.AppConstants
import com.printlab.android.listeners.OnRecyclerItemClick
import com.printlab.android.manager.NavigationManager
import com.printlab.android.manager.PrefsManager
import com.printlab.android.model.CartProductsModel
import com.printlab.android.model.GroceryModel
import com.printlab.android.utils.RecyclerViewUtils
import kotlinx.android.synthetic.main.shopping_cart_frag.view.*

class ShoppingCartFrag : BaseFragment(), View.OnClickListener, OnRecyclerItemClick<Any> {

    companion object {

        const val tag = "ShoppingCartFrag"
    }

    var mTotalPrice = 0.0

    private var rootView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.shopping_cart_frag, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)

    }

    private fun init(view: View) {

        setAdapter(view)

        setClickEvents(view)

    }

    override fun onResume() {
        super.onResume()
        val cartImg = (activity as MainActivity).findViewById<ImageView>(R.id.action_cart)
        val favImg = (activity as MainActivity).findViewById<ImageView>(R.id.action_favorite)
        cartImg.visibility = View.GONE
        favImg.visibility = View.VISIBLE
    }

    private fun setAdapter(view: View) {

        view.listview.layoutManager =
            RecyclerViewUtils.getLinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL)

        /*  val mGroceryList = ArrayList<Any>()

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
  */
        view.listview.adapter = ShoppingCartAdapter(
            activity!!,
            "grocery",
            CartProductsModel.getCartProductsList(),
            this
        )

        for (mGroceryModel in CartProductsModel.getCartProductsList() as ArrayList<GroceryModel>) {
            mTotalPrice += mGroceryModel.sale_price

        }
        view.checkout.text = "Check Out Price $mTotalPrice"

    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        // Makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception

        /*    try {
                mCallback = (HeadlineListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                        + " must implement HeadlineListener");
            }
    }*/

    }

    private fun setClickEvents(view: View) {

        view.checkout.setOnClickListener(this)
    }

    override fun onClick(v: View?) {


        if (PrefsManager.exists(activity!!, AppConstants.UserDetails.USER_NAME)) {
            (activity as MainActivity).setContainerFrag(OrderSummaryFrag.getInstance(), OrderSummaryFrag.mTag)
        } else {
            NavigationManager.start<LoginOptionsActivity>(activity!!)

        }

    }

    var productCount = 0
    override fun onItemClick(pos: Int, viewId: Int, t: Any) {

        val mObj = t as GroceryModel
        when (viewId) {

            R.id.sub -> {

                if (productCount > 1)
                    productCount -= 1

                mTotalPrice -= (productCount * mObj.sale_price)
                rootView!!.checkout.text = "Check Out Price $mTotalPrice"
                // }


            }

            R.id.add -> {

                productCount += 1


                mTotalPrice += (productCount * mObj.sale_price)
                rootView!!.checkout.text = "Check Out Price $mTotalPrice"
            }

        }

    }

}