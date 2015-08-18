// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package cn.com.autoCreate.compressFile;

import cn.com.autoCreate.util.Constants;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.FontUIResource;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class CompressFile extends JFrame{

	private static Logger logger = Logger.getLogger(CompressFile.class.getName());
	private JFrame frame;
	private JPanel panel1,panel2,panel3;
	JPanel panel1_1,panel1_2;
	JPanel panel2_1,panel2_2;
	private JButton submitButton;
	private JButton cancelButton;
	private JButton jsButton,cssButton;
	private String[] jsFileNameList = null;
	private String[] cssFileNameList = null;
	private String jsFilePath = "";
	private String cssFilePath = "";
	private JLabel jsLabel1_1,jsLabel1_2,cssLabel2_1,cssLabel2_2;

	public CompressFile()
	{
		Font font = new Font("宋体", 1, 13);
		setSize(300, 200);
		InitGlobalFont(font);
		frame = new JFrame("CSS/JS合并压缩工具");
//		frame.setLayout(new GridLayout(2, 2));
//		comboBox = new JComboBox(planets1());
//		label = new JLabel("项目：");
//		submitButton = new JButton("提交");
//		cancleButton = new JButton("取消");
//		panel1 = new JPanel();
//		panel1.add(label);
//		panel1.add(comboBox);
//		comboBox.setEditable(true);
//		comboBox.setPrototypeDisplayValue("                   ");
//		panel2 = new JPanel();
//		panel2.add(submitButton);
//		panel2.add(cancleButton);
//		frame.add(panel1);
//		frame.add(panel2);
//		submitButton.addActionListener(this);
//		cancleButton.addActionListener(this);
//		frame.setSize(300, 125);
//		frame.setLocation(568, 280);
//		frame.setVisible(true);
		
		submitButton = new JButton("提交");
		cancelButton = new JButton("取消");
		panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER,70,50));
		panel3.add(submitButton);
		panel3.add(cancelButton);
		//frame.add(panel2);

		panel1 = new JPanel(new GridLayout(2, 1));
		panel2 = new JPanel(new GridLayout(2, 1));
		panel1_1 = new JPanel(null);
		panel1_2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel2_1 = new JPanel(null); 
		panel2_2 = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
		
		jsButton=new JButton("浏览");
		jsButton.setBounds(20, 15, 70, 28);
		panel1_1.add(jsButton);
		
		cssButton=new JButton("浏览");
		cssButton.setBounds(20, 15, 70, 28);
		panel2_1.add(cssButton);
		
		// label
		jsLabel1_1 = new JLabel("\b js路径：");
		jsLabel1_2 = new JLabel("");
		
		panel1_2.add(jsLabel1_1);			
		panel1_2.add(jsLabel1_2);
		
		cssLabel2_1 = new JLabel("\b css路径：");
		cssLabel2_2 = new JLabel("");
		
		panel2_2.add(cssLabel2_1);		
		panel2_2.add(cssLabel2_2);
		
		// add panel
		panel1.add(panel1_1);
		panel1.add(panel1_2);		
		panel2.add(panel2_1);
		panel2.add(panel2_2);		
		
		Container con = this.getContentPane();		
		con.add(panel1);
		con.add(panel2);
		con.add(panel3);
		
		jsButton.addActionListener(buttonListener);
		cssButton.addActionListener(buttonListener);
		submitButton.addActionListener(buttonListener);
	    cancelButton.addActionListener(buttonListener);
	    this.setTitle("CSS/JS合并压缩工具");
		this.setLocation(568, 280);
		this.setSize(300, 300);
		this.setLayout(new GridLayout(3, 2));
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent windowevent){
				System.exit(0);
			}
		});

	}

	ActionListener buttonListener = new ActionListener(){
		@Override
	    public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			if(obj == jsButton){
				
				  JFileChooser chooser = new JFileChooser(new File(System.getProperty("user.dir")));
				  FileNameExtensionFilter filter = new FileNameExtensionFilter(
				          "js文件","js");
				  chooser.setFileFilter(filter);
				  chooser.setMultiSelectionEnabled(true);
				  chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				  int returnVal = chooser.showOpenDialog(CompressFile.this);
				  if(returnVal == JFileChooser.APPROVE_OPTION) {
				        File[] dir = chooser.getSelectedFiles();
				        jsFileNameList = new String[dir.length];
				        String showNameStr = "";
						for(int i=0;i<dir.length;i++){
							showNameStr = showNameStr + "/" + (jsFileNameList[i] = dir[i].getName());
							String suffix = jsFileNameList[i].substring(jsFileNameList[i].lastIndexOf("."), jsFileNameList[i].length());
							if(!suffix.equalsIgnoreCase(".js")){
								JOptionPane.showMessageDialog(frame, "文件名格式错误，请重新选择！");
								jsFileNameList = null;
								showNameStr = "";
								break;
							}
							
						}
						jsFilePath = dir[0].getAbsolutePath();
						String mSeparator = File.separatorChar + "";
						jsFilePath = jsFilePath.substring(0, jsFilePath.lastIndexOf(mSeparator));
						jsLabel1_2.setText(showNameStr);
				   }
			}
			if(obj == cssButton){
				
				  JFileChooser chooser = new JFileChooser(new File(System.getProperty("user.dir")));
				  FileNameExtensionFilter filter = new FileNameExtensionFilter(
				          "css文件","css");
				  chooser.setFileFilter(filter);
				  chooser.setMultiSelectionEnabled(true);
				  chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				  int returnVal = chooser.showOpenDialog(CompressFile.this);
				  if(returnVal == JFileChooser.APPROVE_OPTION) {
				        File[] dir = chooser.getSelectedFiles();
				        cssFileNameList = new String[dir.length];
						for(int i=0;i<dir.length;i++){
							cssFileNameList[i] = dir[i].getName();
							String suffix = cssFileNameList[i].substring(cssFileNameList[i].lastIndexOf("."), cssFileNameList[i].length());
							if(!suffix.equalsIgnoreCase(".css")){
								JOptionPane.showMessageDialog(frame, "文件名格式错误，请重新选择！");
								cssFileNameList = null;
								break;
							}
						}
						cssFilePath = dir[0].getAbsolutePath();
						String mSeparator = File.separatorChar + "";
						cssFilePath = cssFilePath.substring(0, cssFilePath.lastIndexOf(mSeparator));
				   }
			}
			if(obj == submitButton){
								
				if (jsFileNameList != null || cssFileNameList != null ){
														
					modifyBuildXml();				
					System.exit(0);							
					
				} else {
					JOptionPane.showMessageDialog(frame, "您未选择js或css文件！");					
				}
			}
			if(obj == cancelButton){
				System.exit(0);
			}
		}
	};
	
	/**
	 * 将合并文件的配置写入xml
	 * @param filePath 文件路径
	 * @param fileNameList 文件名数组
	 * @param concatFileName 合并后文件名
	 */
	public void write2concat(Element eleTarget, String filePath,String[] fileNameList, String concatFileName){
		
		Element element1 = new Element("concat");
		element1.setAttribute("destfile", (new StringBuilder()).append(filePath + File.separator).append(concatFileName).toString());
		element1.setAttribute("encoding", "utf-8");
		element1.setAttribute("outputencoding", "utf-8");						
		for(int j=0;j<fileNameList.length;j++){
			Element element2 = new Element("path");
			element2.setAttribute("path", (new StringBuilder()).append(filePath + File.separator).append(fileNameList[j]).toString());
			element1.addContent(element2);
		}						
		eleTarget.addContent(element1);
	}
	
	public void modifyBuildXml(){
		
		logger.info(jsFilePath);
		if(jsFileNameList != null){
			for(int i=0;i<jsFileNameList.length;i++)
				logger.info(jsFileNameList[i]);
		}
		if(cssFileNameList != null){
			for(int i=0;i<cssFileNameList.length;i++)
				logger.info(cssFileNameList[i]);
		}
		SAXBuilder saxbuilder = new SAXBuilder();
		/**
		 * 打包后需要修改
		 */
//		String xmlPath = System.getProperty("user.dir");
//		File file = new File(xmlPath + "/build/" + "buildCompress.xml");
		File file = new File(Constants.compressFileName);
		if (file.exists()){
			
			Document document;
			try {
				document = saxbuilder.build(file);
				Element element = document.getRootElement();
				List list = element.getChildren("target");
				for (int i = 0; i < list.size(); i++){
					
					Element eleTarget = (Element)list.get(i);
					//合并js
					if ("concatJs".equalsIgnoreCase(eleTarget.getAttributeValue("name"))){						
						if(jsFileNameList != null)
							write2concat(eleTarget,jsFilePath,jsFileNameList,Constants.jsConcatFileName);

					}//合并css
					if ("concatCss".equalsIgnoreCase(eleTarget.getAttributeValue("name"))){
						if(cssFileNameList != null)
							write2concat(eleTarget,cssFilePath,cssFileNameList,Constants.cssConcatFileName);

					}
					// 压缩js文件
					if ((jsFileNameList != null) && ("uglify".equalsIgnoreCase(eleTarget.getAttributeValue("name")))){
						
						Element element1 = new Element("uglify");
						element1.setAttribute("verbose", "true");
						element1.setAttribute("output", (new StringBuilder()).append(jsFilePath + File.separator).append(Constants.jsMonifyFileName).toString());
						Element element2 = new Element("sources");
						element2.setAttribute("dir", jsFilePath);
						Element element3 = new Element("file");
						element3.setAttribute("name", Constants.jsConcatFileName);
						element2.addContent(element3);
						element1.addContent(element2);
						eleTarget.addContent(element1);
					}
					//压缩css文件
					/**
					 *  <apply executable="java" parallel="false" failonerror="true">  
					 *    <path path = ""/>  
					 *    <arg line="-jar"/>  
					 *    <arg path="${yuicompressor.path}" />  
					 *    <arg line="--charset utf-8" />  
					 *    <arg line="-o" />  
					 *    <targetfile/>  
					 *    <mapper type="glob" from="*.css" to="*.min.css" />  
					 *   </apply>
					 */
					if ((cssFileNameList != null) && ("minifyCss".equalsIgnoreCase(eleTarget.getAttributeValue("name")))){
						
						Element element1 = new Element("apply");
						element1.setAttribute("executable", "java");
						element1.setAttribute("parallel", "false");
						element1.setAttribute("failonerror", "true");
						
						Element element2 = new Element("path");
						element2.setAttribute("path", (new StringBuilder()).append(cssFilePath + File.separator).append("concat.css").toString());
						element1.addContent(element2);
						element2 = new Element("arg");
						element2.setAttribute("line","-jar");
						element1.addContent(element2);
						
						element2 = new Element("arg");
						element2.setAttribute("path","${yuicompressor.path}");
						element1.addContent(element2);
						
						element2 = new Element("arg");
						element2.setAttribute("line","--charset utf-8");
						element1.addContent(element2);
						
						element2 = new Element("arg");
						element2.setAttribute("line","-o");
						element1.addContent(element2);
						
						element2 = new Element("targetfile");						
						element1.addContent(element2);
						
						element2 = new Element("mapper");
						element2.setAttribute("type","glob");
						element2.setAttribute("from","*.css");
						element2.setAttribute("to",Constants.cssMonifyFileName);
						element1.addContent(element2);

						eleTarget.addContent(element1);
					}
					if ("delete".equalsIgnoreCase(eleTarget.getAttributeValue("name"))){
						
						Element eleDelete = null;
						if(jsFileNameList != null){
							eleDelete = new Element("delete");
							eleDelete.setAttribute("file", (new StringBuilder()).append(jsFilePath + File.separator).append(Constants.jsConcatFileName).toString());
							eleTarget.addContent(eleDelete);
						}		
						if(cssFileNameList != null){
							eleDelete = new Element("delete");
							eleDelete.setAttribute("file", (new StringBuilder()).append(cssFilePath + File.separator).append(Constants.cssConcatFileName).toString());
							eleTarget.addContent(eleDelete);
						}
					}
				}
				
				XMLOutputter xmloutputter = new XMLOutputter();
				xmloutputter.setFormat(Format.getPrettyFormat().setEncoding("UTF-8"));
//				xmloutputter.output(document, new FileOutputStream(xmlPath + "/build/" + "buildCompress.xml"));
				xmloutputter.output(document, new FileOutputStream(Constants.compressFileName));
				
			} catch (JDOMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private static void InitGlobalFont(Font font)
	{
		FontUIResource fontuiresource = new FontUIResource(font);
		Enumeration enumeration = UIManager.getDefaults().keys();
		do
		{
			if (!enumeration.hasMoreElements())
				break;
			Object obj = enumeration.nextElement();
			Object obj1 = UIManager.get(obj);
			if (obj1 instanceof FontUIResource)
				UIManager.put(obj, fontuiresource);
		} while (true);
	}
	
	//main test
	public static void main(String[] args){
		
		PropertyConfigurator.configure(Constants.log4jProp);
		new CompressFile();
	}
}
