package top.yixun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages="top.yixun.mapper")
@ComponentScan(basePackages={"top.yixun", "org.n3r.idworker"})
public class YixunApplication {

	public static void main(String[] args) {
		SpringApplication.run(YixunApplication.class, args);
	}

}
