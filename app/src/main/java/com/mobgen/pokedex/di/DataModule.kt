package com.mobgen.pokedex.di

import PokemonApiConstants
import com.mobgen.data.mapper.EvolutionsDataMapper
import com.mobgen.data.mapper.PokemonDataMapper
import com.mobgen.data.mapper.PokemonDetailsDataMapper
import com.mobgen.data.repository.PokemonRepositoryImpl
import com.mobgen.data.repository.service.PokemonService
import com.mobgen.data.repository.service.SpeciesService
import com.mobgen.domain.PokemonRepository
import com.mobgen.pokedex.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


val dataModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }
    single { providePokemonService(get()) }
    single { provideSpeciesService(get()) }
    single { PokemonDataMapper() }
    single { EvolutionsDataMapper() }
    single { PokemonDetailsDataMapper(get()) }
    single { PokemonRepositoryImpl(get(), get(), get(), get()) as PokemonRepository }
}

fun provideOkHttpClient(): OkHttpClient = OkHttpClient().newBuilder()
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE
    })
    .build()

fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
    .baseUrl(PokemonApiConstants.BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .client(client)
    .build()

fun providePokemonService(retrofit: Retrofit): PokemonService = retrofit.create(PokemonService::class.java)

fun provideSpeciesService(retrofit: Retrofit): SpeciesService = retrofit.create(SpeciesService::class.java)

/*@Provides
internal fun provideGson(): Gson {
    return GsonBuilder().create()
}*/




