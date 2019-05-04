import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

class ListSuccessorPredecessorTest {

	private FloorsArrayList list;

	@BeforeAll
	static void printStart(){
		System.out.println();
		System.out.println("==Successor Predecessor Test======================");
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
	void successorCorrectTest(){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			assertEquals(29, list.successor(list.lookup(24)),
					"successor of 24 is not 29 in the list (5,24,29,33,73)");
		});
	}

	@Test
	void successorRemoveTest(){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			list.remove(list.lookup(29));

			assertEquals(33, list.successor(list.lookup(24)),
					"successor of 24 is not 33 after removing 29 in the list (5,24,29,33,73)");
		});
	}

	@Test
	void successorInsertTest(){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			list.insert(26, 6);

			assertEquals(26, list.successor(list.lookup(24)),
					"successor of 24 is not 26 after inserting 26 in the list (5,24,29,33,73)");
		});
	}

	@Test
	void successorRemoveInsertTest(){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			list.remove(list.lookup(29));
			list.insert(26, 6);

			assertEquals(26, list.successor(list.lookup(24)),
					"successor of 24 is not 26 after removing 29 and inserting 26 in the list (5,24,29,33,73)");
		});
	}

	@Test
	void predecessorCorrectTest(){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			assertEquals(29, list.predecessor(list.lookup(33)),
					"predecessor of 33 is not 29 in the list (5,24,29,33,73)");
		});
	}

	@Test
	void predecessorRemoveTest(){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			list.remove(list.lookup(29));

			assertEquals(24, list.predecessor(list.lookup(33)),
					"predecessor of 33 is not 24 after removing 29 in the list (5,24,29,33,73)");
		});
	}

	@Test
	void predecessorInsertTest(){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () ->{
			list.insert(30, 6);
			assertEquals(30, list.predecessor(list.lookup(33)),
					"predecessor of 33 is not 30 after inserting 30 in the list (5,24,29,33,73)");
		});
	}

	@Test
	void predecessorRemoveInsertTest(){
		assertTimeoutPreemptively(Duration.ofSeconds(15), () -> {
			list.remove(list.lookup(29));
			list.insert(26, 6);

			assertEquals(26, list.predecessor(list.lookup(33)),
					"predecessor of 33 is not 26 after removing 29 and inserting 26 in the list (5,24,29,33,73)");
		});
	}

}
