package com.rd.iot_rdss_gateway.netty.server;

import com.rd.iot_rdss_gateway.netty.Process.AsciiDecoder;
import com.rd.iot_rdss_gateway.netty.Process.AsciiEncoder;
import com.rd.iot_rdss_gateway.netty.Process.ServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.rxtx.RxtxChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;

/**
 * @Description:
 * @Author 老薛
 * @Date 2019/6/21 15:33
 * @Version V1.0
 */
public class ServerChannelInitializer extends ChannelInitializer<RxtxChannel> {
    @Override
    protected void initChannel(RxtxChannel rxtxChannel) {
        //消息分隔
        /*ByteBuf delimiter = Unpooled.copiedBuffer(new byte[] { 0x2A});
        ByteBuf delimiter2 = Unpooled.copiedBuffer( "\r\n".getBytes());
        rxtxChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,delimiter2));*/
        rxtxChannel.pipeline().addLast(new LineBasedFrameDecoder(2048));
        rxtxChannel.pipeline().addLast(new StringDecoder());
        rxtxChannel.pipeline().addLast("decoder", new AsciiDecoder(CharsetUtil.US_ASCII));
        rxtxChannel.pipeline().addLast("encoder", new AsciiEncoder(CharsetUtil.US_ASCII));
        rxtxChannel.pipeline().addLast("handler", new ServerHandler());
    }
}
