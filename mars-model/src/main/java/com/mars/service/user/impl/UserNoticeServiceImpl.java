package com.mars.service.user.impl;

import com.mars.core.service.CommonServiceSupport;
import com.mars.model.user.UserNoticeEntity;
import com.mars.service.user.IUserNoticeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lixl on 2018/7/2 0002.
 */
@Service
@Transactional
public class UserNoticeServiceImpl extends CommonServiceSupport<UserNoticeEntity> implements IUserNoticeService {
}
