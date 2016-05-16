package com.boyaa.mf.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.math.BigDecimal;
import java.util.*;

public class CommonUtils {

    /**
     * 求两个数的百分比(商*100)
     *
     * @param v1
     * @param v2
     * @return
     */
    public static double getDoubleValue(int v1, int v2) {
        if (v2 == 0) {
            return 0d;
        }
        return new BigDecimal(v1)
                .divide(new BigDecimal(v2), 4, BigDecimal.ROUND_HALF_EVEN)
                .multiply(new BigDecimal(100)).doubleValue();
    }


    /**
     * @param xmlContent
     * @return
     * @throws DocumentException
     */
    public static List<Map<String, String>> readStringXmlOut(String xmlContent) throws DocumentException {

        List<Map<String, String>> out = new ArrayList<Map<String, String>>();
        Document doc = null;
        doc = DocumentHelper.parseText(xmlContent);
        Element rootElt = doc.getRootElement();
        // 获取根节点下的子节点
        Iterator iter = rootElt.elementIterator(); //properties
        while (iter.hasNext()) {
            Element property = (Element) iter.next();
            Map<String, String> propMap = new HashMap<String, String>();
            Iterator attrs = property.elementIterator();
            while (attrs.hasNext()) {
                Element attr = (Element) attrs.next();
                String attrName = attr.getName();
                String arrrVal = attr.getTextTrim();
                propMap.put(attrName, arrrVal);
            }
            out.add(propMap);
        }
        return out;
    }

    public static String parseModel2String(List<Map<String, String>> metaModels, String rootTag) {

        StringBuffer xmlString = new StringBuffer("<?xml version=\"1.0\" encoding=\"utf-8\" ?>" + "\n");
        xmlString.append("<" + rootTag + ">" + "\n");
        for (int i = 0; i < metaModels.size(); i++) {
            xmlString.append("\t<property>" + "\n");
            Map<String, String> map = metaModels.get(i);
            Set<String> keys = map.keySet();
            Iterator<String> it = keys.iterator();
            while (it.hasNext()) {
                String key = it.next();
                String value = map.get(key);
                xmlString.append("\t\t<" + key + ">").append(value).append("</" + key + ">" + "\n");
            }
            xmlString.append("\t</property>" + "\n");
        }
        xmlString.append("</" + rootTag + ">" + "\n");
        return xmlString.toString();
    }
}
