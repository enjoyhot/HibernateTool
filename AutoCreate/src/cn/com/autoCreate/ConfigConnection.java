package cn.com.autoCreate;


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import cn.com.autoCreate.configureHibernate.ReadAndWriteProperties;
import cn.com.autoCreate.util.Constants;


public class ConfigConnection
{	

	public ConfigConnection()
	{			
	}
	private static Logger logger = Logger.getLogger(ConfigConnection.class.getName());

	public static void main(String args[])
	{
		PropertyConfigurator.configure(Constants.log4jProp);
		ReadAndWriteProperties readandwriteproperties = new ReadAndWriteProperties();		
		readandwriteproperties.writeProperties(Constants.hibernateProp, "hibernate.dialect", readandwriteproperties.readProperties(Constants.jdbcProp, "hibernate.dialect"));
		readandwriteproperties.writeProperties(Constants.hibernateProp, "hibernate.connection.driver_class", readandwriteproperties.readProperties(Constants.jdbcProp, "jdbc.driverClassName"));
		readandwriteproperties.writeProperties(Constants.hibernateProp, "hibernate.connection.url", readandwriteproperties.readProperties(Constants.jdbcProp, "jdbc.url"));
		readandwriteproperties.writeProperties(Constants.hibernateProp, "hibernate.default_schema", readandwriteproperties.readProperties(Constants.jdbcProp, "jdbc.defaultSchema"));
		readandwriteproperties.writeProperties(Constants.hibernateProp, "hibernate.connection.username", readandwriteproperties.readProperties(Constants.jdbcProp, "jdbc.username"));
		readandwriteproperties.writeProperties(Constants.hibernateProp, "hibernate.connection.password", readandwriteproperties.readProperties(Constants.jdbcProp, "jdbc.password"));
	}
}
