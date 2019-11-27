package com.rd.iot_rdss_gateway.netty.Process;

import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @Description:
 * @Author 老薛
 * @Date 2019/6/24 14:10
 * @Version V1.0
 */
@ChannelHandler.Sharable
public class AsciiEncoder extends MessageToMessageEncoder<CharSequence> {
    private final Charset charset;

    public AsciiEncoder() {
        this(Charset.defaultCharset());
    }

    public AsciiEncoder(Charset charset) {
        if (charset == null) {
            throw new NullPointerException("charset");
        } else {
            this.charset = charset;
        }
    }

    protected void encode(ChannelHandlerContext ctx, CharSequence msg, List<Object> out) {
        if (msg.length() != 0) {
            out.add(ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap(msg), this.charset));
        }
    }
}
