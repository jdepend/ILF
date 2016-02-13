package com.qeweb.framework.common.dataisland;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.prop.*;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.bc.sysbop.mobilebop.LocationBOP;
import com.qeweb.framework.bc.sysbop.mobilebop.PictureBOP;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.constant.ConstantAppProp;
import com.qeweb.framework.common.constant.ConstantBOMethod;
import com.qeweb.framework.common.constant.ConstantDataIsland;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.pageflow.ContextUtil;
import com.qeweb.framework.common.utils.*;
import com.qeweb.framework.manager.DisplayType;
import com.qeweb.framework.pal.Page;
import com.qeweb.framework.pal.coarsegrained.*;
import com.qeweb.framework.pal.control.CommandButton;
import com.qeweb.framework.pal.decorativeview.ContainerRelationGroup;
import com.qeweb.framework.pal.finegrained.FinegrainedComponent;
import com.qeweb.framework.pal.finegrained.enumcomp.MutiValueHelper;
import com.qeweb.framework.pal.finegrained.other.Blank;
import com.qeweb.framework.pal.finegrained.other.FileField;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * JSON格式的数据岛
 */
public class JSONDataIsland extends DataIsland{

    @Override
    public String createConRelationIsland(ContainerRelationGroup containerRelationGroup) {
        return null;
    }

    @Override
    public String createDataIsland(Page page) {
        //设置令牌,以防止重复提交
        ContextUtil.setTokenTicket();
        StringBuilder dataIsland = new StringBuilder();
        JSONObject rootJson= new JSONObject();

       // XMLDataIslandHelper.appendHead(dataIsland);
        rootJson.put(SESSION_TICKET, ContextUtil.getSessionTicket());
        rootJson.put(TOKEN_TICKET, ContextUtil.getTokenTicket());
        rootJson.put(SOURCEPAGE, Envir.getRequestURI());


        if(page.getBc() != null) {
            JSONObject pageJson= new JSONObject();


            pageJson.put(BIND, page.getBcId());
            pageJson.put(BO_OPERATIONSTATUS, STATEMACHINE_INIT);
            pageJson.put(PAGE_ONLOAD, page.getOnLoad());
            pageJson.put(TIPS_TYPE, DisplayType.getTipsType());
            if(StringUtils.isEqual(ConstantAppProp.TIPS_TYPE_SIMPLE, DisplayType.getTipsType())
                    && NumberUtils.isNumber(DisplayType.getShowTipDelay())){
                pageJson.put(TIPS_DELAY, DisplayType.getShowTipDelay());
            }
            else if(StringUtils.isEqual(ConstantAppProp.TIPS_TYPE_POPUP, DisplayType.getTipsType())){
                pageJson.put(TIPS_DISPLAY, DisplayType.getShowTipDisplay());
            }
            pageJson.put(CONFIRM_DISPLAY, DisplayType.getConfirmDisplay());
            createOperateDataIsland(page.getCommandList(), page.getBc(), pageJson);
            rootJson.put(PAGE,pageJson);
        }
        else {
            JSONObject pageJson= new JSONObject();
            pageJson.put(PAGE_ONLOAD, page.getOnLoad());
            pageJson.put(TIPS_TYPE, DisplayType.getTipsType());
            if(StringUtils.isEqual(ConstantAppProp.TIPS_TYPE_SIMPLE, DisplayType.getTipsType())
                    && NumberUtils.isNumber(DisplayType.getShowTipDelay())){
                pageJson.put(TIPS_DELAY, DisplayType.getShowTipDelay());
            }
            else if(StringUtils.isEqual(ConstantAppProp.TIPS_TYPE_POPUP, DisplayType.getTipsType())){
                pageJson.put(TIPS_DISPLAY, DisplayType.getShowTipDisplay());
            }
            pageJson.put(CONFIRM_DISPLAY, DisplayType.getConfirmDisplay());
            rootJson.put(PAGE,pageJson);
        }

        getDataIslandContent(page.getContainerList(), Envir.getRequestURI(),rootJson);

        //XMLDataIslandHelper.appendTail(dataIsland);
        System.out.println(rootJson);
        return rootJson.toString();
    }


