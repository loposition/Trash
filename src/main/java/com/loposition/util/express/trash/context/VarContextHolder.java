package com.loposition.util.express.trash.context;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huahua
 * @date create at 上午11:17 2018/4/27
 */
public class VarContextHolder {

  final static Map<String,Object> HOLDER = new HashMap<String, Object>();

  public static void add(String key ,Object object){
    HOLDER.put(key,object);
  }

  public static Object get(String key){
    return HOLDER.get(key);
  }

  public static void removeAll(){HOLDER.clear();}

  public static void add(Map<String,Object> words){HOLDER.putAll(words);}
}
