package com.mars.service.system.impl;

import com.mars.core.service.CommonServiceSupport;
import com.mars.model.system.SysOrgaEntity;
import com.mars.service.system.ISysOrgaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lixl on 2017/7/16.
 */
@Transactional
@Service
public class SysOrgaServiceImpl extends CommonServiceSupport<SysOrgaEntity> implements ISysOrgaService {
}
