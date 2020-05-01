package com.shuwen.kinship_calculator.service.impl;

import com.shuwen.kinship_calculator.bean.Kinship;
import com.shuwen.kinship_calculator.service.IKinshipService;
import com.shuwen.kinship_calculator.utils.KSCUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author pengshihao
 * @since 2019-10-21
 */
@Slf4j
@Service
public class KinshipServiceImpl implements IKinshipService {

    public List<String> callCalcultor(Kinship kinship){
        String relationShip=kinship.getRelationShip();
        Integer sex=kinship.getGender();
        String selectors = this.getSelectors(relationShip);
        List<String> result = new ArrayList<String>();							//匹配结果
        List<String> ids = this.selector2id(selectors,sex);
        for(int i=0;i<ids.size();i++){
            String id=ids.get(i);
            String items = this.getDataById(id);
            if(StringUtils.isNotBlank(items)){
                String[] tempList=items.split("-");
                for(int j=0;j<tempList.length;j++){
                    result.add(tempList[j]);
                }
            }else if(id.indexOf('w')==0||id.indexOf('h')==0){  //找不到关系，随爱人叫
                items = this.getDataById(id.substring(2));
                if(StringUtils.isNotBlank(items)){
                    String[] tempList=items.split("-");
                    for(int j=0;j<tempList.length;j++){
                        result.add(tempList[j]);
                    }
                }
            }
        }
        return this.unique(result);

    };


    /**
     * 功能描述: 分词解析
     * @Param: [str]
     * @Return: java.lang.String
     * @Author: pengshihao
     * @Date: 2019/10/21 10:27
     */
    private String getSelectors(String str){
        str = str.replaceAll("[二|三|四|五|六|七|八|九|十]{1,2}","x");
        String[] lists = str.replace("我","")
                .replace("家的","的")
                .replace("家","的")
                .split("的");
        String[] _data= KSCUtils._data;
        StringBuffer result =new StringBuffer();						//所有可能性
        for(int k=0;k<lists.length;k++){
            String name = lists[k];			//当前匹配词
            String arr ="";						//当前匹配词可能性
            for(int i=0;i< _data.length;i++){
                String value = _data[i];
                if(value.indexOf(name)>-1){		//是否存在该关系
                    String istr=value.split(":")[0].trim();
                    if(StringUtils.isNotBlank(istr)){		//对‘我’的优化
                        arr=istr;
                    }
                }
            }
            if(StringUtils.isNotBlank(arr)){
                result.append(","+arr);
            }
        }
        return result.toString();
    }


    /**
     * 功能描述: 简化选择器
     * @Param: [selector, sex]
     * @Return: java.lang.String
     * @Author: pengshihao
     * @Date: 2019/10/21 10:31
     */
    private List<String> selector2id(String selector,int sex){
        if(sex<0){	//如果自己的性别不确定
            if(selector.indexOf(",w")==0){
                sex = 1;
            }else if(selector.indexOf(",h")==0){
                sex = 0;
            }
        }
        if(sex>-1){
            selector = ","+sex+selector;
        }
        String pattern = ",[w0],w|,[h1],h";
        if(Pattern.matches(pattern,selector)){	//同志关系去除
            return null;
        }
        List<String> result=this.getId(selector);
        if(result==null){
            return null;
        }
        return this.unique(result);
    }


    /**
     * 功能描述: 获取节点Id
     * @Param: [selector]
     * @Return: java.lang.String
     * @Author: pengshihao
     * @Date: 2019/10/21 10:32
     */
    private List<String> getId(String selector){
        List<String> result=new ArrayList<String>();
        List<String> hash=new ArrayList<String>();
        return this.getIdHandle(result,hash,selector);
    }

