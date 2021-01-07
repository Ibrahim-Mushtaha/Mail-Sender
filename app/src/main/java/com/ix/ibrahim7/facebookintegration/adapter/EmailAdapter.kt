package com.ix.ibrahim7.facebookintegration.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ix.ibrahim7.facebookintegration.R
import com.ix.ibrahim7.facebookintegration.databinding.ItemEmailBinding
import com.ix.ibrahim7.facebookintegration.model.Email
import java.util.ArrayList


class EmailAdapter(var data: ArrayList<Email>, val itemclick: onClick) :
        RecyclerView.Adapter<EmailAdapter.EmailViewHolder>() {


    class EmailViewHolder(val item: ItemEmailBinding) : RecyclerView.ViewHolder(item.root) {

        fun bind(email : Email) {
            item.email = email
            item.executePendingBindings()
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
        val itemView_layout: ItemEmailBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_email, parent, false
        )
        return EmailViewHolder(itemView_layout)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {

        val currentItem = data[position]
        holder.bind(currentItem)

        holder.itemView.apply {
            setOnClickListener {
                itemclick.onClickItem(currentItem,holder.adapterPosition, 1)
            }
        }


    }

    interface onClick {
        fun onClickItem(email: Email,position: Int, type: Int)
    }


}
