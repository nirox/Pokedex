package com.mobgen.presentation.launch

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.ar.core.ArCoreApk
import com.mobgen.presentation.R
import com.mobgen.presentation.game.GameActivity
import com.mobgen.presentation.pokedex.PokedexActivity
import kotlinx.android.synthetic.main.activity_launch.*


class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        initListener()
        checkArSupport()

    }

    private fun checkArSupport() {
        val availability = ArCoreApk.getInstance().checkAvailability(this)
        if (availability.isTransient) {
            // Re-query at 5Hz while compatibility is checked in the background.
            Handler().postDelayed(Runnable { checkArSupport() }, 200)
        }
        if (availability.isSupported) {
            Log.v("mio", "supported")
        } else { // Unsupported or unknown.
            Log.v("mio", "unsupported")
        }
    }



    private fun goToGame() {
        startActivity(GameActivity.newInstance(this))
    }

    private fun goToPokedex() {
        startActivity(PokedexActivity.newInstance(this))
    }

    private fun initListener() {
        gameButton.setOnClickListener {
            goToGame()
        }

        pokedexButton.setOnClickListener {
            goToPokedex()
        }
    }
}