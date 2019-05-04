import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class ListMinimumMaximumTest {

	private FloorsArrayList list;

	@BeforeAll
	static void printStart(){
		System.out.println();
		System.out.println("==Minimum Maximum Test============================");
		System.out.println();
	}

	@AfterAll
	static void printDone(){
		System.out.println();
		System.out.println("==Done============================================");
		System.out.println();
	}

	@BeforeEach
	void setup(){
		list = new FloorsArrayList(20);
		list.insert(24, 16);
		list.insert(33, 14);
		list.insert(29, 5);
		list.insert(5, 2);
		list.insert(73, 13);
	}

	@Test
	void minimumEmptyTest(){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			FloorsArrayList emptyList = new FloorsArrayList(23);
			assertTrue(emptyList.minimum() == Double.POSITIVE_INFINITY || emptyList.minimum() == Double.MAX_VALUE,
					"Minimum isn't equal to Infinity in an empty List");
		});
	}

	@Test
	void minimumCorrectTest(){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			assertEquals(5, list.minimum(),
					"Minimum of (5,24,29,33,73) is not 5");
		});
	}

	@Test
	void minimumInsertTest(){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			list.insert(-8, 10);

			assertEquals(-8, list.minimum(),
					"Minimum of (5,24,29,33,73) after inserting -8 is not -8");
		});
	}

	@Test
	void minimumRemoveTest(){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			list.remove(list.lookup(5));

			assertEquals(24, list.minimum(),
					"Minimum of (5,24,29,33,73) after removing 5 is not 24");
		});
	}

	@Test
	void minimumEmptiedTest(){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			list.remove(list.lookup(24));
			list.remove(list.lookup(33));
			list.remove(list.lookup(29));
			list.remove(list.lookup(5));
			list.remove(list.lookup(73));


			assertTrue(list.minimum() == Double.POSITIVE_INFINITY || list.minimum() == Double.MAX_VALUE,
					"Minimum isn't equal to Infinity in an emptied List");
		});
	}

	@Test
	void minimumEmptiedInsertTest(){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			list.remove(list.lookup(24));
			list.remove(list.lookup(33));
			list.remove(list.lookup(29));
			list.remove(list.lookup(5));
			list.remove(list.lookup(73));

			list.insert(-9, 4);

			assertEquals(-9, list.minimum(),
					"Minimum of emptied list after inserting -9 isn't -9");
		});
	}

	@Test
	void maximumEmptyTest(){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			FloorsArrayList emptyList = new FloorsArrayList(23);
			assertTrue(emptyList.maximum() == Double.NEGATIVE_INFINITY || emptyList.minimum() == -Double.MAX_VALUE,
					"Maximum isn't equal to -Infinity in an empty List");
		});
	}

	@Test
	void maximumCorrectTest(){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			assertEquals(73, list.maximum(),
					"Maximum of (5,24,29,33,73) is not 73");
		});
	}

	@Test
	void maximumInsertTest(){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			list.insert(90, 10);

			assertEquals(90, list.maximum(),
					"Maximum of (5,24,29,33,73) after inserting 90 is not 90");
		});
	}

	@Test
	void maximumRemoveTest(){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			list.remove(list.lookup(73));

			assertEquals(33, list.maximum(),
					"Maximum of (5,24,29,33,73) after removing 73 is not 33");
		});
	}

	@Test
	void maximumEmptiedTest(){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			list.remove(list.lookup(24));
			list.remove(list.lookup(33));
			list.remove(list.lookup(29));
			list.remove(list.lookup(5));
			list.remove(list.lookup(73));


			assertTrue(list.maximum() == Double.NEGATIVE_INFINITY || list.minimum() == -Double.MAX_VALUE,
					"Maximum isn't equal to -Infinity in an emptied List");
		});
	}

	@Test
	void maximumEmptiedInsertTest(){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			list.remove(list.lookup(24));
			list.remove(list.lookup(33));
			list.remove(list.lookup(29));
			list.remove(list.lookup(5));
			list.remove(list.lookup(73));

			list.insert(-9, 4);

			assertEquals(-9, list.maximum(),
					"Maximum of emptied list after inserting -9 isn't -9");
		});
	}


}
