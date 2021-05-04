package de.astmaih.intervalmerge.merge;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;

/**
 * Merger class to merge the given intervals
 * the procedures are the following:
 * 1. sort the interval list based on the interval start
 * 2. for every interval of the sorted list:
 *  2a. test if the interval overlaps the last interval in the result list (if the result list is empty, add the element and return)
 *  2b. if the intervalls are overlapping, set the end to the greater end from both intervals
 *  2c. if not, add the new interval to the result list.
 *
 */
@AllArgsConstructor
public class IntervalMerger {

  private IntervalList intervalList;

  /**
   * starts the merge and returns the result of the merge
   * @return {@link IntervalList} the merge result
   */
  public IntervalList merge() {
    if (intervalList == null) {
      return null;
    }
    List<Interval> resultList = new ArrayList<>();
    intervalList.getList().stream().sorted(this::sort).forEach(interval -> mergeInterval(interval, resultList));
    return new IntervalList(resultList);
  }

  private int sort(Interval interval1, Interval interval2) {
    return Integer.compare(interval1.getStart(), interval2.getStart());
  }

  private void mergeInterval(Interval interval, List<Interval> resultList) {
    if (resultList.isEmpty()) {
      resultList.add(new Interval(interval.getStart(), interval.getEnd()));
      return;
    }

    Interval lastMerged = resultList.get(resultList.size() - 1);
    if (lastMerged.getEnd() < interval.getStart()) {
      resultList.add(new Interval(interval.getStart(), interval.getEnd()));
    } else {
      lastMerged.setEnd(Math.max(lastMerged.getEnd(), interval.getEnd()));
    }
  }
}
