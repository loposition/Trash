package com.loposition.util.express.trash.object;

/**
 * @author huahua
 * @date create at 上午10:27 2018/7/11
 */
public class TrashDict implements TrashObject {

  private String content;

  public TrashDict(String content) {
    this.content = content;
  }

  @Override
  public boolean greaterThan(TrashObject trashObject) {
    return false;
  }

  @Override
  public boolean lessThan(TrashObject trashObject) {
    return false;
  }

  @Override
  public boolean equal(TrashObject trashObject) {
    if (trashObject instanceof TrashString) {
      TrashString otherString = (TrashString) trashObject;
      return content.equals(otherString.getContent());
    }
    return false;
  }

  @Override
  public String getContent() {
    return content;
  }

}
