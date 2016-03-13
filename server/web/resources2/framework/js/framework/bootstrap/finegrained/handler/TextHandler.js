
/**
 * TextHandler继承FCHandler(FCHandler.js), 处理文本域textfield或多行文本域textarea
 * @param bopDom	细粒度组件数据岛
 * @returns
 */
function TextHandler(bopDom) {
	TextHandler.superclass.constructor.call(this, bopDom);
}

extend(TextHandler, FCHandler);