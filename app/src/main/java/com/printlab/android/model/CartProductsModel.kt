package com.printlab.android.model

object CartProductsModel {

    private val mCartProductsList = ArrayList<Any>()


    fun getCartProductsList(): ArrayList<Any> {

        return mCartProductsList
    }

    fun setCartProductsList(any: Any) {
        mCartProductsList.add(any)
    }

}