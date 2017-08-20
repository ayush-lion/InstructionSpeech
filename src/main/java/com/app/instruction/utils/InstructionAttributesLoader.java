/**
 * 
 */
package com.app.instruction.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import com.app.instruction.panel.exception.InstructionException;



/**
 * Class is responsible to load Abacus attributes to Map.
 * 
 * @author prashant.joshi (198joshi@gmail.com)
 * @version 03-Aug-2017
 */
public class InstructionAttributesLoader {
	
	private static Properties prop;
	
	static {
		prop = new Properties();
	}
	
	/**
	 * Loading resource file
	 */
	private static Properties getPropertyHandleFromResource(String propertiesFileName) throws InstructionException {
		try {
			InputStream inputStream = InstructionAttributesLoader.class.getClassLoader().getResourceAsStream(propertiesFileName);
			prop.load(inputStream);
		} catch (IOException e) {
			throw (new InstructionException("Not able to read the attributes file!!!"));
		}
		return prop;
	}
	
	/**
	 * Loading resource file
	 */
	private static Properties getPropertyHandleFromFile(String propertiesFileName) throws InstructionException {
		try {
			File file = new File(propertiesFileName);
			if(file.exists() && file.isFile()) {
				FileReader fr = new FileReader(file);
				prop.load(fr);
			} else {
				throw (new InstructionException("Abacus Attributes file not present!!!"));
			}
		} catch (IOException e) {
			throw (new InstructionException("Not able to read the attributes file!!!"));
		}
		return prop;
	}
	
	public static Map<String, String> getAllPropertiesFromFile(String propertiesFileName) throws InstructionException {
		Map<String, String> propertiesMap = new LinkedHashMap<String, String>();
		Properties prop = InstructionAttributesLoader.getPropertyHandleFromFile(propertiesFileName);
		Iterator<Object> it = prop.keySet().iterator();
		while(it.hasNext()) {
			String key = (String) it.next();
			String value = prop.getProperty(key);
			propertiesMap.put(key, value);
		}
		return propertiesMap;
	}
	
	public static Map<String, String> getAllPropertiesFromResource(String propertiesFileName) throws InstructionException {
		Map<String, String> propertiesMap = new LinkedHashMap<String, String>();
		Properties prop = InstructionAttributesLoader.getPropertyHandleFromResource(propertiesFileName);
		Iterator<Object> it = prop.keySet().iterator();
		while(it.hasNext()) {
			String key = (String) it.next();
			String value = prop.getProperty(key);
			propertiesMap.put(key, value);
		}
		return propertiesMap;
	}

}
