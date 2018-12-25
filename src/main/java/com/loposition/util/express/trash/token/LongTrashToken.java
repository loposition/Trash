package com.loposition.util.express.trash.token;

/**
 * @author huahua
 * @date create at 下午11:49 2018/4/4
 */
public class LongTrashToken extends BaseTrashToken {
  private long content;

  public LongTrashToken(long content) {
    this.content = content;
  }

  public long getContent() {
    return content;
  }
}
