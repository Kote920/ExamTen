package com.example.examten.presentation.fragments

import android.opengl.Visibility
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.examten.R
import com.example.examten.data.common.Resource
import com.example.examten.databinding.FragmentBottomSheetBinding
import com.example.examten.databinding.FragmentToAccountBottomSheetBinding
import com.example.examten.presentation.utils.TransferMethods
import com.example.examten.presentation.utils.listenersUtils.Listener
import com.example.examten.presentation.viewModels.ToAccountViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ToAccountBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentToAccountBottomSheetBinding? = null
    private val binding get() = _binding!!

    private var choiceHolder: TransferMethods? = null
    private val viewModel: ToAccountViewModel by viewModels()
//    private lateinit var accountsAdapter: AccountsAdapter


    private var listener: Listener? = null
    fun setListener(listener: Listener) {
        this.listener = listener
    }


    private fun setUp() {
        setUpListeners()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentToAccountBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
    }


    private fun setUpListeners() {

        binding.apply {
            btnWithAccNum.setOnClickListener {
                etInput.hint = "Enter Account Number"
                etInput.visibility = View.VISIBLE
                choiceHolder = TransferMethods.ACCOUNT_NUMBER
            }

            btnWithPN.setOnClickListener {
                etInput.hint = "Enter PN"
                etInput.visibility = View.VISIBLE
                choiceHolder = TransferMethods.PN
            }

            btnWithMobileNum.setOnClickListener {
                etInput.hint = "Enter Mobile Number"
                etInput.visibility = View.VISIBLE
                choiceHolder = TransferMethods.MOBILE_NUMBER
            }

            btnSubmit.setOnClickListener {
                if (choiceHolder == TransferMethods.ACCOUNT_NUMBER){
                    viewModel.validateAccountNumber(etInput.text.toString())
                    bindObserves()
                }


                etInput.visibility = View.GONE
                btnSubmit.visibility = View.GONE
                choiceHolder = null

            }

            etInput.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // Code to run before the text is changed
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // Code to run when the text is changing
                }

                override fun afterTextChanged(s: Editable?) {
                    when(choiceHolder){
                        TransferMethods.MOBILE_NUMBER -> {
                            if(!s.isNullOrEmpty() && s.length == 9  ){
                                btnSubmit.visibility = View.VISIBLE
                            }
                        }
                        TransferMethods.ACCOUNT_NUMBER -> {
                            if(!s.isNullOrEmpty() && s.length == 23  ){
                                btnSubmit.visibility = View.VISIBLE
                            }
                        }
                        TransferMethods.PN -> {
                            if(!s.isNullOrEmpty() && s.length == 11  ){
                                btnSubmit.visibility = View.VISIBLE
                            }
                        }else->{
                            btnSubmit.visibility = View.GONE
                        }
                    }
                }
            })
        }



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun onAccepted(){
        binding.apply {
            etInput.visibility = View.GONE
            btnSubmit.visibility = View.GONE
            choiceHolder = null
        }

    }

    private fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.validationFlow.collect() {
                    when (it) {

                        is Resource.Loading -> {
                            binding.pbValidation.visibility = View.VISIBLE
                        }

                        is Resource.Success -> {
                            binding.pbValidation.visibility = View.GONE
                            val res = it.responseData
                           if (res == null){
                               Toast.makeText(requireContext(), "Invalid Account number", Toast.LENGTH_SHORT).show()
                           }else{
                               onAccepted()
                               binding.apply {
                                   listener?.onOptionSelected(res)
                                   dismiss()
                               }
                           }


                        }

                        is Resource.Failed -> {
                            binding.pbValidation.visibility = View.GONE
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