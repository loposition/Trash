package com.loposition.util.express.trash.exception;

/**
 * @author huahua
 * @date create at 下午4:56 2018/4/5
 */
public class VariableNotFoundException extends RuntimeException {

  private String variableName;

  private String message;

  private static String DEFAULT_MMESSAGE = "not found variable";

  public VariableNotFoundException(String variableName) {
    this.variableName = variableName;
  }

  public VariableNotFoundException(String variableName, String message) {
    this.variableName = variableName;
  }

  @Override
  public String getMessage() {
    return (this.message == null ? DEFAULT_MMESSAGE : this.message) + " because of --" + variableName;
  }
}
