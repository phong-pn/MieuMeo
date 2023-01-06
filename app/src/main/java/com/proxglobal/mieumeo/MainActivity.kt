package com.proxglobal.mieumeo

import ShakeDetector
import ShakeDetector.OnShakeListener
import android.animation.ObjectAnimator
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.daimajia.androidanimations.library.BaseViewAnimator
import com.daimajia.androidanimations.library.YoYo
import com.proxglobal.mieumeo.base.BaseActivity
import com.proxglobal.mieumeo.databinding.ActivityMainBinding
import com.proxglobal.mieumeo.game.GameFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var mSensorManager: SensorManager
    private lateinit var mAccelerometer: Sensor
    private lateinit var mShakeDetector: ShakeDetector
    private var rotation = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager
            .getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mShakeDetector = ShakeDetector()
        mShakeDetector.setOnShakeListener(object : OnShakeListener {
            override fun onShake(count: Int) {

            }

            override fun onShakeChange(gForce: Float) {
                if (gForce > 1.4f) {
                    Log.d("ShakeDetector", "gForce = $gForce")
                    rotation = gForce
                    lifecycleScope.launch(Dispatchers.Main) {
                        handleShakeEvent()
                    }
                }
            }
        })
        supportFragmentManager.beginTransaction()
            .add(R.id.main_container, GameFragment(), null)
            .commit()

    }


    private fun handleShakeEvent() {
        YoYo.with(object : BaseViewAnimator() {
            override fun prepare(target: View) {
                animatorAgent.playTogether(
                    ObjectAnimator.ofFloat(target, "scaleX", rotation, (rotation - 1f) / 2, 1f),
                    ObjectAnimator.ofFloat(target, "scaleY", rotation, (rotation - 1f) / 2, 1f)
                )
            }
        })
            .duration(100)
            .repeat(4)
            .onRepeat {
                Log.d("ShakeDetector", "repeat anim")
            }
            .onEnd { rotation = 1f }
            .playOn(binding.body)
    }

    override fun onResume() {
        super.onResume()
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(
            mShakeDetector,
            mAccelerometer,
            SensorManager.SENSOR_DELAY_UI
        )
    }

    override fun onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector)
        super.onPause()
    }

    override fun getDataBinding() = ActivityMainBinding.inflate(layoutInflater)
}