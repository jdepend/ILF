/**
 * @class Ext.ux.TabCloseMenu
 * @extends Object 
 * Plugin (ptype = 'tabclosemenu') for adding a close context menu to tabs.
 * 
 * @ptype tabclosemenu
 */
Ext.ux.TabCloseMenu = function(){
    var tabs, menu, ctxItem;
    this.init = function(tp){
        tabs = tp;
        tabs.on('contextmenu', onContextMenu);
    };

    function onContextMenu(ts, item, e){
        if(!menu){ // create context menu on first right click
            menu = new Ext.menu.Menu({            
            items: [{
                id: tabs.id + '-close',
                text: I18N.UX_CLOSE_TAB,
                iconCls:'applicationIcon', 
                handler : function(){
                    tabs.remove(ctxItem);
                }
            },{
                id: tabs.id + '-close-others',
                text: I18N.UX_CLOSE_OTHER_TAB,
                iconCls:'application_doubleIcon', 
                handler : function(){
                    tabs.items.each(function(item){
                        if(item.closable && item != ctxItem){
                            tabs.remove(item);
                        }
                    });
                }
            },{
                id: tabs.id + '-close-all',
                text: I18N.UX_CLOSE_ALL_TAB,
                iconCls:'application_cascadeIcon', 
                handler : function(){
                    tabs.items.each(function(item){
                        if(item.closable){
                            tabs.remove(item);
                        }
                    });
                }
            }, '-',{
                id: tabs.id + '-cancel',
                text: I18N.COMMON_CANCEL,
                iconCls:'redo2', 
                handler : function(){
                	menu.hide();
                }
            }
            ]});
        }
        ctxItem = item;
        var items = menu.items;
        items.get(tabs.id + '-close').setDisabled(!item.closable);
        var disableOthers = true;
        tabs.items.each(function(){
            if(this != item && this.closable){
                disableOthers = false;
                return false;
            }
        });
        items.get(tabs.id + '-close-others').setDisabled(disableOthers);
	e.stopEvent();
        menu.showAt(e.getPoint());
    }
};

Ext.preg('tabclosemenu', Ext.ux.TabCloseMenu);
Ext.ns('Ext.ux.grid');