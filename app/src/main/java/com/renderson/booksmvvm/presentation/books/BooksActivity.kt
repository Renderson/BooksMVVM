package com.renderson.booksmvvm.presentation.books

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.renderson.booksmvvm.R
import com.renderson.booksmvvm.data.ApiService
import com.renderson.booksmvvm.data.model.Book
import com.renderson.booksmvvm.data.repository.BooksApiDataSource
import com.renderson.booksmvvm.presentation.base.BaseActivity
import com.renderson.booksmvvm.presentation.details.BooksDetailsActivity
import kotlinx.android.synthetic.main.activity_books.*
import kotlinx.android.synthetic.main.include_toolbar.*

class BooksActivity : BaseActivity() {

    private lateinit var viewModel: BooksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)

        setupToolbar(toolbarMain, R.string.books_title)

        viewModel = ViewModelProvider(
            viewModelStore,
            BooksViewModel.ViewModelFactory(
                BooksApiDataSource(
                    ApiService.service
                )
            )
        ).get(BooksViewModel::class.java)

        if (savedInstanceState == null) {
            viewModel.getBooks()
        }

        viewModel.booksLiveData.observe(this, Observer {
            it?.let { books ->
                with(recyclerBooks) {
                    layoutManager =
                        LinearLayoutManager(this@BooksActivity, RecyclerView.VERTICAL, false)
                    setHasFixedSize(true)
                    adapter = BooksAdapter(books) { book ->
                        val intent = BooksDetailsActivity.getStartIntent(
                            this@BooksActivity,
                            book.title,
                            book.description
                        )
                        this@BooksActivity.startActivity(intent)
                    }
                }
            }
        })

        viewModel.viewFlipperLiveData.observe(this, Observer {
            it?.let { viewFlipper ->
                viewFlipperBooks.displayedChild = viewFlipper.first

                viewFlipper.second?.let { errorMessageResId ->
                    textViewError.text = getString(errorMessageResId)
                }
            }
        })
    }

    private fun removeObserve() {
        viewModel.booksLiveData.removeObservers(this)
        viewModel.viewFlipperLiveData.removeObservers(this)
    }

    override fun onResume() {
        super.onResume()
        shimmerFrameLayout.startShimmerAnimation()
    }

    override fun onPause() {
        shimmerFrameLayout.stopShimmerAnimation()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObserve()
    }
}
