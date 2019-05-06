package com.mobgen.presentation.launch

import android.os.Bundle
import com.mobgen.domain.subscribe
import com.mobgen.domain.useCase.GetPokemonDetails
import com.mobgen.domain.useCase.GetPokemons
import com.mobgen.domain.useCase.GetRandomPokemons
import com.mobgen.presentation.R
import com.mobgen.presentation.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class LaunchActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: LaunchViewModel

    @Inject
    lateinit var getPokemons: GetPokemons

    @Inject
    lateinit var getPokemonDetails: GetPokemonDetails
    @Inject
    lateinit var getRandomPokemons: GetRandomPokemons

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.launch_activity)

        /*getRandomPokemons.execute(3).subscribe(
            executor = AndroidSchedulers.mainThread(),
            onError = {
                println()
                throw it
            },
            onSuccess = {
                println()
            }
        )*/
/*
        getPokemons.execute().subscribe(
            executor = AndroidSchedulers.mainThread(),
            onError = {
                println()
                throw it
            },
            onSuccess = {
                println()
                getPokemons.next().subscribe(
                    executor = AndroidSchedulers.mainThread(),
                    onError = {
                        println()
                        throw it
                    },
                    onSuccess = {
                        println()
                    }

                )
            }

        )
*/
        getPokemonDetails.execute("2").subscribe(
            executor = AndroidSchedulers.mainThread(),
            onError = {
                println()
                throw it
            },
            onSuccess = {
                println()
            }

        )


    }
}