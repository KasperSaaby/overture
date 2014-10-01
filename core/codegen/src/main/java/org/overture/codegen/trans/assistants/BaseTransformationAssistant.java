/*
 * #%~
 * VDM Code Generator
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
package org.overture.codegen.trans.assistants;

import java.util.LinkedList;

import org.overture.codegen.cgast.INode;
import org.overture.codegen.cgast.SStmCG;
import org.overture.codegen.cgast.analysis.AnalysisException;
import org.overture.codegen.cgast.analysis.DepthFirstAnalysisAdaptor;
import org.overture.codegen.cgast.statements.ABlockStmCG;
import org.overture.codegen.cgast.statements.AElseIfStmCG;
import org.overture.codegen.cgast.statements.AIfStmCG;

public class BaseTransformationAssistant
{
	public void replaceNodeWith(INode original, INode replacement)
	{
		if (original != replacement)
		{
			replace(original, replacement);
		}
	}

	public void replaceNodeWithRecursively(INode original, INode replacement,
			DepthFirstAnalysisAdaptor analysis) throws AnalysisException
	{
		if (original != replacement)
		{
			replaceNodeWith(original, replacement);
			replacement.apply(analysis);
		}
	}

	private void replace(INode original, INode replacement)
	{
		INode parent = original.parent();

		if (parent != null)
		{
			parent.replaceChild(original, replacement);
		}

		original.parent(null);
	}

	public SStmCG getEnclosingStm(INode node, String nodeStr)
			throws AnalysisException
	{
		SStmCG enclosingStm = node.getAncestor(SStmCG.class);

		if (enclosingStm == null)
		{
			throw new AnalysisException(String.format("Could not find enclosing statement for %s", node));
		}

		if (enclosingStm instanceof AElseIfStmCG)
		{
			AElseIfStmCG elseIf = (AElseIfStmCG) enclosingStm;
			AIfStmCG enclosingIf = elseIf.getAncestor(AIfStmCG.class);

			LinkedList<AElseIfStmCG> elseIfList = new LinkedList<AElseIfStmCG>(enclosingIf.getElseIf());
			for (int i = 0; i < elseIfList.size(); i++)
			{
				AElseIfStmCG currentElseIf = elseIfList.get(i);
				if (elseIf == currentElseIf)
				{
					enclosingIf.getElseIf().remove(currentElseIf);
					AIfStmCG elsePart = new AIfStmCG();
					elsePart.setIfExp(currentElseIf.getElseIf());
					elsePart.setThenStm(currentElseIf.getThenStm());

					for (int j = i + 1; j < elseIfList.size(); j++)
					{
						enclosingIf.getElseIf().remove(elseIfList.get(j));
						elsePart.getElseIf().add(elseIfList.get(j));
					}

					ABlockStmCG block = new ABlockStmCG();
					block.getStatements().add(elsePart);

					elsePart.setElseStm(enclosingIf.getElseStm());
					enclosingIf.setElseStm(block);

					return elsePart;
				}
			}
		}

		return enclosingStm;
	}
}
