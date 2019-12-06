package com.renderson.booksmvvm.presentation.books

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.renderson.booksmvvm.R
import com.renderson.booksmvvm.presentation.base.BaseActivity
import com.renderson.booksmvvm.presentation.details.BooksDetailsActivity
import kotlinx.android.synthetic.main.activity_books.*
import kotlinx.android.synthetic.main.include_toolbar.*

class BooksActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)

        setupToolbar(toolbarMain, R.string.books_title)

        val viewModel: BooksViewModel = ViewModelProviders.of(this).get(BooksViewModel::class.java)

        viewModel.booksLiveData.observe(this, Observer {
            it?.let { books ->
                with(recyclerBooks){
                    layoutManager = LinearLayoutManager(this@BooksActivity, RecyclerView.VERTICAL, false)
                    setHasFixedSize(true)
                    adapter = BooksAdapter(books) {book ->
                        val intent = BooksDetailsActivity.getStartIntent(this@BooksActivity, book.title, book.description)
                        this@BooksActivity.startActivity(intent )
                    }
                }
            }
        })

        viewModel.getBooks()
    }
}
