package com.printlab.android.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ExpandableListView
import com.printlab.android.adapters.CustomExpandableListAdapter
import com.printlab.android.model.NavHeaderModel
import java.util.ArrayList


class NavigationExpandableListView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ExpandableListView(context, attrs, defStyle) {


    private var currentSelection = 0
    private var currentChildSelection = -1

    private var listHeader: ArrayList<NavHeaderModel>? = null

    private var mOnGroupClickListener: OnGroupClickListener? = null
    private var mOnChildClickListener: OnChildClickListener? = null

    private var expandableListAdapter: CustomExpandableListAdapter? = null

    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val heightMeasured = View.MeasureSpec.makeMeasureSpec(
            Integer.MAX_VALUE shr 2, View.MeasureSpec.AT_MOST
        )
        super.onMeasure(widthMeasureSpec, heightMeasured)
        val params = layoutParams
        params.height = measuredHeight
    }

    fun init(): NavigationExpandableListView {
        this.listHeader = ArrayList<NavHeaderModel>()

        return this
    }

    fun setListMenu(listHeader: List<NavHeaderModel>?): NavigationExpandableListView {

        if (listHeader != null)
            this.listHeader!!.addAll(listHeader)

        return this
    }

    fun addOnGroupClickListener(onGroupClickListener: ExpandableListView.OnGroupClickListener): NavigationExpandableListView {
        mOnGroupClickListener = onGroupClickListener
        setOnGroupClickListener(mOnGroupClickListener)

        return this
    }

    fun addOnChildClickListener(onChildClickListener: ExpandableListView.OnChildClickListener): NavigationExpandableListView {
        this.mOnChildClickListener = onChildClickListener

        setOnChildClickListener(mOnChildClickListener)
        return this
    }

    fun addHeaderModel(headerModel: NavHeaderModel): NavigationExpandableListView {
        this.listHeader!!.add(headerModel)
        return this
    }

    fun build(): NavigationExpandableListView {
        expandableListAdapter = CustomExpandableListAdapter(this.context, listHeader!!)
        setAdapter(expandableListAdapter)

        return this
    }

    fun setSelected(groupPosition: Int) {
        val headerModel = listHeader!![groupPosition]

        if (!headerModel.hasChild) {
            val currentModel = listHeader!![currentSelection]
            currentModel.isHeaderSel = false

            if (currentChildSelection != -1) {
                val childModel = listHeader!![currentSelection].mChildList[currentChildSelection]

                childModel.isChildSelected = false

                currentChildSelection = -1
            }

            headerModel.isHeaderSel = true

            currentSelection = groupPosition
            expandableListAdapter!!
        }
    }

    fun setSelected(groupPosition: Int, childPosition: Int) {

        val currentModel = listHeader!![currentSelection]
        currentModel.isHeaderSel = false

        if (currentChildSelection != -1) {
            val currentChildModel =
                listHeader!![currentSelection].mChildList[currentChildSelection] //listHeader?[currentSelection].mChildList[currentChildSelection]

            currentChildModel.isChildSelected = false
        }

        currentSelection = groupPosition
        currentChildSelection = childPosition

        val childModel = listHeader!![groupPosition].mChildList[childPosition]
        childModel.isChildSelected = true
        expandableListAdapter!!.notifyDataSetChanged()
    }

}