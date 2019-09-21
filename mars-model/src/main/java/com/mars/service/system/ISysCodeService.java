package com.mars.service.system;

import com.mars.core.service.CommonService;
import com.mars.model.system.SysCodeEntity;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lixl on 2017/7/23.
 */
public interface ISysCodeService extends CommonService<SysCodeEntity>{

    /**
     * 根据code查找数据
     * @param code
     * @return
     */
    List<SysCodeEntity> findByCode(String code);

    /**
     * 根据code和value查找对象
     * @param code
     * @param value
     * @return
     */
	SysCodeEntity getByCodeAndValue(String code, Integer value);

	  /**
     * 根据code和value查找name
     * @param code
     * @param value
     * @return
     */
	String getNameByCodeAndValue(String code, Integer value);

	/**
	 * 根据code获取HashMap对象
	 * @param code
	 * @return
	 */
	HashMap<Integer, SysCodeEntity> getValueAndObjHashMap(String code);

	/**
	 * 根据code获取对应数据<code>value</code> 和 <code>name</code>对应的map数据
	 * @param code
	 * @return
	 */
	HashMap<String, String> getValueAndNameHashMap(String code);

	/**
	 * 根据code获取对应数据<code>name</code> 和 <code>value</code>对应的map数据
	 * @param code
	 * @return
	 */
	HashMap<String, Integer> getNameAndValueHashMap(String code);

}
