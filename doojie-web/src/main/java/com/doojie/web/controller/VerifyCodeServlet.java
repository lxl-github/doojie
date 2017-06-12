package com.doojie.web.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 实现验证码Servlet类
 * 
 * @author lxx
 * @creatdate 2010-3-8
 * @modifiedDate
 * @modifier
 * @content 实现评论或登录验证码效果(含随机数字和字母)
 */
@SuppressWarnings("serial")
public class VerifyCodeServlet extends HttpServlet {

	private int width = 90;// 验证码宽度

	private int height = 28; // 验证码高度

	private int codeCount = 4; // 验证码个数

	@SuppressWarnings("unused")
	private int x = 0;

	private int fontHeight; // 字体高度

	@SuppressWarnings("unused")
	private int codeY;

	/**
	 * 验证码Servlet初始化方法 added by neng.zhu ,g.drawString(rn, 13*i+1,
	 * 16)方法在第一次调用的时候特别的慢
	 * 
	 * @throws ServletException
	 */
	public void init() throws ServletException {

		// 高度
		String imgWidth = this.getInitParameter("imgWidth");
		// 宽度
		String imgHeight = this.getInitParameter("imgHeight");
		// 个数
		String codeCount = this.getInitParameter("codeCount");

		try {
			if (imgWidth != null && imgWidth.length() != 0) {
				width = Integer.parseInt(imgWidth);
			}

			if (imgHeight != null && imgHeight.length() != 0) {
				height = Integer.parseInt(imgHeight);
			}

			if (codeCount != null && codeCount.length() != 0) {
				this.codeCount = Integer.parseInt(codeCount);
			}
		} catch (NumberFormatException e) {
			width = 90;
			height = 28;
			this.codeCount = 4;
		}

		x = width / (this.codeCount + 1);
		fontHeight = height - 2;
		codeY = height - 4;

		// 以下代码和doPost中的画图片部分相同，解决第一次特别慢的问题
		// 定义图像对象
		BufferedImage buffImg = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffImg.createGraphics();

		// 随机数生成器类
		Random random = new Random();
		// 将图像绘制成白色
		g.setColor(getcolor(200, 250));
		g.fillRect(0, 0, width, height);

		// 创建字体，字体大小应该根据图片高度来定。
		Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);
		// 设置字体
		g.setFont(font);

		// 画边框
		g.setColor(getcolor(160, 200));
		g.drawRect(0, 0, width - 1, height - 1);

		// 随机数的干扰线 160条
		g.setColor(getcolor(160, 200));
		for (int i = 0; i < 160; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int x1 = random.nextInt(10);
			int y1 = random.nextInt(10);
			g.drawLine(x, y, x + x1, y + y1);
		}

		// 设置验证码4位随机数(包括数字和字母)
		
		String rn = "";
		String[] english = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
				"k", "m", "n", "p", "q", "r", "s", "t", "u", "v", "w", "x",
				"y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "J", "K",
				"L", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
				"Y", "Z" };
		for (int i = 0; i < 4; i++) {

			int temp = random.nextInt(10000);
			if (temp % 2 == 0) {
				rn = english[random.nextInt(48)];
			} else {
				rn = String.valueOf(random.nextInt(10));
			}
			g.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(rn, 13 * i + 1, 16);
		}

		g.dispose();

	}

	// char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
	// 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
	// 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	/**
	 * 验证码Servlet构造方法
	 */
	public VerifyCodeServlet() {
		super();
	}

	/**
	 * 验证码Servlet销毁方法
	 */
	public void destroy() {
		super.destroy();
	}

	/**
	 * 获取颜色判断方法
	 */
	public Color getcolor(int x, int y) {
		Random random = new Random();
		if (x > 255) {
			x = 255;
		}
		if (y > 255) {
			x = 255;
		}
		// 获取红色的取值范围 最少也是X的取值 最大不能超过Y
		int red = x + random.nextInt(y - x);
		int green = x + random.nextInt(y - x);
		int blue = x + random.nextInt(y - x);
		// 创建颜色对象返回
		return new Color(red, green, blue);
	}

	/**
	 * doGet()方法
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);

	}

	/**
	 * doPost()方法
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 定义图像对象
		BufferedImage buffImg = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffImg.createGraphics();

		// 随机数生成器类
		Random random = new Random();
		// 将图像绘制成白色
		g.setColor(getcolor(200, 250));
		g.fillRect(0, 0, width, height);

		// 创建字体，字体大小应该根据图片高度来定。
		Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);
		// 设置字体
		g.setFont(font);

		// 画边框
		g.setColor(getcolor(160, 200));
		g.drawRect(0, 0, width - 1, height - 1);

		// 随机数的干扰线 160条
		g.setColor(getcolor(160, 200));
		for (int i = 0; i < 160; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int x1 = random.nextInt(10);
			int y1 = random.nextInt(10);
			g.drawLine(x, y, x + x1, y + y1);
		}

		// // 保存随即生成的验证码
		// StringBuffer randomCode = new StringBuffer();
		// int red = 0;
		// int green = 0;
		// int blue = 0;
		//
		// // 随机生成codeCount字数的验证码
		// for (int i = 0; i < codeCount; i++) {
		//
		// // 得到随机产生的验证码数字
		// String strRand = String.valueOf(codeSequence[random.nextInt(36)]);
		// // 产生随机颜色
		// red = random.nextInt(255);
		// green = random.nextInt(255);
		// blue = random.nextInt(255);
		//
		// // 用随机产生的颜色将验证码绘制到图像中
		// g.setColor(new Color(red, green, blue));
		// g.drawString(strRand, (i + 1) * x, codeY);
		//
		// // 将产生的四个随机数组合在一起.
		// randomCode.append(strRand);
		//
		// }

		// 设置验证码4位随机数(包括数字和字母)
		String rs = "";
		String rn = "";
		String[] english = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
				"k", "m", "n", "p", "q", "r", "s", "t", "u", "v", "w", "x",
				"y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "J", "K",
				"L", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
				"Y", "Z" };
		for (int i = 0; i < 4; i++) {

			int temp = random.nextInt(10000);
			if (temp % 2 == 0) {
				rn = english[random.nextInt(48)];
			} else {
				rn = String.valueOf(random.nextInt(10));
			}
			rs += rn;
			g.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(rn, 13 * i + 1, 16);
		}

		g.dispose();

		// 将验证码存入session以用来验证
		//SessionUtil.setAttribute("validateCode", rs);
		request.getSession().setAttribute("validateCode", rs);

		// 禁止图像缓存
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-cControl", "no-cache");
		response.setDateHeader("Expires", 0);

		response.setContentType("image/jpeg");
		// 输出图像
		ServletOutputStream sos = response.getOutputStream();
		ImageIO.write(buffImg, "jpeg", sos);
		sos.close();
	}

}
