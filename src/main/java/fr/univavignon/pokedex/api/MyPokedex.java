package fr.univavignon.pokedex.api;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MyPokedex implements IPokedex {

    private IPokemonMetadataProvider metadataProvider;
    private IPokemonFactory pokemonFactory;
    private List<Pokemon> pokemons;

    /**
     * @param metadataProvider
     * @param pokemonFactory
     */
    public MyPokedex(IPokemonMetadataProvider metadataProvider, IPokemonFactory pokemonFactory) {
        this.metadataProvider = metadataProvider;
        this.pokemonFactory = pokemonFactory;
        this.pokemons = new ArrayList<>();
    }

    /**
     * @param index Index of the pokemon to retrieve metadata for.
     * @return
     * @throws PokedexException
     */
    @Override
    public PokemonMetadata getPokemonMetadata(int index) throws PokedexException {
        return metadataProvider.getPokemonMetadata(index);
    }

    /**
     * @param index Pokemon index.
     * @param cp Pokemon CP.
     * @param hp Pokemon HP.
     * @param dust Required dust for upgrading pokemon.
     * @param candy Required candy for upgrading pokemon.
     * @return
     */
    @Override
    public Pokemon createPokemon(int index, int cp, int hp, int dust, int candy) {
        return pokemonFactory.createPokemon(index, cp, hp, dust, candy);
    }

    /**
     * @return
     */
    @Override
    public int size() {
        return pokemons.size();
    }

    /**
     * @param pokemon Pokemon to add to this pokedex.
     * @return
     */
    @Override
    public int addPokemon(Pokemon pokemon) {
        pokemons.add(pokemon);
        return pokemons.size() - 1;
    }

    /**
     * @param id Unique pokedex relative identifier.
     * @return
     * @throws PokedexException
     */
    @Override
    public Pokemon getPokemon(int id) throws PokedexException {
        if (id < 0 || id >= pokemons.size()) {
            throw new PokedexException("Invalid Pokemon ID");
        }
        return pokemons.get(id);
    }

    /**
     * @return
     */
    @Override
    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    /**
     * @param order Comparator instance used for sorting the created view.
     * @return
     */
    @Override
    public List<Pokemon> getPokemons(Comparator<Pokemon> order) {
        List<Pokemon> sortedPokemons = new ArrayList<>(pokemons);
        sortedPokemons.sort(order);
        return sortedPokemons;
    }
}
