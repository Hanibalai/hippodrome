import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HippodromeTest {

    private static final String NAME = "Horse";
    private static final double SPEED = 5;

    @Test
    void hippodromeConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        try {
            new Hippodrome(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Horses cannot be null.", e.getMessage());
        }

        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
        try {
            new Hippodrome(new ArrayList<>());
        } catch (IllegalArgumentException e) {
            assertEquals("Horses cannot be empty.", e.getMessage());
        }
    }

    @Test
    void getHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            horses.add(new Horse(NAME + i, SPEED));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    void move() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(Mockito.mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();
        for (Horse horse : horses) {
            Mockito.verify(horse).move();
        }
    }

    @Test
    void getWinner() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(new Horse(NAME, SPEED, Math.random() * 100));
        }
        Horse winner = horses.stream()
                .max(Comparator.comparingDouble(Horse::getDistance))
                .orElse(new Horse(NAME, SPEED, 100));
        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(winner, hippodrome.getWinner());
    }
}