package fr.univavignon.pokedex.api;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import fr.univavignon.pokedex.api.IPokedex;
import fr.univavignon.pokedex.api.IPokemonFactory;
import fr.univavignon.pokedex.api.IPokemonMetadataProvider;
import fr.univavignon.pokedex.api.PokedexException;
import fr.univavignon.pokedex.api.Pokemon;
import fr.univavignon.pokedex.api.PokemonMetadata;

public class IPokedexTest {
    @Mock
    private IPokemonMetadataProvider metadataProvider;

    @Mock
    private IPokemonFactory pokemonFactory;

    private IPokedex pokedex;

    @Before
    public void setUp() throws PokedexException {
        MockitoAnnotations.initMocks(this);

        when(metadataProvider.getPokemonMetadata(0)).thenReturn(new PokemonMetadata(0, "Bulbasaur", 126, 126, 90));
        when(pokemonFactory.createPokemon(0, 613, 64, 200, 1)).thenReturn(new Pokemon(0, "Bulbasaur", 126, 126, 90, 613, 64, 200, 1, 0.82));
        when(metadataProvider.getPokemonMetadata(1)).thenReturn(new PokemonMetadata(1, "Ivysaur", 156, 158, 120));
        when(pokemonFactory.createPokemon(1, 896, 100, 400, 2)).thenReturn(new Pokemon(1, "Ivysaur", 156, 158, 120, 896, 100, 400, 2, 0.67));

        pokedex = new IPokedex() {
            private List<Pokemon> pokemons = new ArrayList<>();

            @Override
            public int size() {
                return pokemons.size();
            }

            @Override
            public int addPokemon(Pokemon pokemon) {
                pokemons.add(pokemon);
                return pokemons.indexOf(pokemon);
            }

            @Override
            public Pokemon getPokemon(int id) throws PokedexException {
                if (id < 0 || id >= pokemons.size()) {
                    throw new PokedexException("Invalid index: " + id);
                }
                return pokemons.get(id);
            }

            @Override
            public List<Pokemon> getPokemons() {
                return List.copyOf(pokemons);
            }

            @Override
            public List<Pokemon> getPokemons(Comparator<Pokemon> order) {
                List<Pokemon> sortedPokemons = new ArrayList<>(pokemons);
                sortedPokemons.sort(order);
                return List.copyOf(sortedPokemons);
            }

            @Override
            public PokemonMetadata getPokemonMetadata(int index) throws PokedexException {
                return metadataProvider.getPokemonMetadata(index);
            }

            @Override
            public Pokemon createPokemon(int index, int cp, int hp, int dust, int candy) {
                return pokemonFactory.createPokemon(index, cp, hp, dust, candy);
            }
        };
    }

    @Test
    public void testSize() {
        Assert.assertEquals(0, pokedex.size());

        pokedex.addPokemon(pokemonFactory.createPokemon(0, 613, 64, 200, 1));
        Assert.assertEquals(1, pokedex.size());

        pokedex.addPokemon(pokemonFactory.createPokemon(1, 896, 100, 400, 2));
        Assert.assertEquals(2, pokedex.size());
    }
    @Test
    public void testAddAndGetPokemon() throws PokedexException {
        Pokemon bulbasaur = pokedex.createPokemon(0, 613, 64, 200, 1);
        Pokemon ivysaur = pokedex.createPokemon(1, 896, 100, 400, 2);

        Assert.assertEquals(0, pokedex.addPokemon(bulbasaur));
        Assert.assertEquals(1, pokedex.addPokemon(ivysaur));

        Assert.assertEquals(bulbasaur, pokedex.getPokemon(0));
        Assert.assertEquals(ivysaur, pokedex.getPokemon(1));

        try {
            pokedex.getPokemon(-1);
            Assert.fail("Expected PokedexException");
        } catch (PokedexException e) {
            Assert.assertEquals("Invalid index: -1", e.getMessage());
        }

        try {
            pokedex.getPokemon(2);
            Assert.fail("Expected PokedexException");
        } catch (PokedexException e) {
            Assert.assertEquals("Invalid index: 2", e.getMessage());
        }
    }

    @Test
    public void testGetPokemons() {
        Assert.assertTrue(pokedex.getPokemons().isEmpty());

        Pokemon bulbasaur = pokedex.createPokemon(0, 613, 64, 200, 1);
        Pokemon ivysaur = pokedex.createPokemon(1, 896, 100, 400, 2);

        pokedex.addPokemon(bulbasaur);
        pokedex.addPokemon(ivysaur);

        List<Pokemon> pokemons = pokedex.getPokemons();
        Assert.assertEquals(2, pokemons.size());
        Assert.assertEquals(bulbasaur, pokemons.get(0));
        Assert.assertEquals(ivysaur, pokemons.get(1));
    }

    @Test
    public void testGetPokemonsWithComparator() {
        Assert.assertTrue(pokedex.getPokemons(PokemonComparators.CP).isEmpty());

        Pokemon bulbasaur = pokedex.createPokemon(0, 613, 64, 200, 1);
        Pokemon ivysaur = pokedex.createPokemon(1, 896, 100, 400, 2);

        pokedex.addPokemon(ivysaur);
        pokedex.addPokemon(bulbasaur);

        List<Pokemon> pokemons = pokedex.getPokemons(PokemonComparators.CP);
        Assert.assertEquals(2, pokemons.size());
        Assert.assertEquals(bulbasaur, pokemons.get(0));
        Assert.assertEquals(ivysaur, pokemons.get(1));

        pokemons = pokedex.getPokemons(PokemonComparators.NAME);
        Assert.assertEquals(2, pokemons.size());
        Assert.assertEquals(bulbasaur, pokemons.get(0));
        Assert.assertEquals(ivysaur, pokemons.get(1));

        pokemons = pokedex.getPokemons(PokemonComparators.INDEX);
        Assert.assertEquals(2, pokemons.size());
        Assert.assertEquals(bulbasaur, pokemons.get(0));
        Assert.assertEquals(ivysaur, pokemons.get(1));
    }
}