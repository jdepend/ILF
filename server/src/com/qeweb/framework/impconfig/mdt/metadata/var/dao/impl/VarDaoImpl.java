package com.qeweb.framework.impconfig.mdt.metadata.var.dao.impl;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jdom.Document;
import org.jdom.Element;

import com.qeweb.framework.base.impl.XmlDaoImpl;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.GuidUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.common.util.pathutil.VarPathUtil;
import com.qeweb.framework.impconfig.mdt.metadata.valueset.bo.MdtValueSetBO;
import com.qeweb.framework.impconfig.mdt.metadata.valueset.dao.ia.IMdtValueSetDao;
import com.qeweb.framework.impconfig.mdt.metadata.var.bo.VarBO;
import com.qeweb.framework.impconfig.mdt.metadata.var.dao.ia.IVarDao;

/**
 * 变量管理 dao impl
 *
 */
public class VarDaoImpl extends XmlDaoImpl implements IVarDao {

	private static final long serialVersionUID = 7368310797904818422L;

	@Override
	public List<VarBO> getVars(BOTemplate bot) {
		Element rootElement = getRootElement();
		if(rootElement == null)
			return null;
		
		List<Element> moduleElements = getElmentsByXpath(getXPath_VarAll(), rootElement);
		if(ContainerUtil.isNull(moduleElements))
			return null;
		
		List<VarBO> vars = new LinkedList<VarBO> ();
		for (Element element : moduleElements) {
			VarBO var = convertElementToVar(element);

			if (bot == null || ContainerUtil.isNull(bot.getBotMap())) {
				vars.add(var);
				continue;
			}
			// 判断查询结果是否符合查询条件
			boolean inQuery = true;
			if (StringUtils.isNotEmpty((String) bot.getBotMap().get("name"))) {
				if (!StringUtils.hasSubString(var.getName(), bot.getBotMap().get("name").toString())) {
					inQuery = false;
				}
			}
			if (inQuery && StringUtils.isNotEmpty((String) bot.getBotMap().get("alias"))) {
				if (!StringUtils.hasSubString(var.getAlias(), bot.getBotMap().get("alias").toString())) {
					inQuery = false;
				}
			}

			if (inQuery)
				vars.add(var);
		}
		return vars;
	}

	@Override
	public void delete(List<BusinessComponent> vars) {
		Element rootElement = getRootElement();
		if(rootElement == null)
			return;
		
		for(BusinessComponent var : vars){
			Element element = getElmentsByXpath(getXPath_VarId(var.getId()), rootElement).get(0);
			rootElement.removeContent(element);
		}
		saveXML(rootElement);
	}


	@Override
	public VarBO getVar(long id) throws Exception {
		return getVarByXpath(getXPath_VarId(id));
	}

	/**
	 * 根据varName获取变量ID
	 * @param varName
	 * @return
	 * @throws Exception 
	 */
	public Long getVarId(String varName) throws Exception {
		Element el = getElementByXpath(getRootElement(), getXPath_VarName(varName));
		if(el == null)
			return null;
		
		return StringUtils.convertToLong(el.getAttributeValue(ATTR_ID));
	}
	
	@Override
	public List<VarBO> getVarByName(String varName) throws Exception {		
		List<Element> moduleElements = getElmentsByXpath(getXPath_VarName(varName), getRootElement());
		if(ContainerUtil.isNull(moduleElements))
			return null;
		
		List<VarBO> vars = new LinkedList<VarBO> ();
		for (Element element : moduleElements) {
			VarBO var = convertElementToVar(element);
			vars.add(var);
		}
		return vars;
	}
	
	
	@Override
	public List<VarBO> getVarByAlias(String alias) throws Exception {
		List<Element> moduleElements = getElmentsByXpath(getXPath_VarAll(), getRootElement());
		if(ContainerUtil.isNull(moduleElements))
			return null;
		
		List<VarBO> vars = new LinkedList<VarBO> ();
		for (Element element : moduleElements) {
			VarBO var = convertElementToVar(element);
			if (StringUtils.isEqual(var.getAlias(), alias)) {
				vars.add(var);
			}
		}
		return null;
	}

	@Override
	public void insert(VarBO var) {
		Element rootElement = getRootElement();
		if(rootElement == null)
			return;
		
		Element varElement = new Element(NODE_VAR);
		varElement.setAttribute(ATTR_ID, GuidUtil.getGUID() + "");
		varElement.setAttribute(ATTR_MODULE_ID, var.getModuleId());
		varElement.setAttribute(ATTR_NAME, var.getName());
		varElement.setAttribute(ATTR_ALIAS, var.getAlias());
		varElement.setAttribute(ATTR_SCOP, var.getScop());
		varElement.setAttribute(ATTR_ENABLE, var.getEnable());
		varElement.setAttribute(ATTR_VALUESET, var.getValueSetCode());
		varElement.setAttribute(ATTR_DEFVALUE, var.getDefValue());
		
		if(StringUtils.isNotEmpty(var.getCanmodify()))
			varElement.setAttribute(ATTR_CANMODIFY, var.getCanmodify());
		else
			varElement.removeAttribute(ATTR_CANMODIFY);
		
		if(StringUtils.isNotEmpty(var.getCandelete()))
			varElement.setAttribute(ATTR_CANDELETE, var.getCandelete());
		else
			varElement.removeAttribute(ATTR_CANDELETE);
		
		if(StringUtils.isNotEmpty(var.getRemark()))
			varElement.setAttribute(ATTR_REMARK, var.getRemark());
		else
			varElement.removeAttribute(ATTR_REMARK);
		
		rootElement.addContent(0, varElement);
		saveXML(rootElement);
	}
	
