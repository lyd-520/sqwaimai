//package com.roy.sqwaimai.app.config;
//
//import com.alibaba.fastjson.parser.ParserConfig;
//import com.alibaba.fastjson.serializer.SerializeConfig;
//import com.alibaba.fastjson.serializer.SerializerFeature;
//import com.alibaba.fastjson.serializer.ToStringSerializer;
//import com.alibaba.fastjson.serializer.ValueFilter;
//import com.alibaba.fastjson.support.config.FastJsonConfig;
//import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//
//import javax.swing.text.html.parser.Parser;
//import java.math.BigInteger;
//import java.nio.charset.Charset;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * fastjson配置类
// */
//@Configuration("defaultFastjsonConfig")
//@ConditionalOnClass(com.alibaba.fastjson.JSON.class)
//@ConditionalOnMissingBean(FastJsonHttpMessageConverter.class)
//@ConditionalOnWebApplication
//public class DefaultFastjsonConfig {
//
//    @Bean
//    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
//        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
//        converter.setFastJsonConfig(fastjsonConfig());
//        converter.setSupportedMediaTypes(getSupportedMediaType());
//        return converter;
//    }
//
//    /**
//     * fastjson的配置
//     */
//    public FastJsonConfig fastjsonConfig() {
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        fastJsonConfig.setSerializerFeatures(
//                SerializerFeature.PrettyFormat,
//                SerializerFeature.WriteMapNullValue,
//                SerializerFeature.WriteEnumUsingToString,
//                SerializerFeature.DisableCircularReferenceDetect
//        );
//        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
//        ValueFilter valueFilter = new ValueFilter() {
//            @Override
//            public Object process(Object o, String s, Object o1) {
//                if (null == o1) {
//                    o1 = "";
//                }
//                return o1;
//            }
//        };
//        fastJsonConfig.setCharset(Charset.forName("utf-8"));
//        fastJsonConfig.setSerializeFilters(valueFilter);
//        ParserConfig globalInstance = ParserConfig.getGlobalInstance();
//        globalInstance.setAutoTypeSupport(false);
//        globalInstance.addAccept("org.bson.types.ObjectId");
//        fastJsonConfig.setParserConfig(globalInstance);
//        //解决Long转json精度丢失的问题
//        SerializeConfig serializeConfig = SerializeConfig.globalInstance;
//        serializeConfig.put(BigInteger.class, ToStringSerializer.instance);
//        serializeConfig.put(Long.class, ToStringSerializer.instance);
//        serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
//        fastJsonConfig.setSerializeConfig(serializeConfig);
//        return fastJsonConfig;
//    }
//
//    /**
//     * 支持的mediaType类型
//     */
//    public List<MediaType> getSupportedMediaType() {
//        ArrayList<MediaType> mediaTypes = new ArrayList<>();
//        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
//        return mediaTypes;
//    }
//
//}
