package fr.univavignon.pokedex.api;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class IPokemonTrainerFactoryTest {

    @Mock
    private IPokedexFactory pokedexFactoryMock;

    @Mock
    private IPokedex pokedexMock;

    @Mock
    private IPokemonMetadataProvider metadataProviderMock;

    @Mock
    private IPokemonFactory pokemonFactoryMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(pokedexFactoryMock.createPokedex(any(IPokemonMetadataProvider.class), any(IPokemonFactory.class)))
                .thenReturn(pokedexMock);
    }

    @Test
    public void testCreateTrainer() {
        IPokemonTrainerFactory trainerFactory = new PokemonTrainer();
        IPokedexFactory pokedexFactory = mock(IPokedexFactory.class);
        IPokedex pokedex = mock(IPokedex.class);
        when(pokedexFactory.createPokedex(any(IPokemonMetadataProvider.class), any(IPokemonFactory.class))).thenReturn(pokedex);
        PokemonTrainer trainer = (PokemonTrainer) trainerFactory.createTrainer("Ash", Team.VALOR, pokedexFactory);
        assertEquals("Ash", trainer.getName());
        assertEquals(Team.VALOR, trainer.getTeam());
        assertEquals(pokedex, trainer.getPokedex());
    }
}