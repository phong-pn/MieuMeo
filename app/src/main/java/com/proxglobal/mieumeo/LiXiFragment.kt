package com.proxglobal.mieumeo

import android.animation.ObjectAnimator
import android.view.animation.LinearInterpolator
import com.proxglobal.mieumeo.base.BaseFragment
import com.proxglobal.mieumeo.databinding.FragmentLaclocBinding
import com.proxglobal.mieumeo.util.animTogether

class LiXiFragment : BaseFragment<FragmentLaclocBinding>() {
    override fun getDataBinding(): FragmentLaclocBinding {
        return FragmentLaclocBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        animTogether(
            ObjectAnimator.ofFloat(binding.loc, "rotation", 0f, 5f, 10f, 5f, 0f, -5f, -10f, -5f, 0f)
        )
            .repeat(100)
            .interpolate(LinearInterpolator())
            .duration(1000)
            .playOn(binding.loc)
    }
}