	/**
	 * 持久化xml文件
	 * @param rootElement
	 */
	private void saveXML(Element rootElement) {
		Document doc = rootElement.getDocument();
		//修改服务器端文件
		outPutDocToFile(doc, VarPathUtil.getClientVarPath());
		//修改工程中的文件
		outPutDocToFile(doc, VarPathUtil.getServerVarPath());
	}


	@Override
	public void update(VarBO var) {
		Element rootElement = getRootElement();
		if(rootElement == null)
			return;
		
		List<Element> elements = getElmentsByXpath(getXPath_VarId(var.getId()), rootElement);
		if(ContainerUtil.isNull(elements))
			return;
		
		Element varElement = elements.get(0);
		varElement.setAttribute(ATTR_NAME, var.getName());
		varElement.setAttribute(ATTR_ALIAS, var.getAlias());
		varElement.setAttribute(ATTR_ENABLE,var.getEnable());
		varElement.setAttribute(ATTR_SCOP, var.getScop());
		varElement.setAttribute(ATTR_CANMODIFY, var.getCanmodify());
		varElement.setAttribute(ATTR_CANDELETE, var.getCandelete());
		varElement.setAttribute(ATTR_REMARK, var.getRemark());
		varElement.setAttribute(ATTR_MODULE_ID, var.getModuleId());
		varElement.setAttribute(ATTR_VALUESET, var.getValueSetCode());
		varElement.setAttribute(ATTR_DEFVALUE, var.getDefValue());
		saveXML(rootElement);
	}
	
	/**
	 * 根据xpath查询
	 * @param xpath
	 * @return
	 * @throws Exception
	 */
	public VarBO getVarByXpath(String xpath) throws Exception{
		Element el = getElementByXpath(getRootElement(), xpath);
		if(el != null)
			return convertElementToVar(el);	
		else
			return null;
	}
	
	/**
	 * 转换element为变量
	 * @param element
	 * @return
	 */
	private VarBO convertElementToVar(Element element){
		VarBO var = new VarBO();
		var.setId(Long.parseLong(element.getAttributeValue(ATTR_ID)));
		var.setModuleId(element.getAttributeValue(ATTR_MODULE_ID));
		var.setName(element.getAttributeValue(ATTR_NAME));
		var.setAlias(element.getAttributeValue(ATTR_ALIAS));
		var.setScop(element.getAttributeValue(ATTR_SCOP));
		var.setEnable(element.getAttributeValue(ATTR_ENABLE));
		var.setCanmodify(element.getAttributeValue(ATTR_CANMODIFY));
		var.setCandelete(element.getAttributeValue(ATTR_CANDELETE));
		var.setRemark(element.getAttributeValue(ATTR_REMARK));
		var.setValueSetCode(element.getAttributeValue(ATTR_VALUESET));
		var.setDefValue(element.getAttributeValue(ATTR_DEFVALUE));
		
		Set<MdtValueSetBO> ValueSetBOSet = new HashSet<MdtValueSetBO>();
		String[] vsCodes = StringUtils.split(var.getValueSetCode(), ",");
		IMdtValueSetDao mdtValueSetDao = (IMdtValueSetDao)SpringConstant.getCTX().getBean("mdtValueSetDao");
		if(StringUtils.isNotEmpty(vsCodes)) {
			for(String vsCode : vsCodes) {
				List<MdtValueSetBO> valueSetList = mdtValueSetDao.getMdtValueSetByCodeAndName(vsCode, null);
				if(ContainerUtil.isNotNull(valueSetList))
					ValueSetBOSet.add(valueSetList.get(0));
			}
		}
		var.setValueSetBOSet(ValueSetBOSet);
		
		return var;
	}
	
	private String getXPath_VarId(long id){
		return "/" + ROOT + "/" + NODE_VAR + "[@" + ATTR_ID + "='" + id + "']";
	}
	
	private String getXPath_VarName(String varName){
		return "/" + ROOT + "/" + NODE_VAR + "[@" + ATTR_NAME + "='" + varName + "']";
	}
	
	/**
	 * 返回qeweb-var.xml的root节点
	 * @return
	 */
	private Element getRootElement(){
		return getRootElement(VarPathUtil.getClientVarPath(), VarPathUtil.getServerVarPath());
	}
	
	
	/**
	 * 查询条件(查询所有var节点)
	 * @return
	 */
	private String getXPath_VarAll() {
		return "/" + ROOT + "/" + NODE_VAR;
	}
}
