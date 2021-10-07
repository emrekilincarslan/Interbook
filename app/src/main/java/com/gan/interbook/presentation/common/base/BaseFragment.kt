package com.gan.interbook.presentation.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.gan.interbook.presentation.common.ErrorConsumer

abstract class BaseFragment<BINDING : ViewBinding> : Fragment() {
    private lateinit var binding: BINDING

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflateBinding(inflater, container)
        return binding.root
    }

    abstract fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): BINDING

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onViewCreated(binding)
    }

    abstract fun onViewCreated(binding: BINDING)

    fun displayErrorDialog(message: String) {
        (activity as? ErrorConsumer)?.displayErrorDialog(message)
    }
}