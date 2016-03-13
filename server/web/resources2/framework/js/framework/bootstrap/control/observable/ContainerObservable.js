/**
 * 
 */
var ContainerObservable = function(){
	ContainerObservable.superclass.constructor.call(this);
	this.containerName;
		
	this.addObserver = function(observer, containerId) {
        observer.containerId = containerId;
        this.observers.push(observer);
    };
    
    this.clearObserversData = function(){
    	this.notify('clear');
    }
};
extend(ContainerObservable, Observable);