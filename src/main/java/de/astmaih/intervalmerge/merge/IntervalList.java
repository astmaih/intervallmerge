package de.astmaih.intervalmerge.merge;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Model class for a list of intervals
 *
 */
@Data
@AllArgsConstructor
public class IntervalList {
  
  private List<Interval> list;

}
