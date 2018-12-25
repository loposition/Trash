package com.loposition.util.express.trash.token;

/**
 * @author huahua
 * @date create at 下午6:34 2018/4/4
 * description: 所有操作符枚举
 */
public enum  OperatorTrashType {


  EQUAL("==",0),
  UNEQUAL("!=",0),
  GT(">",0),
  LT("<",0),
  GTE(">=",0),
  LTE("<=",0),
  LEFT("(",1),
  RIGHT(")",1),
  ARRAY_LEFT("{",1),
  ARRAY_RIGHT("}",1);



  private final String token;

  private final int level;

  OperatorTrashType(String token,int level){
    this.token = token;
    this.level = level;
  }


  public static OperatorTrashType of (String token){
    for (OperatorTrashType operatorTrashType : OperatorTrashType.values()){
      if (operatorTrashType.getToken().equals(token)){
        return operatorTrashType;
      }
    }
    return null;
  }

  public String getToken() {
    return token;
  }

  public int getLevel() {
    return level;
  }
}
