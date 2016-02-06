/**
 * Created by Administrator on 2016/1/7.
 */

var lineStatus = true;

function isOnline() {
    $.ajax({
        type: "GET",
        url: basePath + "/online",
        dataType: "json",
        timeout: 1000,
        success: function (data) {
            if (!lineStatus) {
                lineTip('你恢复了与服务器的连接');
            }
            lineStatus = true;
        },
        error: function (data) {
            if (lineStatus) {
                lineTip('你失去了与服务器的连接');
            }
            lineStatus = false;
        }
    });
};
window.onload = function () {
    setInterval(isOnline, 5000);
};

function lineTip(msg) {
    // create the notification
    var notification = new NotificationFx({
        message: '<span class="glyphicon glyphicon-bullhorn"></span><p>' + msg + '</p>',
        layout: 'bar',
        effect: 'slidetop',
        type: 'notice'// notice, warning or error
    });
    // show the notification
    notification.show();
}