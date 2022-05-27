package io.renren.common.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

@Component
public class FreeMarkerTemplateUtil {

    /** 数据类型为{@value} .*/
    public static final String UTF_8 = "UTF-8";

    /**
     * 构造方法.
     */
    private FreeMarkerTemplateUtil() {
    }

    /**
     * @param dataMap 传入的数据
     * @param valueName 存储名
     * @return file
     */
    public static byte[] createExcel(final Map<?, ?> dataMap,
                                     final String valueName) throws IOException, TemplateException {
        final Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        configuration.setDefaultEncoding(UTF_8);
        configuration.setClassForTemplateLoading(FreeMarkerTemplateUtil.class, "/ftl");
        final Template template = configuration.getTemplate(valueName);
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final Writer w = new OutputStreamWriter(outputStream, UTF_8);
        template.process(dataMap, w);
        w.close();
        return outputStream.toByteArray();

    }

}