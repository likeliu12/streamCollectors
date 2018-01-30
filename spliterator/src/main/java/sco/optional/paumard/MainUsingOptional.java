package sco.optional.paumard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 * @author José Paumard
 */
public class MainUsingOptional {

	public static void main(String... args) {

		List<Double> result = new ArrayList<>();

		// ThreadLocalRandom.current()
		// .doubles(10_000).boxed()
		// .forEach(
		// d -> NewMath.inv(d)
		// .ifPresent(
		// inv -> NewMath.sqrt(inv)
		// .ifPresent(
		// sqrt -> result.add(sqrt)
		// )
		// )
		// );
		// System.out.println("# result = " + result.size());

		Function<Double, Stream<Double>> flatMapper = d -> NewMath.inv(d).flatMap(inv -> NewMath.sqrt(inv))
				.map(sqrt -> Stream.of(sqrt)).orElseGet(() -> Stream.empty());

		List<Double> rightResult = ThreadLocalRandom.current().doubles(10_000).parallel().map(d -> d * 20 - 10).boxed()
				.flatMap(flatMapper).collect(Collectors.toList());
		System.out.println("# rightResult = " + rightResult.size());
		
		rightResult = Arrays.asList(new Double(21), new Double(0), new Double(-1)).stream().map(a -> a)
				.flatMap(flatMapper).collect(Collectors.toList());
		System.out.println("# rightResult = " + rightResult.size());

	}

	private static void nullValue() {
		
		Optional<String> empty = Optional.empty();
		MainUsingOptional java8Tester = new MainUsingOptional();
		Integer value1 = null;
		Integer value2 = new Integer(10);

		// Optional.ofNullable - allows passed parameter to be null.
		Optional<Integer> a = Optional.ofNullable(value1);

		// Optional.of - throws NullPointerException if passed parameter is null
		Optional<Integer> b = Optional.of(value2);
		System.out.println(java8Tester.sum(a, b));
	}

	private Integer sum(Optional<Integer> a, Optional<Integer> b) {
		// Optional.isPresent - checks the value is present or not

		System.out.println("First parameter is present: " + a.isPresent());
		System.out.println("Second parameter is present: " + b.isPresent());

		// Optional.orElse - returns the value if present otherwise returns
		// the default value passed.
		Integer value1 = a.orElse(new Integer(0));

		// Optional.get - gets the value, value should be present
		Integer value2 = b.get();
		return value1 + value2;
	}
	

	static String B() {
	    System.out.println("B()...");
	    return "B";
	}

	public static void elseElseGet() {
		//create B anyway
	    System.out.println(Optional.of("A").orElse(B()));
	    //not create B if A exist
	    System.out.println(Optional.of("A").orElseGet(() -> B()));
	}
	
}
