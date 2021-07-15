package uk.co.jpawlak.java8exercises.pangas;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class Main {

    public static void main(String[] args) {
        //example_4_distinct_sorted();
        //example_random_numbers();
        //example_string_chars();
        //example_peek();
        //example_for_each_ordered();
        example_to_array();
    }

    static void example_to_array () {
        IntStream infiniteNumberStream = IntStream.iterate(1, i -> i+1);

        Integer[] intArray = infiniteNumberStream.limit(10)
                .boxed()
                .toArray(Integer[]::new);
        /*int[] intArray = infiniteNumberStream.limit(10)
                .toArray();*/

        System.out.println(Arrays.toString(intArray));

    }

    static void example_for_each_ordered () {
        List<Integer> list = Arrays.asList(2, 4, 6, 8, 10);

        System.out.println("for each : ");
        list.stream().parallel().forEach( System.out::println );        //1
        System.out.println("for each ordered : ");
        list.stream().parallel().forEachOrdered( System.out::println ); //2
    }

    static void example_peek (){
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

        List<Integer> newList = list.stream()
                .peek(System.out::println)
                .collect(Collectors.toList());

        System.out.println(newList);
    }

    static void example_string_chars (){
        /*IntStream stream = "12345_abcdefg".chars();
        stream.forEach(p -> System.out.println(p));*/

        //OR

        /*Stream<String> stream = Stream.of("A BC".split(""));
        stream.forEach(p -> System.out.println(p));*/
    }

    static void example_random_numbers () {
        Stream<Integer> randomNumbers = Stream
                .generate(() -> (new Random()).nextInt(100));

        randomNumbers.limit(120)
                .forEach(System.out::println);
    }

    static void example_4_distinct_sorted() {
        List<Integer> numbers = asList(9, 8, 0, 5, 6, 2, 3, 4, 9, 0, 6, 2, 3, 5, 0, 9, 6, 3, 4, 9, 3);

        List<Integer> processed = numbers.stream()
                .distinct()
                .sorted()
                .collect(toList());

        System.out.println(processed);
    }
}
