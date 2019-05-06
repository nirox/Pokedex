package com.mobgen.presentation.launch

import android.os.Bundle
import com.mobgen.presentation.R
import com.mobgen.presentation.game.GameActivity
import com.mobgen.presentation.pokedex.PokedexActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_launch.*

class LaunchActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        initListener()

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