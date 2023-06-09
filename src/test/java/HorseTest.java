import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class HorseTest {

    private static final String NAME = "Spirit";
    private static final double SPEED = 5;
    private static final double DISTANCE = 0;

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "\t", "\n"})
    void horseConstructor(String name) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(name, SPEED, DISTANCE));
        try {
            new Horse(name, 0, 0);
        } catch (IllegalArgumentException e) {
            if (name == null) {
                assertEquals("Name cannot be null.", e.getMessage());
            } else {
                assertEquals("Name cannot be blank.", e.getMessage());
            }
        }

        assertThrows(IllegalArgumentException.class, () -> new Horse(NAME, -1, DISTANCE));
        try {
            new Horse(NAME, -1, DISTANCE);
        } catch (IllegalArgumentException e) {
            assertEquals("Speed cannot be negative.", e.getMessage());
        }

        assertThrows(IllegalArgumentException.class, () -> new Horse(NAME, SPEED, -1));
        try {
            new Horse(NAME, SPEED, -1);
        } catch (IllegalArgumentException e) {
            assertEquals("Distance cannot be negative.", e.getMessage());
        }
    }

    @Test
    void getName() {
        Horse horse = new Horse(NAME, SPEED);
        assertSame(NAME, horse.getName());
    }

    @Test
    void getSpeed() {
        Horse horse = new Horse(NAME, SPEED);
        assertEquals(SPEED, horse.getSpeed());
    }

    @Test
    void getDistance() {
        double distance = 100.0;
        Horse horse = new Horse(NAME, SPEED, distance);
        assertEquals(distance, horse.getDistance());
        horse = new Horse(NAME, SPEED);
        assertEquals(0, horse.getDistance());
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.8, 2.5, 4.2, 6.3})
    void move(Double randomDouble) {
        Horse horse = new Horse(NAME, SPEED);
        double distance = horse.getDistance();

        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomDouble);
            horse.move();
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
            double actualDistance = distance + horse.getSpeed() * Horse.getRandomDouble(0.2, 0.9);
            assertEquals(horse.getDistance(), actualDistance);
        }
    }
}