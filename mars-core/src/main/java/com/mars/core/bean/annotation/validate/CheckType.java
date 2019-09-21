package com.mars.core.bean.annotation.validate;

import com.mars.core.util.ValidateUtils;
import org.apache.commons.lang.StringUtils;

public enum CheckType {
	/** 默认 不验证类型*/
	DEFAULT {
		@Override
		public boolean check(String content) {
			return true;
		}
	},
	/**邮箱格式验证 */
	Mail {
		@Override
		public boolean check(String content) {
			return StringUtils.isEmpty(content) || ValidateUtils.validateEmail(content);
		}
	},
	/**手机号码格式验证 */
	MOBILE{
		@Override
		public boolean check(String content) {
			return StringUtils.isEmpty(content) || ValidateUtils.validateMobile(content);
		}
	},
	/** IP地址格式验证*/
	IP{
		@Override
		public boolean check(String content) {
			return StringUtils.isEmpty(content) || ValidateUtils.validateIp(content);
		}
	},
	/** URL地址格式验证*/
	URL{
		@Override
		public boolean check(String content) {
			return StringUtils.isEmpty(content) || ValidateUtils.validateUrl(content);
		}
	},
	/** 身份证号码格式验证*/
	ID_CARD{
		@Override
		public boolean check(String content) {
			return StringUtils.isEmpty(content) || ValidateUtils.validateIdCard(content);
		}
	},
	/** 中文格式验证*/
	CHINESE{
		@Override
		public boolean check(String content) {
			return StringUtils.isEmpty(content) || ValidateUtils.validateChinese(content);
		}
	},
	/** 用户名格式验证*/
	USERNAME{
		@Override
		public boolean check(String content) {
			return StringUtils.isEmpty(content) || ValidateUtils.validateUserName(content);
		}
	},
	/** 密码格式验证*/
	PASSWD{
		@Override
		public boolean check(String content) {
			return StringUtils.isEmpty(content) || ValidateUtils.validatePasswd(content);
		}
	},
	/** 验证是否为数字*/
	NUMBER{
		@Override
		public boolean check(String content) {
			return StringUtils.isEmpty(content) || ValidateUtils.validateNumber(content);
		}
	},
	/** 验证是否为整数*/
	INTEGER{
		@Override
		public boolean check(String content) {
			return StringUtils.isEmpty(content) || ValidateUtils.validateInteger(content);
		}
	};

	public abstract boolean check(String content);
	
}
