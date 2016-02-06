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