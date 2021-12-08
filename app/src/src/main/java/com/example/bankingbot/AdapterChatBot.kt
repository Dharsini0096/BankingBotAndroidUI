package com.example.bankingbot

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.listitem_chat.view.*
import kotlinx.android.synthetic.main.message_layout.view.*


class AdapterChatBot : RecyclerView.Adapter<AdapterChatBot.MyViewHolder>() {

    var list = ArrayList<ChatModel>()

    inner class MyViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.message_layout, parent, false)
    ) {
        fun bind(chat: ChatModel) = with(itemView) {
            if(!chat.isBot) {
                user_message.text = chat.chat
                user_message.visibility = View.VISIBLE
                bot_message.visibility = View.GONE
            }
            else{
                bot_message.text = chat.chat
                bot_message.visibility = View.VISIBLE
                user_message.visibility = View.GONE
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterChatBot.MyViewHolder = MyViewHolder(parent)

    override fun onBindViewHolder(holder: AdapterChatBot.MyViewHolder, position: Int) {
        holder.bind(list[position])

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addChatToList(chat: ChatModel) {
        list.add(chat)
        notifyDataSetChanged()
    }
}
