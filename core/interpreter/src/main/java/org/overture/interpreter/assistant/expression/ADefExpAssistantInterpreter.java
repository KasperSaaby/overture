package org.overture.interpreter.assistant.expression;

import org.overture.ast.expressions.ADefExp;
import org.overture.ast.expressions.PExp;
import org.overture.interpreter.assistant.IInterpreterAssistantFactory;
import org.overture.interpreter.assistant.definition.PDefinitionListAssistantInterpreter;

public class ADefExpAssistantInterpreter
{
	protected static IInterpreterAssistantFactory af;

	@SuppressWarnings("static-access")
	public ADefExpAssistantInterpreter(IInterpreterAssistantFactory af)
	{
		this.af = af;
	}

//	public static ValueList getValues(ADefExp exp, ObjectContext ctxt)
//	{
//		ValueList list = PDefinitionListAssistantInterpreter.getValues(exp.getLocalDefs(), ctxt);
//		list.addAll(PExpAssistantInterpreter.getValues(exp.getExpression(), ctxt));
//		return list;
//	}

//	public static PExp findExpression(ADefExp exp, int lineno)
//	{
//		PExp found = PExpAssistantInterpreter.findExpressionBaseCase(exp, lineno);
//		if (found != null)
//			return found;
//
//		found = PDefinitionListAssistantInterpreter.findExpression(exp.getLocalDefs(), lineno);
//		if (found != null)
//			return found;
//
//		return PExpAssistantInterpreter.findExpression(exp.getExpression(), lineno);
//	}

}
