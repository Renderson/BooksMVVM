package com.renderson.booksmvvm.data.repository

import com.renderson.booksmvvm.data.BooksResult
import com.renderson.booksmvvm.data.NYTServices
import com.renderson.booksmvvm.data.model.Book
import com.renderson.booksmvvm.data.response.BookBodyResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BooksApiDataSource(private val service: NYTServices): BooksRepository {

    override suspend fun getBooks(booksResultCallback: (result: BooksResult) -> Unit) {
        return withContext(Dispatchers.Main) {
            try {
                service.getBooks().enqueue(object : Callback<BookBodyResponse> {
                    override fun onResponse(
                        call: Call<BookBodyResponse>,
                        response: Response<BookBodyResponse>
                    ) {
                        when {
                            response.isSuccessful -> {
                                val books: MutableList<Book> = mutableListOf()

                                response.body()?.let { booksResponse ->
                                    for (result in booksResponse.bookResults) {
                                        val book = result.bookDetailResponses[0].getBookModel()
                                        books.add(book)
                                    }
                                }

                                booksResultCallback(BooksResult.Success(books))
                            }
                            else -> booksResultCallback(BooksResult.ApiError(response.code()))
                        }
                    }

                    override fun onFailure(call: Call<BookBodyResponse>, t: Throwable) {
                        booksResultCallback(BooksResult.ServerError)
                    }
                })
            } catch (e:Exception){}
        }
    }
}