package com.dev.chatservice.websocket.handlers.subHandlers;

import com.dev.chat.MQObject;

public class HeartBeatHandler extends GeneralHandlerImp {

    /**
     * trigger when client didn't respond for last heart beat
     * retry with limited times
     *
     * @param object object to be committed
     */
    @Override
    public void commit(MQObject object) {

    }

    @Override
    public void handle(MQObject object) {
        super.handle(object);
    }
}
