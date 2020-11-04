package com.renderson.booksmvvm.presentation.books

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.renderson.booksmvvm.R
import com.renderson.booksmvvm.data.BooksResult
import com.renderson.booksmvvm.data.model.Book
import com.renderson.booksmvvm.data.repository.BooksRepository

class BooksViewModel(private val dataSource: BooksRepository) : ViewModel() {

    private val _booksLiveData = MutableLiveData<List<Book>>()
    val booksLiveData: LiveData<List<Book>> = _booksLiveData

    val viewFlipperLiveData: MutableLiveData<Pair<Int, Int?>> = MutableLiveData()

    fun getBooks() {
        dataSource.getBooks { result: BooksResult ->
            when (result) {
                is BooksResult.Success -> {
                    _booksLiveData.value = result.books
                    viewFlipperLiveData.value = Pair(VIEW_FLIPPER_BOOKS, null)
                }
                is BooksResult.ApiError -> {
                    if (result.statusCode == 401) {
                        viewFlipperLiveData.value =
                            Pair(VIEW_FLIPPER_ERROR, R.string.books_error_401)
                    } else {
                        viewFlipperLiveData.value =
                            Pair(VIEW_FLIPPER_ERROR, R.string.books_error_400_generic)
                    }
                }
                is BooksResult.ServerError -> {
                    viewFlipperLiveData.value =
                        Pair(VIEW_FLIPPER_ERROR, R.string.books_error_500_generic)
                }
            }
        }
    }

    class ViewModelFactory(private val dataSource: BooksRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BooksViewModel::class.java)) {
                return modelClass.getConstructor(BooksRepository::class.java)
                    .newInstance(dataSource)
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {
        private const val VIEW_FLIPPER_BOOKS = 1
        private const val VIEW_FLIPPER_ERROR = 2
    }
}