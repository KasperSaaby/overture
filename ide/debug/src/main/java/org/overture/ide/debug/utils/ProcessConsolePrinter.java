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
package org.overture.ide.debug.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.swt.SWT;
import org.overture.ide.ui.internal.util.ConsoleWriter;

public class ProcessConsolePrinter extends Thread
{
	ConsoleWriter cw = null;
	InputStream stream = null;
	boolean error = false;

	public ProcessConsolePrinter(Boolean error, ConsoleWriter cw,
			InputStream inputStream)
	{
		this.cw = cw;
		this.stream = inputStream;
		this.error = error;
		setDaemon(true);
	}

	@Override
	public void run()
	{

		String line = null;
		BufferedReader input = new BufferedReader(new InputStreamReader(stream));
		try
		{
			while ((line = input.readLine()) != null)
			{

				if (cw != null)
				{
					if (error)
					{
						cw.consolePrint(line, SWT.COLOR_RED);
					} else
					{
						cw.println(line);
					}

				} else
				{
					System.out.println(line);
				}
			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
