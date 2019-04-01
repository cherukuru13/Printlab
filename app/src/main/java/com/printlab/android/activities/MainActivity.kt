package com.printlab.android.activities

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ExpandableListView
import com.printlab.android.R
import com.printlab.android.fragments.FavouriteListFrag
import com.printlab.android.fragments.LandingScreen
import com.printlab.android.fragments.ShoppingCartFrag
import com.printlab.android.model.CartProductsModel
import com.printlab.android.model.NavChildModel
import com.printlab.android.model.NavHeaderModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {

        setSupportActionBar(toolBar)

        home.setOnClickListener {

            showHideNavBar()

        }

        val mChildBusinessList = ArrayList<NavChildModel>()
        mChildBusinessList.add(NavChildModel("Standard Business Card"))
        mChildBusinessList.add(NavChildModel("Froasted Business Card"))
        mChildBusinessList.add(NavChildModel("Die Cut Business Card"))
        mChildBusinessList.add(NavChildModel("Express Business Card"))

        navExpListView
            .init()
            .addHeaderModel(NavHeaderModel("Money packet"))
            .addHeaderModel(
                NavHeaderModel(
                    "Business Cards",
                    R.drawable.cartbackground,
                    false,
                    true,
                    false,
                    mChildBusinessList
                )
            )
            .addHeaderModel(
                NavHeaderModel("Greeting Cards")

            )
            .addHeaderModel(NavHeaderModel("Sticker Label"))
            .addHeaderModel(NavHeaderModel("Flyer"))
            .addHeaderModel(NavHeaderModel("Booklet"))
            .addHeaderModel(NavHeaderModel("PhotoBook"))
            .addHeaderModel(NavHeaderModel("Menu Book"))
            .addHeaderModel(NavHeaderModel("Thesis HardCover"))
            .addHeaderModel(NavHeaderModel("Ticketing & Bill book"))
            .addHeaderModel(NavHeaderModel("Poster"))
            .addHeaderModel(NavHeaderModel("Large Format Painting"))
            .addHeaderModel(NavHeaderModel("Marketing Display"))
            .addHeaderModel(NavHeaderModel("Fabric"))
            .addHeaderModel(NavHeaderModel("Stamps"))
            .addHeaderModel(NavHeaderModel("Name Tag"))
            .addHeaderModel(NavHeaderModel("Corporate Gifts & Personalized Gifts"))
            .addHeaderModel(NavHeaderModel("Food Display"))
            .addHeaderModel(NavHeaderModel("Photo"))
            .addHeaderModel(NavHeaderModel("Business Dictionary"))
            .addHeaderModel(NavHeaderModel("Loose Sheet A3 Size"))
            .build()

            .addOnGroupClickListener(ExpandableListView.OnGroupClickListener { parent, v, groupPosition, id ->
                navExpListView.setSelected(groupPosition)
//                AnimUtils.expand(v)

                false
            })

            .addOnChildClickListener(ExpandableListView.OnChildClickListener { parent, v, groupPosition, childPosition, id ->
                navExpListView.setSelected(groupPosition, childPosition)

                false
            })

        navExpListView.setOnGroupExpandListener {

        }

        navExpListView.setOnGroupCollapseListener {

        }
        setToggleBar()

        setContainerFrag(LandingScreen(), LandingScreen.tag)
        setClickEvents()
    }

    private fun setClickEvents() {

        action_cart.setOnClickListener(this)
        action_favorite.setOnClickListener(this)

    }

    fun setContainerFrag(fragment: Fragment?, tag: String? = null) {

        supportFragmentManager.beginTransaction().replace(R.id.activity_main_container, fragment!!, tag!!)
            .addToBackStack(null).commit()

    }

    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)

    }

    private fun setToggleBar() {

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)

        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

    }

    private fun showHideNavBar() {

        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            drawer_layout.openDrawer(GravityCompat.START)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        /*   when (item.itemId) {
               R.id.nav_camera -> {
                   // Handle the camera action
               }
               R.id.nav_gallery -> {

               }
               R.id.nav_slideshow -> {

               }
               R.id.nav_manage -> {

               }
               R.id.nav_share -> {

               }
               R.id.nav_send -> {

               }
           }*/

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onClick(v: View?) {

        // Handle click events
        when (v!!.id) {

            R.id.action_cart -> {

                if (CartProductsModel.getCartProductsList().size > 0)
                    setContainerFrag(ShoppingCartFrag(), ShoppingCartFrag.tag)

            }

            R.id.action_favorite -> {

                setContainerFrag(FavouriteListFrag(), FavouriteListFrag.mTag)

            }


        }

    }

}
