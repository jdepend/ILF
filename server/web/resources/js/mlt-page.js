/**
 * Created by Administrator on 2015/12/18.
 */
var pageScreen = 1;

function requestPage(url) {

    pageScreen = pageScreen + 1;
    $("#pages").trigger('enterBefore', pageScreen);
    createPage();
    $('#iframe' + pageScreen).attr("src", url);

    setTimeout(pagePageEnter, 500);
}

function pagePageEnter() {

    var x = -100 * (pageScreen - 1);
    $('#pages').css({'-webkit-transform': 'translate3d(' + x + '%, 0px, 0px)', '-webkit-transition': '300ms'});

    $('#page' + (pageScreen -1)).css({'display': 'none'});
    $('#page' + pageScreen).css({'display': 'block'});

    $("#pages").trigger('enterAfter', pageScreen);
}

function pagePageBack() {
    var x = -100 * (pageScreen - 2);

    $('#pages').css({'-webkit-transform': 'translate3d(' + x + '%, 0px, 0px)', '-webkit-transition': '300ms'});

    $('#page' + (pageScreen -1)).css({'display': 'block'});
    $('#page' + pageScreen).css({'display': 'none'});
    $('#iframe' + pageScreen).attr("src", "");

    pageScreen = pageScreen - 1;

    $("#pages").trigger('back', pageScreen);
}

function createPage(){

    if($("#page" + pageScreen).length == 0){
        var height = $(window).height();

        var newPage = $("#pageMaster").clone();
        newPage.attr("id", "page" + pageScreen);
        newPage.css('left', (pageScreen - 1) + "00%");
        var newIframe = newPage.find("#iframeMaster");
        newIframe.attr("id", "iframe" + pageScreen);
        newIframe.css('height', height + "px");

        $("#page" + (pageScreen - 1)).after(newPage);
    }
}