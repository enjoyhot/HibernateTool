// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package cn.com.autoCreate.configurePojo;

import java.io.*;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import cn.com.autoCreate.util.Constants;

public class ConfigurePojo
{

	private static Logger logger = Logger.getLogger(ConfigurePojo.class.getName());
	public ConfigurePojo()
	{
	}

	public String readCfgXml()
	{
		String s = "";
		try
		{
			SAXBuilder saxbuilder = new SAXBuilder();
			saxbuilder.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			File file = new File(Constants.cfgxmlFile);			
			Document document = saxbuilder.build(file);
			Element element = document.getRootElement();
			Element element1 = element.getChild("session-factory");
			List list = element1.getChildren("mapping");
			for (int i = 0; i < list.size(); i++)
			{
				String s1 = ((Element)list.get(i)).getAttributeValue("resource").toString();
				s = (new StringBuilder()).append(s).append(s1.substring(s1.lastIndexOf("/") + 1, s1.indexOf(".hbm.xml"))).append(",").toString();
			}

		}
		catch (IOException ioexception)
		{
			ioexception.printStackTrace();
		}
		catch (JDOMException jdomexception)
		{
			jdomexception.printStackTrace();
		}
		return s;
	}

	public String findPojoPathByHbmXml(String s)
	{
		String s1 = "";
		try
		{
			String as[] = s.split(",");
			String s2 = Constants.hbmxmlDir;
			for (int i = 0; i < as.length; i++)
			{
				SAXBuilder saxbuilder = new SAXBuilder();
				saxbuilder.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
				File file = new File((new StringBuilder()).append(s2).append(as[i]).append(".hbm.xml").toString());
				if (file.exists())
				{
					Document document = saxbuilder.build(file);
					Element element = document.getRootElement();
					Element element1 = element.getChild("class");
					String s3 = element1.getAttributeValue("name");
					String s4 = s3.replaceAll("\\.", "/");
					s4 = (new StringBuilder()).append("src/").append(s4).append(".java").toString();
					s1 = (new StringBuilder()).append(s1).append(s4).append(",").toString();
				} else
				{
					System.out.println("The configuration file is not found!");
					s1 = null;
				}
			}

		}
		catch (IOException ioexception)
		{
			ioexception.printStackTrace();
		}
		catch (JDOMException jdomexception)
		{
			jdomexception.printStackTrace();
		}
		return s1;
	}

	public void configPojo(String s)
	{
		logger.info(s);
		String s1;
		String as[];
		s1 = System.getProperty("user.dir");
		s1 = s1.replaceAll("\\\\", "/");
		s1 = s1.substring(0, s1.lastIndexOf("/"));
		as = s.split(",");
		int i = 0;
		
		while(true){
			
		if (i >= as.length)
			break;
		File file = new File((new StringBuilder()).append(s1).append("/").append(as[i]).toString());
		if (file.exists())
		{
			String s2 = readFromFile(file);
			int j = s2.indexOf("@Table(name=\"") + "@Table(name=\"".length();
			int k = s2.indexOf("\"", s2.indexOf("@Table(name=\"") + "@Table(name=\"".length());
			String s3 = s2.substring(j, k);
			String s4 = s3.toLowerCase();
			String as1[] = s4.split("_");
			StringBuffer stringbuffer = new StringBuffer("");
			for (int l = 0; l < as1.length; l++)
				as1[l] = (new StringBuilder()).append(as1[l].substring(0, 1).toUpperCase()).append(as1[l].substring(1)).toString();

			for (int i1 = 0; i1 < as1.length; i1++)
				stringbuffer.append(as1[i1]);

			s4 = stringbuffer.toString();
			s4 = (new StringBuilder()).append(s4.substring(0, 1).toUpperCase()).append(s4.substring(1, s4.length())).toString();
			StringBuilder stringbuilder = new StringBuilder(s2);
			stringbuilder.insert(stringbuilder.indexOf("import"), "import javax.persistence.GeneratedValue;\nimport javax.persistence.GenerationType;\nimport javax.persistence.TableGenerator;\n");
			stringbuilder.insert(stringbuilder.indexOf("public", stringbuilder.indexOf("@Id")), (new StringBuilder()).append("@GeneratedValue(strategy = GenerationType.TABLE, generator = \"").append(s4).append("Generator\")").append("\n\t").append("@TableGenerator(name = \"").append(s4).append("Generator\", ").append("\n\t\t").append("table = \"TB_GENERATOR\", ").append("\n\t\t").append("pkColumnName = \"GEN_NAME\", ").append("\n\t\t").append("valueColumnName = \"GEN_VALUE\", ").append("\n\t\t").append("pkColumnValue = \"").append(s3).append("_PK\", ").append("\n\t\t").append("allocationSize = 1").append("\n\t").append(")").append("\n\t").toString());
			BufferedWriter bufferedwriter;
			try {
				bufferedwriter = new BufferedWriter(new FileWriter((new StringBuilder()).append(s1).append("/").append(as[i]).toString()));
				bufferedwriter.write(stringbuilder.toString());
				bufferedwriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else{
			System.out.println("The entity class is not found!");
			return;
		}
			i++;
		}

	}

	public static String readFromFile(File file)
	{
		StringBuilder stringbuilder;
		BufferedReader bufferedreader;
		try {
			bufferedreader = new BufferedReader(new FileReader(file));
			stringbuilder = new StringBuilder();
			String s;
			while ((s = bufferedreader.readLine()) != null) 
				stringbuilder.append((new StringBuilder()).append(s).append("\n").toString());
			bufferedreader.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block			
			e1.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return stringbuilder.toString();
		
	}
}
