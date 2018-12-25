package com.loposition.util.express.trash.context;

import com.loposition.util.express.trash.plugin.DictService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huahua
 * @date create at 上午10:12 2018/7/11
 * description: 字典服务holder
 */
public class DictServiceHolder {

  private static final List<DictService> DICT_SERVICES_LIST = new ArrayList<>();

  public static Boolean regsiter(DictService service) {
    DICT_SERVICES_LIST.add(service);
    return Boolean.TRUE;
  }


  public static List<DictService> getAll() {
    return DICT_SERVICES_LIST;
  }


  public static DictService getDefaultService() {
    return DICT_SERVICES_LIST.stream().findFirst().orElse(null);
  }
}
