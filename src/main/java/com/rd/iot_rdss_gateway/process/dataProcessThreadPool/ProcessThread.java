package com.rd.iot_rdss_gateway.process.dataProcessThreadPool;

import com.rd.iot_rdss_gateway.process.processTask.ProcessManager.MessageManager;
import com.rd.iot_rdss_gateway.process.processTask.interfaces.ReceiveInterface;
import com.rd.iot_rdss_gateway.process.processTask.model.MessageModel;

/**
 * @Description:
 * @Author 老薛
 * @Date 2019/7/2 19:10
 * @Version V1.0
 */
public class ProcessThread implements Runnable {

    private MessageModel acceptStr;

    public ProcessThread(MessageModel acceptStr) {
        this.acceptStr = acceptStr;
    }

    public MessageModel getAcceptStr() {
        return acceptStr;
    }

    @Override
    public void run() {
        ReceiveInterface process = MessageManager.ResolveAdapater(acceptStr);
        if (process != null) {
            process.handle();
        }
    }
}
