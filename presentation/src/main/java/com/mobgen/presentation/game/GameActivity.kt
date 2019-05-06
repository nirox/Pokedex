package com.mobgen.presentation.game

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.mobgen.presentation.R
import com.mobgen.presentation.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_game.*
import javax.inject.Inject


class GameActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: GameViewModel

    companion object {
        fun newInstance(context: Context) = Intent(context, GameActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GameViewModel::class.java)

        viewModel.data.observe(this, Observer {
            it?.let {}
        })
        Glide.with(this).load(getDrawable(R.drawable.pokeball_gif)).into(load)
        initListeners()
    }

    private fun initListeners() {
        backButton.setOnClickListener {
            onBackPressed()
        }
    }


}
