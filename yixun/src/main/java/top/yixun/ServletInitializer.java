package top.yixun;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
/**
 * @Description: 使用外置tomcat启动application
 * @author: YiYChao
 * @date: 2020年2月1日 下午7:01:11
 */
public class ServletInitializer extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(YixunApplication.class);
	}
	
}
