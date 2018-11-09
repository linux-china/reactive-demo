package org.mvnsearch.spring.reactor;

import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * upstream event
 *
 * @author linux_china
 */
public class UpstreamEvent extends ApplicationEvent {
    private List<String> ipList;

    public UpstreamEvent(List<String> ipList) {
        super(ipList);
        this.ipList = ipList;
    }

    public List<String> getIpList() {
        return ipList;
    }

    public void setIpList(List<String> ipList) {
        this.ipList = ipList;
    }
}
