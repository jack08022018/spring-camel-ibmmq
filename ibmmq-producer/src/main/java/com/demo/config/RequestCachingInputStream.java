package com.demo.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

public class RequestCachingInputStream extends ServletInputStream {

    private final InputStream sourceStream;

    public RequestCachingInputStream(byte[] source) {
        this.sourceStream = new ByteArrayInputStream(source);
    }

    @Override
    public int read() throws IOException {
        return sourceStream.read();
    }

    @Override
    public boolean isFinished() {
        try {
            return sourceStream.available() == 0;
        } catch (IOException e) {
            return true;
        }
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener listener) {
        throw new UnsupportedOperationException();
    }
}
