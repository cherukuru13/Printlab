package com.printlab.android.adapters

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.printlab.android.R
import com.printlab.android.model.NavChildModel
import com.printlab.android.model.NavHeaderModel
import kotlinx.android.synthetic.main.navigation_list_group.view.*
import kotlinx.android.synthetic.main.navigation_list_item.view.*

class CustomExpandableListAdapter(val context: Context, var mHeaderList: MutableList<NavHeaderModel>) :
    BaseExpandableListAdapter() {
    override fun getGroup(groupPosition: Int): Any {

        return mHeaderList[groupPosition]
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {

        val header = getGroup(groupPosition) as NavHeaderModel

        var convertView = convertView
        if (convertView == null) {
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.navigation_list_group, null)
        }

        convertView!!.headerTitle?.text = header.mHeaderTitle

        if (header.mRes != -1)
            convertView.headerMenu.setBackgroundResource(header.mRes)

        if (header.hasChild) {
            convertView.headerTitle?.setTypeface(null, Typeface.NORMAL)
            convertView.headerIndicator.visibility = View.VISIBLE
        } else {
            convertView.headerIndicator.visibility = View.GONE
            if (header.isHeaderSel) {
                convertView.headerTitle?.setTypeface(null, Typeface.BOLD)
            } else {
                convertView.headerTitle?.setTypeface(null, Typeface.NORMAL)
            }
        }

        if (header.isNew) {
            convertView.headerNewTitle.visibility = View.VISIBLE

        } else {
            convertView.headerNewTitle.setVisibility(View.GONE)
        }

        if (isExpanded) {
            convertView.headerIndicator.setImageResource(R.drawable.nav_min)
        } else {
            convertView.headerIndicator.setImageResource(R.drawable.add)
        }

        return convertView

    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return try {
            mHeaderList[groupPosition].mChildList.size
        } catch (e: Exception) {
            0
        }
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return mHeaderList[groupPosition].mChildList[childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val childText = getChild(groupPosition, childPosition) as NavChildModel

        var convertView = convertView
        if (convertView == null) {
            val layoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.navigation_list_item, null)
        }


        convertView!!.childTitle.text = childText.mChildTitle //txtListChild.setText(childText.getTitle())

        if (childText.isChildSelected) {
            convertView.childTitle.setTypeface(null, Typeface.BOLD)
        } else {
            convertView.childTitle.setTypeface(null, Typeface.NORMAL)
        }

        return convertView
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return mHeaderList.size
    }
}