    private List<String> getIdHandle(List<String> result,List<String> hash,String selector){
        String s="";
        if(!hash.contains(selector)){
            hash.add(selector);
            selector=selector.replace("'","");
            boolean status = true;
            do{
                s = selector;
                String[] _filter= KSCUtils._filter;
                for(int i=0;i<_filter.length;i++){
                    String item = _filter[i];
                    String exp=item.split("-")[0].split(":")[1];
                    String str=item.split("-")[1].split(":")[1];
                    // console.log('filter#',item['exp'],selector);
                    if(exp.startsWith("/")){
                        exp=exp.substring(1,exp.length());
                    }
                    /*if(exp.startsWith("^")){
                        exp=exp.substring(1,exp.length());
                    }*/
                    if(exp.endsWith(",")){
                        exp=exp.substring(0,exp.length()-1);
                    }
                    if(exp.endsWith("g")){
                        exp=exp.substring(0,exp.length()-1);
                    }
                    if(exp.endsWith("/")){
                        exp=exp.substring(0,exp.length()-1);
                    }
                    /*if(exp.endsWith("$")){
                        exp=exp.substring(0,exp.length()-1);
                    }*/
                    str=str.replace("'","");
                    selector = selector.replaceAll(exp,str);
                    if(selector.indexOf('#')>-1){
                        String[] arr = selector.split("#");
                        for(int j=0;j<arr.length;j++){
                            this.getIdHandle(result,hash,arr[j]);
                        }
                        status=false;
                        break;
                    }
                }
            }while(s!=selector);
            if(status){
                String pattern = ",[w0],w|,[h1],h";
                if(Pattern.matches(pattern,selector)){	//同志关系去除
                    return null;
                }
                selector = selector.replaceAll(",[01]",""); 	//去前面逗号和性别信息
                if(selector.length()>0){
                    selector=selector.substring(1);
                }
                result.add(selector);
            }
        }
        return result;
    }


    /**
     * 功能描述: 获取数据
     * @Param: [id]
     * @Return: java.lang.String
     * @Author: pengshihao
     * @Date: 2019/10/21 10:34
     */
    private String getDataById(String id){
        String items ="";
        String filter = "&[olx]";  //忽略属性
        String res=this.getData(id,"");
        if(StringUtils.isNotBlank(res)){  //直接匹配称呼
            items=res;
        }else{
            items = this.getData(id,filter);
            if(StringUtils.isBlank(items)){  //忽略年龄条件查找
                id = id.replaceAll("&[ol]","");
                items = this.getData(id,filter);
            }
            if(StringUtils.isBlank(items)){  //忽略年龄条件查找
                id = id.replace("[ol]","x");
                items = this.getData(id,filter);
            }
            if(StringUtils.isBlank(items)){  //缩小访问查找
                String l = id.replace("x","l");
                items = this.getData(l,filter);
                String o = id.replace("x","o");
                items=items.concat("-").concat(this.getData(o,filter));
            }
        }
        return items;
    }

    /**
     * 功能描述: 获取数据处理
     * @Param: [d, filter]
     * @Return: java.lang.String
     * @Author: pengshihao
     * @Date: 2019/10/21 10:35
     */
    public String getData(String d,String filter){
        String res="";
        String[] _data= KSCUtils._data;
        for(int i=0;i<_data.length;i++){
            String istr=_data[i].split(":")[0];
            istr=istr.replace("'","");
            String value=_data[i].split(":")[1];
            if(istr.replaceAll(filter,"").equals(d)){
                res=value;
            }
        }
        if(StringUtils.isNotBlank(res)){
            res=res.replace("[","");
            res=res.replace("]","");
            res=res.replace("'","");
            res=res.split(",")[0];
        }
        return res;
    };



    /**
     * 功能描述: 对特殊语法标识相互包含的行为去重
     * @Param: [arr]
     * @Return: java.lang.String
     * @Author: pengshihao
     * @Date: 2019/10/21 10:33
     */
    private List<String> unique(List<String> arr){
        List<String> result=new ArrayList<String>();
        if(arr.size()>0){
            for(int i=0;i<arr.size();i++){
                String temp = arr.get(i)
                            .replaceAll("[ol](?=s|b)","x")
                            .replaceAll("&[ol]","");
                if(!result.contains(temp)){
                    result.add(temp);
                }
            }
        }
        return result;
    };
}
