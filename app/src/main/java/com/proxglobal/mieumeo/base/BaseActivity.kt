package com.proxglobal.mieumeo.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<V: ViewBinding> : AppCompatActivity() {
    protected lateinit var binding: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getDataBinding()
        setContentView(binding.root)
        initViewModel()
        initView()
        addListener()
        addObservers()
        initData()
    }

    open fun initData(){}

    open fun addObservers(){}

    open fun addListener(){}

    open fun initView(){}

    open fun initViewModel() {
    }

    abstract fun getDataBinding(): V
}