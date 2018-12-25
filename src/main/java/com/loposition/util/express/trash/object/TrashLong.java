package com.loposition.util.express.trash.object;

/**
 * @author huahua
 * @date create at 下午6:41 2018/4/4
 */
public class TrashLong implements TrashObject {

  public long content;

  public TrashLong(long content) {
    this.content = content;
  }

  public boolean greaterThan(TrashObject trashObject) {
    if (trashObject instanceof TrashLong) {
      return this.content > ((TrashLong) trashObject).getContent();
    }else if (trashObject instanceof TrashDouble){
      return this.content > ((TrashDouble)trashObject).getContent();
    }
    return false;
  }

  public boolean lessThan(TrashObject trashObject) {
    if (trashObject instanceof TrashLong) {
      return this.content < ((TrashLong) trashObject).getContent();
    }else if (trashObject instanceof TrashDouble){
      return this.content > ((TrashDouble)trashObject).getContent();
    }
    return false;
  }

  public boolean equal(TrashObject trashObject) {
    if (trashObject instanceof TrashLong) {
      return this.content == ((TrashLong) trashObject).getContent();
    }
    return false;
  }


  public Long getContent() {
    return content;
  }
}
