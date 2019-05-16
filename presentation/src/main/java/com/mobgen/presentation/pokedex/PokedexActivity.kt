package com.mobgen.presentation.pokedex

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.mobgen.presentation.BaseViewModel
import com.mobgen.presentation.R
import com.mobgen.presentation.pokedex.pokemonDetail.PokedexActivityListener
import com.mobgen.presentation.pokedex.pokemonDetail.PokemonDetailFragment
import kotlinx.android.synthetic.main.activity_pokedex.*
import org.koin.android.viewmodel.ext.android.viewModel

class PokedexActivity : AppCompatActivity(), PokedexActivityListener {


    private val viewModel by viewModel<PokedexViewModel>()
    lateinit var adapter: PokedexAdapter
    private var fragmentOpen = false

    companion object {
        fun newInstance(context: Context) =
            Intent(context, PokedexActivity::class.java).apply { addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP) }

        private const val SCROLL_RANGE_TO_NEXT_PAGE = 4
        private const val COLS_NUMBER_GRID = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokedex)

        viewModel.data.observe(this, Observer {
            it?.let { data ->
                when (data.status) {
                    BaseViewModel.Status.LOADING -> {
                        load.visibility = View.VISIBLE
                    }
                    BaseViewModel.Status.SUCCESS -> {
                        load.visibility = View.GONE
                        adapter.setData(data.pokemons)
                    }
                    BaseViewModel.Status.ERROR -> {
                        Toast.makeText(this, getString(R.string.checkConnection), Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
        Glide.with(this).load(getDrawable(R.drawable.pokeball_gif)).into(load)
        initListener()
        listLineal()
        viewModel.getPokemons()
    }

    override fun onBack() {
        onBackPressed()
        fragmentOpen = false
    }

    override fun goToPokemonDetail(id: Long) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right,
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right
            )
            .add(R.id.main, PokemonDetailFragment.newInstance(id))
            .addToBackStack(null)
            .commit()
    }

    private fun initListener() {
        backButton.setOnClickListener {
            onBackPressed()
        }
        changeViewList.setOnClickListener {
            listLineal()
            it.visibility = View.GONE
            changeViewGrid.visibility = View.VISIBLE
        }

        changeViewGrid.setOnClickListener {
            listGrid()
            it.visibility = View.GONE
            changeViewList.visibility = View.VISIBLE
        }

        pokedexList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = pokedexList.layoutManager?.childCount ?: 0
                val totalItemCount = pokedexList.layoutManager?.itemCount ?: 0
                val firstVisibleItemPosition =
                    if (pokedexList.layoutManager is LinearLayoutManager) (pokedexList.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    else (pokedexList.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount - SCROLL_RANGE_TO_NEXT_PAGE
                    && firstVisibleItemPosition >= 0 && viewModel.data.value?.status == BaseViewModel.Status.SUCCESS
                ) {
                    viewModel.getPokemons()
                }
            }

        })
    }

    private fun listGrid() {
        adapter = PokedexAdapter(
            PokedexAdapter.TYPE_GRID,
            viewModel.data.value?.pokemons ?: listOf(),
            object : PokedexAdapter.OnClickItemListener {
                override fun onClickItem(id: Long) {
                    if (!fragmentOpen) {
                        goToPokemonDetail(id)
                        fragmentOpen = true
                    }
                }

            })
        pokedexList.layoutManager = GridLayoutManager(this, COLS_NUMBER_GRID)

        pokedexList.adapter = adapter
    }

    private fun listLineal() {
        adapter = PokedexAdapter(
            PokedexAdapter.TYPE_LIST,
            viewModel.data.value?.pokemons ?: listOf(),
            object : PokedexAdapter.OnClickItemListener {
                override fun onClickItem(id: Long) {
                    if (!fragmentOpen) {
                        goToPokemonDetail(id)
                        fragmentOpen = true
                    }
                }

            })
        pokedexList.layoutManager = LinearLayoutManager(this)

        pokedexList.adapter = adapter
    }

}
