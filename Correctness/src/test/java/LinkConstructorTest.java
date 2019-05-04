import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class LinkConstructorTest {

    private FloorsArrayLink link;

    @BeforeAll
    static void printStart(){
        System.out.println("\n==Link Constructor Test===========================\n");
    }

    @AfterAll
    static void printDone(){
        System.out.println("\n==Done============================================\n");
    }

    private static Stream<Arguments> keysSizesProvider() {
        return Stream.of(
                Arguments.of(50, 50),
                Arguments.of(5000, 500),
                Arguments.of(-0.003, 1),
                Arguments.of(1, 9999999)
        );
    }

    void setup(double linkKey, int linkSize) {
        link = new FloorsArrayLink(linkKey, linkSize);
    }

    @ParameterizedTest
    @MethodSource("keysSizesProvider")
    void constructorKeyTest(double key, int size) throws IllegalAccessException {
        setup(key, size);
        Class FALink = link.getClass();
        double linkKey = Double.MIN_VALUE;
        for (Field f: FALink.getDeclaredFields()) {
            f.setAccessible(true);
            if (f.getType().isAssignableFrom(double.class)){
                if (f.getName().toLowerCase().contains("key")){
                    linkKey = (double) f.get(link);
                }
            }
        }
        assertNotEquals(Double.MIN_VALUE, linkKey, "couldn't get key using reflection");
        assertEquals(key, linkKey,
                "key given to constructor isn't equal to the one set in the field of the class");
    }


    @ParameterizedTest
    @MethodSource("keysSizesProvider")
    void constructorSizeTest(double key, int size) throws IllegalAccessException {
        setup(key, size);
        Class FALink = link.getClass();
        int arraySize = -1;
        for (Field f: FALink.getDeclaredFields()) {
            if (f.getType().isAssignableFrom(int.class)){
                f.setAccessible(true);
                if (f.getName().toLowerCase().contains("size")){
                    arraySize = (int) f.get(link);
                }
            }
        }
        assertEquals(size, arraySize,
                "size given to constructor isn't equal to the one set in the field of the class");
    }
}
