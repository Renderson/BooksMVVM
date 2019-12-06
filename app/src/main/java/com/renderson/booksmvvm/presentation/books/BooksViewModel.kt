package com.renderson.booksmvvm.presentation.books

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.renderson.booksmvvm.data.ApiService
import com.renderson.booksmvvm.data.model.Book
import com.renderson.booksmvvm.data.response.BookBodyResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BooksViewModel : ViewModel() {

    val booksLiveData: MutableLiveData<List<Book>> = MutableLiveData()

    fun getBooks() {
        ApiService.service.getBooks().enqueue(object : Callback<BookBodyResponse> {

            override fun onResponse(
                call: Call<BookBodyResponse>,
                response: Response<BookBodyResponse>
            ) {
                if ( response.isSuccessful ) {
                    val books: MutableList<Book> = mutableListOf()

                    response.body()?.let { bookBodyResponse ->
                        for (result in bookBodyResponse.bookResults) {
                            val book = Book(
                                title = result.bookDetailResponses[0].title,
                                author = result.bookDetailResponses[0].author,
                                description = result.bookDetailResponses[0].description
                            )

                            books.add(book)
                        }
                    }

                    booksLiveData.value = books
                }
            }

            override fun onFailure(call: Call<BookBodyResponse>, t: Throwable) {

            }

        })
    }
}