package cn.com.autoCreate.configureHibernate;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadAndWriteProperties
{

	public ReadAndWriteProperties()
	{
	}

	public String readProperties(String s, String s1)
	{
		Properties properties;
		BufferedInputStream bufferedinputstream;
		properties = new Properties();
		bufferedinputstream = null;
		String s2;
		try {
			bufferedinputstream = new BufferedInputStream(new FileInputStream(s));
			properties.load(bufferedinputstream);
			bufferedinputstream.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s2 = properties.getProperty(s1);		
		return s2;


	}

	public void writeProperties(String s, String s1, String s2)
	{
		Properties properties;
		FileInputStream fileinputstream;
		FileOutputStream fileoutputstream;
		properties = new Properties();
		fileinputstream = null;
		fileoutputstream = null;
		try {
			fileinputstream = new FileInputStream(s);
			properties.load(fileinputstream);
			fileinputstream.close();
			fileoutputstream = new FileOutputStream(s);
			properties.setProperty(s1, s2);
			properties.store(fileoutputstream, (new StringBuilder()).append("Update '").append(s1).append("' value").toString());
			fileoutputstream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}
}