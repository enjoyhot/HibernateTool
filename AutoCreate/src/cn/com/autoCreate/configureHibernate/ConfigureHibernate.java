package cn.com.autoCreate.configureHibernate;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import cn.com.autoCreate.publicClass.DirectortyFilter;
import cn.com.autoCreate.util.Constants;

@SuppressWarnings("serial")
public class ConfigureHibernate extends JFrame implements ActionListener{
	
	private static Logger logger = Logger.getLogger(ConfigureHibernate.class.getName());
	private JFrame frame;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JPanel panel4;
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JButton submitButton;
	private JButton cancleButton;
	private JComboBox comboBox1;
	private JComboBox comboBox2;
	private JTextField textField;
	private static String projectName;
	private static String moduleName;
	private static String dbTables;

	public ConfigureHibernate(){
		
		Font font = new Font("宋体", 1, 13);
		setSize(300, 200);
		InitGlobalFont(font);
		frame = new JFrame("实体类创建工具");
		frame.setLayout(new GridLayout(4, 2));
		comboBox1 = new JComboBox(planets1());
		comboBox2 = new JComboBox(planets2(comboBox1.getSelectedItem()));
		textField = new JTextField(22);
		label1 = new JLabel("项目：");
		label2 = new JLabel("模块：");
		label3 = new JLabel("表名：");
		submitButton = new JButton("提交");
		cancleButton = new JButton("取消");
		panel1 = new JPanel();
		panel1.add(label1);
		panel1.add(comboBox1);
		comboBox1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent actionevent){
				
				comboBox2.removeAllItems();
				Object obj = comboBox1.getSelectedItem();
				Object aobj[] = planets2(obj);
				for (int i = 0; i < aobj.length; i++)
					comboBox2.addItem(aobj[i]);
			}
		});
		comboBox1.setEditable(true);
		comboBox1.setPrototypeDisplayValue("                   ");
		panel2 = new JPanel();
		panel2.add(label2);
		panel2.add(comboBox2);
		comboBox2.setEditable(true);
		comboBox2.setPrototypeDisplayValue("                   ");
		panel3 = new JPanel();
		panel3.add(label3);
		panel3.add(textField);
		panel4 = new JPanel();
		panel4.add(submitButton);
		panel4.add(cancleButton);
		frame.add(panel1);
		frame.add(panel2);
		frame.add(panel3);
		frame.add(panel4);
		submitButton.addActionListener(this);
		cancleButton.addActionListener(this);
		frame.setSize(300, 185);
		frame.setLocation(568, 280);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent windowevent){
				cleanCfgXml();
				System.exit(0);
			}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent actionevent) {
		// TODO Auto-generated method stub
		Object obj = actionevent.getSource();
		if (obj == submitButton)
		{
			if (!"".equalsIgnoreCase(((String)comboBox1.getSelectedItem()).replaceAll(" ", "")) && !"".equalsIgnoreCase(((String)comboBox2.getSelectedItem()).replaceAll(" ", "")) && !"".equalsIgnoreCase(textField.getText().replaceAll(" ", "")))
			{
				projectName = ((String)comboBox1.getSelectedItem()).replaceAll(" ", "");
				moduleName = ((String)comboBox2.getSelectedItem()).replaceAll(" ", "");
				dbTables = textField.getText().replaceAll(" ", "");
				submitButton.setEnabled(false);
				modifyCfgXml();
				modifyHbmXml(dbTables);			
				System.exit(0);
			} else
			{
				JOptionPane.showMessageDialog(frame, "项目和模块、数据库表不能为空！");
			}
		} else{
			cleanCfgXml();
			System.exit(0);
		}
	}

	/**
	 * 为cfgxml添加mapping
	 */
	public void modifyCfgXml(){
		try{
			String s = "";
			s = dbTables.replaceAll("_", "");
			String as[] = s.split(",");
			SAXBuilder saxbuilder = new SAXBuilder();
			saxbuilder.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			File file = new File(Constants.cfgxmlFile);
			Document document = saxbuilder.build(file);
			Element element = document.getRootElement();
			Element element1 = element.getChild("session-factory");
			List list = element1.getChildren("mapping");
			if (list != null)
			{
				for (int i = 0; i < list.size(); i++)
				{
					Element element2 = (Element)list.get(i);
					String s1 = ((Element)list.get(i)).getAttributeValue("resource");
					int j = 0;
					for (int k = 0; k < as.length; k++)
						if (!as[k].equalsIgnoreCase(s1.substring(0, s1.indexOf(".hbm.xml"))))
							j++;

					if (j == as.length)
					{
						element1.removeContent(element2);
						i--;
					} else
					{
						element2.setAttribute("resource", (new StringBuilder()).append(Constants.hbmxmlDir).append(s1).toString());
					}
				}

			}
			XMLOutputter xmloutputter = new XMLOutputter();
			xmloutputter.setFormat(Format.getPrettyFormat().setEncoding("UTF-8"));
			xmloutputter.output(document, new FileOutputStream(Constants.cfgxmlFile));
		}
		catch (IOException ioexception)
		{
			ioexception.printStackTrace();
		}
		catch (JDOMException jdomexception)
		{
			jdomexception.printStackTrace();
		}
	}
	
	/**
	 * 为hbmxml添加属性
	 * @param s hbmxml文件路径
	 */
	public void modifyHbmXml(String s){
		try{
			s = s.toLowerCase();
			String as[] = s.split("_");
			StringBuffer stringbuffer = new StringBuffer("");
			for (int i = 0; i < as.length; i++)
				as[i] = (new StringBuilder()).append(as[i].substring(0, 1).toUpperCase()).append(as[i].substring(1)).toString();

			for (int j = 0; j < as.length; j++)
				stringbuffer.append(as[j]);

			s = stringbuffer.toString();
			String as1[] = s.split(",");
			String s1 = Constants.hbmxmlDir;
			for (int k = 0; k < as1.length; k++){
				
				as1[k] = (new StringBuilder()).append(as1[k].substring(0, 1).toUpperCase()).append(as1[k].substring(1, as1[k].length())).toString();
				SAXBuilder saxbuilder = new SAXBuilder();
				saxbuilder.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);				
				File file = new File((new StringBuilder()).append(s1).append(as1[k]).append(".hbm.xml").toString());
				if (file.exists()){
					
					//设置java文件类名
					Document document = saxbuilder.build(file);
					Element element = document.getRootElement();
					Element element1 = element.getChild("class");
					element1.setAttribute("name", (new StringBuilder()).append(Constants.packageHead).append(projectName).append(".").append(moduleName).append(Constants.pojoAddDir).append(as1[k]).toString());					
					element1.removeAttribute("catalog");
					//写入文件
					XMLOutputter xmloutputter = new XMLOutputter();
					xmloutputter.setFormat(Format.getPrettyFormat().setEncoding("UTF-8"));
					xmloutputter.output(document, new FileOutputStream((new StringBuilder()).append(s1).append(as1[k]).append(".hbm.xml").toString()));				
				} else{
					System.out.println("The configuration file is not found!");
				}
			}

		}
		catch (IOException ioexception){
			ioexception.printStackTrace();
		}
		catch (JDOMException jdomexception){
			jdomexception.printStackTrace();
		}
	}

	public void cleanCfgXml(){
		
		try{
			SAXBuilder saxbuilder = new SAXBuilder();
			saxbuilder.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			File file = new File(Constants.cfgxmlFile);
			Document document = saxbuilder.build(file);
			Element element = document.getRootElement();
			Element element1 = element.getChild("session-factory");
			List list = element1.getChildren("mapping");
			if (list != null)
				element1.removeChildren("mapping");
			XMLOutputter xmloutputter = new XMLOutputter();
			xmloutputter.setFormat(Format.getPrettyFormat().setEncoding("UTF-8"));
			xmloutputter.output(document, new FileOutputStream(Constants.cfgxmlFile));
		}
		catch (IOException ioexception)
		{
			ioexception.printStackTrace();
		}
		catch (JDOMException jdomexception)
		{
			jdomexception.printStackTrace();
		}
	}

	private static void InitGlobalFont(Font font){
		
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

	private static String findProjects(String s)
	{
		//src/cn/com/sandi -> return [cc,...]
		//src/cn/com/sandi/cc -> return [agent,...]
		String s1 = "";
		File file = new File(s);
		File afile[] = file.listFiles(new DirectortyFilter());
		if (afile != null)
		{
			for (int i = 0; i < afile.length; i++)
			{
				File file1 = afile[i];
				s1 = (new StringBuilder()).append(s1).append(file1.getName()).append(",").toString();
			}

		}

		return s1;
	}

	private Object[] planets1()
	{
		String s = System.getProperty("user.dir").replaceAll("\\\\", "/");
		s = s.substring(0, s.lastIndexOf("/"));
		s = (new StringBuilder()).append(s).append(Constants.pojoDirHead).toString();
		String as[] = findProjects(s).split(",");
		return as;
	}

	private Object[] planets2(Object obj)
	{
		String s = System.getProperty("user.dir").replaceAll("\\\\", "/");
		s = s.substring(0, s.lastIndexOf("/"));
		s = (new StringBuilder()).append(s).append(Constants.pojoDirHead).toString();
		String as[] = findProjects((new StringBuilder()).append(s).append("/").append(obj).toString()).split(",");
		return as;
	}
	
}
