package com.rd.iot_rdss_gateway.process.processTask.ProcessManager;

import com.rd.iot_rdss_gateway.process.processTask.interfaces.ReceiveInterface;
import com.rd.iot_rdss_gateway.process.processTask.model.MessageModel;
import com.rd.iot_rdss_gateway.process.processTask.process.RdssLocationTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:
 * @Author 老薛
 * @Date 2019/7/3 09:27
 * @Version V1.0
 */
public class MessageManager {
    private final static Logger logger = LoggerFactory.getLogger(MessageManager.class);

    public static ReceiveInterface ResolveAdapater(MessageModel msg) {
        ReceiveInterface process = null;
        switch (msg.getZhenMark()) {
            case 1:
                process = new RdssLocationTask(msg);
                break;
            default:
                logger.error("无法识别帧类型标识: " + msg.getZhenMark() + " 对应的处理方法，不进行处理！");
                break;
        }
        return process;
    }

}
