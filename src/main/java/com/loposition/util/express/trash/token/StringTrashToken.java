package com.loposition.util.express.trash.token;

/**
 * @author huahua
 * @date create at 下午11:25 2018/4/4
 */
public class StringTrashToken extends BaseTrashToken {

  private String content;

  public StringTrashToken(String content){
    if (content == null){
      content = "";
    }
    this.content = content;
  }

  public String getContent() {
    return content;
  }
}
