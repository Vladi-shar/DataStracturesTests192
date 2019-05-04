
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNull;

public class RunTimeTest {

	private ArrayList<Double> keys20000 = new ArrayList<>();
	private ArrayList<Double> keys7500 = new ArrayList<>();
	private ArrayList<Integer> sizes = new ArrayList<>();
	private boolean passed$$$ = false;

	@BeforeEach
	void setup(){
		try (Stream<String> lines = Files.lines(Paths.get("Generated_keys_27500.txt"))) {
			lines.forEachOrdered(this::addToKeysArray);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try (Stream<String> lines = Files.lines(Paths.get("Generated_Sizes_27500.txt"))) {
			lines.forEachOrdered(line -> sizes.add(Integer.parseInt(line)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addToKeysArray(String line){
		if (line.equals("$$$")) passed$$$ = true;
		else if (!passed$$$) keys20000.add(Double.parseDouble(line));
		else keys7500.add(Double.parseDouble(line));
	}


	@Test
	void runTimeTest(){

		System.out.println();
		System.out.println("\n==RunTimeTest=====================================\n");
		System.out.println();

		FloorsArrayList list = new FloorsArrayList(20000);
		ArrayList<FloorsArrayLink> links = new ArrayList<>();


		try {

			long stopWatchStart = System.nanoTime();

			try {
				for (int i = 0; i < 20000; i++) {
					list.insert(keys20000.get(i), sizes.get(i));
				}
			} catch (Exception e){
				assertNull(e,"Caught exception " + e.toString() + " during insert. terminated test");
			}


			long stopWatchInsertLap = System.nanoTime();

			// lookup existing keys and save pointers
			try {
				for (int i = 0; i < 20000; i++) {
					FloorsArrayLink link = list.lookup(keys20000.get(i));
					links.add(link);
				}

				// lookup not existing keys
				for (int i = 0; i < 7500; i++) {
					list.lookup(keys7500.get(i));
				}
			} catch (Exception e){
				assertNull(e,"Caught exception " + e.toString() + " during lookup. terminated test.");
			}

			long stopWatchLookupLap = System.nanoTime();

			try{
			// remove existing keys
				for (int i = 0; i < 20000; i++) {
					list.remove(links.get(i));
				}
			} catch (Exception e){
				assertNull(e,"Caught exception " + e.toString() + " during remove. terminated test.");
			}

			long stopWatchRemoveLap = System.nanoTime();

//			try{
//				for (int i = 19999; i >= 0; i--) {
//					list.lookup(keys20000.get(i));
//				}
//			} catch (Exception e){
//				assertNull(e,"Caught exception " + e.toString() + " during second lookup. terminated test.");
//			}

			long stopWatchStop = System.nanoTime();


			System.out.println(
					"Total  runtime = "
							+ NumberFormat.getInstance().format(stopWatchStop - stopWatchStart)
							+ " nano seconds");

			System.out.println(
					"Insert runtime = "
							+ NumberFormat.getInstance().format(stopWatchInsertLap - stopWatchStart)
							+ " nano seconds");

			System.out.println(
					"Lookup runtime = "
							+ NumberFormat.getInstance().format(stopWatchLookupLap - stopWatchInsertLap)
							+ " nano seconds");

			System.out.println(
					"Remove runtime = "
							+ NumberFormat.getInstance().format(stopWatchRemoveLap - stopWatchLookupLap)
							+ " nano seconds");

		}
		catch (NullPointerException e){

		}

		System.out.println("\n==Done============================================\n");
	}
}
