<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
    <ul class="nav nav-tabs">
        <li role="presentation" class="active"><a href="#" id="tab1">tabl</a></li>
        <li role="presentation"><a href="#" id="tab2">tab2</a></li>
        <li role="presentation"><a href="#" id="tab3">tab3</a></li>
        <li role="presentation"><a href="#" id="test2">test2</a></li>
    </ul>

    <div id="tab_workspace" class="starter-template"></div>


    <script type="text/javascript">
        $(function () {

            $("#tab1").click(
                    function () {
                        $(this).parent().parent().children("[class='active']").removeClass("active");
                        $(this).parent().addClass("active")
                        $("#tab_workspace").load("tab1.html");
                    }
            );

            $("#tab2").click(
                    function () {
                        $(this).parent().parent().children("[class='active']").removeClass("active");
                        $(this).parent().addClass("active")
                        $("#tab_workspace").load("tab2.html");
                    }
            );

            $("#tab3").click(
                    function () {
                        $(this).parent().parent().children("[class='active']").removeClass("active");
                        $(this).parent().addClass("active")
                        $("#tab_workspace").load("tab3.html");
                    }
            );

            $("#test2").click(
                    function () {
                        $(this).parent().parent().children("[class='active']").removeClass("active");
                        $(this).parent().addClass("active")
                        $("#tab_workspace").load("test2.html");
                    }
            );

            $("#tab_workspace").load("tab1.html");
        });
    </script>
</div>
