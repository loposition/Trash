package com.loposition.util.express.trash.object;

/**
 * @author huahua
 * @date create at 下午6:39 2018/4/4
 */
public interface TrashObject {

  Object getContent();

  boolean greaterThan(TrashObject trashObject);

  boolean lessThan(TrashObject trashObject);

  boolean equal(TrashObject trashObject);
}
