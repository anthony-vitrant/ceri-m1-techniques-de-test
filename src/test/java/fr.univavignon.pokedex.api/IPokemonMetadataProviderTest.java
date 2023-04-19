package fr.univavignon.pokedex.api;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class IPokemonMetadataProviderTest {

    private IPokemonMetadataProvider metadataProvider;

    @Before
    public void setUp() throws PokedexException {
        metadataProvider = Mockito.mock(IPokemonMetadataProvider.class);
        when(metadataProvider.getPokemonMetadata(0)).thenReturn(new PokemonMetadata(0, "Bulbasaur", 49, 49, 90));
    }

    @Test
    public void testGetPokemonMetadata() throws PokedexException {
        PokemonMetadata metadata = metadataProvider.getPokemonMetadata(0);
        assertEquals("Bulbasaur", metadata.getName());
        assertEquals(49, metadata.getAttack());
        assertEquals(49, metadata.getDefense());
        assertEquals(90, metadata.getStamina());
    }
}
