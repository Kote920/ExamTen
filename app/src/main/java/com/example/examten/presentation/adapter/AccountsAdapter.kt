package com.example.examten.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.examten.databinding.ItemAccountRecyclerBinding
import com.example.examten.presentation.model.AccountUI

class AccountsAdapter(private var itemClick: (AccountUI) -> Unit) :
    ListAdapter<AccountUI, AccountsAdapter.AccountsViewHolder>(CategoriesDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AccountsViewHolder(
        ItemAccountRecyclerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: AccountsViewHolder, position: Int) {
        holder.bind()
    }

    inner class AccountsViewHolder(private val binding: ItemAccountRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var model: AccountUI
        private var childList: List<AccountUI>? = null

        fun bind() {
            model = currentList[adapterPosition]
            binding.apply {
                tvAccountName.text = model.accountName
                tvAccountNumber.text = model.accountNumber
                tvBalance.text = model.balance.toString()
                tvValuteType.text = model.valuteType

                }
            listeners()
        }

        private fun listeners() {
            binding.root.setOnClickListener {

                itemClick.invoke(model)
            }
        }

    }

    class CategoriesDiffUtil : DiffUtil.ItemCallback<AccountUI>() {
        override fun areItemsTheSame(oldItem: AccountUI, newItem: AccountUI): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AccountUI, newItem: AccountUI): Boolean {
            return oldItem == newItem
        }
    }

}