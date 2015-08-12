// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package cn.com.autoCreate;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import cn.com.autoCreate.configurePojo.ConfigurePojo;

public class ConfigurePojoAnnotate
{
	private static Logger logger = Logger.getLogger(ConfigurePojoAnnotate.class.getName());

	public ConfigurePojoAnnotate()
	{
	}

	public static void main(String args[])
	{
		PropertyConfigurator.configure("config/log4j.properties");
		ConfigurePojo configurepojo = new ConfigurePojo();
		if (configurepojo.findPojoPathByHbmXml(configurepojo.readCfgXml()) != null)			
			configurepojo.configPojo(configurepojo.findPojoPathByHbmXml(configurepojo.readCfgXml()));
	}
}
