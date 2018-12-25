package com.loposition.util.express.trash.plugin.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author huahua
 * @date create at 下午4:59 2018/8/17
 */
public class DictResultDTO {
  private Boolean flag;
  private List<String> words;

  public DictResultDTO(Boolean flag, Collection<String> words) {
    this.flag = flag;
    this.words = new ArrayList<>(words);
  }

  public Boolean getFlag() {
    return flag;
  }

  public void setFlag(Boolean flag) {
    this.flag = flag;
  }

  public List<String> getWords() {
    return words;
  }

  public void setWords(List<String> words) {
    this.words = words;
  }

  @Override
  public String toString() {
    return "DictResultDTO{" +
        "flag=" + flag +
        ", words=" + words +
        '}';
  }
}
