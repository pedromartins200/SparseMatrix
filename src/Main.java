import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main driver class
 * @author - Pedro Martins
 *
 */
public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		List<String> inputs = new ArrayList<>();

		while (!SparseMatrixReader.validate(inputs)) {
			inputs.add(sc.nextLine());
		}
		sc.close();
		final long startTime = System.nanoTime();
		// Create a solver object
		// solver object will "do the work"
		Solver solver = new Solver(inputs);
		// print result in stdout
		solver.solve();
		System.out.println("Total execution time: " + (System.nanoTime() - startTime) / 1000000.0 + " miliseconds");
	}
}

