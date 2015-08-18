package cn.com.autoCreate.util;



public class Constants {
	
	/**
	 * �����ļ�����
	 */
	public static String log4jProp = "config/log4j.properties";

	public static String jdbcProp = "config/jdbc.properties";
	
	public static String hibernateProp = "config/hibernate.properties";
	//========================= ����Pojo����==============================
	/**
	 * hbm.xmlĿ¼����
	 */
	public static String  hbmxmlDir = "hibernate/hbm_xml/cn/com/projectName/moduleName/model/";
	//used for hbm.xml name setting,too
	public static String packageHead = "cn.com.";
	
	/**
	 * cfg.xmlĿ¼����
	 */
	public static String cfgxmlFile = "hibernate/hibernate.cfg.xml";
	
	/**
	 * javaĿ¼����
	 * used for .java dir setting
	 */
	public static String pojoDirHead = "/src/cn/com";
	public static String pojoAddDir = ".model.";
	
	//========================�ϲ�ѹ��js,css�ļ�����============================
	/**
	 * ѹ���ļ���
	 */
	public static String compressFileName = "buildCompress.xml";
	/**
	 * �ϲ��ļ�����
	 */
	public static String jsConcatFileName = "concat.js";
	public static String cssConcatFileName = "concat.css";
	/**
	 * ѹ���ļ���
	 */
	public static String jsMonifyFileName = "concat.min.js";
	public static String cssMonifyFileName = "*.min.css";
		
}
