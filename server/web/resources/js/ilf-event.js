/**
 * Created by Administrator on 2016/1/25.
 */
var LoadDataEvent = "LoadDataEvent";//装载业务数据后发出的事件
var LoadGroupDataEvent = "LoadGroupDataEvent";//装载组数据后发出的事件
var LoadListElementEvent = "LoadListElementEvent";//装载列表数据后发出的事件

var LoadMetaDataEvent = "LoadMetaDataEvent";//装载页面静态数据后发出的事件

var LoadEventDataEvent = "LoadEventDataEvent";//装载事件数据后触发的事件

var QueryTitleFormOpenEvent = "QueryTitleFormOpenEvent";//查询窗口打开事件
var QueryTitleFormCloseEvent = "QueryTitleFormCloseEvent";//查询窗口关闭事件
var QueryTitleFormSubmitEvent = "QueryTitleFormSubmitEvent";//查询窗口提交事件
var QueryTitleFormNeedCloseEvent = "QueryTitleFormNeedCloseEvent";//查询窗口需要关闭事件

var TitleHiddenEvent = "TitleHiddenEvent";//标题隐藏事件
var TitleShowEvent = "TitleShowEvent";//标题显示事件
var TitleHiddenedEvent = "TitleHiddenedEvent";//标题隐藏后发出事件
var TitleShowedEvent = "TitleShowedEvent";//标题显示后发出事件

var PageListTopEvent = "PageListTopEvent"; //改变列表的Top事件
var PageListUpEvent = "PageListUpEvent";//向下滑动
var PageListDownEvent = "PageListDownEvent"; //向上滑动

$(window).bind(LoadEventDataEvent, function(event, param){
    processEvent(param);
});

function processEvent(data){
    for(var chainIndex in data.chains){
        var eventChain = data.chains[chainIndex];
        $("#" + eventChain.eventSource.id).bind(eventChain.eventName, function(event, param){
            //得到event对应的chain
            var theEventChain = getTheEventChain(data, event);
            //触发target事件
            if (theEventChain != null) {
                //根据对象模型获取目标对象参数
                var targetParam = getTargetParam(data, event);
                //触发事件
                for (var targetIndex in theEventChain.targets) {
                    var targetObj = theEventChain.targets[targetIndex];
                    $("#" + targetObj.id).trigger(targetObj.functionName, [param, targetParam]);
                }
            }
        });
    }
}


function getTheEventChain(data, event){
    var theEventChain = null;
    for(theChainIndex in data.chains){
        var eventChain1 = data.chains[theChainIndex];
        if(eventChain1.eventSource.id === event.currentTarget.id && eventChain1.eventName === event.type){
            theEventChain = data.chains[theChainIndex];
            break;
        }
    }
    return theEventChain;
}

function getTargetParam(data, event){

    var targetParam = null;
    if(data.objectModel != null && data.objectModel.relations != null && data.objectModel.relations.length > 0) {
        var sourceValue = $("#" + event.target.id).val();
        for (var relationIndex in data.objectModel.relations) {
            var relation = data.objectModel.relations[relationIndex];
            if (relation.source == event.currentTarget.id) {
                if (relation.valueRanges != null) {
                    var range = relation.valueRanges[sourceValue];
                    if(range != null && range.length > 0){
                        targetParam = getRangeValues(data, relation.target, range);
                    }
                    break;
                }else if(relation.valueStates != null){
                    targetParam = relation.valueStates[sourceValue];
                    break;
                }
            }
        }
    }
    return targetParam;
}

function getRangeValues(data, elementId, range){
    for(var elementIndex in data.objectModel.elements){
        var element = data.objectModel.elements[elementIndex];
        if(element.type == elementId){
            return element.rangeValues[range];
        }
    }
    return null;
}
