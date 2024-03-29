package com.ecjtu.hht.booksmate.common.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ObjectMappingCustomer  extends ObjectMapper {

	 public ObjectMappingCustomer() {  
	        super();  
//	      // 允许单引号  
//	      this.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);  
//	      // 字段和值都加引号  
//	      this.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);  
//	      // 数字也加引号  
//	      this.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);  
//	      this.configure(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS, true);  
	        // 空值处理为空串  
	        this.getSerializerProvider().setNullValueSerializer(  
	                new JsonSerializer<Object>() {
	  
	                    @Override  
	                    public void serialize(Object value, JsonGenerator jg,
	                            SerializerProvider sp) throws IOException, JsonProcessingException {
	                        jg.writeString("");  
	                    }  
	                });  
	  
	    }  
	
	
}
