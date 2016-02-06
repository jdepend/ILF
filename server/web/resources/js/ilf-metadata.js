/**
 * Created by Administrator on 2016/2/4.
 */

var lt_MetaData;

function processMetaData(data) {
    lt_MetaData = data.objectModel;
    $(window).trigger(LoadMetaDataEvent, data.objectModel);
}