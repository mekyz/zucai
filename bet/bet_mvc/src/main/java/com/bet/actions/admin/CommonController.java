package com.bet.actions.admin;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bet.orms.AdminInfo;
import com.lrcall.common.utils.StringTools;

@Controller("adminCommonController")
@RequestMapping("/admin")
public class CommonController extends BaseAdminController
{
	// 用户任意页面
	@RequestMapping(value = "/*")
	public ModelAndView userPage(HttpServletRequest request)
	{
		String url = request.getRequestURI();
		if (!StringTools.isNull(url) && url.endsWith("/"))
		{
			ModelAndView mv = new ModelAndView(getRedirectViewName("index"));
			return mv;
		}
		ModelAndView mv = new ModelAndView(getRedirectViewName("login"));
		mv.addObject("adminInfo", new AdminInfo());
		return mv;
	}

	// 首页页面
	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("index"));
		AdminInfo adminInfo = getAdminSession(request);
		mv.addObject("adminInfo", adminInfo);
		return mv;
	}

	// 关于页面
	@RequestMapping(value = "/about")
	public ModelAndView about(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView(getViewName("about"));
		return mv;
	}

	/**
	 * 获取登录验证码
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 */
	@RequestMapping(value = "getAuthCode")
	public void getAuthCode(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException
	{
		int width = 63;
		int height = 37;
		Random random = new Random();
		// 设置response头信息
		// 禁止缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		// 生成缓冲区image类
		BufferedImage image = new BufferedImage(width, height, 1);
		// 产生image类的Graphics用于绘制操作
		Graphics g = image.getGraphics();
		// Graphics类的样式
		g.setColor(StringTools.getRandColor(200, 250));
		g.setFont(new Font("Times New Roman", 0, 28));
		g.fillRect(0, 0, width, height);
		// 绘制干扰线
		for (int i = 0; i < 40; i++)
		{
			g.setColor(StringTools.getRandColor(130, 200));
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int x1 = random.nextInt(12);
			int y1 = random.nextInt(12);
			g.drawLine(x, y, x + x1, y + y1);
		}
		// 绘制字符
		String strCode = "";
		for (int i = 0; i < 4; i++)
		{
			String rand = String.valueOf(random.nextInt(10));
			strCode = strCode + rand;
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(rand, 13 * i + 6, 28);
		}
		// 将字符保存到session中用于前端的验证
		setCodeSession(request, strCode);
		g.dispose();
		ImageIO.write(image, "JPEG", response.getOutputStream());
		response.getOutputStream().flush();
	}
}
