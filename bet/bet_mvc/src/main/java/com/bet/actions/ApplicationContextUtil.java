package com.bet.actions;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 重新装载applicationContext.xml并实例化上下文bean, 如果有些线程配置类也是在这个配置文件中，那么会造成做相同工作的的线程会被启两次。一次是web容器初始化时启动，另一次是上述代码显示的实例化了一次。这在业务上是要避免的。
 * ================================================================================================= ======================================================================
 * 解决方法就是通过保存ApplicationContext的静态变量
 * 
 * @author libit
 */
@Component("applicationContextUtil")
public class ApplicationContextUtil implements ApplicationContextAware
{
	private static ApplicationContext context;// 声明一个静态变量保存

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
		context = applicationContext;
	}

	public static ApplicationContext getContext()
	{
		return context;
	}
}