    private void createOperateDataIsland(Collection<CommandButton> btns, BusinessObject bo, JSONObject jsonObject) {
        //page 下的命令 构造
        JSONArray cmdArray = new JSONArray();
        jsonObject.put(OPERATE, cmdArray);
        for(CommandButton btn :btns){
            JSONObject cmdJson = new JSONObject();

            cmdArray.add(cmdJson);
        }
    }

    private void getDataIslandContent(List<Container> containerList, String sourcePage, JSONObject jsonObject) {
        JSONArray cnArray = new JSONArray();

        for (Container container : containerList){
            if(container instanceof Form){
                JSONObject formJson = new JSONObject();
                createFormDataIsland(container, formJson);
                cnArray.add(formJson);
            }
//            else if(container instanceof Table){
//                dataIsland.append(createTableDataIsland((Table)container, sourcePage));
//            }
//            else if(container instanceof Tree){
//                dataIsland.append(createTreeDataIsland((Tree)container));
//            }
//            else if(container instanceof Tab){
//                dataIsland.append(createTabDataIsland((Tab)container, sourcePage));
//            }
        }
        jsonObject.put("container", cnArray);
    }

    public String createFormDataIsland(Container container,JSONObject jsonObject) {
        //XMLDataIslandHelper.appendStartTag(sbr, BO);
        Map<String, FinegrainedComponent> fcList = container.getFcList();
//        if(StringUtils.isEmpty(null))
            jsonObject.put(BO_ID, container.getId());
//        else{
//            XMLDataIslandHelper.appendAttr(sbr, BO_ID, hiddenContainerId);
//            fcList = getHiddenFcList((Table) container, hiddenContainerId);
//        }
        jsonObject.put(BIND, container.getBcId());
        jsonObject.put(BO_OPERATIONSTATUS, STATEMACHINE_INIT);
//        XMLDataIslandHelper.appendEndTag(sbr);
        JSONArray bopArray = new JSONArray();

        for(FinegrainedComponent fc : fcList.values()) {
            JSONObject bopJson = new JSONObject();

            BOProperty bop =  fc.getBc();
            BOProperty realBOP = BoOperateUtil.getRealBop(bop);
            //占位标签并不绑定bop
            if(fc instanceof Blank || bop == null)
                continue;
            //XMLDataIslandHelper.appendStartTag(sbr, BOP);
            bopJson.put(BIND, fc.getBcId());
            bopJson.put(GROUPNAME, fc.getGroupName());
            //XMLDataIslandHelper.appendAttr(sbr, BOP_ISTIGGER, "true", realBOP.isTiggerCRelation());
            bopJson.put(BOP_ISTIGGER, realBOP.isTiggerCRelation());
            if(fc.isEnableBopRelation() && realBOP.hasBOPRelation()) {
                bopJson.put(BOP_ISRELATE, "true");
                bopJson.put(BOP_CLASS, realBOP.getClass().getName());
            }
            //XMLDataIslandHelper.appendAttr(sbr, BOP_ISCONRELATE, "true", realBOP.hasBORelation());
            bopJson.put(BOP_ISCONRELATE, realBOP.hasBORelation());
            bopJson.put(BOP_JS, realBOP.getDesirousMethod());
            //终端bop标识
            if(realBOP instanceof LocationBOP){
                bopJson.put(BOP_TERMINAL_LOCATION, "true");
            }
            else if(realBOP instanceof PictureBOP){
                bopJson.put(BOP_TERMINAL_PIC, "true");
            }

            if(fc instanceof FileField) {
                bopJson.put(BOP_ISFILE, "true");
                String operate = ((FileField) fc).getOperate();
                if(StringUtils.isNotEmpty(operate))
                    bopJson.put(OPERATE, operate);
            }
            //XMLDataIslandHelper.appendEndTag(sbr);
            appendValue(fc,bopJson);
            //appendPdropertiesForSpecialFC(sbr, fc);
            appendStatus(fc,bopJson);
            appendRange(fc,bopJson);
//            XMLDataIslandHelper.appendEndTag(sbr, BOP);
//            //超链接按钮添加operate节点
//            if(container instanceof Form && realBOP instanceof OperateBOP) {
//                XMLDataIslandHelper.appendStartTag(sbr, OPERATE);
//                XMLDataIslandHelper.appendAttr(sbr, OPERATE_ID, VCUtil.createOperateName(container.getId(), container.getBcId(), fc.getBcId()));
//                XMLDataIslandHelper.appendAttr(sbr, OPERATE_HASMSG, "true");
//                OperateBOP optBop = (OperateBOP) realBOP;
//                XMLDataIslandHelper.appendAttr(sbr, OPERATE_METHOD, optBop.getOperate());
//                if(!optBop.isSubmit())
//                    XMLDataIslandHelper.appendAttr(sbr, OPERATE_NOTSUBMIT, "true");
//                XMLDataIslandHelper.appendSingleEndTag(sbr);
//            }
            bopArray.add(bopJson);
        }
        jsonObject.put("bop",bopArray);
//
//        if(isHidden) {
//            XMLDataIslandHelper.appendStartTag(sbr, OPERATE);
//            XMLDataIslandHelper.appendAttr(sbr, OPERATE_ID, VCUtil.createFinegrainedID(container.getName(), ConstantBOMethod.BO_INIT));
//            XMLDataIslandHelper.appendAttr(sbr, OPERATE_METHOD, ConstantBOMethod.BO_INIT);
//            XMLDataIslandHelper.appendAttr(sbr, OPERATE_TEXT, AppLocalization.getLocalization("form.save"));
//            XMLDataIslandHelper.appendAttr(sbr, OPERATE_HASMSG, "true");
//            XMLDataIslandHelper.appendSingleEndTag(sbr);
//        }
//        else {
//            createOperateDataIsland(container, sbr);
//        }
//
//        XMLDataIslandHelper.appendEndTag(sbr, BO);
        return "";
       // return null;
    }

