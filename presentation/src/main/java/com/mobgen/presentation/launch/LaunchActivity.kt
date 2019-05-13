package com.mobgen.presentation.launch

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mobgen.presentation.R
import com.mobgen.presentation.game.GameActivity
import com.mobgen.presentation.pokedex.PokedexActivity
import kotlinx.android.synthetic.main.activity_launch.*

class LaunchActivity : AppCompatActivity() {

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