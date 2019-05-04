import com.sun.org.apache.xpath.internal.Arg;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ListConstructorTest {

    private FloorsArrayList list;

    @BeforeAll
    static void printStart(){
        System.out.println("\n==List Constructor Test===========================\n");
    }

    @AfterAll
    static void printDone(){
        System.out.println("\n==Done============================================\n");
    }

    private static Stream<Arguments> keysSizesProvider() {
        return Stream.of(
                Arguments.of(1),
                Arguments.of(50),
                Arguments.of(500),
                Arguments.of(1000)
        );
    }

    void setup(int N) {
        list = new FloorsArrayList(N);
    }

    @ParameterizedTest
    @MethodSource("keysSizesProvider")
    void constructorListSizeTest(int N) throws IllegalAccessException {
        setup(N);
        Class FAList = list.getClass();
        int listSize = -1;
        for (Field f: FAList.getDeclaredFields()) {
            f.setAccessible(true);
            if (f.getType().isAssignableFrom(int.class)){
                if (f.getName().toLowerCase().contains("size") && !(f.getName().toLowerCase().contains("max") || f.getName().toLowerCase().contains("top"))){
                    listSize = (int) f.get(list);
                }
            }
        }
        assertNotEquals(-1, listSize, "couldn't get list size using reflection");
        assertEquals(0, listSize,
                "size of a new list should be 0");
    }


    @ParameterizedTest
    @MethodSource("keysSizesProvider")
    void constructorListFirstLinkTest(int N) throws IllegalAccessException {
        setup(N);
        Class FAList = list.getClass();
        int linkArrSize = -1;
        for (Field f: FAList.getDeclaredFields()) {
            f.setAccessible(true);
            if (f.getType().isAssignableFrom(FloorsArrayLink.class)){
                FloorsArrayLink l = (FloorsArrayLink) f.get(list);
                if (l.getKey() == Double.NEGATIVE_INFINITY || l.getKey() == -Double.MAX_VALUE){
                    linkArrSize = l.getArrSize();
                }
                else if (l.getKey() == Double.MIN_VALUE || l.getKey() == Integer.MIN_VALUE){
                    assertEquals(-Double.MAX_VALUE, l.getKey(),
                            "first key should be Double.NEGATIVE_INFINITY or -Double.MAX_VALUE");
                }
            }
        }
        assertNotEquals(-1, linkArrSize,
                "couldn't get link array size using reflection");
        assertEquals(N+1, linkArrSize,
                "Array size of -infinity link isn't equal to N+1");
    }

    @ParameterizedTest
    @MethodSource("keysSizesProvider")
    void constructorListLastLinkTest(int N) throws IllegalAccessException {
        setup(N);
        Class FAList = list.getClass();
        int linkArrSize = -1;
        for (Field f: FAList.getDeclaredFields()) {
            f.setAccessible(true);
            if (f.getType().isAssignableFrom(FloorsArrayLink.class)){
                FloorsArrayLink l = (FloorsArrayLink) f.get(list);
                if (l.getKey() == Double.POSITIVE_INFINITY || l.getKey() == Double.MAX_VALUE){
                    linkArrSize = l.getArrSize();
                }
                else if (l.getKey() == Integer.MAX_VALUE){
                    assertEquals(-Double.MAX_VALUE, l.getKey(),
                            "last key should be Double.POSITIVE_INFINITY or Double.MAX_VALUE");
                }
            }
        }
        assertNotEquals(-1, linkArrSize, "couldn't get list size using reflection");
        assertEquals(N+1, linkArrSize,
                "Array size of +infinity link isnt equal to N+1");
    }

}
