import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ListInsertTest {

	private FloorsArrayList list;

	@BeforeAll
	static void printStart(){
		System.out.println();
		System.out.println("==Insert Test========================");
		System.out.println();
	}

	@AfterAll
	static void printDone(){
		System.out.println();
		System.out.println("==Done============================================");
		System.out.println();
	}

	private static Stream<Arguments> NProvider() {
		return Stream.of(
				Arguments.of(10), Arguments.of(10), Arguments.of(10), Arguments.of(10), Arguments.of(10),
				Arguments.of(100), Arguments.of(100), Arguments.of(100), Arguments.of(100),
				Arguments.of(1000), Arguments.of(1000),
				Arguments.of(2000)
		);
	}

	void setup(int N){
		Random rn = new Random();
		list = new FloorsArrayList(N);
		for (int i = 0; i < N; i++) {
			list.insert((double)i, rn.nextInt(3*N/4)+1);
		}
	}

	void negSetup(int N){
		Random rn = new Random();
		list = new FloorsArrayList(N);
		int start = -N/2;
		int end = N/2;
		for (int i = start; i < end; i++) {
			list.insert((double)i, rn.nextInt(3*N/4)+1);
		}

	}

	@ParameterizedTest
	@MethodSource("NProvider")
	void insertNotThrowTest(int N){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> assertDoesNotThrow(() -> setup(N)));
	}

	@ParameterizedTest
	@MethodSource("NProvider")
	void insertNotThrowTestNeg(int N){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> assertDoesNotThrow(() -> negSetup(N)));
	}

	@ParameterizedTest
	@MethodSource("NProvider")
	void insertedTest(int N){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () ->{
			setup(N);
			FloorsArrayLink negInf = null, curr = null;
			Class FAL = list.getClass();
			for (Field f:FAL.getDeclaredFields()) {
				f.setAccessible(true);
				if (f.getType().isAssignableFrom(FloorsArrayLink.class)
						&& ((((FloorsArrayLink)f.get(list)).getKey() == Double.NEGATIVE_INFINITY)
						|| (((FloorsArrayLink)f.get(list)).getKey() == -Double.MAX_VALUE))){
					curr = (FloorsArrayLink)f.get(list);
					negInf = curr;
					break;
				}
			}
			assertNotNull(curr,
					"list doesn't contain -infinity link");
			assertEquals(0.0, curr.getNext(1).getKey(),
					"link was not inserted");
		});

	}


	@ParameterizedTest
	@MethodSource("NProvider")
	void insertedTestNeg(int N){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () ->{
			negSetup(N);
			FloorsArrayLink curr = null;
			Class FAL = list.getClass();
			for (Field f:FAL.getDeclaredFields()) {
				f.setAccessible(true);
				if (f.getType().isAssignableFrom(FloorsArrayLink.class)
						&& ((((FloorsArrayLink)f.get(list)).getKey() == Double.NEGATIVE_INFINITY)
							|| (((FloorsArrayLink)f.get(list)).getKey() == -Double.MAX_VALUE))){
					curr = (FloorsArrayLink)f.get(list);
					break;
				}
			}
			assertNotNull(curr,
					"list doesn't contain -infinity link");
			assertEquals(-(double)N/2, curr.getNext(1).getKey(),
					"link was not inserted");
		});
	}

	@ParameterizedTest
	@MethodSource("NProvider")
	void insertedAllTest(int N){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () ->{
			setup(N);
			FloorsArrayLink negInf = null, curr = null;
			Class FAL = list.getClass();
			for (Field f:FAL.getDeclaredFields()) {
				f.setAccessible(true);
				if (f.getType().isAssignableFrom(FloorsArrayLink.class)
						&& ((((FloorsArrayLink)f.get(list)).getKey() == Double.NEGATIVE_INFINITY)
						|| (((FloorsArrayLink)f.get(list)).getKey() == -Double.MAX_VALUE))){
					curr = (FloorsArrayLink)f.get(list);
					negInf = curr;
					break;
				}
			}
			assertNotNull(curr,
					"list doesn't contain -infinity link");
			curr = curr.getNext(1);
			int i = 0;
			while (curr.getKey() < N
					&& (curr.getKey() != Double.MAX_VALUE || curr.getKey() != Double.POSITIVE_INFINITY)){
				assertEquals((double)i, curr.getKey(),
						"link was not inserted");
				curr = curr.getNext(1);
				i++;
			}
		});

	}

	@ParameterizedTest
	@MethodSource("NProvider")
	void insertedAllTestNeg(int N){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () ->{
			negSetup(N);
			FloorsArrayLink curr = null;
			Class FAL = list.getClass();
			for (Field f:FAL.getDeclaredFields()) {
				f.setAccessible(true);
				if (f.getType().isAssignableFrom(FloorsArrayLink.class)
						&& ((((FloorsArrayLink)f.get(list)).getKey() == Double.NEGATIVE_INFINITY)
						|| (((FloorsArrayLink)f.get(list)).getKey() == -Double.MAX_VALUE))){
					curr = (FloorsArrayLink)f.get(list);
					break;
				}
			}
			assertNotNull(curr,
					"list doesn't contain -infinity link");
			curr = curr.getNext(1);
			int i = -N/2;
			while (curr.getKey() < N/2
					&& (curr.getNext(1).getKey() != Double.MAX_VALUE || curr.getKey() != Double.POSITIVE_INFINITY)){
				assertEquals((double)i, curr.getKey(),
						"link was not inserted");
				curr = curr.getNext(1);
				i++;
			}
		});
	}

}
