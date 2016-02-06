package com.rofine.platform.web.model.event;

import com.rofine.platform.web.model.object.ObjectModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wangdg on 2016/1/22.
 */
public class EventModel {

    private List<EventChain> chains = new ArrayList<EventChain>();

    private ObjectModel objectModel;

    public void addEventChain(String sourceId, String eventName, Map<String, String> targetInfos){
        EventChain chain = new EventChain();

        EventSource source = new EventSource();
        source.setId(sourceId);
        chain.setEventSource(source);

        chain.setEventName(eventName);

        EventTarget eventTarget;
        for(String targetId : targetInfos.keySet()) {
            eventTarget = new EventTarget();
            eventTarget.setId(targetId);
            eventTarget.setFunctionName(targetInfos.get(targetId));

            chain.addTarget(eventTarget);
        }
        this.chains.add(chain);
    }

    public List<EventChain> getChains() {
        return chains;
    }

    public void setChains(List<EventChain> chains) {
        this.chains = chains;
    }

    public ObjectModel getObjectModel() {
        return objectModel;
    }

    public void setObjectModel(ObjectModel objectModel) {
        this.objectModel = objectModel;
    }
}
