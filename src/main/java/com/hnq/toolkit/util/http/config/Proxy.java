package com.hnq.toolkit.util.http.config;

import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * @author henengqiang
 * @date 2019/07/01
 */
public class Proxy implements Serializable {

    private final String host;

    private final int port;

    public Proxy(String host, int port) {
        if (StringUtils.isEmpty(host)) {
            throw new IllegalArgumentException("host cannot be empty!");
        }
        this.host = host;
        this.port = port < 0 ? 80 : port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

}
