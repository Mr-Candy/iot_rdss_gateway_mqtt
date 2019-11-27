package com.rd.iot_rdss_gateway.netty.Process;

import com.rd.iot_rdss_gateway.process.dataProcessThreadPool.ThreadPoolManager;
import com.rd.iot_rdss_gateway.process.processTask.model.MessageModel;
import com.rd.iot_rdss_gateway.util.SpringUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:
 * @Author 老薛
 * @Date 2019/6/21 16:18
 * @Version V1.0
 */
public class ServerHandler extends SimpleChannelInboundHandler<MessageModel> {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageModel msg) {
        SpringUtil.getBean(ThreadPoolManager.class).addOrders(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("channel关闭，原因" + cause.getMessage());
        ctx.close();
    }
}
