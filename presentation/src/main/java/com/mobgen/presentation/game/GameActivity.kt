package com.mobgen.presentation.game

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.bumptech.glide.Glide
import com.mobgen.presentation.BaseViewModel
import com.mobgen.presentation.R
import kotlinx.android.synthetic.main.activity_game.*
import org.koin.android.viewmodel.ext.android.viewModel

class GameActivity : AppCompatActivity() {


    private val viewModel by viewModel<GameViewModel>()
    private val pokemonButtons = mutableListOf<Button>()
    private var pokemonTarget: String = String()
    private var round = 0
    private var points = 0

    companion object {
        fun newInstance(context: Context) =
            Intent(context, GameActivity::class.java).apply { addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP) }

        const val POKEMON_GENERATE_PER_ROUND = 4
        const val TOTAL_ROUNDS = 10
        const val TIME_TO_RESET = 1000L
        const val TEXT_FORMAT = "%d%s"
        const val MEDIUM_ALPHA = 0.5F
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        viewModel.data.observe(this, Observer {
            it?.let { data ->
                if (data.status == BaseViewModel.Status.SUCCESS) {
                    load.visibility = View.GONE
                    resetView()
                    bindPokemon(data.randomPokemons)
                }
                if (data.status == BaseViewModel.Status.ERROR) {
                    Toast.makeText(this, getString(R.string.checkConnection), Toast.LENGTH_LONG).show()
                }
            }
        })
        Glide.with(this).load(getDrawable(R.drawable.pokeball_gif)).into(load)
        initView()
        initListeners()
        viewModel.getPokemon()
    }

    private fun initView() {
        roundCounter.text = String.format(TEXT_FORMAT, round, String.format(getString(R.string.round), TOTAL_ROUNDS))
        pokemonButtons.add(pokemon1)
        pokemonButtons.add(pokemon2)
        pokemonButtons.add(pokemon3)
        pokemonButtons.add(pokemon4)
    }

    private fun bindPokemon(pokemons: List<GameViewModel.PokemonRandomBindView>) {
        round++
        Glide.with(this).load(pokemons.first().image).into(pokemonImage)
        pokemonImage.visibility = View.VISIBLE
        pokemonTarget = pokemons.first().name
        pokemons.shuffled().forEachIndexed { index, pokemonRandomBindView ->
            pokemonButtons[index].text = pokemonRandomBindView.name
        }
    }

    private fun initListeners() {
        backButton.setOnClickListener {
            onBackPressed()
        }

        restart.setOnClickListener {
            resetGame()
        }

        pokemonButtons.forEach { button ->
            button.setOnClickListener {
                if (viewModel.data.value?.status != BaseViewModel.Status.LOADING) {
                    pokemonImage.setColorFilter(getColor(R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY)

                    if (round < TOTAL_ROUNDS) {
                        roundCounter.text =
                            String.format(TEXT_FORMAT, round, String.format(getString(R.string.round), TOTAL_ROUNDS))
                        viewModel.getPokemon()
                    }
                    if (round <= TOTAL_ROUNDS) checkPokemonName(button, button.text.toString())
                    if (round == TOTAL_ROUNDS) finishGame()
                }
            }
        }
    }

    private fun finishGame() {
        round++
        pokemonImage.alpha = MEDIUM_ALPHA
        roundCounter.text = ""
        totalPoints.text = String.format(getString(R.string.points), points)
        restart.visibility = View.VISIBLE
    }

    private fun checkPokemonName(button: Button, name: String) {
        if (pokemonTarget != name) {
            button.background = getDrawable(R.drawable.button_round_red)
            pokemonButtons.first { it.text.toString() == pokemonTarget }.background =
                getDrawable(R.drawable.button_round_green)
        } else {
            button.background = getDrawable(R.drawable.button_round_green)
            points++
        }
    }

    private fun resetView() {
        pokemonButtons.forEach { button ->
            button.background = getDrawable(R.drawable.button_round)
        }
        pokemonImage.setColorFilter(
            getColor(R.color.black),
            android.graphics.PorterDuff.Mode.MULTIPLY
        )
    }

    private fun resetGame() {
        load.visibility = View.VISIBLE
        restart.visibility = View.GONE
        round = 0
        points = 0
        pokemonButtons.forEach { it.text = "" }
        pokemonImage.alpha = 1F
        pokemonImage.visibility = View.GONE
        roundCounter.text =
            String.format(TEXT_FORMAT, round, String.format(getString(R.string.round), TOTAL_ROUNDS))
        totalPoints.text = ""
        resetView()
        viewModel.getPokemon()
    }
}
