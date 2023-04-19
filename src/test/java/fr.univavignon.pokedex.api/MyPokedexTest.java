package fr.univavignon.pokedex.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

public class MyPokedexTest {

    private MyPokedex pokedex;

    @Mock
    private IPokemonMetadataProvider metadataProvider;

    @BeforeEach
    public void setUp() throws PokedexException {
        MockitoAnnotations.initMocks(this);
        Mockito.when(metadataProvider.getPokemonMetadata(0))
                .thenReturn(new PokemonMetadata(0, "Bulbasaur", 126, 126, 90));
        pokedex = new MyPokedex(metadataProvider, null);
    }

    @Test
    public void testGetPokemonMetadata() throws PokedexException {
        PokemonMetadata metadata = pokedex.getPokemonMetadata(0);
        assertEquals(metadata.getIndex(), 0);
        assertEquals(metadata.getName(), "Bulbasaur");
        assertEquals(metadata.getAttack(), 126);
        assertEquals(metadata.getDefense(), 126);
        assertEquals(metadata.getStamina(), 90);
    }
}