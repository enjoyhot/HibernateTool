// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package cn.com.autoCreate.compressFile;

import cn.com.autoCreate.publicClass.DirectortyFilter;
import cn.com.autoCreate.publicClass.FileFilter;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class CompressFile extends JFrame implements ActionListener{

	private static Logger logger = Logger.getLogger(CompressFile.class.getName());
	private JFrame frame;
	private JPanel panel1;
	private JPanel panel2;
	private JLabel label;
	private JButton submitButton;
	private JButton cancleButton;
	private JComboBox comboBox;
	private static String projectName;
	private static String moduleName;

	public CompressFile()
	{
		Font font = new Font("宋体", 1, 13);
		setSize(300, 200);
		InitGlobalFont(font);
		frame = new JFrame("CSS/JS合并压缩工具");
		frame.setLayout(new GridLayout(2, 2));
		comboBox = new JComboBox(planets1());
		label = new JLabel("项目：");
		submitButton = new JButton("提交");
		cancleButton = new JButton("取消");
		panel1 = new JPanel();
		panel1.add(label);
		panel1.add(comboBox);
		comboBox.setEditable(true);
		comboBox.setPrototypeDisplayValue("                   ");
		panel2 = new JPanel();
		panel2.add(submitButton);
		panel2.add(cancleButton);
		frame.add(panel1);
		frame.add(panel2);
		submitButton.addActionListener(this);
		cancleButton.addActionListener(this);
		frame.setSize(300, 125);
		frame.setLocation(568, 280);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent windowevent)
			{
				System.exit(0);
			}
		});
	}

	public void actionPerformed(ActionEvent actionevent)
	{
		Object obj = actionevent.getSource();
		if (obj == submitButton)
		{
			if (!"".equalsIgnoreCase(((String)comboBox.getSelectedItem()).replaceAll(" ", "")))
			{
				projectName = ((String)comboBox.getSelectedItem()).replaceAll(" ", "");
				submitButton.setEnabled(false);
				String s = System.getProperty("user.dir");
				s = s.replaceAll("\\\\", "/");
				s = s.substring(0, s.lastIndexOf("/"));
				String as[] = planets2(projectName);
				if (as.length <= 1)
					as = planets3(projectName);
label0:
				for (int i = 0; i < as.length; i++)
				{
					String fileNameSplit = findJspFiles((new StringBuilder()).append(s).append("/WebContent/").append(projectName).append("/").append(as[i]).toString());
					logger.info((new StringBuilder()).append(s).append("/WebContent/").append(projectName).append("/").append(as[i]).toString());
					moduleName = as[i];
					if (fileNameSplit.length() > 0)
					{					
						String as1[] = fileNameSplit.split(",");
						int j = 0;
						do
						{
							if (j >= as1.length)
								continue label0;
							String s3 = (new StringBuilder()).append(s).append("/WebContent/").append(projectName).append("/").append(as[i]).append("/").append(as1[j]).toString();
							logger.info(s3);
							String s4 = findCssLinks(s3);
							String jsContent = findJsScripts(s3);
							logger.info(jsContent.isEmpty() + jsContent);
							if (!s4.isEmpty())
								modifyBuildCssXml_cp(s4, as1[j]);
							if (!jsContent.isEmpty())
								modifyBuildJsXml_cp(jsContent, as1[j]);
							j++;
						} while (true);
					}
					String s2 = findJspFiles((new StringBuilder()).append(s.substring(0, s.lastIndexOf("/"))).append("/Resource/WebContent/").append(projectName).append("/").append(as[i]).toString());
					if (s2.length() > 0)
					{
						String as2[] = s2.split(",");
						int k = 0;
						do
						{
							if (k >= as2.length)
								continue label0;
							String s5 = (new StringBuilder()).append(s.substring(0, s.lastIndexOf("/"))).append("/Resource/WebContent/").append(projectName).append("/").append(as[i]).append("/").append(as2[k]).toString();
							String s7 = findCssLinks(s5);
							String s8 = findJsScripts(s5);
							logger.info(s7.isEmpty() + "" + s8.isEmpty());
							if (!s7.isEmpty())
								modifyBuildCssXml_rp(s7, as2[k]);
							if (!s8.isEmpty())
								modifyBuildJsXml_rp(s8, as2[k]);
							k++;
						} while (true);
					}
					System.out.println((new StringBuilder()).append("The catalog: ").append(projectName).append("/").append(as[i]).append(" does not exists file!").toString());
				}

				System.exit(0);
			} else
			{
				JOptionPane.showMessageDialog(frame, "项目名不能为空！");
			}
		} else
		{
			System.exit(0);
		}
	}

	public static String readFromFile(File file)
	{
		StringBuilder stringbuilder;
		BufferedReader bufferedreader;
		try {
			bufferedreader = new BufferedReader(new FileReader(file));
			stringbuilder = new StringBuilder();
			String s = "";
			while ((s = bufferedreader.readLine()) != null) 
				stringbuilder.append(s);
			bufferedreader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		logger.info(stringbuilder.toString());
		return stringbuilder.toString();

	}

	public static String findCssLinks(String s)
	{
		String s1 = "";
		File file = new File(s);
		if (file.exists())
		{
			String s2 = readFromFile(file);
			String s3 = "<!-- css start -->";
			String s4 = "<!-- css end -->";
			String s5 = "href=\"";
			String s6 = "\"";
			if (!s2.isEmpty())
			{
				int i = s2.indexOf(s3);
				int j = s2.indexOf(s4);
				if (i != -1 && j != -1)
					s2 = s2.substring(i + s3.length(), j);
				else
					return s1;
			}
			while (!s2.isEmpty()) 
			{
				Pattern pattern = Pattern.compile("href");
				Matcher matcher = pattern.matcher(s2);
				if (matcher.find())
				{
					int k = s2.indexOf(s5) + s5.length();
					int l = s2.indexOf(s6, s2.indexOf(s5) + s5.length());
					String s7 = s2.substring(k, l);
					String s8 = "${pageContext.request.contextPath}";
					String s9 = "${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.themeName}";
					int i1 = s7.indexOf(s8);
					int j1 = s7.indexOf(s9);
					if (i1 == -1)
					{
						s1 = (new StringBuilder()).append(s1).append("../WebContent/").append(projectName).append("/").append(moduleName).append("/").append(s7).append(",").toString();
					} else
					{
						if (i1 != -1)
							s7 = s7.replaceAll("\\$\\{pageContext\\.request\\.contextPath\\}/", "");
						if (j1 != -1)
							s7 = s7.replaceAll("\\$\\{sessionScope\\.SPRING_SECURITY_CONTEXT\\.authentication\\.principal\\.themeName\\}", "default");
						s1 = (new StringBuilder()).append(s1).append("../../Resource/WebContent/").append(s7).append(",").toString();
					}
					s2 = s2.substring(l);
				} else
				{
					s2 = "";
				}
			}
		}
		return s1;
	}

	public static String findJsScripts(String s)
	{
		String s1 = "";
		File file = new File(s);
		if (file.exists())
		{
			String s2 = readFromFile(file);
			String s3 = "<!-- js start -->";
			String s4 = "<!-- js end -->";
			String s5 = "src=\"";
			String s6 = "\"";
			if (!s2.isEmpty())
			{
				int i = s2.indexOf(s3);
				int j = s2.indexOf(s4);
				if (i != -1 && j != -1)
					s2 = s2.substring(i + s3.length(), j);
				else
					return s1;
			}
			while (!s2.isEmpty()) 
			{
				Pattern pattern = Pattern.compile("src");
				Matcher matcher = pattern.matcher(s2);
				if (matcher.find())
				{
					int k = s2.indexOf(s5) + s5.length();
					int l = s2.indexOf(s6, s2.indexOf(s5) + s5.length());
					String s7 = s2.substring(k, l);
					int i1 = s7.indexOf("?");
					if (i1 != -1)
						s7 = s7.substring(0, i1);
					String s8 = "${pageContext.request.contextPath}";
					int j1 = s7.indexOf(s8);
					if (j1 == -1)
					{
						s1 = (new StringBuilder()).append(s1).append("../WebContent/").append(projectName).append("/").append(moduleName).append("/").append(s7).append(",").toString();
					} else
					{
						s7 = s7.replaceAll("\\$\\{.*\\}/", "");
						s1 = (new StringBuilder()).append(s1).append("../../Resource/WebContent/").append(s7).append(",").toString();
					}
					s2 = s2.substring(l);
				} else
				{
					s2 = "";
				}
			}
		}
		return s1;
	}

	public void modifyBuildJsXml_cp(String s, String s1)
	{
		try
		{
			String as[] = s.split(",");
			SAXBuilder saxbuilder = new SAXBuilder();
			File file = new File("buildCompress.xml");
			logger.info(file.getAbsolutePath());
			if (file.exists())
			{
				Document document = saxbuilder.build(file);
				Element element = document.getRootElement();
				List list = element.getChildren("target");
				String s2 = (new StringBuilder()).append("${web.dir}/").append(projectName).append("/").append(moduleName).append("/").append("js/").append(s1.substring(0, s1.indexOf(".jsp"))).toString();
				String s3 = (new StringBuilder()).append("${web.dir}/").append(projectName).append("/").append(moduleName).append("/").append("interimJsFolder/").toString();
label0:
				for (int i = 0; i < list.size(); i++)
				{
					Element element1 = (Element)list.get(i);
					if ("uglify".equalsIgnoreCase(element1.getAttributeValue("name")))
					{
						int j = 0;
						do
						{
							if (j >= as.length)
								continue label0;
							if (as[j].indexOf("min.js") == -1)
							{
								Element element4 = new Element("uglify");
								element4.setAttribute("verbose", "true");
								element4.setAttribute("output", (new StringBuilder()).append(s3).append(as[j].substring(as[j].lastIndexOf("/") + 1, as[j].indexOf(".js"))).append("-min.js").toString());
								Element element5 = new Element("sources");
								element5.setAttribute("dir", "${basedir}");
								Element element8 = new Element("file");
								element8.setAttribute("name", as[j]);
								element5.addContent(element8);
								element4.addContent(element5);
								element1.addContent(element4);
							}
							j++;
						} while (true);
					}
					if ("concatJs".equalsIgnoreCase(element1.getAttributeValue("name")))
					{
						Element element2 = new Element("concat");
						element2.setAttribute("destfile", (new StringBuilder()).append(s2).append("-min.js").toString());
						element2.setAttribute("encoding", "${charset}");
						for (int k = 0; k < as.length; k++)
							if (as[k].indexOf("min.js") == -1)
							{
								Element element6 = new Element("path");
								element6.setAttribute("path", (new StringBuilder()).append(s3).append(as[k].substring(as[k].lastIndexOf("/") + 1, as[k].indexOf(".js"))).append("-min.js").toString());
								element2.addContent(element6);
							} else
							{
								Element element7 = new Element("path");
								element7.setAttribute("path", as[k]);
								element2.addContent(element7);
							}

						element1.addContent(element2);
						continue;
					}
					if ("delete".equalsIgnoreCase(element1.getAttributeValue("name")))
					{
						Element element3 = new Element("delete");
						element3.setAttribute("dir", s3);
						element1.addContent(element3);
					}
				}

				XMLOutputter xmloutputter = new XMLOutputter();
				xmloutputter.setFormat(Format.getPrettyFormat().setEncoding("UTF-8"));
				xmloutputter.output(document, new FileOutputStream("buildCompress.xml"));
			} else
			{
				System.out.println("The configuration file is not found!");
			}
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

	public void modifyBuildCssXml_cp(String s, String s1)
	{
		try
		{
			logger.info("11111111111");
			String as[] = s.split(",");
			SAXBuilder saxbuilder = new SAXBuilder();
			File file = new File("buildCompress.xml");
			logger.info(file.getAbsolutePath());
			if (file.exists())
			{
				Document document = saxbuilder.build(file);
				Element element = document.getRootElement();
				List list = element.getChildren("target");
				String s2 = (new StringBuilder()).append("${web.dir}/").append(projectName).append("/").append(moduleName).append("/").append("css/").append(s1.substring(0, s1.indexOf(".jsp"))).toString();
				for (int i = 0; i < list.size(); i++)
				{
					Element element1 = (Element)list.get(i);
					if ("concatCss".equalsIgnoreCase(element1.getAttributeValue("name")))
					{
						Element element2 = new Element("concat");
						element2.setAttribute("destfile", (new StringBuilder()).append(s2).append("-source.css").toString());
						element2.setAttribute("encoding", "${charset}");
						for (int j = 0; j < as.length; j++)
						{
							Element element7 = new Element("path");
							element7.setAttribute("path", as[j]);
							element2.addContent(element7);
						}

						element1.addContent(element2);
						continue;
					}
					if ("minify".equalsIgnoreCase(element1.getAttributeValue("name")))
					{
						Element element3 = new Element("apply");
						element3.setAttribute("executable", "java");
						element3.setAttribute("verbose", "true");
						element3.setAttribute("dest", "${basedir}/..");
						element3.setAttribute("failonerror", "true");
						element3.setAttribute("parallel", "false");
						Element element6 = new Element("fileset");
						element6.setAttribute("dir", "${basedir}/..");
						Element element8 = new Element("include");
						element8.setAttribute("name", (new StringBuilder()).append("WebContent/").append(projectName).append("/").append(moduleName).append("/").append("css/").append(s1.substring(0, s1.indexOf(".jsp"))).append("-source.css").toString());
						element6.addContent(element8);
						Element element9 = new Element("arg");
						element9.setAttribute("line", "-jar");
						Element element10 = new Element("arg");
						element10.setAttribute("path", "${yuicompressor.path}");
						Element element11 = new Element("arg");
						element11.setAttribute("line", "--charset gbk");
						Element element12 = new Element("arg");
						element12.setAttribute("value", "--type");
						Element element13 = new Element("arg");
						element13.setAttribute("value", "css");
						Element element14 = new Element("arg");
						element14.setAttribute("value", "-o");
						Element element15 = new Element("targetfile");
						Element element16 = new Element("mapper");
						element16.setAttribute("type", "regexp");
						element16.setAttribute("from", "^(.*?(?<!-min))\\.(css)$");
						element16.setAttribute("to", "\\1-min.\\2");
						element3.addContent(element6);
						element3.addContent(element9);
						element3.addContent(element10);
						element3.addContent(element11);
						element3.addContent(element12);
						element3.addContent(element13);
						element3.addContent(element14);
						element3.addContent(element15);
						element3.addContent(element16);
						element1.addContent(element3);
						continue;
					}
					if ("rename".equalsIgnoreCase(element1.getAttributeValue("name")))
					{
						Element element4 = new Element("rename");
						element4.setAttribute("src", (new StringBuilder()).append(s2).append("-source-min.css").toString());
						element4.setAttribute("dest", (new StringBuilder()).append(s2).append("-min.css").toString());
						element1.addContent(element4);
						continue;
					}
					if ("delete".equalsIgnoreCase(element1.getAttributeValue("name")))
					{
						Element element5 = new Element("delete");
						element5.setAttribute("file", (new StringBuilder()).append(s2).append("-source.css").toString());
						element1.addContent(element5);
					}
				}

				XMLOutputter xmloutputter = new XMLOutputter();
				xmloutputter.setFormat(Format.getPrettyFormat().setEncoding("UTF-8"));
				xmloutputter.output(document, new FileOutputStream("buildCompress.xml"));
			} else
			{
				System.out.println("The configuration file is not found!");
			}
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

	public void modifyBuildJsXml_rp(String s, String s1)
	{
		try
		{
			String as[] = s.split(",");
			SAXBuilder saxbuilder = new SAXBuilder();
			File file = new File("buildCompress.xml");
			if (file.exists())
			{
				Document document = saxbuilder.build(file);
				Element element = document.getRootElement();
				List list = element.getChildren("target");
				String s2 = (new StringBuilder()).append("${buildResource.dir}/WebContent/").append(projectName).append("/").append(moduleName).append("/").append("js/").append(s1.substring(0, s1.indexOf(".jsp"))).toString();
				String s3 = (new StringBuilder()).append("${buildResource.dir}/WebContent/").append(projectName).append("/").append(moduleName).append("/").append("interimJsFolder/").toString();
label0:
				for (int i = 0; i < list.size(); i++)
				{
					Element element1 = (Element)list.get(i);
					if ("uglify".equalsIgnoreCase(element1.getAttributeValue("name")))
					{
						int j = 0;
						do
						{
							if (j >= as.length)
								continue label0;
							as[j] = as[j].substring(as[j].indexOf("WebContent"), as[j].length());
							if (as[j].indexOf("min.js") == -1)
							{
								Element element4 = new Element("uglify");
								element4.setAttribute("verbose", "true");
								element4.setAttribute("output", (new StringBuilder()).append(s3).append(as[j].substring(as[j].lastIndexOf("/") + 1, as[j].indexOf(".js"))).append("-min.js").toString());
								Element element5 = new Element("sources");
								element5.setAttribute("dir", "${buildResource.dir}");
								Element element8 = new Element("file");
								element8.setAttribute("name", as[j]);
								element5.addContent(element8);
								element4.addContent(element5);
								element1.addContent(element4);
							}
							j++;
						} while (true);
					}
					if ("concatJs".equalsIgnoreCase(element1.getAttributeValue("name")))
					{
						Element element2 = new Element("concat");
						element2.setAttribute("destfile", (new StringBuilder()).append(s2).append("-min.js").toString());
						element2.setAttribute("encoding", "${charset}");
						for (int k = 0; k < as.length; k++)
						{
							as[k] = as[k].substring(as[k].indexOf("WebContent"), as[k].length());
							if (as[k].indexOf("min.js") == -1)
							{
								Element element6 = new Element("path");
								element6.setAttribute("path", (new StringBuilder()).append(s3).append(as[k].substring(as[k].lastIndexOf("/") + 1, as[k].indexOf(".js"))).append("-min.js").toString());
								element2.addContent(element6);
							} else
							{
								Element element7 = new Element("path");
								element7.setAttribute("path", (new StringBuilder()).append("${buildResource.dir}/").append(as[k]).toString());
								element2.addContent(element7);
							}
						}

						element1.addContent(element2);
						continue;
					}
					if ("delete".equalsIgnoreCase(element1.getAttributeValue("name")))
					{
						Element element3 = new Element("delete");
						element3.setAttribute("dir", s3);
						element1.addContent(element3);
					}
				}

				XMLOutputter xmloutputter = new XMLOutputter();
				xmloutputter.setFormat(Format.getPrettyFormat().setEncoding("UTF-8"));
				xmloutputter.output(document, new FileOutputStream("buildCompress.xml"));
			} else
			{
				System.out.println("The configuration file is not found!");
			}
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

	public void modifyBuildCssXml_rp(String s, String s1)
	{
		try
		{
			String as[] = s.split(",");
			SAXBuilder saxbuilder = new SAXBuilder();
			File file = new File("buildCompress.xml");
			if (file.exists())
			{
				Document document = saxbuilder.build(file);
				Element element = document.getRootElement();
				List list = element.getChildren("target");
				String s2 = (new StringBuilder()).append("${buildResource.dir}/WebContent/").append(projectName).append("/").append(moduleName).append("/").append("css/").append(s1.substring(0, s1.indexOf(".jsp"))).toString();
				for (int i = 0; i < list.size(); i++)
				{
					Element element1 = (Element)list.get(i);
					if ("concatCss".equalsIgnoreCase(element1.getAttributeValue("name")))
					{
						Element element2 = new Element("concat");
						element2.setAttribute("destfile", (new StringBuilder()).append(s2).append("-source.css").toString());
						element2.setAttribute("encoding", "${charset}");
						for (int j = 0; j < as.length; j++)
						{
							as[j] = as[j].substring(as[j].indexOf("WebContent"), as[j].length());
							Element element7 = new Element("path");
							element7.setAttribute("path", (new StringBuilder()).append("${buildResource.dir}/").append(as[j]).toString());
							element2.addContent(element7);
						}

						element1.addContent(element2);
						continue;
					}
					if ("minify".equalsIgnoreCase(element1.getAttributeValue("name")))
					{
						Element element3 = new Element("apply");
						element3.setAttribute("executable", "java");
						element3.setAttribute("verbose", "true");
						element3.setAttribute("dest", "${buildResource.dir}");
						element3.setAttribute("failonerror", "true");
						element3.setAttribute("parallel", "false");
						Element element6 = new Element("fileset");
						element6.setAttribute("dir", "${buildResource.dir}");
						Element element8 = new Element("include");
						element8.setAttribute("name", (new StringBuilder()).append("WebContent/").append(projectName).append("/").append(moduleName).append("/").append("css/").append(s1.substring(0, s1.indexOf(".jsp"))).append("-source.css").toString());
						element6.addContent(element8);
						Element element9 = new Element("arg");
						element9.setAttribute("line", "-jar");
						Element element10 = new Element("arg");
						element10.setAttribute("path", "${yuicompressor.path}");
						Element element11 = new Element("arg");
						element11.setAttribute("line", "--charset gbk");
						Element element12 = new Element("arg");
						element12.setAttribute("value", "--type");
						Element element13 = new Element("arg");
						element13.setAttribute("value", "css");
						Element element14 = new Element("arg");
						element14.setAttribute("value", "-o");
						Element element15 = new Element("targetfile");
						Element element16 = new Element("mapper");
						element16.setAttribute("type", "regexp");
						element16.setAttribute("from", "^(.*?(?<!-min))\\.(css)$");
						element16.setAttribute("to", "\\1-min.\\2");
						element3.addContent(element6);
						element3.addContent(element9);
						element3.addContent(element10);
						element3.addContent(element11);
						element3.addContent(element12);
						element3.addContent(element13);
						element3.addContent(element14);
						element3.addContent(element15);
						element3.addContent(element16);
						element1.addContent(element3);
						continue;
					}
					if ("rename".equalsIgnoreCase(element1.getAttributeValue("name")))
					{
						Element element4 = new Element("rename");
						element4.setAttribute("src", (new StringBuilder()).append(s2).append("-source-min.css").toString());
						element4.setAttribute("dest", (new StringBuilder()).append(s2).append("-min.css").toString());
						element1.addContent(element4);
						continue;
					}
					if ("delete".equalsIgnoreCase(element1.getAttributeValue("name")))
					{
						Element element5 = new Element("delete");
						element5.setAttribute("file", (new StringBuilder()).append(s2).append("-source.css").toString());
						element1.addContent(element5);
					}
				}

				XMLOutputter xmloutputter = new XMLOutputter();
				xmloutputter.setFormat(Format.getPrettyFormat().setEncoding("UTF-8"));
				xmloutputter.output(document, new FileOutputStream("buildCompress.xml"));
			} else
			{
				System.out.println("The configuration file is not found!");
			}
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

	private static String findProjects(String s)
	{
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

	public static String findJspFiles(String s)
	{
		String s1 = "";
		File file = new File(s);
		File afile[] = file.listFiles(new FileFilter());
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
		s = (new StringBuilder()).append(s).append("/src/cn/com/sandi").toString();
		String as[] = findProjects(s).split(",");
		return as;
	}

	private String[] planets2(Object obj)
	{
		String s = System.getProperty("user.dir").replaceAll("\\\\", "/");
		s = s.substring(0, s.lastIndexOf("/"));
		s = (new StringBuilder()).append(s).append("/WebContent").toString();
		String as[] = findProjects((new StringBuilder()).append(s).append("/").append(obj).toString()).split(",");
		return as;
	}

	private String[] planets3(Object obj)
	{
		String s = System.getProperty("user.dir").replaceAll("\\\\", "/");
		s = s.substring(0, s.lastIndexOf("/"));
		s = s.substring(0, s.lastIndexOf("/"));
		s = (new StringBuilder()).append(s).append("/Resource/WebContent").toString();
		String as[] = findProjects((new StringBuilder()).append(s).append("/").append(obj).toString()).split(",");
		return as;
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
}
