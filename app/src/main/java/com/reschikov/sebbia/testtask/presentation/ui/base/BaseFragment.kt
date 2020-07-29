package com.reschikov.sebbia.testtask.presentation.ui.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.reschikov.sebbia.testtask.R
import com.reschikov.sebbia.testtask.entity.dot.NoNetWork
import com.reschikov.sebbia.testtask.presentation.ui.MainActivity
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_common_recycler.*
import javax.inject.Inject

abstract class BaseFragment(resId : Int) : Fragment(resId) {

    @Inject lateinit var factory: ViewModelFactory
    protected lateinit var viewModel: BaseViewModel
    protected val navController : NavController by lazy { findNavController() }

    private val observerError by lazy {
        Observer<Throwable?>{
            it?.let {
                if (it is NoNetWork) activity?.showAlertDialog(getString(R.string.err_no_network)) {viewModel.checkNet()}
                else {
                    val errMsg = it.message ?: it.toString()
                    activity?.showAlertDialog(errMsg, null)
                }
            }
        }
    }

    private val observerVisibleProgress by lazy {
        Observer<Boolean> { pb_circle.visibility = if (it) View.VISIBLE else View.GONE }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getErrorMsg().observe(viewLifecycleOwner, observerError)
        viewModel.isVisibleProgress().observe(viewLifecycleOwner, observerVisibleProgress)
    }

    protected fun showTitleScreen(text : String){
        (activity as MainActivity).supportActionBar?.title = text
    }

    protected fun FragmentActivity.showAlertDialog(message: String, check : (() -> Unit)? ){
        val namePositive = check?.run { getString(R.string.but_retry)} ?: run { getString(R.string.but_ok) }
        val alert = AlertDialog.Builder(this, R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
            .setTitle(R.string.title_warning)
            .setIcon(R.drawable.ic_warning)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(namePositive) { dialog, which ->
                check?.invoke()
                dialog.dismiss()
            }
            .create()
        if (check != null) alert.setButton(Dialog.BUTTON_NEGATIVE, getString(R.string.but_close)) { dialog, which ->
                dialog.dismiss()
            }
        alert.show()
    }
}