package com.proxglobal.mieumeo.util

import android.animation.ObjectAnimator
import android.graphics.Rect
import android.graphics.RectF
import android.view.MotionEvent
import android.view.TouchDelegate
import android.view.View
import com.daimajia.androidanimations.library.BaseViewAnimator
import com.daimajia.androidanimations.library.YoYo

fun View.isVisible(): Boolean {
    return visibility == View.VISIBLE
}

fun View.isInvisible(): Boolean {
    return visibility == View.INVISIBLE
}

fun View.isGone(): Boolean {
    return visibility == View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}


fun View.setOnPressListener(
    onPress: (view: View) -> Unit,
    onRelease: (view: View) -> Unit
) {
    setOnTouchListener { v, event ->
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                onPress(v)
            }
            MotionEvent.ACTION_UP -> {
                onRelease(v)
            }
        }
        false
    }
}

fun View.setOnClickListenerWithScaleAnimation(onClick: (View) -> Unit) {
    val composer = YoYo.with(ScaleSmall()).duration(50)
    var yoYoString: YoYo.YoYoString? = null
    setOnPressListener(
        onPress = {
            yoYoString = composer.playOn(this)
        },
        onRelease = {
            yoYoString?.stop(true)
        }
    )
    setOnClickListener {
        onClick(it)
    }
}

fun View.increaseClickArea(size: Int) {
    (parent as View).post {
        val r = Rect()
        getHitRect(r)
        r.top -= size
        r.bottom += size
        r.left -= size
        r.right += size
        (parent as View).touchDelegate = TouchDelegate(r, this)
    }
}

val View.screenLocation
    get(): IntArray {
        val point = IntArray(2)
        getLocationOnScreen(point)
        return point
    }

val View.boundingBox
    get(): RectF {
        val (x, y) = screenLocation
        return RectF(x.toFloat(), y.toFloat(), (x + width).toFloat(), (y + height).toFloat())
    }

class ScaleSmall : BaseViewAnimator() {
    override fun prepare(target: View?) {
        animatorAgent.playTogether(
            ObjectAnimator.ofFloat(target, "scaleY", 1f, 0.9f),
            ObjectAnimator.ofFloat(target, "scaleX", 1f, 0.9f)
        )
    }
}