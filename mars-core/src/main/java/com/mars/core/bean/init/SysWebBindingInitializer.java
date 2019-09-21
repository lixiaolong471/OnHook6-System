package com.mars.core.bean.init;

import com.mars.core.bean.init.editor.SysDateEditor;
import com.mars.core.bean.init.editor.SysTimestampEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by lixl on 2017/5/15.
 */
public class SysWebBindingInitializer implements WebBindingInitializer {

    @Override
    public void initBinder(WebDataBinder binder, WebRequest request) {
        binder.registerCustomEditor(Date.class,new SysDateEditor());
        binder.registerCustomEditor(Timestamp.class,new SysTimestampEditor());
    }
}
