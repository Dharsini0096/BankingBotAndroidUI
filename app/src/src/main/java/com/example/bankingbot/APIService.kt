package com.example.bankingbot

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIService {
    @FormUrlEncoded
    @POST("chat")
    fun chatWithTheBit(@Field("chatInput") chatText : String,@Field("bankname") bankname : String ): Call<ChatResponse>
}

data class ChatResponse(val chatBotReply: String)

