package fr.univavignon.pokedex.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class IPokedexFactoryTest {

    private IPokedexFactory pokedexFactory;

    @Before
    public void setUp() {
        pokedexFactory = Mockito.mock(IPokedexFactory.class);
    }

    @Test
    public void testCreatePokedex() {
        IPokemonMetadataProvider metadataProvider = Mockito.mock(IPokemonMetadataProvider.class);
        IPokemonFactory pokemonFactory = Mockito.mock(IPokemonFactory.class);

        Mockito.when(pokedexFactory.createPokedex(metadataProvider, pokemonFactory)).thenReturn(Mockito.mock(IPokedex.class));

        IPokedex pokedex = pokedexFactory.createPokedex(metadataProvider, pokemonFactory);
        assertNotNull(pokedex);
    }
}
