// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package cn.com.autoCreate.compressFile;

import java.io.*;
import java.util.List;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class CleanFile
{

	public CleanFile()
	{
	}

	public void cleanBuildCompressXml(){
		
		try{
			SAXBuilder saxbuilder = new SAXBuilder();
			File file = new File("buildCompress.xml");
			if (file.exists())
			{
				Document document = saxbuilder.build(file);
				Element element = document.getRootElement();
				List list = element.getChildren("target");
				for (int i = 0; i < list.size(); i++){
					
					Element element1 = (Element)list.get(i);
					element1.removeContent();
				}

				XMLOutputter xmloutputter = new XMLOutputter();
				xmloutputter.setFormat(Format.getPrettyFormat().setEncoding("UTF-8"));
				xmloutputter.output(document, new FileOutputStream("buildCompress.xml"));
				
			} else{
				System.out.println("没有找到对应配置文件！");
			}
		}
		catch (IOException ioexception){			
			ioexception.printStackTrace();
		} catch (JDOMException jdomexception){			
			jdomexception.printStackTrace();
		}
	}
}
