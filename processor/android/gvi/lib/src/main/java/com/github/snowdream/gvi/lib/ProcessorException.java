package com.github.snowdream.gvi.lib;

/**
 * ProcessorException
 *
 * Created by yanghui.yangh on 2016/4/20.
 */
public class ProcessorException extends Exception {
    public ProcessorException() {
    }

    public ProcessorException(String detailMessage) {
        super(detailMessage);
    }

    public ProcessorException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ProcessorException(Throwable throwable) {
        super(throwable);
    }
}
