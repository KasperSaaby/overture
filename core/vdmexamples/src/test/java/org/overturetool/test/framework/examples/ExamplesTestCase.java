/*******************************************************************************
 * Copyright (c) 2009, 2011 Overture Team and others.
 *
 * Overture is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Overture is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Overture.  If not, see <http://www.gnu.org/licenses/>.
 * 	
 * The Overture Tool web-site: http://overturetool.org/
 *******************************************************************************/
package org.overturetool.test.framework.examples;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.overturetool.test.framework.BaseTestCase;
import org.overturetool.test.util.MessageReaderWritter;

public class ExamplesTestCase extends BaseTestCase
{
	public ExamplesTestCase()
	{
	}

	public ExamplesTestCase(File file)
	{
		super(file);
	}

	@Override
	public void test() throws Exception
	{
		if (mode != ContentModed.File)
		{
			return;
		}
		System.out.println(file.getName());
		System.out.println(getReadme());
	}

	public Set<File> getSpecFiles(String extension, File root)
	{
		Set<File> files = new HashSet<File>();
		for (File chld : root.listFiles())
		{
			if (chld.isDirectory())
			{
				files.addAll(getSpecFiles(extension, chld));
			} else if (chld.getName().endsWith(extension))
			{
				files.add(chld);
			}
		}
		return files;
	}

	protected VdmReadme getReadme()
	{
		File readme = null;

		if (file != null)
		{

			for (File chld : file.listFiles())
			{
				if (chld.getName().equals("README.txt"))
				{
					readme = chld;
				}
			}
		}
		if (readme == null)
		{
			return null;
		}
		return new VdmReadme(readme, getName(), true);
	}

	protected File getFile(String filename)
	{
		
		if (file != null)
		{

			for (File chld : file.listFiles())
			{
				if (chld.getName().equals(filename))
				{
					return chld;
				}
			}
		}
	
		return null;
	}
	
	protected void compareResults(Set<IMessage> warnings, Set<IMessage> errors, Object result,String filename)
	{
		File file = getFile(filename);
		
		assertNotNull("Result file " + filename + " does not exist", file);
		
		
		MessageReaderWritter mrw = new MessageReaderWritter();
		boolean parsed = mrw.readFromFile(file);
		
		if(parsed)
		{
			
		}
		else
		{
			assert false : "Could not read result file: " + file.getName();
		}
		
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected <T>Result mergeResults(
			Set<? extends Result<T>> parse,IResultCombiner<T> c)
	{
		Set<IMessage> warnings = new HashSet<IMessage>();
		Set<IMessage> errors = new HashSet<IMessage>();
		T result = null;
		
		for (Result<T> r : parse)
		{
			warnings.addAll(r.warnings);
			errors.addAll(r.errors);
			result = c.combine(result, r.result);
		}
		return new Result(result, warnings, errors);
	}
	
	
	
}
