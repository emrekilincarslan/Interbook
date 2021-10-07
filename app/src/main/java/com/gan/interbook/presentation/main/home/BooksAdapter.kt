package com.gan.interbook.presentation.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gan.interbook.business.domain.book.BookItemModel
import com.gan.interbook.databinding.LayoutBookItemBinding
import com.gan.interbook.framework.resources.abstraction.ResourceProvider
import com.gan.interbook.presentation.common.ItemClickListener
import com.gan.interbook.presentation.main.home.viewmodel.BookItemViewModel

class BooksAdapter(
    private val resourceProvider: ResourceProvider,
    private val itemClickListener: ItemClickListener<BookItemModel>
) :
    RecyclerView.Adapter<BookViewHolder>() {
    private val data: MutableList<BookItemModel> = mutableListOf()

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return data[position].id.hashCode().toLong()
    }

    fun setData(bookModels: List<BookItemModel>) {
        data.clear()
        data.addAll(bookModels)

        //todo optimize item updating
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder =
        BookViewHolder(
            LayoutBookItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            resourceProvider,
            itemClickListener
        )

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int =
        data.size
}

class BookViewHolder(
    binding: LayoutBookItemBinding,
    resourceProvider: ResourceProvider,
    itemClickListener: ItemClickListener<BookItemModel>
) :
    RecyclerView.ViewHolder(binding.root) {
    val viewModel = BookItemViewModel(
        resourceProvider,
        itemClickListener
    )

    init {
        binding.viewModel = viewModel
    }

    fun bind(bookModel: BookItemModel) {
        viewModel.bookModel = bookModel
    }
}