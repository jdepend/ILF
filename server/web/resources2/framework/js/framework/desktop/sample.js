/*!
 * Ext JS Library 3.2.0
 * Copyright(c) 2006-2010 Ext JS, Inc.
 * licensing@extjs.com
 * http://www.extjs.com/license
 */

// Sample desktop configuration
MyDesktop = new Ext.app.App({
	init :function(){
		Ext.QuickTips.init();
	},

	getModules : function(){
		createModule('100', 'test1', '', '/WEB-INF/pages/demo/load/container/table/demoTable_4.jsp');
		createModule('101', 'test2', '', '/WEB-INF/pages/demo/load/container/form/demoForm_1.jsp');
		return [
		    new MyDesktop['100'](),
		    new MyDesktop['101'](),
            new MyDesktop.BogusMenuModule()
		];
	},

    // config for the start menu
    getStartConfig : function(){
        return {
            title: 'Jack Slocum',
            iconCls: 'user',
            toolItems: [{
                text:'Settings',
                iconCls:'settings',
                scope:this
            },'-',{
                text:'Logout',
                iconCls:'logout',
                scope:this
            }]
        };
    }
});


function createModule(id, text, iconCls, path) {
	MyDesktop[id] = Ext.extend(Ext.app.Module, {
	    id:id,
	    init : function(){
	        this.launcher = {
	            text: text,
	            iconCls:'tabs',
	            handler : this.createWindow,
	            scope: this
	        }
	    },

	    createWindow : function(){
	        var desktop = this.app.getDesktop();
	        var win = desktop.getWindow(id);
	        if(!win){
	            win = desktop.createWindow({
	                id: id,
	                title:text,
	                width:740,
	                height:480,
	                iconCls: 'tabs',
	                shim:false,
	                animCollapse:false,
	                border:false,
	                constrainHeader:true,
	                layout: 'fit',
	                html : "<iframe src='"
						+ appConfig.ctx + actionURL.getRedirect(path)
						+ "' style='scrollbar:true;' height='100%' width='100%' frameborder='0'></iframe>"
	            });
	        }
	        win.show();
	    }
	});
}

/*
 * Example windows
 */



// for example purposes
var windowIndex = 0;

MyDesktop.slaveModule = Ext.extend(Ext.app.Module, {
    init : function(){
        this.launcher = {
            text: 'Window '+(++windowIndex),
            iconCls:'bogus',
            handler : this.createWindow,
            scope: this,
            windowId:windowIndex
        }
    },

    createWindow : function(src){
        var desktop = this.app.getDesktop();
        var win = desktop.getWindow('bogus'+src.windowId);
        if(!win){
            win = desktop.createWindow({
                id: 'bogus'+src.windowId,
                title:src.text,
                width:640,
                height:480,
                html : '<p>Something useful would be in here.</p>',
                iconCls: 'bogus',
                shim:false,
                animCollapse:false,
                constrainHeader:true
            });
        }
        win.show();
    }
});


MyDesktop.BogusMenuModule = Ext.extend(MyDesktop.slaveModule, {
    init : function(){
        this.launcher = {
            text: 'test3',
            iconCls: 'bogus',
            handler: function() {
				return false;
			},
            menu: {
                items:[{
                    text: 'Bogus Window '+(++windowIndex),
                    iconCls:'bogus',
                    handler : this.createWindow,
                    scope: this,
                    windowId: windowIndex
                    },{
                    text: 'Bogus Window '+(++windowIndex),
                    iconCls:'bogus',
                    handler : this.createWindow,
                    scope: this,
                    windowId: windowIndex
                    },{
                    text: 'Bogus Window '+(++windowIndex),
                    iconCls:'bogus',
                    handler : this.createWindow,
                    scope: this,
                    windowId: windowIndex
                    },{
                    text: 'Bogus Window '+(++windowIndex),
                    iconCls:'bogus',
                    handler : this.createWindow,
                    scope: this,
                    windowId: windowIndex
                    },{
                    text: 'Bogus Window '+(++windowIndex),
                    iconCls:'bogus',
                    handler : this.createWindow,
                    scope: this,
                    windowId: windowIndex
                }]
            }
        }
    }
});
