package fr.univavignon.pokedex.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Comparator;

import static org.junit.Assert.*;

public class MyPokedexTest {

    private MyPokedex pokedex;

    @Mock
    private IPokemonMetadataProvider metadataProvider;

    @Mock
    private IPokemonFactory pokemonFactory;

    @BeforeEach
    public void setUp() throws PokedexException {
        MockitoAnnotations.initMocks(this);
        Mockito.when(metadataProvider.getPokemonMetadata(0))
                .thenReturn(new PokemonMetadata(0, "Bulbasaur", 126, 126, 90));
        pokedex = new MyPokedex(metadataProvider, pokemonFactory);
    }

    @Test
    public void testGetPokemonMetadata() throws PokedexException {
        // Test getPokemonMetadata method
        PokemonMetadata metadata = pokedex.getPokemonMetadata(0);
        assertEquals(metadata.getIndex(), 0);
        assertEquals(metadata.getName(), "Bulbasaur");
        assertEquals(metadata.getAttack(), 126);
        assertEquals(metadata.getDefense(), 126);
        assertEquals(metadata.getStamina(), 90);
    }

    @Test
    public void testCreatePokemon() {
        // Test createPokemon method
        Pokemon pokemon = new Pokemon(0, "Bulbasaur", 126, 126, 90, 613, 64, 4000, 4, 50);
        Mockito.when(pokemonFactory.createPokemon(0, 613, 64, 4000, 4)).thenReturn(pokemon);
        Pokemon newPokemon = pokedex.createPokemon(0, 613, 64, 4000, 4);
        assertNotNull(newPokemon);
        assertEquals(newPokemon.getIndex(), 0);
        assertEquals(newPokemon.getName(), "Bulbasaur");
        assertEquals(newPokemon.getAttack(), 126);
        assertEquals(newPokemon.getDefense(), 126);
        assertEquals(newPokemon.getStamina(), 90);
        assertEquals(newPokemon.getCp(), 613);
        assertEquals(newPokemon.getHp(), 64);
        assertEquals(newPokemon.getDust(), 4000);
        assertEquals(newPokemon.getCandy(), 4);
    }

    @Test
    public void testGetPokemon() throws PokedexException {
        // Test getPokemon method
        Pokemon pokemon = new Pokemon(0, "Bulbasaur", 126, 126, 90, 613, 64, 4000, 4,50);
        pokedex.addPokemon(pokemon);
        Pokemon retrievedPokemon = pokedex.getPokemon(0);
        assertEquals(retrievedPokemon.getIndex(), pokemon.getIndex());
        assertEquals(retrievedPokemon.getName(), pokemon.getName());
        assertEquals(retrievedPokemon.getAttack(), pokemon.getAttack());
        assertEquals(retrievedPokemon.getDefense(), pokemon.getDefense());
        assertEquals(retrievedPokemon.getStamina(), pokemon.getStamina());
        assertEquals(retrievedPokemon.getCp(), pokemon.getCp());
        assertEquals(retrievedPokemon.getHp(), pokemon.getHp());
        assertEquals(retrievedPokemon.getDust(), pokemon.getDust());
        assertEquals(retrievedPokemon.getCandy(), pokemon.getCandy());
    }

    @Test
    public void testGetPokemons() {
        // Test getPokemons method
        Pokemon pokemon1 = new Pokemon(0, "Bulbasaur", 126, 126, 90, 613, 64, 4000, 4, 50);
        Pokemon pokemon2 = new Pokemon(1, "Ivysaur", 156, 158, 120, 900, 100, 8000, 8, 50);
        Pokemon pokemon3 = new Pokemon(2, "Venusaur", 198, 200, 160, 1800, 300, 16000, 16, 50);

        pokedex.addPokemon(pokemon1);
        pokedex.addPokemon(pokemon2);
        pokedex.addPokemon(pokemon3);

        // Test getPokemons without ordering
        assertEquals(pokedex.getPokemons().size(), 3);
        assertEquals(pokedex.getPokemons().get(0), pokemon1);
        assertEquals(pokedex.getPokemons().get(1), pokemon2);
        assertEquals(pokedex.getPokemons().get(2), pokemon3);

        // Test getPokemons with ordering
        Comparator<Pokemon> comparator = new Comparator<Pokemon>() {
            public int compare(Pokemon p1, Pokemon p2) {
                return p2.getAttack() - p1.getAttack();
            }
        };
        assertEquals(pokedex.getPokemons(comparator).size(), 3);
        assertEquals(pokedex.getPokemons(comparator).get(0), pokemon3);
        assertEquals(pokedex.getPokemons(comparator).get(1), pokemon2);
        assertEquals(pokedex.getPokemons(comparator).get(2), pokemon1);
    }

    @Test
    public void testAddPokemon() throws PokedexException {
        // Test addPokemon method
        assertEquals(pokedex.size(), 0);
        Pokemon pokemon = new Pokemon(0, "Bulbasaur", 126, 126, 90, 613, 64, 4000, 4,50);
        pokedex.addPokemon(pokemon);
        assertEquals(pokedex.size(), 1);
        assertEquals(pokedex.getPokemon(0), pokemon);

        // Test getPokemon method with invalid ID
        assertThrows(PokedexException.class, () -> pokedex.getPokemon(-1));
        assertThrows(PokedexException.class, () -> pokedex.getPokemon(1));
        assertThrows(PokedexException.class, () -> pokedex.getPokemon(1000));
    }

    @Test
    public void testSize() {
        // Test size method
        assertEquals(pokedex.size(), 0);
        Pokemon pokemon = new Pokemon(0, "Bulbasaur", 126, 126, 90, 613, 64, 4000, 4,50);
        pokedex.addPokemon(pokemon);
        assertEquals(pokedex.size(), 1);
    }
}