    private void appendValue( FinegrainedComponent fc,JSONObject jsonObject) {
        Value value = fc.getBc().getValue();
        String display = ((value.getClass().getName() != Value.class.getName()) ?
                "display='"+ XMLUtil.encode(value.getDisplayValue()) + "'" : "");
        JSONObject valueJson = new JSONObject();

//        if (fc.isHasExpend()) {
//            dataIsland.append("<value expend='min' " + display + ">");
//            XMLDataIslandHelper.appendContent(dataIsland, value.getValue());
//            dataIsland.append("</value>");
//            dataIsland.append("<value expend='max' " + display + ">");
//            XMLDataIslandHelper.appendContent(dataIsland, value.getValue());
//            dataIsland.append("</value>");
//        }
//        else
        valueJson.put("display",display);
        if (value instanceof MutiValue) {
//            dataIsland.append("<value " + display + ">");
            Set<String> keys = MutiValueHelper.getChecked(fc.getBc()).keySet();
            String muti = "";
            for(String key : keys) {
                muti += key + ",";
            }
            muti = StringUtils.removeEnd(muti);
            valueJson.put("real",muti);
           // XMLDataIslandHelper.appendContent(dataIsland, muti);
            //dataIsland.append(muti);
            //dataIsland.append("</value>");
        }
        else {
            valueJson.put("real",value.getValue());
//            XMLDataIslandHelper.appendContent(dataIsland, value.getValue());
//            dataIsland.append("</value>");
        }
        jsonObject.put("value",valueJson);
    }

    private void appendStatus(FinegrainedComponent fc,JSONObject jsonObject) {
        JSONObject statusJson = new JSONObject();

        BOProperty bop = fc.getBc();
        if(bop != null){
            Status status = bop.getStatus();
            //XMLDataIslandHelper.appendStartTag(dataIsland, STATUS);
            if(status.isHidden())
                statusJson.put(STATUS_HIDDEN, "true");
            if(status.isReadonly())
                statusJson.put(STATUS_READONLY, "true");
            if(status.isDisable())
                statusJson.put(STATUS_DISABLE, "true");
            //XMLDataIslandHelper.appendSingleEndTag(dataIsland);
        }
        jsonObject.put(STATUS,statusJson);
    }

