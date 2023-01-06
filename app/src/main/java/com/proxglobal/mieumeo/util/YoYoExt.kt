package com.proxglobal.mieumeo.util

import android.animation.Animator
import android.view.View
import com.daimajia.androidanimations.library.BaseViewAnimator
import com.daimajia.androidanimations.library.YoYo

fun animTogether(vararg animator: Animator): YoYo.AnimationComposer {
    return YoYo.with(object : BaseViewAnimator() {
        override fun prepare(target: View) {
            animatorAgent.playTogether(animator.toList())
        }
    })
}