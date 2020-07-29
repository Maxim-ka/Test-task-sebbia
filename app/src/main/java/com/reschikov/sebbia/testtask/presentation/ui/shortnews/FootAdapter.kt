package com.reschikov.sebbia.testtask.presentation.ui.shortnews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.reschikov.sebbia.testtask.R
import com.reschikov.sebbia.testtask.entity.dot.NoNetWork
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.block_item_footer.view.*

class FootAdapter(private val retry : () -> Unit) : LoadStateAdapter<FootAdapter.FooterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FooterViewHolder {
        return FooterViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: FooterViewHolder, loadState: LoadState) {
        holder.bind(loadState)
        holder.itemView.but_retry.setOnClickListener {retry.invoke()}
    }

    class FooterViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        companion object{
            @JvmStatic
            fun create(parent: ViewGroup) : FooterViewHolder{
                val view = LayoutInflater.from(parent.context).inflate(R.layout.block_item_footer, parent, false)
                return FooterViewHolder(view)
            }
        }

        fun bind(loadState: LoadState) {
            with(itemView){
                tv_error.apply {
                    visibility = loadState.takeIf { it is LoadState.Error }?.let {
                        text = (it as LoadState.Error).takeIf {state -> state.error is NoNetWork }?.let {
                            context.getString(R.string.err_no_network) } ?: it.error.message
                    View.VISIBLE } ?: View.GONE
                }
                but_retry.visibility = if (loadState is LoadState.Error) View.VISIBLE else View.GONE
                pb_progress.visibility = if (loadState is LoadState.Loading) View.VISIBLE else View.GONE
            }
        }
    }
}