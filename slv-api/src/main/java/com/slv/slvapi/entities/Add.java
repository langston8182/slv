package com.slv.slvapi.entities;

/**
 * Add operation.
 */
public class Add extends Operation {

  /**
   * The path contain name of the added.
   */
  String path;

  /**
   * Provides Add object.
   * 
   * @param path
   *          The path given
   */
  public Add(String path) {
    this.path = path;
  }

  /**
   * @return {@link Add#path}.
   */
  public String getPath() {
    return path;
  }
}
