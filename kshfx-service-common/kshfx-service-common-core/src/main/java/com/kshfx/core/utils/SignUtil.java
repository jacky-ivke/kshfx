package com.kshfx.core.utils;

import org.springframework.util.StringUtils;

public class SignUtil {

	public static boolean checkTimestamp(Long time, Integer condition) {
		Long currentTimeMillis = System.currentTimeMillis();
		Long addTime = currentTimeMillis + condition;
		Long subTime = currentTimeMillis - condition;
        return addTime > time && time > subTime;
    }

	public static boolean verifySign(String params, String originalSign) {
		boolean success = false;
		if (StringUtils.isEmpty(params) || StringUtils.isEmpty(originalSign)) {
			return success;
		}
		String targetSign = createSign(params);
		if (!StringUtils.isEmpty(originalSign) && originalSign.equalsIgnoreCase(targetSign)) {
			success = true;
		}
		System.out.println("【请求签名参数:" + params + "】" + "【请求签名串:" + originalSign + "】" + "【生成签名串:" + targetSign + "】");
		return success;
	}

	protected static String createSign(String params) {
		String sign = "";
		if (StringUtils.isEmpty(params)) {
			return sign;
		}
		sign = Md5Utils.getMD5(params);
		return sign;
	}
}
