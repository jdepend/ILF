/**
 * 被观察者基类
 */
function Observable() {
	//观察者队列
	this.observers = [];
	
	/**
	 * 通知观察者执行方法
	 * @param data 观察者执行update时的参数
	 */
	this.notify = function(data) {
		var length = this.observers.length;
        if (length > 0) {
	        for (var i = 0; i < length; i++) {  
	            this.observers[i].update(this, data);  
	        } 
	        
	        this.callback();
        }
	};
	
	/**
	 * 添加观察者
	 * @param {} observer
	 */
	this.addObserver = function(observer) {  
       this.observers.push(observer);
    };
    
    /**
     * 回掉方法
     */
    this.callback = function() {  
        
    };
};