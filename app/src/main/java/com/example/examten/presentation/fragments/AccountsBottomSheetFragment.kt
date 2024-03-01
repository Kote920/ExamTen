package com.example.examten.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examten.data.common.Resource
import com.example.examten.databinding.FragmentBottomSheetBinding
import com.example.examten.presentation.adapter.AccountsAdapter
import com.example.examten.presentation.utils.listenersUtils.Listener
import com.example.examten.presentation.viewModels.AccountsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AccountsBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AccountsViewModel by viewModels()
    private lateinit var accountsAdapter: AccountsAdapter


    private var listener: Listener? = null
    fun setListener(listener: Listener) {
        this.listener = listener
    }


    private fun setUp() {
        setUpListeners()
        setUpRecycler()
        viewModel.getAccounts()
        bindObserves()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
    }


    private fun setUpListeners() {


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpRecycler() {
        accountsAdapter = AccountsAdapter {
            listener?.onOptionSelected(it)
            dismiss()
        }

        binding.apply {
            accountsRecyclerView.adapter = accountsAdapter
            accountsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.accountsFlow.collect() {
                    when (it) {

                        is Resource.Loading -> {
                            binding.pbAccounts.visibility = View.VISIBLE
                        }

                        is Resource.Success -> {
                            binding.pbAccounts.visibility = View.GONE
                            val res = it.responseData
                            accountsAdapter.submitList(res)

                        }

                        is Resource.Failed -> {
                            binding.pbAccounts.visibility = View.GONE
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