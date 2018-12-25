package com.loposition.util.express.trash.token;

/**
 * @author huahua
 * @date create at 上午10:04 2018/4/27
 */
public class DoubleTrashToken extends BaseTrashToken {

  private double content;

  public DoubleTrashToken(double content){
    this.content = content;
  }

  public double getContent(){
    return this.content;
  }
}
