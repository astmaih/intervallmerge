package de.astmaih.intervalmerge.merge;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Model class for an interval
 * 
 *
 */
@Data
@AllArgsConstructor
public class Interval {
	
	private int start;
	private int end;

}
