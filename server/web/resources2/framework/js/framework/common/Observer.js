/**
 * 观察者基类
 */
function Observer(){
	/**
	 * 执行方法
	 * @param {} observable 被观察者
	 * @param {} data  观察者执行update时的参数
	 */
	this.update = function(observable, data) {}
}