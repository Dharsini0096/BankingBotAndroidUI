package com.example.bankingbot

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.chat_activity.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChatActivity :AppCompatActivity()
{
    private val adapterChatBot = AdapterChatBot()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_activity)

        val message = intent.getStringExtra("bankname")

        val okHttp = OkHttpClient.Builder()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://bankingbotmain.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create()).client(okHttp.build())
            .build()

        val apiService = retrofit.create(APIService::class.java)

        rvChatList.layoutManager = LinearLayoutManager(this)
        rvChatList.adapter = adapterChatBot

        etChat.onFocusChangeListener = View.OnFocusChangeListener{ view, b ->
            if(b){
                rvChatList.scrollToPosition(adapterChatBot.itemCount)
            }
        }

        adapterChatBot.addChatToList(ChatModel("Hello! Talk with bot of $message !! ", true))
        rvChatList.scrollToPosition(adapterChatBot.itemCount-1)

        btnSend.setOnClickListener {
            rvChatList.scrollToPosition(adapterChatBot.itemCount)
            if(etChat.text.isNullOrEmpty()){
                Toast.makeText(this, "Please enter a text", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            adapterChatBot.addChatToList(ChatModel(etChat.text.toString()))
            apiService.chatWithTheBit(etChat.text.toString(),message!!).enqueue(callBack)
            etChat.text.clear()
            rvChatList.scrollToPosition(adapterChatBot.itemCount-1)
        }
    }

    private val callBack = object  : Callback<ChatResponse> {
        override fun onResponse(call: Call<ChatResponse>, response: Response<ChatResponse>) {
            if(response.isSuccessful &&  response.body()!= null){
                adapterChatBot.addChatToList(ChatModel(response.body()!!.chatBotReply, true))
                rvChatList.scrollToPosition(adapterChatBot.itemCount-1)
            }else{
                Toast.makeText(this@ChatActivity, "Something went wrong", Toast.LENGTH_LONG).show()
            }
        }

        override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
            Toast.makeText(this@ChatActivity, "Something went wrong", Toast.LENGTH_LONG).show()
        }

    }
}