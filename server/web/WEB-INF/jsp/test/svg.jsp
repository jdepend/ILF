<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getContextPath();
%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>Highstock Example</title>

        <script src="<%=basePath%>/resources/js/jquery.min.js"></script>
		<style type="text/css">

		</style>
        <script type="text/javascript">
            $(function () {
                $.getJSON('<%=basePath%>/svg/initdata', function (data) {

                    // split the data set into ohlc and volume
                    var ohlc = [],
                            volume = [],
                            dataLength = data.length,
                    // set the allowed units for data grouping
                            groupingUnits = [[
                                'week',                         // unit name
                                [1]                             // allowed multiples
                            ], [
                                'month',
                                [1, 2, 3, 4, 6]
                            ]],

                            i = 0;

                    for (i; i < dataLength; i += 1) {
                        ohlc.push([
                            data[i][0], // the date
                            data[i][1], // open
                            data[i][2], // high
                            data[i][3], // low
                            data[i][4] // close
                        ]);

                        volume.push([
                            data[i][0], // the date
                            data[i][5] // the volume
                        ]);
                    }


                    // create the chart
                    $('#container').highcharts('StockChart', {

                        rangeSelector: {
                            selected: 1
                        },

                        title: {
                            text: 'AAPL Historical'
                        },

                        yAxis: [{
                            labels: {
                                align: 'right',
                                x: -3
                            },
                            title: {
                                text: 'OHLC'
                            },
                            height: '60%',
                            lineWidth: 2
                        }, {
                            labels: {
                                align: 'right',
                                x: -3
                            },
                            title: {
                                text: 'Volume'
                            },
                            top: '65%',
                            height: '35%',
                            offset: 0,
                            lineWidth: 2
                        }],

                        series: [{
                            type: 'candlestick',
                            name: 'AAPL',
                            data: ohlc,
                            dataGrouping: {
                                units: groupingUnits
                            },
                            events:{
                                click:function(event){
                                    displayInfo(event.point);
                                   // console.log(event.point);
                                }
                            }
                        }, {
                            type: 'column',
                            name: 'Volume',
                            data: volume,
                            yAxis: 1,
                            dataGrouping: {
                                units: groupingUnits
                            },
                            events:{
                                click:function(event){
                                    displayInfo(event.point);
                                    //console.log(event.point);
                                }
                            }
                        }]
                    });
                });
            });

        var maxdate=0;
        $(window).ready(function(){
            $("#updateBtn").bind("click", timedCount);
            $("#stopBtn").bind("click", stopCount);
        });
        function displayInfo(point){

            if(point.series.name == "AAPL"){
                $("#message").html("<p>系列："+point.series.name+"</p><p>open："+point.open+"  close："+point.close+"</p><p>high："+point.high+" low："+point.low+"</p>")
            }else{
                $("#message").html("<p>系列："+point.series.name+"</p><p>volume："+point.y+"</p>")
            }
        }


        var t;
        //1秒更新一次
        function timedCount(){
            totlePoint();
            var chart = $('#container').highcharts();
            series1 = chart.series[0];
            series2 = chart.series[1];
            //console.log(chart);
            $.getJSON('<%=basePath%>/svg/updatedata/'+maxdate, function (data) {
                //console.log(data);
                for(var i in data){
                    series1.addPoint([data[i][0],data[i][1],data[i][2],data[i][3],data[i][4]],false);
                    series2.addPoint([data[i][0],data[i][5]],false);
                }
                maxdate = data[data.length-1][0];
                //console.log(maxdate);
                chart.redraw();
                t=setTimeout("timedCount()",1000);
            });
        }

        function stopCount(){
            clearTimeout(t);
        }

        function totlePoint(){
            var chart = $('#container').highcharts();
            //console.log(chart);
            $("#message").html("<p>共多少个节点："+chart.series[0].data.length);
        }
        </script>
	</head>
	<body>
<script src="<%=basePath%>/resources/js/svg/highstock.js"></script>
<script src="<%=basePath%>/resources/js/svg/exporting.js"></script>

<div id="container" style="height: 400px; min-width: 310px"></div>

<div><input id="updateBtn" type="button" value="启动更新"/>&nbsp;<input id="stopBtn" type="button" value="暂停更新"/></div>
<span id="cnt" style="color: green;"></span>
</br>
<div id="message" style="background-color: gray;height: 100px; width: 400px"/>

</body>
</html>
