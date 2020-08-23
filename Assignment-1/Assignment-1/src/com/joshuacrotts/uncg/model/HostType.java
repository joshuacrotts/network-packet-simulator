package com.joshuacrotts.uncg.model;

/**
 *
 * @author Joshua
 */
public enum HostType {
  SOURCE {
    @Override
    public String toString() {
      return "SOURCE";
    }
  },
  DESTINATION {
    @Override
    public String toString() {
      return "DESTINATION";
    }
  }
}
