import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

class ListRemoveTest {

	private FloorsArrayList list;

	@BeforeAll
	static void printStart(){
		System.out.println();
		System.out.println("==Insert Lookup Remove Test========================");
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
				Arguments.of(10),Arguments.of(10),Arguments.of(10),Arguments.of(10),Arguments.of(10),
				Arguments.of(100),Arguments.of(100),Arguments.of(100),Arguments.of(100),
				Arguments.of(1000),Arguments.of(1000),
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
	void removeNotThrowTest(int N){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			setup(N);
			assertDoesNotThrow(() -> {
				list.remove(list.lookup(0));
			});
		});
	}

	@ParameterizedTest
	@MethodSource("NProvider")
	void removeNotThrowTestNeg(int N){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			negSetup(N);
			assertDoesNotThrow(() -> {
				list.remove(list.lookup(0));
			});
		});
	}

	@ParameterizedTest
	@MethodSource("NProvider")
	void removeLookup(int N){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			setup(N);
			int start = 0;
			int end = N;
			for (int i = start; i < end; i++) {
				if (i % 2 == 0) {
					list.remove(list.lookup((double) i));
				}
			}
			for (int i = start; i < end; i++) {
				if (i % 2 == 0) {
					assertNull(list.lookup((double) i),
							"lookup of removed link returned not null");
				}
			}
		});
	}

	@ParameterizedTest
	@MethodSource("NProvider")
	void removeLookupNeg(int N){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			negSetup(N);
			int start = -N / 2;
			int end = N / 2;
			for (int i = start; i < end; i++) {
				if (i % 2 == 0) {
					list.remove(list.lookup((double) i));
				}
			}
			for (int i = start; i < end; i++) {
				if (i % 2 == 0) {
					assertNull(list.lookup((double) i),
							"lookup of removed link returned not null");
				}
			}
		});
	}

	@ParameterizedTest
	@MethodSource("NProvider")
	void emptySizeTest(int N){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			list = new FloorsArrayList(N);
			assertEquals(0, list.getSize(),
					"Empty list size is not 0");
		});
	}

	@ParameterizedTest
	@MethodSource("NProvider")
	void removeAllTest(int N){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			setup(N);
			int start = 0;
			int end = N;
			int sizeActual = list.getSize();
			for (int i = start; i < end; i++) {
				list.remove(list.lookup((double) i));
				sizeActual = list.getSize();
			}
			assertEquals(0, sizeActual,
					"size of list after inserting " + N + "keys and then removing them is not 0");
		});
	}

	@ParameterizedTest
	@MethodSource("NProvider")
	void removeSizeTest(int N){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			setup(N);
			int size = N;
			int sizeActual = list.getSize();
			for (int i = 0; i < N; i++) {
				if (i % 2 == 0) {
					list.remove(list.lookup((double) i));
					size--;
					sizeActual = list.getSize();
				}
				assertEquals(size, sizeActual,
						"Size after removing one link from list with " + size + 1 + "links isn't equal" +
								"to " + size);
			}
		});
	}

	@ParameterizedTest
	@MethodSource("NProvider")
	void removeSizeTestNeg(int N){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			negSetup(N);
			int start = -N / 2;
			int end = N / 2;
			int size = N;
			int sizeActual = list.getSize();
			for (int i = start; i < end; i++) {
				if (i % 2 == 0) {
					list.remove(list.lookup((double) i));
					size--;
					sizeActual = list.getSize();
				}
				assertEquals(size, sizeActual,
						"Size after removing one link from list with " + size + 1 + "links isn't equal" +
								"to " + size);
			}
		});
	}


	@ParameterizedTest
	@MethodSource("NProvider")
	void removeInsertSizeTest(int N){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			setup(N);
			int start = 0;
			int end = N;
			for (int i = start; i < end; i++) {
				if (i % 2 == 0) {
					list.remove(list.lookup((double) i));
				}
			}
			int size = N / 2;
			int sizeActual = list.getSize();
			//System.out.println(size + " " + sizeActual);
			Random rn = new Random();
			for (int i = start; i < end; i++) {
				if (i % 2 == 0) {
					list.insert((double) i, rn.nextInt(3 * N / 4) + 1);
					size++;
					sizeActual = list.getSize();
				}
				assertEquals(size, sizeActual,
						"size after removing " + N / 2 + " links from a list with " + N + " links"
								+ "and then inserting " + (i + 1) + " isn't equal to " + size);
			}
		});
	}

	@ParameterizedTest
	@MethodSource("NProvider")
	void removeInsertSizeTestNeg(int N){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			negSetup(N);
			int start = -N / 2;
			int end = N / 2;
			for (int i = start; i < end; i++) {
				if (i % 2 == 0) {
					list.remove(list.lookup((double) i));
				}
			}
			int size = N / 2;
			int sizeActual = list.getSize();
			//System.out.println(size + " " + sizeActual);
			Random rn = new Random();
			for (int i = start; i < end; i++) {
				if (i % 2 == 0) {
					list.insert((double) i, rn.nextInt(3 * N / 4) + 1);
					size++;
					sizeActual = list.getSize();
				}
				assertEquals(size, sizeActual,
						"size after removing " + N / 2 + " links from a list with " + N + " links"
								+ "and then inserting " + (start + i + 1) + " isn't equal to " + size);
			}
		});
	}

}
