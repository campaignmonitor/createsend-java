package com.createsend.models.subscribers;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ConsentToTrack {
  UNCHANGED("Unchanged"),
  YES("Yes"),
  NO("No");

  private String value;

  ConsentToTrack(String value) {
    this.value = value;
  }

  @JsonCreator
  public static ConsentToTrack forValue(String value) {
    for (ConsentToTrack type : ConsentToTrack.values()) {
      if (type.value.toLowerCase().equals(value.toLowerCase())) {
        return type;
      }
    }

    return null;
  }
}
