package fr.univavignon.pokedex.api;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class IPokemonFactoryTest {

    private IPokemonFactory pokemonFactory;

    @Before
    public void setUp() {
        pokemonFactory = Mockito.mock(IPokemonFactory.class);
        when(pokemonFactory.createPokemon(0, 110, 60, 400, 4)).thenReturn(new Pokemon(0, "Bulbasaur", 49, 49, 90, 110, 60, 400, 4, 56.0));
    }

    @Test
    public void testCreatePokemon() {
        Pokemon pokemon = pokemonFactory.createPokemon(0, 110, 60, 400, 4);
        assertEquals(0, pokemon.getIndex());
        assertEquals("Bulbasaur", pokemon.getName());
        assertEquals(110, pokemon.getCp());
        assertEquals(60, pokemon.getHp());
        assertEquals(400, pokemon.getDust());
        assertEquals(4, pokemon.getCandy());
        assertEquals(56.0, pokemon.getIv(), 0.01);
    }
}
