import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LinkSetterGetterTest {

	private FloorsArrayLink link, prev, next, neg;

	@BeforeAll
	static void printStart(){
		System.out.println("\n==Setter Getter Test==============================\n");
	}

	@AfterAll
	static void printDone(){
		System.out.println("\n==Done============================================\n");
	}

	private static Stream<Arguments> keysSizesProvider() {
		return Stream.of(
				Arguments.of(50, 30, 70, -30, 50, 30, 70, 10),
				Arguments.of(5000, 3000, 7000, -4242, 500, 300, 700, 100),
				Arguments.of(0.5, 0.3, 0.7, -0.003, 5, 3, 7, 1),
				Arguments.of(1,2,3,4, 10, 10, 10, 9999999)
		);
	}

	void setup(double linkKey, double prevKey, double nextKey, double negKey,
	           int linkSize, int prevSize, int nextSize, int negSize){
		link = new FloorsArrayLink(linkKey, linkSize);
		prev = new FloorsArrayLink(prevKey, prevSize);
		next = new FloorsArrayLink(nextKey, nextSize);
		neg = new FloorsArrayLink(negKey, negSize);
	}

	@ParameterizedTest
	@MethodSource("keysSizesProvider")
	void keyGetterTest(double linkKey, double prevKey, double nextKey, double negKey,
	                   int linkSize, int prevSize, int nextSize, int negSize){
		setup(linkKey, prevKey, nextKey, negKey, linkSize, prevSize, nextSize, negSize);
		assertEquals(linkKey, link.getKey(),
				"key returned by getKey isn't equal to value given to constructor");
	}

	@ParameterizedTest
	@MethodSource("keysSizesProvider")
	void negKeyGetterTest(double linkKey, double prevKey, double nextKey, double negKey,
	                      int linkSize, int prevSize, int nextSize, int negSize){
		setup(linkKey, prevKey, nextKey, negKey, linkSize, prevSize, nextSize, negSize);
		assertEquals(negKey, neg.getKey());
	}

	@ParameterizedTest
	@MethodSource("keysSizesProvider")
	void sizeGetterTest(double linkKey, double prevKey, double nextKey, double negKey,
	                    int linkSize, int prevSize, int nextSize, int negSize){
		setup(linkKey, prevKey, nextKey, negKey, linkSize, prevSize, nextSize, negSize);
		assertEquals(linkSize, link.getArrSize(),
				"size returned by getArrSize isn't equal to value given to constructor");
	}

	@ParameterizedTest
	@MethodSource("keysSizesProvider")
	void nextGetterTest(double linkKey, double prevKey, double nextKey, double negKey,
	                    int linkSize, int prevSize, int nextSize, int negSize){
		setup(linkKey, prevKey, nextKey, negKey, linkSize, prevSize, nextSize, negSize);
		for (int i = 1; i <= linkSize; i++) {
			link.setNext(i, next);
		}
		for (int i = 1; i <= linkSize; i++) {
			assertEquals(next, link.getNext(i),
					"getNext doesn't return the correct link");
		}
	}

	@ParameterizedTest
	@MethodSource("keysSizesProvider")
	void prevGetterTest(double linkKey, double prevKey, double nextKey, double negKey,
	                    int linkSize, int prevSize, int nextSize, int negSize){
		setup(linkKey, prevKey, nextKey, negKey, linkSize, prevSize, nextSize, negSize);
		for (int i = 1; i <= linkSize; i++) {
			link.setPrev(i, prev);
		}
		for (int i = 1; i <= linkSize; i++) {
			assertEquals(prev, link.getPrev(i),
					"getPrev doesn't return the correct link");
		}
	}

	@ParameterizedTest
	@MethodSource("keysSizesProvider")
	void chainGettersTest(double linkKey, double prevKey, double nextKey, double negKey,
	                      int linkSize, int prevSize, int nextSize, int negSize){
		setup(linkKey, prevKey, nextKey, negKey, linkSize, prevSize, nextSize, negSize);
		link.setNext(1, next);
		link.setPrev(1, prev);
		next.setPrev(1, link);
		prev.setNext(1, link);
		prev.setPrev(1, neg );
		neg.setNext (1, prev);
		assertEquals(link, link.getNext(1).getPrev(1).getPrev(1).getPrev(1).getNext(1).getNext(1),
				"link.next.prev.prev.prev.next.next isn't equal to link\nin the list <-neg<->prev<->link<->next-> ");
	}

	@ParameterizedTest
	@MethodSource("keysSizesProvider")
	void nonExistentNextTest(double linkKey, double prevKey, double nextKey, double negKey,
	                         int linkSize, int prevSize, int nextSize, int negSize){
		setup(linkKey, prevKey, nextKey, negKey, linkSize, prevSize, nextSize, negSize);
		link.setNext(1, next);
		link.setPrev(1, prev);
		next.setPrev(1, link);
		prev.setNext(1, link);
		prev.setPrev(1, neg);
		neg.setNext(1, prev);

		FloorsArrayLink lastLink = neg.getNext(1).getNext(1).getNext(1).getNext(1);

		assertTrue(lastLink == null || (!lastLink.equals(neg) && !lastLink.equals(prev) &&
						!lastLink.equals(link) && !lastLink.equals(next)),
				getEqualLink(lastLink, "last next in: neg->prev->link->next->\\\\"));
	}

	private String getEqualLink(FloorsArrayLink lastLink, String list){
		if (lastLink != null){
			String output = list + " equals to one of the existent ones: ";
			return  lastLink.equals(neg)  ? (output + "neg") :
					lastLink.equals(prev) ? (output + "prev") :
							lastLink.equals(link) ? (output + "link") :
									output + "next";
		}
		return "";
	}

	@ParameterizedTest
	@MethodSource("keysSizesProvider")
	void nonExistentPrevTest(double linkKey, double prevKey, double nextKey, double negKey,
	                         int linkSize, int prevSize, int nextSize, int negSize){
		setup(linkKey, prevKey, nextKey, negKey, linkSize, prevSize, nextSize, negSize);
		link.setNext(1, next);
		link.setPrev(1, prev);
		next.setPrev(1, link);
		prev.setNext(1, link);
		prev.setPrev(1, neg);
		neg.setNext(1, prev);

		FloorsArrayLink lastLink = next.getPrev(1).getPrev(1).getPrev(1).getPrev(1);

		assertTrue((lastLink == null || ( !lastLink.equals(neg) && !lastLink.equals(prev) &&
						!lastLink.equals(link) && !lastLink.equals(next))),
				getEqualLink(lastLink, "first prev in: \\\\<-neg<-prev<-link<-next"));
	}


	@ParameterizedTest
	@MethodSource("keysSizesProvider")
	void nextSetterTest(double linkKey, double prevKey, double nextKey, double negKey,
	                    int linkSize, int prevSize, int nextSize, int negSize){
		setup(linkKey, prevKey, nextKey, negKey, linkSize, prevSize, nextSize, negSize);
		link.setNext(4, next);
		link.setNext(4, prev);

		assertNotEquals(next, link.getNext(4),
				"setNext doesn't change the old pointer");
	}

	@ParameterizedTest
	@MethodSource("keysSizesProvider")
	void prevSetterTest(double linkKey, double prevKey, double nextKey, double negKey,
	                    int linkSize, int prevSize, int nextSize, int negSize){
		setup(linkKey, prevKey, nextKey, negKey, linkSize, prevSize, nextSize, negSize);
		link.setPrev(4, prev);
		link.setPrev(4, next);

		assertNotEquals(prev, link.getPrev(4),
				"setPrev doesn't change the old pointer");
	}

}
