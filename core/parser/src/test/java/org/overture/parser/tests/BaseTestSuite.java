package org.overture.parser.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Vector;

import junit.framework.Test;
import junit.framework.TestSuite;

public class BaseTestSuite
{
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected static TestSuite createTestCompleteFile(String name,File testRoot,Class testCase) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException
	{
		Constructor ctor = testCase.getConstructor(new Class[]{File.class});
		TestSuite suite = new TestSuite(name);
		
		if (testRoot != null && testRoot.exists())
		{
			
			for (File file : testRoot.listFiles())
			{
				if(file.getName().startsWith("."))
				{
					continue;
				}
				Object instance = ctor.newInstance(new Object[]{file});
				suite.addTest((Test) instance);
			}
		}
		return suite;
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected static TestSuite createTestSingleLineFile(String name,File testRoot,Class testCase) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException, IOException
	{
		Constructor ctor = testCase.getConstructor(new Class[]{String.class,String.class});
		TestSuite suite = new TestSuite(name);
		
		if (testRoot != null && testRoot.exists())
		{
			for (File file : testRoot.listFiles())
			{
				if(file.getName().startsWith("."))
				{
					continue;
				}
				List<String> lines = readFile(file);
				if(lines!=null)
				{
					for (int i = 0; i < lines.size(); i++)
					{
						Object instance = ctor.newInstance(new Object[]{file.getName()+" "+i+" - "+lines.get(i),lines.get(i)});
						suite.addTest((Test) instance);
					}
				}
				
			}
		}
		return suite;
		
	}
	
	protected static List<String> readFile(File file) throws IOException
	{
		List<String> lines = new Vector<String>();
		BufferedReader reader = null;

		try
		{
			reader = new BufferedReader(new FileReader(file));
			String text = null;

			// repeat until all lines is read
			while ((text = reader.readLine()) != null)
			{
				if (text.trim().length() > 0 && !text.trim().startsWith("//"))
				{
					lines.add(text);
				}
			}
			return lines;
		} finally
		{
			try
			{
				if (reader != null)
				{
					reader.close();
				}
			} catch (IOException e)
			{
			}
		}
	}
}
