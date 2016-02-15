
/** 
* 此js为补充Extjs的chechbox和radio校验 
* 
* checkbox需要添加属性： 
* showInvalidText：Boolean 
* 此属性为标识出在那个checkbox后面添加错误图片 
* 注意：boxLabel需要两个字符不然图片会合字重叠，可以在字符后加一空格' ' 
* 
* radio需要添加属性： 
* showInvalidText：Boolean 
* 此属性为标识出在那个radio后面添加错误图片 
*/ 
Ext.apply(Ext.form.Checkbox.prototype, { 
    getErrorCt:function(){
        return this.el.parent().parent().findParent('.x-form-element', 5, true) || // use form element wrap if available 
            this.el.parent().parent().findParent('.x-form-field-wrap', 5, true);   // else direct field wrap 
    } 
}); 
Ext.apply(Ext.form.Radio.prototype, { 
    getErrorCt:function(){ 
        return this.el.parent().parent().findParent('.x-form-element', 5, true) || // use form element wrap if available 
            this.el.parent().parent().findParent('.x-form-field-wrap', 5, true);   // else direct field wrap 
    } 
}); 
function markInvalid(msg){ 
    if (!this.rendered || this.preventMark) { // not rendered 
        return; 
    } 
    this.el.addClass(this.invalidClass); 
    msg = msg || this.invalidText; 
    switch (this.msgTarget) { 
        case 'qtip' : 
            this.el.dom.qtip = msg; 
            this.el.dom.qclass = 'x-form-invalid-tip'; 
            if (Ext.QuickTips) { // fix for floating editors interacting with 
                                    // DND 
                Ext.QuickTips.enable(); 
            } 
            break; 
        case 'title' : 
            this.el.dom.title = msg; 
            break; 
        case 'under' : 
            if (!this.errorEl) { 
                var elp = this.getErrorCt(); 
                this.errorEl = elp.createChild( { 
                    cls : 'x-form-invalid-msg' 
                }); 
                this.errorEl.setWidth(elp.getWidth(true) - 20); 
            } 
            this.errorEl.update(msg); 
            Ext.form.Field.msgFx[this.msgFx].show(this.errorEl, this); 
            break; 
        case 'side' : 
            if (!this.showInvalidText) { 
                break; 
            } 
            if (!this.errorIcon) { 
                var elp = this.getErrorCt(); 
                this.errorIcon = elp.createChild( { 
                    cls : 'x-form-invalid-icon' 
                }); 
            } 
            this.errorIcon.alignTo(this.el.next(), 'tl-tr', [2, 0]); 
            this.errorIcon.dom.qtip = msg; 
            this.errorIcon.dom.qclass = 'x-form-invalid-tip'; 
            this.errorIcon.show(); 
            this.on('resize', this.alignErrorIcon, this); 
            break; 
        default : 
            var t = Ext.getDom(this.msgTarget); 
            t.innerHTML = msg; 
            t.style.display = this.msgDisplay; 
            break; 
    } 
    this.fireEvent('invalid', this, msg); 
} 
function clearInvalid(){ 
    if (!this.rendered || this.preventMark) { // not rendered 
        return; 
    } 
    this.el.removeClass(this.invalidClass); 
    switch (this.msgTarget) { 
        case 'qtip' : 
            this.el.dom.qtip = ''; 
            break; 
        case 'title' : 
            this.el.dom.title = ''; 
            break; 
        case 'under' : 
            if (this.errorEl) { 
                Ext.form.Field.msgFx[this.msgFx].hide(this.errorEl, this); 
            } 
            break; 
        case 'side' : 
            if (this.errorIcon) { 
                this.errorIcon.dom.qtip = ''; 
                this.errorIcon.hide(); 
                this.un('resize', this.alignErrorIcon, this); 
            } 
            break; 
        default : 
            var t = Ext.getDom(this.msgTarget); 
        	if(!!t) {
        		t.innerHTML = ''; 
        		t.style.display = 'none';
        	}
            break; 
    } 
    this.fireEvent('valid', this); 
} 
function validate(){ 
    var array = this.ownerCt.find('name', this.name); 
    if (this.validateValue(this.processValue(this.getRawValue()))) { 
        for(var i=0;i<array.length;i++){ 
            array[i].clearInvalid(); 
        } 
        return true; 
    } 
    return false; 
} 
function validateValue() {
    if (!this.getGroupValue()) { 
        this.markInvalid(); 
        return false; 
    } 
    return true; 
} 
Ext.form.Checkbox.prototype.markInvalid=Ext.form.Radio.prototype.markInvalid = markInvalid; 
Ext.form.Checkbox.prototype.clearInvalid=Ext.form.Radio.prototype.clearInvalid = clearInvalid; 
//Ext.form.Checkbox.prototype.validate=Ext.form.Radio.prototype.validate = validate; 
Ext.form.Checkbox.prototype.validateValue=function(){ 
	//是否必填, 如果非必填直接返回
	if(this.ownerCt.allowBlank) {
		return true;
	}
	
    var array = this.ownerCt.find('name', this.name); 
    for(var i=0;i<array.length;i++){ 
        if(array[i]){ 
            return true; 
        } 
    } 
    this.markInvalid(); 
    return false; 
};
//Ext.form.Radio.prototype.validateValue = validateValue;