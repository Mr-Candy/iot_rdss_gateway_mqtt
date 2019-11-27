package com.rd.iot_rdss_gateway.netty.Process;

import com.rd.iot_rdss_gateway.process.processTask.model.MessageModel;
import com.rd.iot_rdss_gateway.util.ASCIIUtil;
import com.rd.iot_rdss_gateway.util.StringByteConvertor;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @Description:
 * @Author 老薛
 * @Date 2019/6/24 13:31
 * @Version V1.0
 */
@ChannelHandler.Sharable
public class AsciiDecoder extends MessageToMessageDecoder<String> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Charset charset;

    public AsciiDecoder(Charset charset) {
        if (charset == null) {
            throw new NullPointerException("charset");
        } else {
            this.charset = charset;
        }
    }

    protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) {
        /*byte[] bs = new byte[msg.readableBytes()];
        msg.readBytes(bs);
        String text = StringByteConvertor.bytesToHexString(bs);
        logger.info("接收数据：" + text);*/
        logger.info("接收数据：" + msg);
        if(msg.startsWith("$BDTXR")/*||msg.startsWith("$BDWAA")*/){
            String[] receives = msg.split(",");
            String type = receives[1];  //信息类别
            String address = receives[2];//发信方地址
            String mode = receives[3];//电文形式 0-汉字；1-代码；2-混合传输
            String content = receives[receives.length-1].split("\\*")[0];//电文内容
       //     logger.info("接收电文内容：" + content);

            try {
                DataInputStream data = new DataInputStream(new ByteArrayInputStream(StringByteConvertor.hexStringToBytes(content)));
                if (data.available() <= 4) {
                    logger.error("数据内容不全，丢弃该帧数据：" + msg);
                    return;
                }
                byte qiShi = data.readByte();//自协议起始标识
                if (qiShi != 35) {
                    logger.error("起始标识不对，丢弃该帧");
                    return;
                }
                byte shiBieMark = data.readByte();//子协议识别标识
                byte zhenMark = data.readByte();//帧类型标识符
                byte zhenDataMark = data.readByte();//帧数据标识符
                byte zhenNum = (byte) ((zhenDataMark & 0xe0) >> 5);
                boolean buChuanMark = false;
                if ((zhenDataMark & 0x10) >> 4 == 1) {
                    buChuanMark = true;
                }
                byte[] body = new byte[data.available()];
                data.read(body);
                MessageModel message = new MessageModel(shiBieMark, zhenMark, zhenNum, buChuanMark, body);
                out.add(message);
            }catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }else{
            logger.error("协议类型不支持，丢弃该帧");
            return;
        }
    }

    public static void main(String[] args) {
        String s = ASCIIUtil.hexStr2BinStr(Integer.toHexString(32));
        System.out.println(s);
    }
}
