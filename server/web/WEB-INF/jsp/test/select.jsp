<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getContextPath();
%>
<style>
body{background:#ddd;font:14px/1.7 tahoma,'\5fae\8f6f\96c5\9ed1',sans-serif;}
h1,h2,h3{font-size:36px;line-height:1;}
h2{font-size:24px;}
h3{font-size:18px;}
fieldset{margin:2em 0;}
fieldset legend{font-weight:bold;font-size:16px;line-height:2;}
a{color:#06f;text-decoration:none;}
a:hover{color:#00f;}

.wrap{width:800px;margin:0 auto;padding:20px 40px;border-radius:8px;background:#fff;box-shadow:0 0 10px rgba(0,0,0,0.5);}
</style>
<div class="wrap">
  <h1>jQuery cxSelect 联动下拉菜单</h1>

  <h2>示例</h2>
  <fieldset id="city_china">
    <legend>默认</legend>
    <p>省份：<select class="province"></select></p>
    <p>城市：<select class="city"></select></p>
    <p>地区：<select class="area"></select></p>
  </fieldset>

  <fieldset id="city_china_val">
    <legend>设置默认值及选项标题</legend>
    <p>所在地区：
      <select class="province" data-first-title="选择省">
        <option value="浙江省" selected></option>
      </select>
      <select class="city" data-first-title="选择市">
        <option value="杭州市" selected></option>
      </select>
      <select class="area" data-first-title="选择地区">
        <option value="西湖区" selected></option>
      </select>
    </p>
  </fieldset>
      
  <fieldset id="global_location">
    <legend>全球主要国家城市联动</legend>
    <p>所在地区：
      <select class="country" data-first-title="选择国家"></select>
      <select class="state"></select>
      <select class="city"></select>
      <select class="region"></select>
    </p>
  </fieldset>
  
  <fieldset id="custom_data">
    <legend>自定义选项</legend>
    <p>一：<select class="first"></select></p>
    <p>二：<select class="second"></select></p>
    <p>三：<select class="third"></select></p>
    <p>四：<select class="fourth"></select></p>
    <p>五：<select class="fifth"></select></p>
  </fieldset>

</div>

<script>
$.cxSelect.defaults.url = '<%=basePath%>/resources/data/cityData.min.json';

$('#city_china').cxSelect({
  selects: ['province', 'city', 'area']
});

$('#city_china_val').cxSelect({
  selects: ['province', 'city', 'area'],
  nodata: 'none'
});

$('#global_location').cxSelect({
  url: '<%=basePath%>/resources/data/globalData.min.json',
  selects: ['country', 'state', 'city', 'region'],
  nodata: 'none'
});

$('#custom_data').cxSelect({
  selects: ['first', 'second', 'third', 'fourth', 'fifth'],
  required: true,
  jsonValue: 'v',
  url: [
    {'v': '1', 'n': '第一级 >', 's': [
      {'v': '2', 'n': '第二级 >', 's': [
        {'v': '3', 'n': '第三级 >', 's': [
          {'v': '4', 'n': '第四级 >', 's': [
            {'v': '5', 'n': '第五级 >', 's': [
              {'v': '6', 'n': '第六级 >'}
            ]}
          ]}
        ]}
      ]}
    ]},
    {'v': 'test number', 'n': '测试数字', 's': [
      {'v': 'text', 'n': '文本类型', 's': [
        {'v': '4', 'n': '4'},
        {'v': '5', 'n': '5'},
        {'v': '6', 'n': '6'},
        {'v': '7', 'n': '7'},
        {'v': '8', 'n': '8'},
        {'v': '9', 'n': '9'},
        {'v': '10', 'n': '10'}
      ]},
      {'v': 'number', 'n': '数值类型', 's': [
        {'v': 11, 'n': 11},
        {'v': 12, 'n': 12},
        {'v': 13, 'n': 13},
        {'v': 14, 'n': 14},
        {'v': 15, 'n': 15},
        {'v': 16, 'n': 16},
        {'v': 17, 'n': 17}
      ]}
    ]},
    {'v': 'test boolean','n': '测试 Boolean 类型', 's': [
      {'v': true ,'n': true},
      {'v': false ,'n': false}
    ]},
    {v: 'test quotes', n: '测试属性不加引号', s: [
      {v: 'quotes', n: '引号'}
    ]},
    {v: 'test other', n: '测试奇怪的值', s: [
      {v: '[]', n: '数组（空）'},
      {v: [1,2,3], n: '数组（数值）'},
      {v: ['a','b','c'], n: '数组（文字）'},
      {v: new Date(), n: '日期'},
      {v: new RegExp('\\d+'), n: '正则对象'},
      {v: /\d+/, n: '正则直接量'},
      {v: {}, n: '对象'},
      {v: document.getElementById('custom_data'), n: 'DOM'},
      {v: null, n: 'Null'},
      {n: '未设置 value'}
    ]},
    {'v': '' , 'n': '无子级'}
  ]
});
</script>