    private void appendRange(FinegrainedComponent fc,JSONObject jsonObject) {
        JSONObject rangeJson = new JSONObject();

        BOProperty bop = fc.getBc();
        if(bop == null)
            return;

        BCRange bopRange = fc.isQueryRange() ? bop.getQueryRange() : bop.getRange();
        if(bopRange == null)
            return;

        //XMLDataIslandHelper.appendStartTag(dataIsland, BOP_RANGE);
        if(bopRange.isRequired())
            rangeJson.put(BOP_RANGE_REQUIRED, "true");
        if(bopRange.getMinLength() != -1)
        rangeJson.put(BOP_RANGE_MINLENGTH, bopRange.getMinLength() + "");
        if(bopRange.getMaxLength() != -1)
        rangeJson.put(BOP_RANGE_MAXLENGTH, bopRange.getMaxLength() + "");
        //XMLDataIslandHelper.appendEndTag(dataIsland);
        JSONArray rangArray = new JSONArray();

        if(ContainerUtil.isNotNull(bopRange.getRangeList())){
            for (Range range : bopRange.getRangeList()) {
                JSONObject rangeItemJson = new JSONObject();

                if (range instanceof EnumRange){
                    rangeItemJson.put("type","Enum");
                    rangeItemJson.put("rule",bopRange.rangeLogic(range));
                    JSONArray enumItemArray = new JSONArray();
                    Map<String, String> result = ((EnumRange) range).getResult();
                    for (String key : result.keySet()) {
                        JSONObject enumItemJson = new JSONObject();
                        enumItemJson.put("value",key);
                        enumItemJson.put("label",StringUtils.filter(result.get(key)));
                        enumItemArray.add(enumItemJson);
                    }
                    rangeItemJson.put("itemlist",enumItemArray);
                    //appendEnumRange(dataIsland, bopRange, range);
                }
//                else if (range instanceof SequenceRange)
//                    appendSequenceRange(dataIsland, bopRange, range);
//                else if (range instanceof LogicRange)
//                    appendLogicRange(dataIsland, bopRange, range);
                rangArray.add(rangeItemJson);
            }
        }
        rangeJson.put("ranglist",rangArray);
        jsonObject.put(BOP_RANGE,rangeJson);
       // XMLDataIslandHelper.appendEndTag(dataIsland, BOP_RANGE);
    }
    @Override
    public String createFormDataIsland(Container container) {
        return null;
    }

    @Override
    public String createHiddenFormDataIsland(Table table, String hiddenFormId) {
        return null;
    }

    @Override
    public String createTableDataIsland(String containerName, com.qeweb.framework.common.Page page, String dataIsland, String sourcePage) {
        return null;
    }

    @Override
    public String createTableDataIsland(String containerName, BusinessObject bo, String dataIsland, String sourcePage) {
        return null;
    }

    @Override
    public String createTableDataIsland(Table table, String sourcePage) {
        return null;
    }

    @Override
    public String createTreeDataIsland(Tree tree) {
        return null;
    }

    @Override
    public String createTabDataIsland(Tab tab, String sourcePage) {
        return null;
    }

    @Override
    public BOProperty revertBOPById(String finegrainedId, String dataIsland) {
        return null;
    }

    @Override
    public List<BusinessObject> revertBOList(String dataisland_boId, String dataIsland) {
        return null;
    }

    @Override
    public BOTemplate createBOTemplate(String containerId, String dataIsland) {
        return null;
    }

    @Override
    public String updateFRelationDataIsland(String dataIsland, Map<String, BusinessComponent> bopList) {
        return null;
    }

    @Override
    public String updateFCRelationDataIsland(String dataIsland, Map<String, Object> resultMap) {
        return null;
    }

    @Override
    public String updateCRelationDataIsland(String dataIsland, Map<String, Object> boList, String relations, String sourcePage) {
        return null;
    }

    @Override
    public BusinessObject revertRelationBO(String dataIsland) {
        return null;
    }

    @Override
    public void revertBop(String dataisland_boId, String dataIsland, BusinessObject bo) {

    }

    @Override
    public String updateContainer(String dataIsland, String boId, String boBind, Object data) {
        return null;
    }

    @Override
    public String updatePageOperate(String dataIsland, String boBind, BusinessObject bo) {
        return null;
    }
}
