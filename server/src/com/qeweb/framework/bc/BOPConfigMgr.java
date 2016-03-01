package com.qeweb.framework.bc;

import com.qeweb.framework.base.ia.IXmlDao;
import com.qeweb.framework.base.impl.XmlDaoImpl;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.utils.StringUtils;
import org.apache.commons.io.FileUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Created by wangdg on 2016/2/17.
 */
public class BOPConfigMgr {

    private Map<String, Map<String, List<String>>> configs;

    private static final BOPConfigMgr instance = new BOPConfigMgr();

    private static final String BOP_DIR = "bopconfig";

    private BOPConfigMgr(){
        this.init();
    }

    public static BOPConfigMgr getInstance(){
        return instance;
    }

    private void init(){
        configs = new HashMap<String, Map<String, List<String>>>();
        LinkedHashMap<String, List<String>> config;
        List<String> object;

        String name;
        String className;
        String param;

        String fileDirName = Envir.getClassesPath() + File.separator + BOP_DIR;
        IXmlDao xmlDao = new XmlDaoImpl();
        try {
            File fileDir = ResourceUtils.getFile(fileDirName);
            if(fileDir.exists() && fileDir.isDirectory()){
                for(String fileName : fileDir.list()){
                    String filePath = fileDirName + File.separator + fileName;
                    Document doc = xmlDao.buildDomFromFile(filePath);
                    config = new LinkedHashMap<String, List<String>>();
                    for(Element element : (List<Element>)doc.getRootElement().getChildren()){
                        name = element.getAttributeValue("name");
                        className = element.getAttributeValue("class");
                        param = element.getAttributeValue("param");

                        object = new ArrayList<String>();
                        object.add(className);
                        if(!StringUtils.isEmpty(param)){
                            object.add(param);
                        }

                        config.put(name, object);
                    }
                    int pos = fileName.indexOf(".xml");
                    if(pos != -1) {
                        configs.put(fileName.substring(0, pos), config);
                    }
                }
            }else{

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, List<String>> getConfig(String className){

        Map<String, List<String>> config = this.configs.get(className);
        if(config == null){
            config = this.configs.get(className.substring(className.lastIndexOf('.'), className.length()));
        }

        return config;
    }

    public void reset(){
        this.init();
    }

}
