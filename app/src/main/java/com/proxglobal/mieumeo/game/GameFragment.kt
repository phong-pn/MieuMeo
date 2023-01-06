package com.proxglobal.mieumeo.game

import android.content.res.Resources
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.RectF
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import androidx.lifecycle.lifecycleScope
import com.proxglobal.mieumeo.base.BaseFragment
import com.proxglobal.mieumeo.databinding.FragmentGameBinding
import com.proxglobal.mieumeo.game.player.ShitRender
import com.proxglobal.mieumeo.util.boundingBox
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class GameFragment : BaseFragment<FragmentGameBinding>() {
    override fun getDataBinding(): FragmentGameBinding {
        return FragmentGameBinding.inflate(layoutInflater)
    }

    private var xGesture = 0f
    private var yGesture = 0f
    private var positions: List<Float>
    private var humanPosition: Int
    init {
        val w = Resources.getSystem().displayMetrics.widthPixels.toFloat()
        positions = listOf(
            w / 8f,
            3 * w / 8f,
            5 * w / 8f,
            7 * w / 8f
        )
        humanPosition = 2
    }

    var isPlaying = false
    lateinit var listRender: List<ShitRender>
    override fun initView() {
        binding.surface.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                initRender()
                startGame()
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                isPlaying = false
                listRender.forEach { it.stopRender() }
            }

        })
    }

    private fun startGame() {
        lifecycleScope.launch(Dispatchers.Main) {
            isPlaying = true
            listRender.forEach { it.startRender() }

            while (isActive && isPlaying) {
                binding.surface.holder.apply {
                    val canvas = lockCanvas()
                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
                    listRender.forEach {
                        if (listRender.indexOf(it) == 0) {
                            if (it.haveShitInRect(binding.human.boundingBox)) {
                                isPlaying = false
                                Log.d("Gamee", "overlay")
                                Log.d("Gamee", "human: ${binding.human.boundingBox}")
                                Log.d("Gamee", "addListener: ${listRender[0].listShit.keys.iterator().next().getRectF()}")
                            }
                        }
                        it.draw(canvas)
                    }
                    unlockCanvasAndPost(canvas)
                }
                Log.d("Gamee", "addListener: ${listRender.get(0).listShit.keys.iterator().next().getRectF()}")
                delay(12)
            }
        }
    }

    private fun initRender() {
        listRender = positions.map { ShitRender(it) }
    }


    override fun addListener() {
        super.addListener()
        binding.surface.setOnTouchListener { v, event ->
            if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                xGesture = event.x
            }
            if (event.actionMasked == MotionEvent.ACTION_UP) {
                if (event.x - xGesture > 0) {
                    if (humanPosition < positions.size - 1) {
                        humanPosition ++
                        binding.human.x = positions[humanPosition] - binding.human.width / 2
                    }
                } else if (event.x - xGesture < 0) {
                    if (humanPosition > 0) {
                        humanPosition--
                        binding.human.x = positions[humanPosition] - binding.human.width / 2
                    }
                } else {
                    isPlaying = !isPlaying
                    Log.d("Gamee", "overlap")
                    Log.d("Gamee", "human: ${binding.human.boundingBox}")
                    Log.d("Gamee", "addListener: ${listRender.get(0).listShit.keys.iterator().next().getRectF()}")
                }
            }
            true
        }
    }
}