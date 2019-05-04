import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ListSortedTest {


	@Test
	void insertOrderSortedTest(){
		System.out.println();
		System.out.println("==Insert Sorted Order Test========================");
		System.out.println();

		assertTimeoutPreemptively(Duration.ofMinutes(2), () -> {
					FloorsArrayList list = new FloorsArrayList(1005);
					ArrayList<Double> keys = new ArrayList<>();
					double[] keysArray = new double[1000];
					for (int i = 0; i < 1000; i++) {
						double key = Math.random() * 100;
						boolean inserted = false;
						while (!inserted) {
							if (keys.contains(key)) {
								key = Math.random() * 100;
							} else {
								inserted = true;
								keys.add(key);
								keysArray[i] = key;

								list.insert(key, (i % 300) + 1);

							}
						}

					}
					Arrays.sort(keysArray);
					FloorsArrayLink l = list.lookup(list.minimum());
					for (double v : keysArray) {
						assertEquals(v, l.getKey(),
								"The list isn't sorted properly.");
						l = l.getNext(1);
					}
				});

		System.out.println("==Done============================================");
		System.out.println();
	}

}
