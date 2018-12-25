package com.loposition.util.express.trash.exception;

/**
 * @author huahua
 * @date create at 下午11:28 2018/4/4
 * description: //TODO
 */
public class StringParseException extends RuntimeException {

  private int startIndex;

  public StringParseException(int startIndex){
    this.startIndex = startIndex;
  }
}
