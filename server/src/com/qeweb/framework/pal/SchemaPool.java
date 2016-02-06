package com.qeweb.framework.pal;

import java.util.HashMap;
import java.util.Map;

import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.common.constant.ConstantSplit;
import com.qeweb.framework.frameworkbo.SysSchemaConfig;

/**
 * SchemaPool
 */
public class SchemaPool {
	static private Map<String, Schema> schemaPool = new HashMap<String, Schema>(); 
	
	final public static Schema getSchema(String containerId, String bindBO, String bindBOP, String sourcePage) {
		String key = containerId + ConstantSplit.GA_PARAM_SPLIT + bindBO + ConstantSplit.GA_PARAM_SPLIT + bindBOP + ConstantSplit.GA_PARAM_SPLIT + sourcePage;
		
		if(isContains(key))
			return getSchema(key);
		
		BOTemplate bot = new BOTemplate();
		bot.push("containerId", containerId);
		bot.push("bindBO", bindBO);
		bot.push("bindBOP", bindBOP);
		bot.push("sourcePage", sourcePage);
		SysSchemaConfig schemaConfig = new SysSchemaConfig();
		Schema schema = (Schema) schemaConfig.query(bot, 0);
		addSchema(key, schema);
		
		return getSchema(key);
	}
	
	final private static void addSchema(String key, Schema schema) {
		schemaPool.put(key, schema);
	}
	
	final private static Schema getSchema(String key) {
		Schema schema = schemaPool.get(key);
		//默认样式
		if(schema == null)
			schema = new Schema(1, "", "color:red;", "font-size:12px;");
		return schema;
	}
	
	final private static boolean isContains(String key) {
		return schemaPool.containsKey(key);
	}
}
