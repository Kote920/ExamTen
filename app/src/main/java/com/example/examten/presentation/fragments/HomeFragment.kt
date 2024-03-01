package com.example.examten.presentation.fragments

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.examten.data.common.Resource
import com.example.examten.databinding.FragmentHomeBinding
import com.example.examten.presentation.base.BaseFragment
import com.example.examten.presentation.utils.listenersUtils.Listener
import com.example.examten.presentation.model.AccountUI
import com.example.examten.presentation.utils.Currency
import com.example.examten.presentation.viewModels.AccountsViewModel
import com.example.examten.presentation.viewModels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private var currencyFrom: Currency? = null
    private var currencyTo: Currency? = null

    private val viewModel: HomeViewModel by viewModels()


    override fun setUp() {

    }

    override fun listeners() {

        binding.fromAccountContainer.setOnClickListener {
            showAccountsBottomSheet()
        }

        binding.toAccountContainer.setOnClickListener{
            showToAccountBottomSheet()
        }
    }


    private fun showAccountsBottomSheet() {
        val accountsBottomSheetFragment = AccountsBottomSheetFragment().apply {
            setListener(object : Listener {
                override fun onOptionSelected(account: AccountUI) {

                    binding.apply {
                        fromAccountName.text = account.accountName
                        fromAccountNumber.text = account.accountNumber
                        fromAccountBalance.text = account.balance.toString()



                    }
                    when(account.valuteType){
                        "GEL" -> currencyFrom = Currency.GEl
                        "USD" -> currencyFrom = Currency.USD
                        "EUR" -> currencyFrom = Currency.EUR

                    }
                }
            })
        }
        accountsBottomSheetFragment.show(childFragmentManager, accountsBottomSheetFragment.tag)
    }

    private fun showToAccountBottomSheet() {
        val toAccountsBottomSheetFragment = ToAccountBottomSheetFragment().apply {
            setListener(object : Listener {
                override fun onOptionSelected(account: AccountUI) {
                    binding.apply {
                        toAccountNumber.text = account.accountNumber
                        toValuteType.text = account.valuteType


                    }
                    when(account.valuteType){
                        "GEL" -> currencyTo = Currency.GEl
                        "USD" -> currencyTo = Currency.USD
                        "EUR" -> currencyTo = Currency.EUR

                    }
                    if(!checkCurrencies()) {
                        viewModel.getCurrency()
                        bindObserves()
                    }

                }
            })
        }
        toAccountsBottomSheetFragment.show(childFragmentManager, toAccountsBottomSheetFragment.tag)
    }

    private fun checkCurrencies(): Boolean{
        return currencyTo == currencyFrom
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currencyFlow.collect() {
                    when (it) {

                        is Resource.Loading -> {
                            binding.pbCurrency.visibility = View.VISIBLE
                        }

                        is Resource.Success -> {
                            binding.pbCurrency.visibility = View.GONE
                            val res = it.responseData

                            binding.apply {
                                if(!etBuy.text.isNullOrEmpty()){
                                    etSell.text =  res!!.rate * (etBuy.text.toString().toDouble())
                                }
                            }

                        

                        }

                        is Resource.Failed -> {
                            binding.pbCurrency.visibility = View.GONE
                            val errorMessage = it.message
                            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT)
                                .show()

                        }

                    }
                }
            }
        }

    }
}