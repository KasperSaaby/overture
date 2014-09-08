package org.overture.core.npp;

import org.overture.ast.analysis.AnalysisException;
import org.overture.ast.analysis.QuestionAnswerAdaptor;
import org.overture.ast.node.INode;
import org.overture.ast.patterns.ATypeBind;

public class BindingsNpp extends QuestionAnswerAdaptor<IndentTracker, String> implements
		IPrettyPrinter
{
	ISymbolTable mytable;
	IPrettyPrinter rootNpp;

	private static String BINDING_NOT_FOUND = "ERROR: Binding not found";
	private static String space = " ";

	@Override
	public void setInsTable(ISymbolTable it)
	{
		mytable = it;
		
	}
	
	public BindingsNpp(NewPrettyPrinter root, ISymbolTable nst)
	{
		rootNpp = root;
		mytable = nst;
	}
	
	@Override
	public String caseATypeBind(ATypeBind node, IndentTracker question)
			throws AnalysisException
	{
		String pattern = node.getPattern().toString();
		String type = node.getType().toString();
		String binding = mytable.getCOLON();
		
		return Utilities.append(pattern, type, binding);
	}
	
	@Override
	public String createNewReturnValue(INode node, IndentTracker question)
			throws AnalysisException
	{
		return BINDING_NOT_FOUND;
	}

	@Override
	public String createNewReturnValue(Object node, IndentTracker question)
			throws AnalysisException
	{
		return BINDING_NOT_FOUND;
	}
}
