package cn.com.autoCreate;

import org.apache.log4j.PropertyConfigurator;

import cn.com.autoCreate.configureHibernate.ConfigureHibernate;
import cn.com.autoCreate.util.Constants;

public class ConfigureHbmCfg
{

	public ConfigureHbmCfg()
	{
	}

	public static void main(String args[])
	{
		PropertyConfigurator.configure(Constants.log4jProp);
		ConfigureHibernate configurehibernate = new ConfigureHibernate();
	}
}
