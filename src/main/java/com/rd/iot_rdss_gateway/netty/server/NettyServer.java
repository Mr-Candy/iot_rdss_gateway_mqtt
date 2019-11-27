package com.rd.iot_rdss_gateway.netty.server;

import com.rd.iot_rdss_gateway.config.NettyConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.rxtx.RxtxChannel;
import io.netty.channel.rxtx.RxtxDeviceAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author 老薛
 * @Date 2019/6/21 15:01
 * @Version V1.0
 */
//@Component
public class NettyServer {
    private Logger log = LoggerFactory.getLogger(NettyServer.class);
    private final OioEventLoopGroup group = new OioEventLoopGroup();
    private RxtxChannel channel;

    @Resource
    private NettyConfig nettyConfig;
    /**
     * 启动服务
     */
    public ChannelFuture run() {
        String serialport = nettyConfig.getSerialPort();
        int baudRate = nettyConfig.getBaudRate();
        ChannelFuture future = null;
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(RxtxChannel.class)
                    .handler(new ServerChannelInitializer());
            future = b.connect(new RxtxDeviceAddress(serialport)).sync();
            channel = (RxtxChannel) future.channel();
            channel.config().setBaudrate(baudRate);
            log.info("Netty server listening start..."+ serialport + "  " + baudRate);
        } catch (Exception e) {
            log.error("Netty start error:", e);
        } finally {
            if (future != null && future.isSuccess()) {
                log.info("Netty server listening ready for connections...");
            } else {
                log.error("Netty server start up Error!");
            }
        }

        return future;
    }

    public void destroy() {
        log.info("Shutdown Netty Server...");
        if (channel != null) {
            channel.close();
        }
        group.shutdownGracefully();
        log.info("Shutdown Netty Server Success!");
    }
}
