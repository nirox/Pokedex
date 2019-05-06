package com.mobgen.presentation.pokedex.pokemonDetail


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.mobgen.presentation.BaseViewModel
import com.mobgen.presentation.R
import com.mobgen.presentation.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_pokemon_detail.*
import javax.inject.Inject

class PokemonDetailFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: PokemonDetailViewModel
    lateinit var activityListener: PokedexActivityListener

    companion object {
        const val TAG = "PokemonDetailFragment"
        private const val ARG_ID = "id"

        fun newInstance(id: Long): PokemonDetailFragment =
            PokemonDetailFragment().apply { arguments = Bundle().apply { putLong(ARG_ID, id) } }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pokemon_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        context?.let {
            activityListener = activity as PokedexActivityListener
            Glide.with(this).load(it.getDrawable(R.drawable.pokeball_gif)).into(load)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PokemonDetailViewModel::class.java)

        viewModel.data.observe(this, Observer {
            it?.let { data ->
                when (data.status) {
                    BaseViewModel.Status.LOADING -> {
                        load.visibility = View.VISIBLE
                    }
                    BaseViewModel.Status.SUCCESS -> {
                        load.visibility = View.GONE
                        bindData(data.pokemon)
                    }
                    BaseViewModel.Status.ERROR -> {
                        Toast.makeText(context, getString(R.string.checkConnection), Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
        initListener()

        viewModel.getPokemonById(arguments?.getLong(ARG_ID) ?: -1L)
    }

    private fun initListener() {
        backButton.setOnClickListener {
            activityListener.onBack()
        }
    }

    private fun bindData(pokemon: PokemonDetailViewModel.PokemonDetailBindView?) {
        pokemon?.let {
            pokemonName.text = pokemon.name
            description.text = pokemon.description
            Glide.with(this).load(pokemon.image).into(pokemonImage)
            pokemonType1.text = pokemon.type.first().first
            Glide.with(this).load(pokemon.type.first().second).into(pokemonType1Image)
            pokemonType2.text = pokemon.type.last().first
            Glide.with(this).load(pokemon.type.last().second).into(pokemonType2Image)
            if (pokemon.evolutions.isNotEmpty()) {
                evolutionLabel.visibility = View.VISIBLE
                evolution1.visibility = View.VISIBLE
                Glide.with(this).load(pokemon.evolutions.first().second).into(evolution1)
                if (pokemon.evolutions.size >= 2) {
                    evolution2.visibility = View.VISIBLE
                    evolutionArrow1.visibility = View.VISIBLE
                    Glide.with(this).load(pokemon.evolutions.first().second).into(evolution2)
                    if (pokemon.evolutions.size >= 3) {
                        evolutionArrow2.visibility = View.VISIBLE
                        evolution3.visibility = View.VISIBLE
                        Glide.with(this).load(pokemon.evolutions.first().second).into(evolution2)
                    }
                }
            }
        }
    }
}
