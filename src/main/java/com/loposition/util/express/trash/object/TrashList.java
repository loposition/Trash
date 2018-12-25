package com.loposition.util.express.trash.object;

import com.loposition.util.express.trash.context.VarContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author huahua
 * @date create at 下午7:06 2018/4/4
 */
public class TrashList<T extends TrashObject> implements TrashObject {

  public List<T> content;

  public TrashList(List<T> content) {
    this.content = content;
  }

  public TrashList(String key) {
    Object o = VarContextHolder.get(key.substring(1, key.length()));
    if (o instanceof List) {
      List list = (List) o;
      if(list.size()>0){
        content = (List<T>) list.stream().map(
            item -> {return new TrashString(String.valueOf(item));}
        ).collect(Collectors.toList());
      }
    } else {
      content = Collections.EMPTY_LIST;
    }
  }


  @Override
  public boolean greaterThan(TrashObject trashObject) {
    return false;
  }

  @Override
  public boolean lessThan(TrashObject trashObject) {
    return false;
  }

  @Override
  public boolean equal(TrashObject trashObject) {
    return false;
  }

  @Override
  public List<T> getContent() {
    return content;
  }
}
