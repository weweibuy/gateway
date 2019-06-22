package com.weweibuy.gateway.common.utils;

import java.util.List;

/**
 * @author durenhao
 * @date 2019/5/18 22:33
 **/
public class ListUtil {

    public static boolean isEmpty(List list){
        return list == null || list.size() == 0 ? true : false;
    }


}
