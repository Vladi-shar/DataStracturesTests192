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

public class ListLookupTest {

	private FloorsArrayList list;

	@BeforeAll
	static void printStart(){
		System.out.println();
		System.out.println("==Lookup Test=====================================");
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
				Arguments.of(10)
				, Arguments.of(10), Arguments.of(10), Arguments.of(10), Arguments.of(10),
				Arguments.of(100), Arguments.of(100), Arguments.of(100), Arguments.of(100),
				Arguments.of(1000), Arguments.of(1000),
				Arguments.of(2000)
		);
	}

	void setup(int N){
		Random rn = new Random();
		list = new FloorsArrayList(N);
		int start = 0;
		int end = N;
		for (int i = start; i < end; i++){
			list.insert((double)i, rn.nextInt(N/2)+1);
		}
	}

	void negSetup(int N){
		Random rn = new Random();
		list = new FloorsArrayList(N);
		int start = -N/2;
		int end = N/2;
		for (int i = start; i < end; i++) {
			list.insert((double)i, rn.nextInt(N/2)+1);
		}

	}

	@ParameterizedTest
	@MethodSource("NProvider")
	void lookupNotThrowTest(int N){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> assertDoesNotThrow(() ->{
			setup(N);
			list.lookup((double)0);
		}));
	}

	@ParameterizedTest
	@MethodSource("NProvider")
	void lookupNotThrowTestNeg(int N){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> assertDoesNotThrow(() -> {
			negSetup(N);
			list.lookup((double)0);
		}));
	}



	@ParameterizedTest
	@MethodSource("NProvider")
	void lookupNotNullTest(int N){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			setup(N);
			int end = N;
			assertNotNull(list.lookup(end/2),
					"Lookup of link in the list returned null");
		});
	}

	@ParameterizedTest
	@MethodSource("NProvider")
	void lookupNotNullTestNeg(int N){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			negSetup(N);
			int end = N/2;
			assertNotNull(list.lookup(end/2),
					"Lookup of link in the list returned null");
		});
	}

	@ParameterizedTest
	@MethodSource("NProvider")
	void lookupCorrectTest(int N){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			setup(N);
			int end = N;
			assertEquals(list.lookup((double)0).getKey(), (double)(0),
					"Lookup of link in the list returned wrong key");
		});
	}

	@ParameterizedTest
	@MethodSource("NProvider")
	void lookupCorrectTestNeg(int N){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			negSetup(N);
			int end = N/2;
			assertEquals(list.lookup((double)0).getKey(), (double)(0),
					"Lookup of link in the list returned wrong key");
		});
	}

	@ParameterizedTest
	@MethodSource("NProvider")
	void lookupNullTest(int N){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			setup(N);
			int end = N;
			assertNull(list.lookup(end + end),
					"Lookup of link not in the list didn't return null");
		});
	}

}
