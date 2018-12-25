package com.loposition.util.express.trash.util;


/**
 * @author huahua
 * @date create at 上午9:49 2018/6/16
 */
public class ObjectNameUtil {

  public static String getDefaultName(Object object){
    if (object == null){
      return null;
    }
    String simpleName = object.getClass().getSimpleName();
    return String.valueOf(simpleName.toLowerCase().charAt(0)) + simpleName
        .substring(1, simpleName.length());
  }
}
