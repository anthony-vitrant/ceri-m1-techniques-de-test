package fr.univavignon.pokedex.api;

import java.util.Comparator;
import java.util.List;

/**
 * Factory interface for class that aims to create IPokedex instance.
 * @author fv
 */
public interface IPokedexFactory extends IPokedex {

	/**
	 * Creates a new pokedex instance using the given 
	 * <code>metadataProvider</code> and <code>pokemonFactory</code>.
	 * @param metadataProvider Metadata provider the created pokedex will use.
	 * @param pokemonFactory Pokemon factory the created pokedex will use.
	 * @return Created pokedex instance.
	 */
	IPokedex createPokedex(IPokemonMetadataProvider metadataProvider, IPokemonFactory pokemonFactory);

	@Override
	int size();

	@Override
	int addPokemon(Pokemon pokemon);

	@Override
	Pokemon getPokemon(int id) throws PokedexException;

	@Override
	List<Pokemon> getPokemons();

	@Override
	List<Pokemon> getPokemons(Comparator<Pokemon> order);

	@Override
	Pokemon createPokemon(int index, int cp, int hp, int dust, int candy);

	@Override
	PokemonMetadata getPokemonMetadata(int index) throws PokedexException;
}
