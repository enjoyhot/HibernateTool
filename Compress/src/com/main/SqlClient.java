package com.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;



public class SqlClient {
	public static void main(String[] args){
		/**
		SAXBuilder saxbuilder = new SAXBuilder();
		saxbuilder.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		File file = new File("hibernate/hibernate.cfg.xml");
		Document document;
		try {
			document = saxbuilder.build(file);
			Element element = document.getRootElement();
			Element element1 = element.getChild("session-factory");
			List list = element1.getChildren("mapping");
			System.out.println(list.size());
			if (list != null)
				element1.removeChildren("mapping");
			XMLOutputter xmloutputter = new XMLOutputter();
			xmloutputter.setFormat(Format.getPrettyFormat().setEncoding("UTF-8"));
			xmloutputter.output(document, new FileOutputStream("hibernate/hibernate.cfg.xml"));
		} catch (JDOMException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		**/
		
		//Statement a = null
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
        Connection conn = null;
        try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}//加入oracle的驱动
        /**
         *  数据库连接，oracle代表链接的是oracle数据库；
         *  thin:@MyDbComputerNameOrIP代表的是数据库所在的IP地址（可以保留thin:）；
         *  1521代表链接数据库的端口号；
         *  ORCL代表的是数据库名称
         */
        String url = "jdbc:oracle:thin:@172.16.23.116:1521:orcl";
        //String url = "jdbc:oracle:thin:@MyDbComputerNameOrIP:1521:ORCL";
        String UserName = "venuspb";// 数据库用户登陆名 ( 也有说是 schema 名字的 )
        String Password = "sandi";// 密码
        ResultSet result = null;
        try {
			conn = DriverManager.getConnection(url, UserName, Password);
	        String sql = "select * from user_tables";// 预编译语句，“？”代表参数
	        pre = conn.prepareStatement(sql);// 实例化预编译语句
	        result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
	        int jj = 0;
	        while (result.next()){
	        	System.out.println(result.getString("TABLE_NAME"));
	        	jj++;
	        }
	       
	        System.out.println("-----------" + jj);
	        String sql2 = "select * from AGENT";// 预编译语句，“？”代表参数
	        pre = conn.prepareStatement(sql2);// 实例化预编译语句
	        result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
	        int i = 0;	        
	        while (result.next()){
	        	String firstStr = "";
	        	String rowStr = "";
	        	rowStr = firstStr + 0;
	        	rowStr = rowStr + (++i);
	        	ResultSetMetaData md = result.getMetaData(); 	        	
	            for(int j=1; j<=md.getColumnCount(); j++){ 
	            	firstStr = firstStr + "|" + md.getColumnName(j); 
	            	rowStr = rowStr + " | " + result.getString(j);   
	            } 	   
	            System.out.println(firstStr);
	        	System.out.println(rowStr);
	        	break;
	        }
	        
	        System.out.println("-----------");
            ResultSetMetaData md = result.getMetaData();  
            for(int j=1; j<=md.getColumnCount(); j++){   
                System.out.println(md.getColumnName(j));   
            } 
	        conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
//	public void closeConnection(Connection conn){ 
//		/** 如果为null就创建一个新的实例化对象*/ 
//	    if(dbConn==null){ 
//	    	dbConn = new DBConnection(); 
//	    } 
//	    dbConn.closeConnection(conn);/** 调用关闭连接的方法*/ 
//	} 
}


