package fr.univavignon.pokedex.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class RocketPokemonFactoryTest {

    private RocketPokemonFactory rocketPokemonFactory;

    @BeforeEach
    public void setUp() {
        rocketPokemonFactory = new RocketPokemonFactory();
    }

    @Test
    public void testIndex2NameMapping() {
        Map<Integer, String> index2name = RocketPokemonFactory.index2name;
        assertEquals("Ash's Pikachu", index2name.get(-1));
        assertEquals("MISSINGNO", index2name.get(0));
        assertEquals("Bulbasaur", index2name.get(1));
    }

    @Test
    public void testCreatePokemon() {
        int index = 1;
        int cp = 500;
        int hp = 100;
        int dust = 3000;
        int candy = 50;

        Pokemon pokemon = rocketPokemonFactory.createPokemon(index, cp, hp, dust, candy);

        assertEquals(index, pokemon.getIndex());
        assertEquals("Bulbasaur", pokemon.getName());
        assertTrue(pokemon.getAttack() >= 0 && pokemon.getAttack() <= 100);
        assertTrue(pokemon.getDefense() >= 0 && pokemon.getDefense() <= 100);
        assertTrue(pokemon.getStamina() >= 0 && pokemon.getStamina() <= 100);
        assertEquals(cp, pokemon.getCp());
        assertEquals(hp, pokemon.getHp());
        assertEquals(dust, pokemon.getDust());
        assertEquals(candy, pokemon.getCandy());
        assertEquals(1.0, pokemon.getIv(), 0.0);
    }

    @Test
    public void testCreatePokemonWithNegativeIndex() {
        int index = -5;
        int cp = 600;
        int hp = 120;
        int dust = 3500;
        int candy = 60;

        Pokemon negativeIndexPokemon = rocketPokemonFactory.createPokemon(index, cp, hp, dust, candy);

        assertEquals(-5, negativeIndexPokemon.getIndex());
        assertEquals("MISSINGNO", negativeIndexPokemon.getName());
        assertTrue(negativeIndexPokemon.getAttack() >= 0 && negativeIndexPokemon.getAttack() <= 100);
        assertTrue(negativeIndexPokemon.getDefense() >= 0 && negativeIndexPokemon.getDefense() <= 100);
        assertTrue(negativeIndexPokemon.getStamina() >= 0 && negativeIndexPokemon.getStamina() <= 100);
        assertEquals(cp, negativeIndexPokemon.getCp());
        assertEquals(hp, negativeIndexPokemon.getHp());
        assertEquals(dust, negativeIndexPokemon.getDust());
        assertEquals(candy, negativeIndexPokemon.getCandy());
        assertEquals(1.0, negativeIndexPokemon.getIv(), 0.0);
    }

    @Test
    public void testCreateAshPikachu() {
        int index = -1;
        int cp = 1500;
        int hp = 200;
        int dust = 5000;
        int candy = 100;

        Pokemon ashPikachu = rocketPokemonFactory.createPokemon(index, cp, hp, dust, candy);

        assertEquals(index, ashPikachu.getIndex());
        assertEquals("Ash's Pikachu", ashPikachu.getName());
        assertEquals(1000, ashPikachu.getAttack());
        assertEquals(1000, ashPikachu.getDefense());
        assertEquals(1000, ashPikachu.getStamina());
        assertEquals(cp, ashPikachu.getCp());
        assertEquals(hp, ashPikachu.getHp());
        assertEquals(dust, ashPikachu.getDust());
        assertEquals(candy, ashPikachu.getCandy());
        assertEquals(0.0, ashPikachu.getIv(), 0.0);
    }

    @Test
    public void testCreateMissingNo() {
        int index = 999;
        int cp = 1000;
        int hp = 150;
        int dust = 4000;
        int candy = 75;

        Pokemon missingNo = rocketPokemonFactory.createPokemon(index, cp, hp, dust, candy);

        assertEquals(0, missingNo.getIndex());
        assertEquals("MISSINGNO", missingNo.getName());
        assertTrue(missingNo.getAttack() >= 0 && missingNo.getAttack() <= 100);
        assertTrue(missingNo.getDefense() >= 0 && missingNo.getDefense() <= 100);
        assertTrue(missingNo.getStamina() >= 0 && missingNo.getStamina() <= 100);
        assertEquals(cp, missingNo.getCp());
        assertEquals(hp, missingNo.getHp());
        assertEquals(dust, missingNo.getDust());
        assertEquals(candy, missingNo.getCandy());
        assertEquals(1.0, missingNo.getIv(), 0.0);
    }

    @Test
    public void testGenerateRandomStat() {
        int minStat = 100;
        int maxStat = 0;
        for (int i = 0; i < 1000; i++) {
            int randomStat = RocketPokemonFactory.generateRandomStat();
            assertTrue(randomStat >= 0 && randomStat <= 100);
            minStat = Math.min(minStat, randomStat);
            maxStat = Math.max(maxStat, randomStat);
        }
        assertTrue(minStat >= 0 && maxStat <= 100);
    }
}