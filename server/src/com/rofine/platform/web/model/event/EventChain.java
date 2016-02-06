package com.rofine.platform.web.model.event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangdg on 2016/1/22.
 */
public class EventChain {

    private EventSource eventSource;

    private String eventName;

    private List<EventTarget> targets = new ArrayList<EventTarget>();

    public EventSource getEventSource() {
        return eventSource;
    }

    public void setEventSource(EventSource eventSource) {
        this.eventSource = eventSource;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public List<EventTarget> getTargets() {
        return targets;
    }

    public void setTargets(List<EventTarget> targets) {
        this.targets = targets;
    }

    public void addTarget(EventTarget target){
        this.targets.add(target);
    }
}
