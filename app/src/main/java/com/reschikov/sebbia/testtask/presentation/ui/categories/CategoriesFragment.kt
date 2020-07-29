package com.reschikov.sebbia.testtask.presentation.ui.categories

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.reschikov.sebbia.testtask.KEY_CATEGORY_CODE
import com.reschikov.sebbia.testtask.R
import com.reschikov.sebbia.testtask.data.net.model.Category
import com.reschikov.sebbia.testtask.di.adapter.ScopeFragment
import com.reschikov.sebbia.testtask.presentation.ui.OnItemClickListener
import com.reschikov.sebbia.testtask.presentation.ui.base.BaseFragment
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_common_recycler.*
import javax.inject.Inject

@ScopeFragment
class CategoriesFragment : BaseFragment(R.layout.fragment_common_recycler),
    OnItemClickListener<Category> {

    @Inject lateinit var categoriesAdapter : CategoriesAdapter

    private val observerListSpecialties by lazy {
        Observer<List<Category>> {
            if (it.isEmpty()) activity?.showAlertDialog(getString(R.string.warning_empty_list_received), null)
            else categoriesAdapter.list = it
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(CategoriesViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (viewModel as CategoriesViewModel).getLivDataListCategories().observe(viewLifecycleOwner, observerListSpecialties)
        rv_list.adapter = categoriesAdapter
        rv_list.setHasFixedSize(true)
        showTitleScreen(getString(R.string.title_categories))
    }

    override fun onItemClick(item: Category) {
        navController.navigate(R.id.action_categoriesFragment_to_listShortNewsFragment, Bundle().apply {
            putLong(KEY_CATEGORY_CODE, item.id)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        rv_list.adapter = null
    }
}