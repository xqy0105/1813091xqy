/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.doc_manage.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.common.utils.Constant;
import io.renren.common.xss.SQLFilter;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * 查询参数
 *
 * @author Mark sunlightcs@gmail.com
 */
public class DocPage<T>  extends Page<T>{
    private Map<String,Object> params;

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
@Override
public Map<Object,Object> condition(){
        Map p=params;
        return p;
}
    public DocPage(long current, long size) {
        super(current, size);
    }

    public IPage<T> getPage(Map<String, Object> params) {
        return this;
    }
    public IPage<T> getPage() {
        return this;
    }

}
