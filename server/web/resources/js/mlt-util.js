/**
 * Created by Administrator on 2016/1/11.
 */
/**
 * Created by Administrator on 2016/1/11.
 */
function showErrorMsg(msg) {
    // create the notification
    var notification = new NotificationFx({
        message : '<p>' + msg +'</p>',
        layout : 'growl',
        effect : 'jelly',
        type : 'notice'
    });
    notification.show();
}

function showMsg(msg) {
    // create the notification
    var notification = new NotificationFx({
        message : '<p>' + msg +'</p>',
        layout : 'growl',
        effect : 'jelly',
        type : 'notice'
    });
    notification.show();
}

function showDate(group, input){
    var curr = new Date().getFullYear();
    var opt = {

    }
    opt.date = {preset : 'date', dateFormat : 'yy-mm-dd', dateOrder : 'yyyymmdd'};
    opt.datetime = { preset : 'datetime', stepMinute: 5  };
    opt.time = {preset : 'time'};
    opt.tree_list = {preset : 'list', labels: ['Region', 'Country', 'City']};
    opt.image_text = {preset : 'list', labels: ['Cars']};
    opt.select = {preset : 'select'};
    <!--Script-->

    group.show();
    input.scroller('destroy').scroller($.extend(opt['date'], { theme: 'wp light', mode: 'scroller', display: 'modal', lang: 'zh' }));
}