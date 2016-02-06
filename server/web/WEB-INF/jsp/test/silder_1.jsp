<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getContextPath();
%>
<!DOCTYPE html>
<head>
    <meta charset="utf-8" />
    <title>slider-1</title>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
    <meta name="description" content="" />
    <meta name="keywords" content="" />
    <link href="<%=basePath%>/resources/css/test-slider_1.css" rel="stylesheet" type="text/css">
</head>
<body>
<header>HEADER </header>

<section>
    <!-- view container -->
    <div id="test-pages" class="view-container" style="">
        <!-- pageA container -->
        <div id="test-pageA" class="page-container" style="left:0%;display:block;"> <b id="btnNext1" class="page-num">A</b> </div>
        <!-- pageB container -->
        <div id="test-pageB" class="page-container" style="left:100%;display:none;"> <b id="btnNext2" class="page-num">B</b></div>
        <!-- pageC container -->
        <div id="test-pageC" class="page-container" style="left:200%;display:none;"> <b id="pageBack" class="page-num">C</b></div>
    </div>

</section>

<footer>FOOTER</footer>

<!-- framework -->
<script type="text/javascript" src="<%=basePath%>/resources/js/frame.min.js"></script>
<script>
    var btnNext1 = $('#btnNext1');
    var btnNext2 = $('#btnNext2');
    var pageBack = $('#pageBack');
    var pages = $('#test-pages');
    var pageA = $('#test-pageA');
    var pageB = $('#test-pageB');
    var pageC = $('#test-pageC');

    btnNext1.click(function(){
        pages.css({'-webkit-transform' : 'translate3d(-100%, 0px, 0px)' , '-webkit-transition' : '300ms'});
        pageA.css({'display' : 'none'});
        pageB.css({'display' : 'block'});
        pageC.css({'display' : 'none'});
    });

    btnNext2.click(function(){
        pages.css({'-webkit-transform' : 'translate3d(-200%, 0px, 0px)' , '-webkit-transition' : '300ms'});
        pageA.css({'display' : 'none'});
        pageB.css({'display' : 'none'});
        pageC.css({'display' : 'block'});
    });
    pageBack.click(function(){
        pages.css({'-webkit-transform' : 'translate3d(-100%, 0px, 0px)' , '-webkit-transition' : '300ms'});
        pageA.css({'display' : 'none'});
        pageB.css({'display' : 'block'});
        pageC.css({'display' : 'none'});
    });
</script>
</body>
</html>