package com.renderson.booksmvvm.data.repository

import com.renderson.booksmvvm.data.BooksResult

interface BooksRepository {

    suspend fun getBooks(booksResultCallback: (result: BooksResult) -> Unit)
}