package com.rowland.xmlparser.presentation.downloadfeature.model;

/**
 * Class that represents a download in the presentation layer.
 */

public class DownloadModel  {

  String key;
  String value;

  public DownloadModel() {

  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }


  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("***** Download Details *****\n");
    stringBuilder.append("key=" + this.getKey() + "\n");
    stringBuilder.append("value=" + this.getValue() + "\n");
    stringBuilder.append("*******************************");
    return stringBuilder.toString();
  }
}
