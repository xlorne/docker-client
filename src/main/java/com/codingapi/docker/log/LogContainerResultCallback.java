package com.codingapi.docker.log;

import com.github.dockerjava.api.async.ResultCallbackTemplate;
import com.github.dockerjava.api.model.Frame;

public class LogContainerResultCallback extends ResultCallbackTemplate<LogContainerResultCallback, Frame> {

    private final StringBuilder logs = new StringBuilder();

    @Override
    public void onNext(Frame frame) {
        String streamType = frame.getStreamType().toString()+":";
        logs.append(frame.toString().replace(streamType, "")).append("\n");
    }

    public String getLogs() {
        return logs.toString();
    }
}
