package properties;

import java.beans.XMLDecoder;

public class PropertiesLoader {
	private static PropertiesLoader instance;
	private Properties properties;
	
	public Properties getProperties() {
		return properties;
	}
	
	public PropertiesLoader() 
	{
		XMLDecoder decoder = new XMLDecoder(getClass().getClassLoader().getResourceAsStream(("resources/Properties.xml")));
		properties = (Properties)decoder.readObject();
		decoder.close();
	}
	
	public PropertiesLoader(String fileName) 
	{
		XMLDecoder decoder = new XMLDecoder(getClass().getClassLoader().getResourceAsStream(fileName));
		properties = (Properties)decoder.readObject();
		decoder.close();
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
