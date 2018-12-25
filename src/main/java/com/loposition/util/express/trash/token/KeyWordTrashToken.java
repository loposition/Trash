package com.loposition.util.express.trash.token;

/**
 * @author huahua
 * @date create at 下午6:33 2018/4/4
 */
public class KeyWordTrashToken  extends BaseTrashToken {

  private String content;

  private KeywordTrashType keywordTrashType;

  public String getContent() {
    return content;
  }

  public KeyWordTrashToken(KeywordTrashType keywordTrashType) {

    this.keywordTrashType = keywordTrashType;
  }

  public boolean is(KeywordTrashType keywordTrashType){
    return this.keywordTrashType == keywordTrashType;
  }
}
