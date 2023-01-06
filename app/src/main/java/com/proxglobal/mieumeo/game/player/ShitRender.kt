package com.proxglobal.mieumeo.game.player

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.Log
import com.proxglobal.mieumeo.game.model.Shit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap
import kotlin.coroutines.CoroutineContext

class ShitRender(private val xPosition: Float) : CoroutineScope {
    val listShit = ConcurrentHashMap<Shit, Long>()
    var isPlaying = false

    var paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
    }

    fun startRender() {
        isPlaying = true
        launch {
            while (isPlaying) {
                listShit.put(
                    Shit(x =  xPosition).apply {
                        val h = Resources.getSystem().displayMetrics.heightPixels.toFloat()
                        run(h) {
                            listShit.remove(this)
                        }
                    }, System.currentTimeMillis()
                )

                delay((400..800).random().toLong())
            }
        }
    }

    fun draw(canvas: Canvas) {
        listShit.forEach {
            canvas.drawRect(it.key.getRectF(), paint)
        }
    }

    fun stopRender() {
        isPlaying = false
    }

    fun haveShitInRect(rect: RectF): Boolean {
        return listShit.keys.find { if (it.getRectF().intersect(rect)){
            Log.d("Gamee", "${listShit.keys.indexOf(it)}")
            true
        } else false
        } != null
    }

    fun RectF.isOverlapping(other: RectF): Boolean {
        if (this.top > other.bottom || this.bottom < other.top) {
            return false
        }
        return !(this.right > other.left || this.left < other.right)
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default
}