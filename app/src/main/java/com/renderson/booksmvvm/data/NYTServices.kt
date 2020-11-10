package com.renderson.booksmvvm.data

import com.renderson.booksmvvm.data.response.BookBodyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NYTServices {

    @GET("lists.json")
    fun getBooks(
        @Query("api-key") apiKey: String = "8vlpwrJxE6ua4gMjAjKXJOGk9mhIGztk",
        @Query("list") list: String = "hardcover-fiction"
    ): Call<BookBodyResponse>
}