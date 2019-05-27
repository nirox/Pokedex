package com.mobgen.presentation.pokedex.pokemonDetail


import android.app.ActivityManager
import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.mobgen.presentation.BaseViewModel
import com.mobgen.presentation.R
import com.mobgen.presentation.ar.ArActivity
import com.mobgen.presentation.ar.Util3d
import kotlinx.android.synthetic.main.fragment_pokemon_detail.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class PokemonDetailFragment : Fragment() {

    private val viewModel by viewModel<PokemonDetailViewModel>()
    lateinit var activityListener: PokedexActivityListener
    private val typesText = mutableListOf<TextView>()
    private val typesImage = mutableListOf<ImageView>()
    private val evolutions = mutableListOf<ImageView>()
    private val evolutionArrows = mutableListOf<ImageView>()
    private var id: Long = 0L
    private lateinit var textToSpeech: TextToSpeech

    companion object {
        const val TAG = "PokemonDetailFragment"
        private const val ARG_ID = "id"
        private const val MIN_OPENGL_VERSION = 3.0

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
            Glide.with(this).load(R.drawable.pokeball_gif).into(load)
        }

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
                        speachText()
                    }
                    BaseViewModel.Status.ERROR -> {
                        Toast.makeText(context, getString(R.string.checkConnection), Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
        initListener()
        initView()
        viewModel.getPokemonById(id)

    }


    override fun onDestroy() {
        textToSpeech.stop()
        textToSpeech.shutdown()
        super.onDestroy()
    }

    private fun initView() {
        id = arguments?.getLong(ARG_ID) ?: -1L
        if (!::textToSpeech.isInitialized) textToSpeech =
            TextToSpeech(context, TextToSpeech.OnInitListener {}).apply { language = Locale.ENGLISH }
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
                if (value.id != id) evolutions[index].setOnClickListener {
                    activityListener.goToPokemonDetail(value.id)
                    textToSpeech.stop()
                    textToSpeech.shutdown()
                }
                if (index != 0) evolutionArrows[index - 1].visibility = View.VISIBLE
                Glide.with(this).load(value.image).into(evolutions[index].also { it.visibility = View.VISIBLE })
            }
        }
        pokemon2d.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                if (isSupportedDevice()) {
                    textToSpeech.stop()
                    activityListener.goToPokemonAr(this@PokemonDetailFragment.id, ArActivity.MODE_2D)
                }
            }
        }
        pokemon3d.apply {
            if (Util3d.Pokemon.values().firstOrNull { value -> value.pName == pokemon?.name } != null) visibility =
                View.VISIBLE
            setOnClickListener {
                if (isSupportedDevice()) {
                    textToSpeech.stop()
                    activityListener.goToPokemonAr(this@PokemonDetailFragment.id)
                }
            }
        }
    }


    private fun speachText() {
        val text =
            String.format(getString(R.string.speechText), pokemonName.text.toString(), description.text.toString())
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun isSupportedDevice(): Boolean {
        val openGlVersionString = (context?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
            .deviceConfigurationInfo
            .glEsVersion
        if (java.lang.Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Toast.makeText(context, getString(R.string.openGLRequired), Toast.LENGTH_LONG)
                .show()
            return false
        }
        return true
    }

}
