/*
 * #%~
 * The VDM Type Checker
 * %%
 * Copyright (C) 2008 - 2014 Overture
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #~%
 */
package org.overture.typechecker.assistant.module;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.overture.ast.assistant.IAstAssistant;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.modules.AModuleExports;
import org.overture.ast.modules.PExport;
import org.overture.typechecker.assistant.ITypeCheckerAssistantFactory;

//FIXME only used in 1 class. move it
public class AModuleExportsAssistantTC implements IAstAssistant
{
	protected ITypeCheckerAssistantFactory af;

	public AModuleExportsAssistantTC(ITypeCheckerAssistantFactory af)
	{
		this.af = af;
	}

	public Collection<? extends PDefinition> getDefinitions(
			AModuleExports aModuleExports, LinkedList<PDefinition> actualDefs)
	{
		List<PDefinition> exportDefs = new Vector<PDefinition>();

		for (List<PExport> etype : aModuleExports.getExports())
		{
			for (PExport exp : etype)
			{
				exportDefs.addAll(af.createPExportAssistant().getDefinition(exp, actualDefs));
			}
		}

		// Mark all exports as used

		for (PDefinition d : exportDefs)
		{
			af.createPDefinitionAssistant().markUsed(d);
		}

		return exportDefs;
	}

	public Collection<? extends PDefinition> getDefinitions(
			AModuleExports aModuleExports)
	{
		List<PDefinition> exportDefs = new Vector<PDefinition>();

		for (List<PExport> etype : aModuleExports.getExports())
		{
			for (PExport exp : etype)
			{
				exportDefs.addAll(af.createPExportAssistant().getDefinition(exp));
			}
		}

		// Mark all exports as used

		for (PDefinition d : exportDefs)
		{
			af.createPDefinitionAssistant().markUsed(d);
		}

		return exportDefs;
	}

}