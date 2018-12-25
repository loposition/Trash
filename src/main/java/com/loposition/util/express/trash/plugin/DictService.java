package com.loposition.util.express.trash.plugin;

import com.loposition.util.express.trash.plugin.dto.DictResultDTO;

/**
 * @author huahua
 * @date create at 上午10:08 2018/7/11
 * description: 字典服务
 */
public interface DictService {

  DictResultDTO textBelongsDict(String dict, String text);
}
