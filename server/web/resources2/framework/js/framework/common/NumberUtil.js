var NumberUtil = {
	//加
	add : function(arg1,arg2){
	    var r1,r2,m;
	    try{r1=String(arg1).split(".")[1].length;}catch(e){r1=0;}
	    try{r2=String(arg2).split(".")[1].length;}catch(e){r2=0;}
	    m=Math.pow(10,Math.max(r1,r2));
	    return (arg1*m+arg2*m)/m;
	},
	//减
	sub : function(arg1,arg2){
	    var r1,r2,m,n;
	    try{r1=String(arg1).split(".")[1].length;}catch(e){r1=0;}
	    try{r2=String(arg2).split(".")[1].length;}catch(e){r2=0;}
	    m=Math.pow(10,Math.max(r1,r2));
	    //last modify by deeka
	    //动态控制精度长度
	    n=(r1>=r2)?r1:r2;
	    return ((arg1*m-arg2*m)/m).toFixed(n);
	},	
	//乘
	mul : function(arg1,arg2)
	{
		var m=0,s1=String(arg1),s2=String(arg2);
	    try{m+=s1.split(".")[1].length;}catch(e){}
	    try{m+=s2.split(".")[1].length;}catch(e){}
	    return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m);
	},
	//除
	div : function(arg1,arg2){
		var t1=0,t2=0,r1,r2;
	    try{t1=String(arg1).split(".")[1].length;}catch(e){}
	    try{t2=String(arg2).split(".")[1].length;}catch(e){}
	    with(Math){
	        r1=Number(String(arg1).replace(".",""));
	        r2=Number(String(arg2).replace(".",""));
	        return (r1/r2)*pow(10,t2-t1);
	    }
	},
	//取模
	mod : function(arg1,arg2){
		var t1=0,t2=0,r1,r2;
	    try{t1=String(arg1).split(".")[1].length;}catch(e){}
	    try{t2=String(arg2).split(".")[1].length;}catch(e){}
	    with(Math){
	        r1=Number(String(arg1).replace(".",""));
	        r2=Number(String(arg2).replace(".",""));
	        return (r1%r2)*pow(10,t2-t1);
	    }
	}
};