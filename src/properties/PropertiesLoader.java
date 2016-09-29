package properties;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PropertiesLoader {
	private static PropertiesLoader instance;
	private Properties properties;
	
	public Properties getProperties() {
		return properties;
	}
	
	public PropertiesLoader() 
	{
		try {
			XMLDecoder decoder = new XMLDecoder(new FileInputStream("Properties.xml"));
			properties = (Properties)decoder.readObject();
			decoder.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public PropertiesLoader(String fileName) 
	{
		try {
			XMLDecoder decoder = new XMLDecoder(new FileInputStream(fileName));
			properties = (Properties)decoder.readObject();
			decoder.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static PropertiesLoader getInstance() {
		if (instance == null) 
			instance = new PropertiesLoader();
		return instance;
	}

	public static PropertiesLoader getNewInstance(String fileName) { 
			instance = new PropertiesLoader(fileName);
		return instance;
	}
}
