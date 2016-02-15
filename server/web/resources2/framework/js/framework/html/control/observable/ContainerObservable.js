/**
 * 
 */
var ContainerObservable = function(){
	ContainerObservable.superclass.constructor.call(this);
	this.sourceFcId;
		
	this.addObserver = function(observer, targetDomId) {
		observer.targetDomId = targetDomId;
       	this.observers.push(observer);
    };
    
    this.clearObserversData = function(){
    	this.notify('clear');
    }
};
extend(ContainerObservable, Observable);