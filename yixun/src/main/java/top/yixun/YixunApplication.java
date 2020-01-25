package top.yixun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@MapperScan(basePackages="top.yixun.mapper")
@ComponentScan(basePackages={"top.yixun"})
public class YixunApplication {

	public static void main(String[] args) {
		SpringApplication.run(YixunApplication.class, args);
		System.err.println("Hello Boot");
	}

}
