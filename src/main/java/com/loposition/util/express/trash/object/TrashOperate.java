package com.loposition.util.express.trash.object;

/**
 * @author huahua
 * @date create at 上午10:08 2018/4/8
 */
public class TrashOperate implements TrashObject{

  private TrashOperateType trashOperateType;

  public TrashOperateType getTrashOperateType() {
    return trashOperateType;
  }

  public TrashOperate(TrashOperateType trashOperateType){
    this.trashOperateType = trashOperateType;
  }

  public Object getContent() {
    return trashOperateType;
  }

  public boolean greaterThan(TrashObject trashObject) {
    return false;
  }

  public boolean lessThan(TrashObject trashObject) {
    return false;
  }

  public boolean equal(TrashObject trashObject) {
    return false;
  }
}
