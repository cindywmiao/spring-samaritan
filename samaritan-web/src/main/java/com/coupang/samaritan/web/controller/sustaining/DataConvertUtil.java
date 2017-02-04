package com.coupang.samaritan.web.controller.sustaining;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Created by changhua.wu on 12/15/16.
 */

@Slf4j
public class DataConvertUtil {


	final static ObjectMapper mapper = new ObjectMapper();

	public static String convertObjectToJson(Object object) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

		try {
			return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString( object );
		} catch (JsonProcessingException e) {
			log.error("Error on Serialize Object to Json, object= {}, detail exception:{}", object, e );
			throw new RuntimeException(e);
		}

	}


	public static <T> T convertJsonToObject(String json, Class<T> clazz){
		try {
			return mapper.readValue(json, clazz);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


}
