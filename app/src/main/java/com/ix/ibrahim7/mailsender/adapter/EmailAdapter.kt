package com.ix.ibrahim7.mailsender.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ix.ibrahim7.mailsender.R
import com.ix.ibrahim7.mailsender.databinding.ItemEmailBinding
import com.ix.ibrahim7.mailsender.model.Message
import com.ix.ibrahim7.mailsender.util.Constant.DAYFORMAT
import kotlinx.android.synthetic.main.item_email.view.*
import java.text.SimpleDateFormat
import java.util.*


class EmailAdapter(var data: ArrayList<Message>, val itemclick: onClick) :
        RecyclerView.Adapter<EmailAdapter.EmailViewHolder>() {


    class EmailViewHolder(val item: ItemEmailBinding) : RecyclerView.ViewHolder(item.root) {

        fun bind(message : Message) {
            item.email = message
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
            tvEmailDate.text = getDate(currentItem.date.toLong(),DAYFORMAT)
            setOnClickListener {
                itemclick.onClickItem(currentItem,holder.adapterPosition, 1)
            }
        }


    }


    fun getDate(milliSeconds: Long, dateFormat: String?): String? {
        // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat(dateFormat)

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }

    interface onClick {
        fun onClickItem(message: Message, position: Int, type: Int)
    }


}
