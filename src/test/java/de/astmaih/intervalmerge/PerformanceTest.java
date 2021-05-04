package de.astmaih.intervalmerge;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Random;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.astmaih.intervalmerge.merge.IntervalList;
import de.astmaih.intervalmerge.merge.IntervalListConverter;
import de.astmaih.intervalmerge.merge.IntervalMerger;

@DisabledIfSystemProperty(named = "skipPerformanceTest", matches = "true")
class PerformanceTest {
  
  Random random = new Random();
   
  private static final Logger log = LoggerFactory.getLogger(PerformanceTest.class);

  @Test
  void testBigInput() {
    Runtime runtime = Runtime.getRuntime();
    long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
    log.info("Used Memory before: {}MB", usedMemoryBefore / (1024 * 1024));
    
    String testString = IntStream.range(0, 10000000).boxed().map(i -> createInterval()).collect(Collectors.joining(" "));
    
    long start = System.currentTimeMillis();

    
    IntervalList list = new IntervalListConverter().getAsObject(null, null, testString);
    IntervalList result = new IntervalMerger(list).merge();
    
    long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
    long end = System.currentTimeMillis();

    log.info("Memory increased: {}MB", (usedMemoryAfter-usedMemoryBefore) / (1024 * 1024));
    log.info("Test duration: {}ms", end - start);

    assertFalse(result.getList().isEmpty());
    
  }
  
  private String createInterval() {
    int start = random.nextInt(100);
    int end = random.nextInt(100 - start) + start;
    return new StringJoiner(",", "[", "]").add(Integer.toString(start)).add(Integer.toString(end)).toString();
  }
}
