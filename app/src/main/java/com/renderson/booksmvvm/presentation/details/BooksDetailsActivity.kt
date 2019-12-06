
package com.renderson.booksmvvm.presentation.details

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.renderson.booksmvvm.R
import com.renderson.booksmvvm.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_books_details.*
import kotlinx.android.synthetic.main.include_toolbar.*
import java.security.AccessControlContext

class BooksDetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books_details)

        setupToolbar(toolbarMain, R.string.books_details)

        bookDetailsTitle.text = intent.getStringExtra(EXTRA_TITLE)
        bookDetailsDescription.text = intent.getStringExtra(EXTRA_DESCRIPTION)
    }

    companion object {
        private const val EXTRA_TITLE = "EXTRA_TITLE"
        private const val EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION"

        fun getStartIntent(context: Context, title: String, description: String): Intent{
            return Intent(context, BooksDetailsActivity::class.java ).apply {
                putExtra(EXTRA_TITLE, title)
                putExtra(EXTRA_DESCRIPTION, description)
            }
        }
    }
}
