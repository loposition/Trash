package com.loposition.util.express.trash.token;

import com.loposition.util.express.trash.object.TrashObject;

/**
 * @author huahua
 * @date create at 下午6:03 2018/4/4
 */
public class OperatorTrashToken extends BaseTrashToken {

  private String token ;
  private OperatorTrashType operatorTrashType;

  public OperatorTrashToken(OperatorTrashType operatorTrashType){
    this.token = operatorTrashType.getToken();
    this.operatorTrashType = operatorTrashType;
  }

  public Boolean execute(TrashObject arg1, TrashObject arg2){
    switch (operatorTrashType){
      case EQUAL:
        return arg1.equal(arg2);
      case UNEQUAL:
        return !arg1.equal(arg2);
      case GT:
        return arg1.greaterThan(arg2);
      case LT:
        return arg1.lessThan(arg2);
      case GTE:
        return !arg1.lessThan(arg2);
      case LTE:
        return !arg1.greaterThan(arg2);
      default:
        return Boolean.FALSE;
    }
  }


  public boolean isOperate(OperatorTrashType operatorTrashType){
    return this.operatorTrashType == operatorTrashType;
  }

}
