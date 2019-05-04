import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

class ListArraySizesInvariantTest {


	private static FloorsArrayList list;

	@BeforeAll
	static void printStart(){
		System.out.println();
		System.out.println("==Invariant Test==================================");
		System.out.println();
	}

	@AfterAll
	static void printDone(){
		System.out.println();
		System.out.println("==Done============================================");
		System.out.println();
	}

	@BeforeEach
	void setList(){
		list = new FloorsArrayList(123);
		Random rn = new Random();
		for (int i = 0; i < 123 ; i++) {
			int size = rn.nextInt(60)+1;
			list.insert((double)i, size);
		}
	}

	private static Stream<Arguments> modProvider() {
		return Stream.of(
				Arguments.of(2),
				Arguments.of(3),
				Arguments.of(5),
				Arguments.of(7),
				Arguments.of(11)
		);
	}

	@RepeatedTest(5)
	void prevArraySizeTest() {
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			FloorsArrayLink curr = list.lookup(list.maximum()).getNext(1); // point to infinity
			double negInf = list.lookup(list.minimum()).getPrev(1).getKey();
			FloorsArrayLink prev;
			while (curr.getPrev(1).getKey() != negInf) {
				curr = curr.getPrev(1);
				int size = curr.getArrSize();
				for (int index = 1; index <= size; index++) {
					prev = curr.getPrev(index);
					assertNotNull(prev,
							"prev of " + curr.getKey() + " at index " + index + " is null");
					assertTrue(prev.getArrSize() >= index,
							"size of the array of the prev link at index " + index + " is lower then the index");
				}
			}
		});
	}

	@RepeatedTest(5)
	void nextArraySizeTest(){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			FloorsArrayLink curr = list.lookup(list.minimum()).getPrev(1); // point to -infinity
			double posInf = list.lookup(list.maximum()).getNext(1).getKey();
			FloorsArrayLink next;
			while (curr.getNext(1).getKey() != posInf)
				curr = curr.getNext(1);
			int size = curr.getArrSize();
			for (int index = 1; index <= size; index++) {
				next = curr.getNext(index);
				assertNotNull(next,
						"next of " + curr.getKey() + " at index " + index + " is null");
				assertTrue(next.getArrSize() >= index,
						"size of the array of the next link at index " + index + " is lower then the index");
			}
		});
	}


	/**
	 * Test if after removing a link, it's prev's array is updated properly.
	 */
	@ParameterizedTest
	@MethodSource("modProvider")
	void nextArraySizeRemoveTest(int mod){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			for (int i = 0; i < 123; i++) {
				if (i % mod == 0) {
					list.remove(list.lookup((double) i));
				}
			}
			FloorsArrayLink curr = list.lookup(list.minimum()).getPrev(1); // point to -infinity
			double posInf = list.lookup(list.maximum()).getNext(1).getKey();
			FloorsArrayLink next;
			while (curr.getNext(1).getKey() != posInf) {
				curr = curr.getNext(1);
				int size = curr.getArrSize();
				for (int index = 1; index <= size; index++) {
					next = curr.getNext(index);
					assertNotNull(next,
							"next of " + curr.getKey() + " at index " + index + " is null");
					assertTrue(next.getArrSize() >= index,
							"size of the array of the next link at index " + index + " is lower then the index");
				}
			}
		});
	}

	/**
	 * Test if after removing a link, it's next's array is updated properly.
	 */
	@ParameterizedTest
	@MethodSource("modProvider")
	void prevArraySizeRemoveTest(int mod){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			for (int i = 0; i < 123; i++) {
				if (i % mod == 0) {
					list.remove(list.lookup((double) i));
				}
			}
			FloorsArrayLink curr = list.lookup(list.maximum()).getNext(1); // point to infinity
			double negInf = list.lookup(list.minimum()).getPrev(1).getKey();
			FloorsArrayLink prev;
			while (curr.getPrev(1).getKey() != negInf) {
				curr = curr.getPrev(1);
				int size = curr.getArrSize();
				for (int index = 1; index <= size; index++) {
					prev = curr.getPrev(index);
					assertNotNull(prev,
							"prev of " + curr.getKey() + " at index " + index + " is null");
					assertTrue(prev.getArrSize() >= index,
							"size of the array of the prev link at index " + index + " is lower then the index");
				}
			}
		});
	}

}
