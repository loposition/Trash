package com.loposition.util.express.trash.dto;

import java.util.List;

/**
 * @author huahua
 * @date create at 下午3:21 2018/7/23
 */
public class TrashResult {

  private Boolean flag;

  private List<String> hitWords;

  private List<String> dict;

  public TrashResult(Boolean calculator, List<String> hitWords,
      List<String> dict) {
    this.flag = calculator;
    this.hitWords = hitWords;
    this.dict = dict;
  }

  public Boolean getFlag() {
    return flag;
  }

  public void setFlag(Boolean flag) {
    this.flag = flag;
  }

  public List<String> getHitWords() {
    return hitWords;
  }

  public void setHitWords(List<String> hitWords) {
    this.hitWords = hitWords;
  }

  public List<String> getDict() {
    return dict;
  }

  public void setDicts(List<String> dict) {
    this.dict = dict;
  }
}
