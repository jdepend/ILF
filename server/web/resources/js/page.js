/**
 * Created by Administrator on 2015/12/23.
 */
$(function () {
    $("#lt_morePageData").click(
        function () {
            loadPageData();
        }
    );
    setTimeout(resetTop, 100);

    document.addEventListener('touchmove', function (e) {
        myScroll.refresh();
        e.preventDefault();
    }, false);

    setTimeout(pageLoaded, 200);

    $("#lt_list-wrapper").bind(PageListTopEvent, function(event, top){
        listTop(top);
    });
});

function resetTop(){
    $("#lt_list-wrapper").css("top", $("#lt_list-wrapper").parent().offset().top);
}

function listTop(top){
    $("#lt_list-wrapper").animate({top:top}, "slow");
}

var myScroll, pullUpEl, pullUpOffset;
var lt_listPageState = "downstate";
var lt_scrollStartY;

function pullUpAction() {
    setTimeout(function () {
        loadPageData();
        myScroll.refresh();
    }, 500);
}


function pageLoaded() {

    pullUpEl = document.getElementById('lt_pullUp');
    pullUpOffset = pullUpEl.offsetHeight;

    myScroll = new iScroll('lt_list-wrapper', {
        useTransition: true,
        onRefresh: function () {
            if (lt_morePageData) {
                if (pullUpEl.className.match('loading')) {
                    pullUpEl.className = '';
                    $('.pullUpLabel').html('向上获得更多。。。');
                }
            }
        },
        onScrollStart: function(){
            lt_scrollStartY = this.y;
        },
        onScrollMove: function () {

            if(lt_listPageState === "downstate" && ((this.y - lt_scrollStartY) < -50)){
                listTop("50px");
                $("#lt_list-wrapper").trigger(PageListUpEvent);
                lt_listPageState = "upstate";
            }

            if (lt_morePageData) {
                if (this.y < (this.maxScrollY - 5) && !pullUpEl.className.match('flip')) {
                    pullUpEl.className = 'flip';
                    pullUpEl.querySelector('.pullUpLabel').innerHTML = '放开获得数据。。。';
                    this.maxScrollY = this.maxScrollY;
                } else if (this.y > (this.maxScrollY + 5) && pullUpEl.className.match('flip')) {
                    pullUpEl.className = '';
                    pullUpEl.querySelector('.pullUpLabel').innerHTML = '向上获得更多。。。';
                    this.maxScrollY = pullUpOffset;
                }
            }
        },
        onScrollEnd: function () {
            if (lt_listPageState === "upstate" && ((this.y - lt_scrollStartY) > 50)) {
                listTop("80px");
                $("#lt_list-wrapper").trigger(PageListDownEvent);
                lt_listPageState = "downstate";
            }

            if (lt_morePageData) {
                if (pullUpEl.className.match('flip')) {
                    pullUpEl.className = 'loading';
                    pullUpEl.querySelector('.pullUpLabel').innerHTML = '获取。。。';
                    pullUpAction();
                }
            }else{
                pullUpEl.className = '';
                $('#lt_pullUp').remove();
            }
        },
        checkDOMChanges: true,
        vScrollbar: false
    });
}

