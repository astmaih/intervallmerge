package de.astmaih.intervalmerge;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import de.astmaih.intervalmerge.merge.Interval;
import de.astmaih.intervalmerge.merge.IntervalList;
import de.astmaih.intervalmerge.merge.IntervalMerger;

class IntervalMergerTest {

  @ParameterizedTest
  @MethodSource
  void testMerge(IntervalList inputList, IntervalList expectedList) {
    assertEquals(expectedList, new IntervalMerger(inputList).merge());

  }

  private static Stream<Arguments> testMerge() {
    return Stream.of(
        Arguments.of(null, null),
        Arguments.of(createIntervalList(new int[] {1, 2}, new int[] {3, 4}), createIntervalList(new int[] {1, 2}, new int[] {3, 4})),
        Arguments.of(createIntervalList(new int[] {1, 2}, new int[] {1, 2}), createIntervalList(new int[] {1, 2})),
        Arguments.of(createIntervalList(new int[] {-1, 2}, new int[] {-5, -3}), createIntervalList(new int[] {-5, -3}, new int[] {-1, 2})),
        Arguments.of(createIntervalList(new int[] {1, 8}, new int[] {2, 7}, new int[] {9, 18}), createIntervalList(new int[] {1, 8}, new int[] {9, 18})),
        Arguments.of(createIntervalList(new int[] {1, 4}, new int[] {7, 15}, new int[] {3, 8}), createIntervalList(new int[] {1, 15})),
        Arguments.of(createIntervalList(new int[] {1, 2}, new int[] {8, 15}, new int[] {2, 8}), createIntervalList(new int[] {1, 15})),
        Arguments.of(createIntervalList(new int[] {1, 7}, new int[] {2, 8}, new int[] {9, 18}), createIntervalList(new int[] {1, 8}, new int[] {9, 18})),
        Arguments.of(createIntervalList(new int[] {25, 30}, new int[] {2, 19}, new int[] {14, 23}, new int[] {4, 8}), createIntervalList(new int[] {2, 23}, new int[] {25, 30}))
    );
  }

  private static IntervalList createIntervalList(int[]... interval) {
    return new IntervalList(Arrays.stream(interval).map(i -> new Interval(i[0], i[1])).collect(Collectors.toList()));
  }

}
