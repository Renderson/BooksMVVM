package com.renderson.booksmvvm.data.repository

import com.renderson.booksmvvm.data.BooksResult

interface BooksRepository {

    fun getBooks(booksResultCallback: (result: BooksResult) -> Unit)
}