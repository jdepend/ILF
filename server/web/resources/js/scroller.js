var myScroll,myScroll2,myScroll3,
    pullDownEl, pullDownOffset,
    pullUpEl, pullUpOffset,
    generatedCount = 0;

function pullUpAction () {
    setTimeout(function () {	// <-- Simulate network congestion, remove setTimeout from production!
        var el, li, i;
        el = $('#thelist2');

        for (i=0; i<3; i++) {
            li = $('<li></li>');
            li.html('Generated row ' + (++generatedCount));
            el.append(li);
        }

        myScroll2.refresh();		// Remember to refresh when contents are loaded (ie: on ajax completion)
    }, 1000);	// <-- Simulate network congestion, remove setTimeout from production!
}
function loaded() {

    pullUpEl = document.getElementById('pullUp');
    pullUpOffset = pullUpEl.offsetHeight;

    myScroll2 = new iScroll('wrapper2', {
        useTransition: true,
        onRefresh: function () {
            if (pullUpEl.className.match('loading')) {
                pullUpEl.className = '';
                $('.pullUpLabel').html('Pull up to load more...');
            }
        },
        onScrollMove: function () {
            if (this.y < (this.maxScrollY - 5) && !pullUpEl.className.match('flip')) {
                pullUpEl.className = 'flip';
                pullUpEl.querySelector('.pullUpLabel').innerHTML = 'Release to refresh...';
                this.maxScrollY = this.maxScrollY;
            } else if (this.y > (this.maxScrollY + 5) && pullUpEl.className.match('flip')) {
                pullUpEl.className = '';
                pullUpEl.querySelector('.pullUpLabel').innerHTML = 'Pull up to load more...';
                this.maxScrollY = pullUpOffset;
            }
        },
        onScrollEnd: function () {
            if (pullUpEl.className.match('flip')) {
                pullUpEl.className = 'loading';
                pullUpEl.querySelector('.pullUpLabel').innerHTML = 'Loading...';
                pullUpAction();	// Execute custom function (ajax call?)
            }
        },
        checkDOMChanges: true,
        vScrollbar:false

    });

    setTimeout(function () { document.getElementById('wrapper2').style.left = '0'; }, 800);
}



document.addEventListener('touchmove', function (e) { myScroll2.refresh();  e.preventDefault(); }, false);
document.addEventListener('DOMContentLoaded', function () { setTimeout(loaded, 200); }, false);