package uk.co.jpawlak.java8exercises.exercises;

import org.junit.Ignore;
import org.junit.Test;
import uk.co.jpawlak.java8exercises.utils.Company;
import uk.co.jpawlak.java8exercises.utils.Employee;

import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.*;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static java.util.Arrays.asList;
import static java.util.stream.LongStream.concat;
import static uk.co.jpawlak.java8exercises.utils.Employees.allEmployees;

@SuppressWarnings("all")
public class Pack_4_Streams_Difficult {

    private static final List<Employee> EMPLOYEES = allEmployees();

    @Ignore
    @Test
    public void exercise_1_findFirst() {
        // find whether there are two employees with the same first name and surname and return the name
        // the solution has to be a single statement, complexity O(n^2) is acceptable

        String result = null;

        //TODO write your code here
        result = EMPLOYEES.stream()
                .collect(Collectors.groupingBy(
                        employee -> employee.getFirstName() + " " + employee.getSurname(),
                        LinkedHashMap::new,
                        Collectors.toList()))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().size() >= 2)
                .findFirst()
                .map(stringListEntry -> stringListEntry.getKey())
                .orElse(null);

        //Collector<Employee, ?, LinkedHashMap<String, List<Object>>> objectLinkedHashMapCollector = Collectors.groupingBy(employee -> employee.getFirstName() + " " + employee.getSurname(), LinkedHashMap::new, Collectors.toList());

        assertThat(result, sameBeanAs("Holly Davies"));
    }

    @Ignore
    @Test
    public void exercise_2_groupingBy_counting() {
        // find the total number of groups of at least 5 employees living close to each other
        // consider all employees with the same 2 first characters of the home address post code a single group
        // you can collect to map and then stream over it, however the solution has to be a single statement

        long result = 0;
//        result = EMPLOYEES.stream()
//                .collect(Collectors.groupingBy
//                        (o -> o.getHomeAddress().getPostCode().substring(0,2),
//                        Collectors.counting()
//                        ))
//                .values()
//                .stream()
//                .filter(aLong -> aLong >= 5)
//                .count();

        result = EMPLOYEES.stream()
                .collect(Collectors.groupingBy
                        (o -> o.getHomeAddress().getPostCode().substring(0, 2)))
                .entrySet()
                .stream()
                .filter(stringListEntry -> stringListEntry.getValue().size() >= 5)
                .mapToLong(entry -> Long.valueOf(1))
                .sum();

        /*Collector<Employee, ?, Map<Object, Long>> objectMapCollector = Collectors.groupingBy
                (o -> o.getHomeAddress().getPostCode().substring(0, 2),
                        Collectors.counting()
                );*/
        //TODO write your code here

        assertThat(result, sameBeanAs(110L));
    }

    @Ignore
    @Test
    public void exercise_3_flatMap() {
        // find the total number of all different home and correspondence addresses

        long result = 0;

        //TODO write your code here
        result = EMPLOYEES.stream()
                .flatMap(employee -> Stream.of(employee.getHomeAddress(), employee.getCorrespondenceAddress().orElse(null)))
                .filter(address -> address != null)
                .distinct()
                .count();

        assertThat(result, sameBeanAs(1820L));
    }

    @Ignore
    @Test
    public void exercise_4_groupingBy_summingInt() {
        // find how much in total each company pays (annually) to their employees, order result by amount
        // you can convert the salaries to ints using BigDecimal#intValue method
        // you can collect to map and then stream over it, however the solution has to be a single statement

        DecimalFormat decimalFormat = new DecimalFormat("£#,###.00");
        List<String> result = null;
        result = EMPLOYEES.stream()
                .collect(Collectors.groupingBy(
                        emp -> emp.getCompany().getName(),
                        Collectors.summingInt(
                                value -> value.getSalary().intValue())))
                .entrySet()
                .stream()
                .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
                .map(pair -> pair.getKey() + " - " + decimalFormat.format(pair.getValue()))
                .collect(Collectors.toList());

        //TODO write your code here

        assertThat(result, sameBeanAs(asList(
                "Anglo American - £12,119,153.00",
                "HSBC - £11,469,144.00",
                "Royal Bank of Scotland Group - £11,127,807.00",
                "BP - £10,925,088.00",
                "AstraZeneca - £10,507,305.00",
                "HBOS - £10,428,819.00",
                "Royal Dutch Shell - £10,100,098.00",
                "Barclays plc - £10,071,534.00",
                "Vodafone Group - £10,029,401.00",
                "GlaxoSmithKline - £9,499,235.00"
        )));
    }

    @Ignore
    @Test
    public void exercise_5_patternCompileSplitAsStream() {
        // count the instances of words and output a list of formatted strings
        // output the strings sorted lexicographically by name
        // you can use collect twice
        // as always, a single statement solution is expected
        // hint: look at Pattern.compile(regex).splitAsStream(string)

        String string = "dog" + "\n" + "bird" + "\n" + "cat" + "\n" + "cat" + "\n" + "dog" + "\n" + "cat";
        List<String> result = null;

        //TODO write your code here
        result = Pattern.compile("\n").splitAsStream(string)
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted((o1, o2) -> o1.getKey().compareTo(o2.getKey()))
                .map(entry -> entry.getKey() + " - " + entry.getValue())
                .collect(Collectors.toList());





        assertThat(result, sameBeanAs(asList(
                "bird - 1",
                "cat - 3",
                "dog - 2"
        )));
    }

    @Ignore
    @Test
    public void exercise_6_mapToLong() {
        // the rows and columns of the chess board are assigned arbitrary numbers (instead of letters and digits)
        // the value of the square is a multiplication of the numbers assigned to corresponding row and column
        // e.g. square A1 has a value 6,432 * 6,199 = 39,871,968
        // calculate the sum of values of all squares
        int[] rows = {6432, 8997, 8500, 7036, 9395, 9372, 9715, 9634};
        int[] columns = {6199, 9519, 6745, 8864, 8788, 7322, 7341, 7395};
        long result = 0;

        //TODO write your code here
        result = Arrays.stream(rows)
                .asLongStream()
                .flatMap(row -> Arrays.stream(columns)
                .mapToLong(col -> row * col))
                .sum();

        assertThat(result, sameBeanAs(4294973013L));
    }

    @Ignore
    @Test
    public void exercise_7_randomLongs_concat_toArray() {
        // concatenate two random streams of numbers (seed is fixed for testing purposes),
        // then revert the sign of the negative ones
        // then sort them and pick 10 middle elements (hint: you can use skip and limit)
        // then do modulo 1000 (remainder of division by 1000)
        // and finally collect the result into an array
        LongStream longs = new Random(0).longs(10);
        IntStream ints = new Random(0).ints(10);
        int[] result = null;

        //TODO write your code here
        result = LongStream.concat(longs, ints.asLongStream())
                .map(Math::abs)
                .sorted()
                .skip(5)
                .limit(10)
                .map(n -> n % 1000)
                .mapToInt(n -> (int) n)
                .toArray();


        assertThat(result, sameBeanAs(new long[] {106, 266, 402, 858, 313, 688, 303, 137, 766, 896}));
    }

}
