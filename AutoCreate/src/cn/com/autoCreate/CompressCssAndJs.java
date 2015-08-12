// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package cn.com.autoCreate;

import org.apache.log4j.PropertyConfigurator;

import cn.com.autoCreate.compressFile.CompressFile;
import cn.com.autoCreate.util.Constants;

public class CompressCssAndJs
{

	public CompressCssAndJs()
	{
	}

	public static void main(String args[])
	{
		PropertyConfigurator.configure(Constants.log4jProp);
		CompressFile compressfile = new CompressFile();
	}
}
