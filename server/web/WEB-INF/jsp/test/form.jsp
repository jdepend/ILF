<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
    <form class="form-horizontal">
        <div class="form-group">
            <label for="inputEmail3" class="col-xs-4 control-label">方案全称：</label>

            <div class="col-xs-8">
                <input class="form-control" id="inputEmail3" required>
            </div>
        </div>
        <div class="form-group">
            <label for="inputEmail3" class="col-xs-4 control-label">方案简称：</label>

            <div class="col-xs-8">
                <input class="form-control" id="inputEmail3" required>
            </div>
        </div>

        <div class="form-group">
            <label for="inputEmail3" class="col-xs-4 control-label">方案类型：</label>

            <div class="col-xs-8">
                <select class="form-control" required>
                    <option>系统方案</option>
                    <option>机关方案</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="inputEmail3" class="col-xs-4 control-label">考核年度：</label>

            <div class="col-xs-8">
                <input class="form-control" id="inputEmail3">
            </div>
        </div>
        <div class="form-group">
            <label for="inputEmail3" class="col-xs-4 control-label">考评提醒时间：</label>

            <div class="col-xs-8">
                <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd"
                     data-link-field="dtp_input2" data-link-format="yyyy-MM-dd">
                    <input class="form-control" size="16" type="text" value="" readonly="">
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label for="inputEmail3" class="col-xs-4 control-label">状态：</label>

            <div class="col-xs-8">
                <select class="form-control" disabled>
                    <option>请选择</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="inputEmail3" class="col-xs-4 control-label">填报提醒时间：</label>

            <div class="col-xs-8">
                <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd"
                     data-link-field="dtp_input2" data-link-format="yyyy-MM-dd">
                    <input class="form-control" size="16" type="text" value="" readonly="">
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label for="inputEmail3" class="col-xs-4 control-label">主考部门：</label>

            <div class="col-xs-8">
                <input class="form-control" id="inputEmail3">
            </div>
        </div>
        <div class="form-group">
            <label for="inputEmail3" class="col-xs-4 control-label">排序：</label>

            <div class="col-xs-8">
                <input class="form-control" id="inputEmail3">
            </div>
        </div>
        <div class="form-group">
            <label for="inputEmail3" class="col-xs-4 control-label">加减分是否绩效办复核：</label>

            <div class="col-xs-8">
                <label class="radio-inline">
                    <input type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1">是
                </label>
                <label class="radio-inline">
                    <input type="radio" name="inlineRadioOptions" id="inlineRadio2" value="option2">否
                </label>
            </div>
        </div>

        <div class="form-group">
            <div class="col-xs-12" style="text-align: center;">
                <button type="submit" class="btn btn-primary">提交</button>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript">
    $('.form_datetime').datetimepicker({
        language:  'zh-CN',
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        forceParse: 0,
        showMeridian: 1
    });
    $('.form_date').datetimepicker({
        language:  'zh-CN',
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0
    });
    $('.form_time').datetimepicker({
        language:  'zh-CN',
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 1,
        minView: 0,
        maxView: 1,
        forceParse: 0
    });
</script>