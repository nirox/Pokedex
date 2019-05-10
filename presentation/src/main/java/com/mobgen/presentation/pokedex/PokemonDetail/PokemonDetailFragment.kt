package com.mobgen.presentation.pokedex.pokemonDetail


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
    var typesText = mutableListOf<TextView>()
    var typesImage = mutableListOf<ImageView>()
    var evolutions = mutableListOf<ImageView>()
    var evolutionArrows = mutableListOf<ImageView>()

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
                        detailBackground.setBackgroundResource(data.pokemon!!.detailBackground)
                    }
                    BaseViewModel.Status.ERROR -> {
                        Toast.makeText(context, getString(R.string.checkConnection), Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
        initListener()
        initView()
        viewModel.getPokemonById(arguments?.getLong(ARG_ID) ?: -1L)
    }

    private fun initView() {
        typesImage.add(pokemonType1Image)
        typesImage.add(pokemonType2Image)
        typesText.add(pokemonType1)
        typesText.add(pokemonType2)
        evolutions.add(evolution1)
        evolutions.add(evolution2)
        evolutions.add(evolution3)
        evolutionArrows.add(evolutionArrow1)
        evolutionArrows.add(evolutionArrow2)
    }

    private fun initListener() {
        backButton.setOnClickListener {
            activityListener.onBack()
        }
    }

    private fun bindData(pokemon: PokemonDetailViewModel.PokemonDetailBindView?) {
        pokemon?.let {
            descriptionLabel.visibility = View.VISIBLE
            pokemonName.text = pokemon.name.capitalize()
            description.text = pokemon.description
            Glide.with(this).load(pokemon.image).into(pokemonImage)
            pokemon.type.forEachIndexed { index, value ->
                typesText[index].text = value.first
                Glide.with(this).load(value.second).into(typesImage[index])
            }
            if (pokemon.evolutions.isNotEmpty()) evolutionLabel.visibility = View.VISIBLE
            pokemon.evolutions.forEachIndexed { index, value ->
                if (index != 0) evolutionArrows[index - 1].visibility = View.VISIBLE
                Glide.with(this).load(value.second).into(evolutions[index].also { it.visibility = View.VISIBLE })
            }
        }
    }
}
