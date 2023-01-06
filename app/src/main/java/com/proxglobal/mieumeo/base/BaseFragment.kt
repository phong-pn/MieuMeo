package com.proxglobal.mieumeo.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<V: ViewBinding>: Fragment() {
    protected lateinit var binding: V
    open fun initData(){}

    open fun addObservers(){}

    open fun addListener(){}

    open fun initView(){}

    open fun initViewModel() {
    }

    abstract fun getDataBinding(): V

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getDataBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initView()
        addListener()
        addObservers()
        initData()
    }
}