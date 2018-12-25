package com.loposition.util.express.trash.object;

/**
 * @author huahua
 * @date create at 上午10:28 2018/4/27
 */
public class TrashDouble implements TrashObject {

  private double content;

  public Double getContent() {
    return content;
  }

  public TrashDouble(Double content){
    this.content = content;
  }

  public boolean greaterThan(TrashObject trashObject) {
    if (trashObject instanceof TrashDouble){
      return this.content >  ((TrashDouble)trashObject).getContent();
    }else if (trashObject instanceof  TrashLong){
      return this.content > ((TrashLong)trashObject).getContent();
    }
    return false;
  }

  public boolean lessThan(TrashObject trashObject) {
    if (trashObject instanceof TrashDouble){
      return this.content > ((TrashDouble)trashObject).getContent();
    }else if (trashObject instanceof TrashLong){
      return this.content > ((TrashLong)trashObject).getContent();
    }
    return false;
  }

  public boolean equal(TrashObject trashObject) {
    return false;
  }
}
