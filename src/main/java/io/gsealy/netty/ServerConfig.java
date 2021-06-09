package io.gsealy.netty;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {

  @Bean
  public ReactiveWebServerFactory reactiveWebServerFactory(
      @Value("${server.netty.idle-timeout}") Duration idleTimeout) {

    final NettyReactiveWebServerFactory factory = new NettyReactiveWebServerFactory();
    factory.addServerCustomizers(server ->
        server.tcpConfiguration(tcp ->
            tcp.bootstrap(bootstrap -> bootstrap.childHandler(new ChannelInitializer<>() {
              @Override
              protected void initChannel(Channel channel) {
                channel.pipeline().addLast(
                    new IdleStateHandler(0, 0, idleTimeout.toNanos(), NANOSECONDS),
                    new ChannelDuplexHandler() {
                      @Override
                      public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
                        if (evt instanceof IdleStateEvent) {
                          ctx.close();
                        }
                      }
                    }
                );
              }
            }))
        ));
    return factory;
  }

}
