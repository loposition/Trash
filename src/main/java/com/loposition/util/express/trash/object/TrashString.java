package com.loposition.util.express.trash.object;

/**
 * @author huahua
 * @date create at 下午6:40 2018/4/4
 */
public class TrashString implements TrashObject {

  private String content;

  public TrashString(String content){
    this.content = content;
  }


  public boolean greaterThan(TrashObject trashObject) {
    return false;
  }

  public boolean lessThan(TrashObject trashObject) {
    return false;
  }

  public boolean equal(TrashObject trashObject) {
    if (trashObject instanceof TrashString){
      TrashString otherString  = (TrashString) trashObject;
      return content.equals(otherString.getContent());
    }
    return false;
  }



  public String getContent() {
    return content;
  }


}
