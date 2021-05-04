package de.astmaih.intervalmerge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import javax.faces.convert.ConverterException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import de.astmaih.intervalmerge.merge.Interval;
import de.astmaih.intervalmerge.merge.IntervalList;
import de.astmaih.intervalmerge.merge.IntervalListConverter;

class IntervalListConverterTest {
  
  private static final IntervalList INTERVAL = new IntervalList(Arrays.asList(new Interval(1,2), new Interval(5, 8), new Interval(4, 7)));
  private static final IntervalList INTERVAL_NEGATIVE = new IntervalList(Arrays.asList(new Interval(-1,2), new Interval(-5, 8), new Interval(4, 7)));
  private static final String INTERVAL_STRING = "[1,2] [5,8] [4,7]";
  private static final String INTERVAL_STRING_NEGATIVE = "[-1,2] [-5,8] [4,7]";
  
  
  
  @Test
  void testGetAsStringHappyFlow() {
    assertEquals(INTERVAL_STRING, new IntervalListConverter().getAsString(null, null, INTERVAL));
  }

  @Test
  void testGetAsStringNegativeValues() {
    assertEquals(INTERVAL_STRING_NEGATIVE, new IntervalListConverter().getAsString(null, null, INTERVAL_NEGATIVE));
  }
  
  @Test
  void testGetAsStringNullObject() {
    assertEquals("", new IntervalListConverter().getAsString(null, null, null));
  }
  
  @Test
  void testGetAsStringWrongClass() {
    IntervalListConverter converter = new IntervalListConverter();
    assertThrows(ConverterException.class, () -> converter.getAsString(null, null, "wrong class"));
  }
  
  @Test
  void testGetAsObjectNegativeValues() {
    assertEquals(INTERVAL_NEGATIVE, new IntervalListConverter().getAsObject(null, null, INTERVAL_STRING_NEGATIVE));
  }

  @Test
  void testGetAsObjectHappyFlow() {
    assertEquals(INTERVAL, new IntervalListConverter().getAsObject(null, null, INTERVAL_STRING));
  }
  
  @Test
  void testGetAsObjectNullObject() {
    assertNull(new IntervalListConverter().getAsObject(null, null, null));
  }
  
  @Test
  void testGetAsObjectTooManySpaces() {
    String testString = " [1,2] [5,8]  [4,7] ";
    assertEquals(INTERVAL, new IntervalListConverter().getAsObject(null, null, testString));
  }
  
  @ParameterizedTest
  @ValueSource(strings = {"[1,2", "1,2]", "[1]", "[1,2,3]", "[1,e]", "[2,1]", "[1, 2]", "[1,2], [3,4]"})
  void testGetAsObjectWrongSyntax(String input) {
    IntervalListConverter converter = new IntervalListConverter();
    assertThrows(ConverterException.class, () -> converter.getAsObject(null, null, input));
    
  }
}
