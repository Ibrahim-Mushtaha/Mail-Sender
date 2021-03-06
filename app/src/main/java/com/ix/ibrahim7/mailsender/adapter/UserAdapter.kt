package com.ix.ibrahim7.mailsender.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ix.ibrahim7.mailsender.R
import com.ix.ibrahim7.mailsender.databinding.ItemEmailBinding
import com.ix.ibrahim7.mailsender.databinding.ItemUserBinding
import com.ix.ibrahim7.mailsender.model.Users
import java.util.ArrayList


class UserAdapter(var data: ArrayList<Users>, val itemclick: onClick) :
        RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    class UserViewHolder(val item: ItemUserBinding) : RecyclerView.ViewHolder(item.root) {

        fun bind(users : Users) {
            item.user = users
            item.executePendingBindings()
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView_layout: ItemUserBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_user, parent, false
        )
        return UserViewHolder(itemView_layout)
    }

    override fun getItemCount()= data.size


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val currentItem = data[position]
        holder.bind(currentItem)

        holder.itemView.apply {
            setOnLongClickListener {
                itemclick.onClickItem(currentItem,holder.adapterPosition, 1)
                true
            }
        }


    }

    interface onClick {
        fun onClickItem(users: Users,position: Int, type: Int)
    }


}
