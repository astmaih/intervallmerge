package de.astmaih.intervalmerge.merge;

import java.util.Arrays;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 * class for converting input and output from/to frontend
 *
 */
public class IntervalListConverter implements Converter {
  
  private static final String SYNTAX_ERROR_HEADER = "Syntax error";
  private static final String SYNTAX_ERROR_BODY = "Sntax error for element: ";

  /**
   * 
   * {@inheritDoc}
   */
  @Override
  public IntervalList getAsObject(FacesContext context, UIComponent component, String value) {
    if (value == null) {
      return null;
    }
    return new IntervalList(
        Arrays.stream(value.trim().split(" ")).filter(v -> !v.trim().equals("")).map(this::mapStringToInterval).collect(Collectors.toList()));
  }

  /**
   * 
   * {@inheritDoc}
   */
  @Override
  public String getAsString(FacesContext context, UIComponent component, Object value) {
    if (value == null) {
      return "";
    } else if (value instanceof IntervalList) {
      return ((IntervalList) value).getList().stream().map(this::mapIntervalToString).collect(Collectors.joining(" "));
    } else {
      throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion error",
          "given Object is not an instance of IntervalList"));
    }
  }

  private Interval mapStringToInterval(String value) {
    if (!value.startsWith("[") || !value.endsWith("]")) {
      throw new ConverterException(
          new FacesMessage(FacesMessage.SEVERITY_ERROR, SYNTAX_ERROR_HEADER, SYNTAX_ERROR_BODY + value));
    }

    String[] edges = value.substring(1, value.length() - 1).split(",");

    if (edges.length != 2) {
      throw new ConverterException(
          new FacesMessage(FacesMessage.SEVERITY_ERROR, SYNTAX_ERROR_HEADER, SYNTAX_ERROR_BODY + value));
    }
    
    try {
      Interval interval = new Interval(Integer.parseInt(edges[0]), Integer.parseInt(edges[1]));
      if (interval.getStart() > interval.getEnd()) {
        throw new ConverterException(
            new FacesMessage(FacesMessage.SEVERITY_ERROR, SYNTAX_ERROR_HEADER, "End of interval should be greater than start " + value));
      } 
      return interval;
    } catch (NumberFormatException e) {
      throw new ConverterException(
          new FacesMessage(FacesMessage.SEVERITY_ERROR, SYNTAX_ERROR_HEADER, SYNTAX_ERROR_BODY + value), e);
    }
  }

  private String mapIntervalToString(Interval interval) {
    return new StringJoiner(",", "[", "]").add(Integer.toString(interval.getStart()))
        .add(Integer.toString(interval.getEnd())).toString();
  }

}
