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
		PreparedStatement pre = null;// ����Ԥ����������һ�㶼�������������Statement
        Connection conn = null;
        try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}//����oracle������
        /**
         *  ���ݿ����ӣ�oracle�������ӵ���oracle���ݿ⣻
         *  thin:@MyDbComputerNameOrIP����������ݿ����ڵ�IP��ַ�����Ա���thin:����
         *  1521�����������ݿ�Ķ˿ںţ�
         *  ORCL����������ݿ�����
         */
        String url = "jdbc:oracle:thin:@172.16.23.116:1521:orcl";
        //String url = "jdbc:oracle:thin:@MyDbComputerNameOrIP:1521:ORCL";
        String UserName = "venuspb";// ���ݿ��û���½�� ( Ҳ��˵�� schema ���ֵ� )
        String Password = "sandi";// ����
        ResultSet result = null;
        try {
			conn = DriverManager.getConnection(url, UserName, Password);
	        String sql = "select * from user_tables";// Ԥ������䣬�������������
	        pre = conn.prepareStatement(sql);// ʵ����Ԥ�������
	        result = pre.executeQuery();// ִ�в�ѯ��ע�������в���Ҫ�ټӲ���
	        int jj = 0;
	        while (result.next()){
	        	System.out.println(result.getString("TABLE_NAME"));
	        	jj++;
	        }
	       
	        System.out.println("-----------" + jj);
	        String sql2 = "select * from AGENT";// Ԥ������䣬�������������
	        pre = conn.prepareStatement(sql2);// ʵ����Ԥ�������
	        result = pre.executeQuery();// ִ�в�ѯ��ע�������в���Ҫ�ټӲ���
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
//		/** ���Ϊnull�ʹ���һ���µ�ʵ��������*/ 
//	    if(dbConn==null){ 
//	    	dbConn = new DBConnection(); 
//	    } 
//	    dbConn.closeConnection(conn);/** ���ùر����ӵķ���*/ 
//	} 
}


