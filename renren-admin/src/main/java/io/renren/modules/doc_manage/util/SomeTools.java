package io.renren.modules.doc_manage.util;

import java.util.ArrayList;
import java.util.Map;

public class SomeTools {
    public static void delnullcondtion(Map<String,Object> params){
        if(params==null){
            return;
        }

        ArrayList<String> keys=new ArrayList();
        params.forEach((k,v)->{
            if(v==null || v.toString().equals("")){
                keys.add(k);
            }
        });
        keys.forEach(e->{
            params.remove(e);
        });
    }
}
