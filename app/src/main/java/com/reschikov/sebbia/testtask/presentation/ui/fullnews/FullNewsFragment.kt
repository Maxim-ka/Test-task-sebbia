package com.reschikov.sebbia.testtask.presentation.ui.fullnews

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.reschikov.sebbia.testtask.KEY_NEWS
import com.reschikov.sebbia.testtask.R
import com.reschikov.sebbia.testtask.data.net.model.ShortNews
import com.reschikov.sebbia.testtask.presentation.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_fullnews.*

class FullNewsFragment : BaseFragment(R.layout.fragment_fullnews){

    private val observerFullNews by lazy {
        Observer<String?> {
            it?.let {
                tv_full_description.text = HtmlCompat.fromHtml(it, FROM_HTML_MODE_LEGACY)
                tv_full_description.movementMethod = LinkMovementMethod.getInstance()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(FullNewsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (viewModel as FullNewsViewModel).getFullNewsLiveData().observe(viewLifecycleOwner, observerFullNews)
        arguments?.let {
            it.getParcelable<ShortNews>(KEY_NEWS)?.let { news ->
                showTitleScreen(news.title)
                tv_short_description.text = news.shortDescription
                savedInstanceState ?: run { (viewModel as FullNewsViewModel).getFullNews(news.id) }
            }
        }
    }
}