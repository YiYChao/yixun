package top.yixun;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import top.yixun.netty.WSServer;

/**
 * @Description: netty的启动类
 * @author YiYChao
 * @Date: 2020-1-25 22:36:13
 */
@Component
public class NettyBooter implements ApplicationListener<ContextRefreshedEvent>{

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			try {
				WSServer.getInstance().start();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}

	
}
