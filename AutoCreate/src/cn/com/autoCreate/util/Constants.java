package cn.com.autoCreate.util;



public class Constants {
	
	/**
	 * 配置文件声明
	 */
	public static String log4jProp = "config/log4j.properties";

	public static String jdbcProp = "config/jdbc.properties";
	
	public static String hibernateProp = "config/hibernate.properties";
	//========================= 创建Pojo配置==============================
	/**
	 * hbm.xml目录声明
	 */
	public static String  hbmxmlDir = "hibernate/hbm_xml/cn/com/projectName/moduleName/model/";
	//used for hbm.xml name setting,too
	public static String packageHead = "cn.com.";
	
	/**
	 * cfg.xml目录声明
	 */
	public static String cfgxmlFile = "hibernate/hibernate.cfg.xml";
	
	/**
	 * java目录声明
	 * used for .java dir setting
	 */
	public static String pojoDirHead = "/src/cn/com";
	public static String pojoAddDir = ".model.";
	
	//========================合并压缩js,css文件配置============================
	/**
	 * 压缩文件名
	 */
	public static String compressFileName = "buildCompress.xml";
	/**
	 * 合并文件命名
	 */
	public static String jsConcatFileName = "concat.js";
	public static String cssConcatFileName = "concat.css";
	/**
	 * 压缩文件名
	 */
	public static String jsMonifyFileName = "concat.min.js";
	public static String cssMonifyFileName = "*.min.css";
		
}
