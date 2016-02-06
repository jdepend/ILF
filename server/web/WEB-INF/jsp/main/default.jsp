<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="jumbotron" id="welcome" style="background-color: #ffffff;">
    <h1>你好！</h1>
    <p>这是ILF（Interactive Layout Framework），一个为快速构建移动端应用界面而生的框架。</p>
    <p><a class="btn btn-primary btn-lg" id="learn" href="" role="button">了解更多</a></p>
</div>
<style>
@media (min-width: 768px) {
        #welcome {
            padding:50px 200px;
        }
    }
@media (max-width: 768px) {
    #welcome {
        padding:20px 20px;
    }
}
</style>
<script>
    $("#learn").click(
            function(){
                parent.location.href="<%=request.getContextPath()%>/learn.html";
            }
    );
</script>