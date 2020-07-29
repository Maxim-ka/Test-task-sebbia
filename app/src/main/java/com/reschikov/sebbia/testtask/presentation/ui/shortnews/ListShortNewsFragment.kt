package com.reschikov.sebbia.testtask.presentation.ui.shortnews

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.reschikov.sebbia.testtask.KEY_CATEGORY_CODE
import com.reschikov.sebbia.testtask.KEY_NEWS
import com.reschikov.sebbia.testtask.R
import com.reschikov.sebbia.testtask.data.net.model.ShortNews
import com.reschikov.sebbia.testtask.di.adapter.ScopeFragment
import com.reschikov.sebbia.testtask.entity.dot.NoNetWork
import com.reschikov.sebbia.testtask.presentation.ui.OnItemClickListener
import com.reschikov.sebbia.testtask.presentation.ui.base.BaseFragment
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_common_recycler.*
import javax.inject.Inject

@ScopeFragment
class ListShortNewsFragment : BaseFragment(R.layout.fragment_common_recycler),
    OnItemClickListener<ShortNews> {

    @Inject lateinit var shortNewsAdapter : ShortNewsAdapter

    private val observerListShortNews by lazy {
        Observer<PagingData<ShortNews>> {
            shortNewsAdapter.submitData(lifecycle, it)
        }
    }
    private val loadStateListener = {combined : CombinedLoadStates ->
        if (combined.refresh is LoadState.Error){
            renderError((combined.refresh as LoadState.Error).error)
        } else if (combined.source.refresh is LoadState.Error){
            renderError((combined.source.refresh as LoadState.Error).error)
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(ListShortNewsViewModel::class.java)
        savedInstanceState ?: run{
            arguments?.let {bundle ->
                (viewModel as ListShortNewsViewModel).createShortNewsLiveData(bundle.getLong(KEY_CATEGORY_CODE))
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createRecycler()
        (viewModel as ListShortNewsViewModel).getListShortNewsLiveData().observe(viewLifecycleOwner, observerListShortNews)
    }

    private fun createRecycler(){
        val mergeAdapter = shortNewsAdapter.withLoadStateHeaderAndFooter(FootAdapter{shortNewsAdapter.retry()},
            FootAdapter{ shortNewsAdapter.retry()})
        rv_list.adapter = mergeAdapter
        rv_list.setHasFixedSize(false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showTitleScreen(getString(R.string.title_news))
    }

    override fun onStart() {
        super.onStart()
        shortNewsAdapter.addLoadStateListener(loadStateListener)
    }

    override fun onItemClick(item: ShortNews) {
        navController.navigate(R.id.action_listShortNewsFragment_to_fullNewsFragment, Bundle().apply {
            putParcelable(KEY_NEWS, item)
        })
    }

    private fun renderError(e : Throwable){
        val msg : String = if (e is NoNetWork) getString(R.string.err_no_network)
            else e.message ?: e.toString()
        activity?.showAlertDialog(msg) { shortNewsAdapter.refresh() }
    }

    override fun onStop() {
        super.onStop()
        shortNewsAdapter.removeLoadStateListener(loadStateListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        rv_list.adapter = null
    }
}