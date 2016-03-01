<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<html>
<head>
  <title>Column Layout</title>

	<script type="text/javascript">

    Ext.onReady(function(){

       // NOTE: This is an example showing simple state management. During development,
       // it is generally best to disable state management as dynamically-generated ids
       // can change across page loads, leading to unpredictable results.  The developer
       // should ensure that stable state ids are set for stateful components in real apps.
       Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

       var viewport = new Ext.Viewport({
            layout:'border',
            items:[{
                region:'west',
                id:'west-panel',
                title:'West',
                split:true,
                width: 200,
                minSize: 175,
                maxSize: 400,
                collapsible: true,
                margins:'35 0 5 5',
                cmargins:'35 5 5 5',
                layout:'accordion',
                layoutConfig:{
                    animate:true
                },
                items: [{
                    html: "aaa",
                    title:'Navigation',
                    autoScroll:true,
                    border:false,
                    iconCls:'nav'
                },{
                    title:'Settings',
                    html: "aaa",
                    border:false,
                    autoScroll:true,
                    iconCls:'settings'
                }]
            },
            
            
            
            {
                region:'center',
                margins:'35 5 5 0',
                layout:'column',
                autoScroll:true,
                items:[{
                    columnWidth:.33,
                    baseCls:'x-plain',
                    bodyStyle:'padding:5px 0 5px 5px',
                    items:[{
                        title: 'A Panel',
                        html: "aaa"
                    }]
                },{
                    columnWidth:.33, 
                    baseCls:'x-plain',
                    bodyStyle:'padding:5px 0 5px 5px',
                    items:[{
                        title: 'A Panel',
                        html: "aaa"
                    }]
                },{
                    columnWidth:.33,
                    baseCls:'x-plain',
                    bodyStyle:'padding:5px',
                    items:[{
                        title: 'A Panel',
                        html: "aaa"
                    },
                    
                    {
                        title: 'Another Panel',
      
                        columnWidth:.33,
                        html: "aaa"
                    }]
                }
               
                ]
            }]
        });
    });
	</script>
</head>
<body>
  </body>
</html>

