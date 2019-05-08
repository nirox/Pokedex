class PokemonApiConstants {
    companion object {
        private const val VERSION = "/api/v2"
        const val API_POKEAPI_POKEMON = "$VERSION/pokemon/"
        const val API_POKEAPI_SPECIE = "$VERSION/pokemon-species/{id}"
        const val API_POKEAPI_EVOLUTION = "$VERSION/evolution-chain/{id}"
        const val API_POKEAPI_POKEMON_DETAIL = "$VERSION/pokemon/{id}"
        const val BASE_URL = "https://pokeapi.co/"
        const val IMAGE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/%1\$d.png"
        const val LIMIT = 50
        const val MAX_POKEMON_LIMIT = 807
        const val DEFAULT_LANGUAGE = "en"
        const val DEFAULT_VERSION_GAME = "red"
    }
}