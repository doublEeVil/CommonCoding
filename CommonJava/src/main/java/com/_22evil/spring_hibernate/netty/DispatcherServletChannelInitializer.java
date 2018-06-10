package com._22evil.spring_hibernate.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.jws.WebService;
import javax.net.ssl.SSLEngine;
import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

public class DispatcherServletChannelInitializer extends ChannelInitializer<SocketChannel> {


	private final DispatcherServlet dispatcherServlet;
	private static DispatcherServletChannelInitializer dis = new DispatcherServletChannelInitializer();

	public static DispatcherServletChannelInitializer getInstance(){
		return dis;
	}
	public DispatcherServletChannelInitializer() {

		MockServletContext servletContext = new MockServletContext();
		MockServletConfig servletConfig = new MockServletConfig(servletContext);
		AnnotationConfigWebApplicationContext wac = new AnnotationConfigWebApplicationContext();
		//servletContext.createFilter()
		//servletContext.a
		servletContext.addInitParameter("async-supported","true");
		wac.setServletContext(servletContext);
		wac.setServletConfig(servletConfig);
		wac.register(AppConfig.class);
		wac.refresh();

		this.dispatcherServlet = new DispatcherServlet(wac);
		try {
			this.dispatcherServlet.init(servletConfig);
		}catch (ServletException e){

		}

		//set spring config in xml
		//this.dispatcherServlet = new DispatcherServlet();
		//this.dispatcherServlet.setContextConfigLocation("classpath*:/applicationContext.xml");
		//this.dispatcherServlet.init(servletConfig);
	}

	@Override
	public void initChannel(SocketChannel channel) throws Exception {
		ChannelPipeline pipeline = channel.pipeline();
		// Uncomment the following line if you want HTTPS
//		SSLEngine engine = SecureChatSslContextFactory.getServerContext().createSSLEngine();
//		engine.setUseClientMode(false);
//		pipeline.addLast("ssl", new SslHandler(engine));
		pipeline.addLast("decoder", new HttpRequestDecoder());
		pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
		pipeline.addLast("encoder", new HttpResponseEncoder());
		pipeline.addLast("chunkedWriter", new ChunkedWriteHandler());
		pipeline.addLast("handler", new ServletNettyHandler(this.dispatcherServlet));
	}
	public void destroySpring(){
		this.dispatcherServlet.destroy();
	}

}
