package com.loposition.util.express.trash.token;

/**
 * @author huahua
 * @date create at 上午8:20 2018/4/5
 */
public class VariableTrashToken extends BaseTrashToken {

  private String content;


  public VariableTrashToken(String content) {
    this.content = content;
  }

  public String getContent() {
    return content;
  }
}
