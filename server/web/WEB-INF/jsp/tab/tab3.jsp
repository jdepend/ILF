<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/1/6
  Time: 9:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/header.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0">

    <title>Mobiscroll</title>

    <style type="text/css">
        body {
            padding: 0;
            margin: 0;
            font-family: arial, verdana, sans-serif;
            font-size: 12px;
            background: #ddd;
        }
        input, select {
            width: 100%;
            padding: 5px;
            margin: 5px 0;
            border: 1px solid #aaa;
            box-sizing: border-box;
            border-radius: 5px;
            -moz-box-sizing: border-box;
            -webkit-box-sizing: border-box;
            -webkit-border-radius: 5px;
        }
        .header {
            border: 1px solid #333;
            background: #111;
            color: white;
            font-weight: bold;
            text-shadow: 0 -1px 1px black;
            background-image: linear-gradient(#3C3C3C,#111);
            background-image: -webkit-gradient(linear,left top,left bottom,from(#3C3C3C),to(#111));
            background-image: -moz-linear-gradient(#3C3C3C,#111);
        }
        .header h1 {
            text-align: center;
            font-size: 16px;
            margin: .6em 0;
            padding: 0;
            text-overflow: ellipsis;
            overflow: hidden;
            white-space: nowrap;
        }
        .content {
            padding: 15px;
            background: #fff;
        }
        .car {
            position: relative;
            height: 100%;
        }
        .car img {
            height: 28px;
            display: block;
            margin: 0 auto;
        }
        .car .img-cont {
            width: 80px;
            height: 28px;
            text-align: center;
            float: left;
            position: relative;
            top: 50%;
            margin-top: -14px;
        }
        .car span {
            float: left;
        }
    </style>

    <script type="text/javascript">
        $(function () {
            var curr = new Date().getFullYear();
            var opt = {

            }

            opt.date = {preset : 'date'};
            opt.datetime = { preset : 'datetime', minDate: new Date(2012,3,10,9,22), maxDate: new Date(2014,7,30,15,44), stepMinute: 5  };
            opt.time = {preset : 'time'};
            opt.tree_list = {preset : 'list', labels: ['Region', 'Country', 'City']};
            opt.image_text = {preset : 'list', labels: ['Cars']};
            opt.select = {preset : 'select'};
            <!--Script-->

            $('select.changes').bind('change', function() {
                var demo = $('#demo').val();
                $(".demos").hide();
                if (!($("#demo_"+demo).length))
                    demo = 'default';

                $("#demo_" + demo).show();
                $('#test_'+demo).val('').scroller('destroy').scroller($.extend(opt[$('#demo').val()], { theme: $('#theme').val(), mode: $('#mode').val(), display: $('#display').val(), lang: $('#language').val() }));
            });

            $('#demo').trigger('change');
        });
    </script>
</head>

<body>
<div class="header">
    <h1>Mobiscroll</h1>
</div>

<div class="content">
    <div>
        <label for="theme">Theme</label>
        <select name="theme" id="theme" class="changes">
            <option value="default">Default</option>
            <option value="android">Android</option>
            <option value="android-ics light">Android ICS Light</option>
            <option value="android-ics">Android ICS</option>
            <option value="ios">iOS</option>
            <option value="jqm">Jquery Mobile</option>
            <option value="sense-ui">Sense UI</option>
            <option value="wp light">Windows Phone Light</option>
            <option value="wp">Windows Phone</option>
            <!--Themes-->
        </select>
    </div>
    <div>
        <label for="mode">Mode</label>
        <select name="mode" id="mode" class="changes">
            <option value="scroller">Scroller</option>
            <option value="clickpick">Clickpick</option>
            <option value="mixed">Mixed</option>
        </select>
    </div>
    <div>
        <label for="display">Display</label>
        <select name="display" id="display" class="changes">
            <option value="modal">Modal</option>
            <option value="inline">Inline</option>
            <option value="bubble">Bubble</option>
            <option value="top">Top</option>
            <option value="bottom">Bottom</option>
        </select>
    </div>
    <div>
        <label for="language">Language</label>
        <select name="language" id="language" class="changes">
            <option value="">English</option>
            <option value="hu">Magyar</option>
            <option value="de">Deutsch</option>
            <option value="es">Espa駉l</option>
            <option value="fr">Fran鏰is</option>
            <option value="it">Italiano</option>
            <option value="no">Norsk</option>
            <option value="pt-BR">Pr Brasileiro</option>
            <option value="zh">Chinese</option>
            <option value="nl">Nederlands</option>
            <option value="tr">T黵k鏴</option>
            <option value="ja">Japanese</option>
            <!--Lang-->
        </select>
    </div>
    <div>
        <label for="demo">Demo</label>
        <select name="demo" id="demo" class="changes">
            <option value="date" selected>Date</option>
            <option value="datetime" >Datetime</option>
            <option value="time" >Time</option>
            <option value="tree_list" >Tree List</option>
            <option value="image_text" >Image & Text</option>
            <option value="select" >Select</option>
            <!--Demos-->
        </select>
    </div>
    <div id="demo_default" class="demos">
        <label for="test_default">Click here to try</label>
        <input type="text" name="test_default" id="test_default" />
    </div>
    <div data-role="fieldcontain" class="demos fieldcontain" id="demo_tree_list">
        <label for="test_tree_list_dummy">Click here to try</label><ul id="test_tree_list"><li>America<ul><li>USA<ul><li>Washington</li><li>Florida</li><li>Los Angeles</li><li>San Francisco</li></ul></li><li>Canada<ul><li>Vancouver</li><li>Winnipeg</li><li>Calgary</li></ul></li><li>Brazil<ul><li>Rio de Janeiro</li><li>Sao Paolo</li></ul></li></ul></li><li>Europe<ul><li>France<ul><li>Paris</li><li>Toulouse</li><li>Marseille</li><li>Lyon</li></ul></li><li>Germany<ul><li>Berlin</li><li>Frankfurt</li><li>Hamburg</li></ul></li><li>Spain<ul><li>Madrid</li><li>Barcelona</li><li>Saragosa</li></ul></li><li>Italy<ul><li>Rome</li><li>Palermo</li><li>Genoa</li><li>Torino</li></ul></li></ul></li><li>Asia<ul><li>China<ul><li>Shanghai</li><li>Hong Kong</li><li>Beijing</li></ul></li><li>Japan<ul><li>Tokyo</li><li>Kagoshima</li><li>Osaka</li></ul></li></ul></li></ul>
    </div>
    <div data-role="fieldcontain" class="demos fieldcontain" id="demo_image_text">
        <label for="test_image_text_dummy">Click here to try</label><ul id="test_image_text"><li><div class="car"><div class="img-cont"><img src="http://mobiscroll.com/Content/img/BMW_logo.png"></div><span>BMW</span></div></li><li><div class="car"><div class="img-cont"><img src="http://mobiscroll.com/Content/img/Dacia_logo.png"></div><span>Dacia</span></div></li><li><div class="car"><div class="img-cont"><img src="http://mobiscroll.com/Content/img/Ferrari_logo.png"></div><span>Ferrari</span></div></li><li><div class="car"><div class="img-cont"><img src="http://mobiscroll.com/Content/img/Mercedes_logo.png"></div><span>Mercedes</span></div></li><li><div class="car"><div class="img-cont"><img src="http://mobiscroll.com/Content/img/Opel_logo.png"></div><span>Opel</span></div></li><li><div class="car"><div class="img-cont"><img src="http://mobiscroll.com/Content/img/Renault_logo.png"></div><span>Renault</span></div></li><li><div class="car"><div class="img-cont"><img src="http://mobiscroll.com/Content/img/Volkswagen_logo.png"></div><span>Volkswagen</span></div></li></ul>
    </div>
    <div data-role="fieldcontain" class="demos fieldcontain" id="demo_select">
        <label for="test_select" class="ui-input-text">Click here to try</label><select id="test_select" class="demos" data-role="none"><option value="">Please Select</option><option value="1">Atlanta</option><option value="2">Berlin</option><option value="3">Boston</option><option value="4">Chicago</option><option value="5">London</option><option value="6">Los Angeles</option><option value="7">New York</option><option value="8">Paris</option><option value="9">San Francisco</option></select>
    </div>
    <!--Html-->
</div>
</body>
</html>

