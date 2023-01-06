package com.proxglobal.mieumeo.game.model

import android.graphics.RectF
import android.util.Log

data class Shit(
    val width: Int = 100,
    val height: Int = 100,
    val x: Float = 0f,
    var y: Float = -0f,
    var image: Int = 0
) {
    private var runningThread: Thread? = null
    fun run(target: Float, onFinished: () -> Unit) {
        runningThread = Thread {
            while (y < target) {
                Thread.sleep(3)
                y += 1
            }
            onFinished()
            runningThread!!.interrupt()
            runningThread = null
        }
        runningThread!!.start()
    }

    fun getRectF(): RectF {
        val rectF = RectF()
        rectF.left = x
        rectF.top = y
        rectF.right = x + width
        rectF.bottom = y + height
        Log.d("Gamee", "getRectF: $rectF")
        return rectF
    }
}