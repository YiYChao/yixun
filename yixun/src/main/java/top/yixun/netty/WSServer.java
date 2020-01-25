package top.yixun.netty;

import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Description: Netty服务器
 * @author: YiYChao
 * @data:2020-1-25 22:21:23
 */
@Component
public class WSServer {

	// 创建单例对象
	private static class SingletionWSServer {
		static final WSServer instance = new WSServer();
	}
	// 返回获取服务器对象
	public static WSServer getInstance(){
		return SingletionWSServer.instance;
	}
	
	private EventLoopGroup parentGroup;
	private EventLoopGroup childGroup;
	private ServerBootstrap server;
	private ChannelFuture future;
	
	public WSServer(){
		parentGroup = new NioEventLoopGroup();
		childGroup = new NioEventLoopGroup();
		server = new ServerBootstrap();
		server.group(parentGroup, childGroup)
			.channel(NioServerSocketChannel.class)
			.childHandler(new WSServerInitialzer());
	}
	
	public void start(){
		this.future = server.bind(8086);
		System.err.println("Netty websocket server服务器启动成功。。。");
	}
}
