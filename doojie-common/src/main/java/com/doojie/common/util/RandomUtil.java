package com.doojie.common.util;

import java.util.Random;

/**
 * 功能概述：随机生成几位不重复的数字
 * <br/>
 * 创建时间：2015年6月8日下午2:50:06
 * <br/>
 * 修改记录：
 * @author lixiaoliang
 */
public class RandomUtil {

	public static String random(int n) {
        if (n < 1 || n > 10) {
            throw new IllegalArgumentException("cannot random " + n + " bit number");
        }
        Random ran = new Random();
        if (n == 1) {
            return String.valueOf(ran.nextInt(10));
        }
        int bitField = 0;
        char[] chs = new char[n];
        for (int i = 0; i < n; i++) {
            while(true) {
                int k = ran.nextInt(10);
                if( (bitField & (1 << k)) == 0) {
                    bitField |= 1 << k;
                    chs[i] = (char)(k + '0');
                    break;
                }
            }
        }
        return new String(chs);
    }
	
	/**
	 * 生成10为验证码
	 * @return
	 */
	public static String randomCheckCode(){
		Random random = new Random();
		// 设置验证码10位随机数(包括数字和字母)
		String rs = "";
		String rn = "";
		String[] english = { "A", "B", "C", "D", "E", "F", "G", "H", "J", "K",
				"L", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
				"Y", "Z" };
		for (int i = 0; i < 10; i++) {
			int temp = random.nextInt(10000);
			if (temp % 2 == 0) {
				rn = english[random.nextInt(24)];
			} else {
				rn = String.valueOf(random.nextInt(10));
			}
			rs += rn;
		}
		return rs;
	}
	
	/**
	 * 取出一个指定长度大小的随机正整数.
	 * 
	 * @param length
	 *            int 设定所取出随机数的长度。length小于11
	 * @return int 返回生成的随机数。
	 */
	public static int buildRandom(int length) {
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < length; i++) {
			num = num * 10;
		}
		return (int) ((random * num));
	}
}
