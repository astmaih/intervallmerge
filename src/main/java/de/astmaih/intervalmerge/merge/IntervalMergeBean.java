package de.astmaih.intervalmerge.merge;

import javax.inject.Named;

import org.springframework.web.context.annotation.RequestScope;

import lombok.Getter;
import lombok.Setter;

/**
 * CDI Bean for variables and commands for merge_interval.xhtml
 *
 */
@Named
@RequestScope
@Getter
@Setter
public class IntervalMergeBean {
	
	private IntervalList input;
	private IntervalList result;
	
	
	/**
	 * Starts the merge and sets the result value
	 */
	public void merge() {
		result = new IntervalMerger(input).merge();
	}

}
