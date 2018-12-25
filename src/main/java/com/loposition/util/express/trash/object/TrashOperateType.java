package com.loposition.util.express.trash.object;

/**
 * @author huahua
 * @date create at 上午10:09 2018/4/8
 */
public enum TrashOperateType {

  GT(">", 2),
  LT("<", 2),
  GTE(">=", 2),
  EQUAL("==", 2),
  UNEQUAL("!=", 2),
  LTE("<=", 2),
  NOT("not", 1),
  AND("and", 2),
  OR("or", 2);

  private String operate;
  private int operateNum;

  TrashOperateType(String operate, int operateNum) {
    this.operate = operate;
    this.operateNum = operateNum;
  }


  public static TrashOperateType of(String operate){
    for (TrashOperateType trashOperateType : TrashOperateType.values()){
      if (trashOperateType.getOperate().equals(operate)){
        return trashOperateType;
      }
    }
    return null;
  }


  public String getOperate() {
    return operate;
  }

  public int getOperateNum() {
    return operateNum;
  }
}
