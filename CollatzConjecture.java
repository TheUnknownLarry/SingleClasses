package de.theunknownlarry.singleclasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
 * With this class you can test the CollatzConjecture for an given section
 * Usage:
 *   first parameter: start value (included)
 *   second parameter: end value (included)
 *   -k: goes over the section, but if it gets to a value that was used before it says it is known and continues with next value [optional] (has to be last parameter)
 */
public class CollatzConjecture
{
    public static void main(final String[] args)
    {
        if (args.length == 2) {
            iterateOver(Long.parseLong(args[0]), Long.parseLong(args[1]));
        }
        else if (args.length == 3) {
            if (args[2].equalsIgnoreCase("-k")) {
                knowWhichWorks(Long.parseLong(args[0]), Long.parseLong(args[1]));
            }
        }
    }

    private static void knowWhichWorks(final long min, final long max)
    {
        ArrayList<Long> valuesWithHighestIterations = new ArrayList<>();
        long iterations = 0L;
        final ArrayList<Long> workingList = new ArrayList<>();
        for (long i = min; i <= max; i++) {
            System.out.print(i);
            final long iterationsForI = problemKnowing(i, workingList);
            if (!workingList.contains(i)) {
                workingList.add(i);
            }
            System.out.print("\n");
            System.out.print("iterations for " + i + ": " + iterationsForI + "\n");
            if (iterationsForI > iterations) {
                valuesWithHighestIterations = new ArrayList<>();
                valuesWithHighestIterations.add(i);
                iterations = iterationsForI;
            }
            else if (iterationsForI == iterations) {
                valuesWithHighestIterations.add(i);
            }
        }
        System.out.println("\nHighest iteration count for " + Arrays.toString(valuesWithHighestIterations.toArray()) + ": " + iterations);
        workingList.sort(Collections.reverseOrder());
        System.out.println("10 Highest used numbers: " + Arrays.toString(workingList.subList(0, 10).toArray()));
    }

    public static long problemKnowing(final long number, final List<Long> workingList)
    {
        if (workingList.contains(number)) {
            System.out.print(" --> known");
            return 1L;
        }
        if (number == 1L) {
            return 1L;
        }
        final long next;
        final long rest = number % 2L;
        if (rest == 0L) {
            //even
            next = number / 2L;
        }
        else if (rest == 1L) {
            //odd
            next = 3L * number + 1L;
        }
        else {
            throw new IllegalArgumentException(String.valueOf(number));
        }
        System.out.print(" --> " + next);
        final long iterations = problemKnowing(next, workingList) + 1L;
        workingList.add(number);
        return iterations;
    }

    private static void iterateOver(final long min, final long max)
    {
        ArrayList<Long> valuesWithHighestIterations = new ArrayList<>();
        long iterations = 0L;
        for (long i = min; i <= max; i++) {
            System.out.print(i);
            final long iterationsForI = problem(i);
            System.out.print("\n");
            System.out.print("iterations for " + i + ": " + iterationsForI + "\n");
            if (iterationsForI > iterations) {
                valuesWithHighestIterations = new ArrayList<>();
                valuesWithHighestIterations.add(i);
                iterations = iterationsForI;
            }
            else if (iterationsForI == iterations) {
                valuesWithHighestIterations.add(i);
            }
        }
        System.out.print("\niterations for " + Arrays.toString(valuesWithHighestIterations.toArray()) + ": " + iterations + "\n");
    }

    public static long problem(final long number)
    {
        if (number == 1L) {
            return 1L;
        }
        final long rest = number % 2L;
        if (rest == 0L) {
            //even
            final long next = number / 2L;
            System.out.print(" --> " + next);
            return problem(next) + 1L;
        }
        else if (rest == 1L) {
            //odd
            final long next = 3L * number + 1L;
            System.out.print(" --> " + next);
            return problem(next) + 1L;
        }
        throw new IllegalArgumentException(String.valueOf(number));
    }
}
