package com.ix.ibrahim7.facebookintegration.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ix.ibrahim7.facebookintegration.R
import com.ix.ibrahim7.facebookintegration.databinding.ItemCategoryBinding
import com.ix.ibrahim7.facebookintegration.databinding.ItemEmailBinding
import com.ix.ibrahim7.facebookintegration.model.Category
import com.ix.ibrahim7.facebookintegration.model.Email
import kotlinx.android.synthetic.main.item_category.view.*
import java.util.ArrayList


class CategoryAdapter(var data: ArrayList<Category>, val itemclick: onClick) :
        RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {


    class CategoryViewHolder(val item: ItemCategoryBinding) : RecyclerView.ViewHolder(item.root) {

        fun bind(category : Category) {
            item.category = category
            item.executePendingBindings()
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView_layout: ItemCategoryBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_category, parent, false
        )
        return CategoryViewHolder(itemView_layout)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        val currentItem = data[position]
        holder.bind(currentItem)

        holder.itemView.apply {
            tvCategoryColor.background.setTint(currentItem.color.toInt())
            setOnClickListener {
                itemclick.onClickItem(currentItem,holder.adapterPosition, 1)
            }

            setOnLongClickListener {
                itemclick.onClickItem(currentItem,holder.adapterPosition, 2)
                true
            }
        }


    }

    interface onClick {
        fun onClickItem(category: Category,position: Int, type: Int)
    }


}
