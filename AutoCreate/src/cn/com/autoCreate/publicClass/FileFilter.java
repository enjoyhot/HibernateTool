// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package cn.com.autoCreate.publicClass;

import java.io.File;

public class FileFilter
	implements java.io.FileFilter
{

	public FileFilter()
	{
	}

	public boolean accept(File file)
	{
		return file.isFile();
	}
}
