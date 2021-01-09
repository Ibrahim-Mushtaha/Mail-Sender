package com.ix.ibrahim7.mailsender.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ix.ibrahim7.mailsender.R
import com.ix.ibrahim7.mailsender.databinding.ItemCategoryBinding
import com.ix.ibrahim7.mailsender.databinding.ItemCategoryLinerBinding
import com.ix.ibrahim7.mailsender.databinding.ItemEmailBinding
import com.ix.ibrahim7.mailsender.model.Category
import kotlinx.android.synthetic.main.item_category_liner.view.*
import java.util.ArrayList


class CategoryDialogAdapter(var data: ArrayList<Category>, val itemclick: onClick) :
        RecyclerView.Adapter<CategoryDialogAdapter.CategoryViewHolder>() {


    class CategoryViewHolder(val item: ItemCategoryLinerBinding) : RecyclerView.ViewHolder(item.root) {

        fun bind(category : Category) {
            item.category = category
            item.executePendingBindings()
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView_layout: ItemCategoryLinerBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_category_liner, parent, false
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
            setOnClickListener {
                tvChooseCard.background.setTint(currentItem.color.toInt())
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
