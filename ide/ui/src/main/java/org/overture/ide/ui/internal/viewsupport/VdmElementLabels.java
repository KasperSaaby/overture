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
package org.overture.ide.ui.internal.viewsupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.StyledString;
import org.overture.ast.definitions.AExplicitFunctionDefinition;
import org.overture.ast.definitions.AExplicitOperationDefinition;
import org.overture.ast.definitions.AImplicitFunctionDefinition;
import org.overture.ast.definitions.AImplicitOperationDefinition;
import org.overture.ast.definitions.AInheritedDefinition;
import org.overture.ast.definitions.AInstanceVariableDefinition;
import org.overture.ast.definitions.ALocalDefinition;
import org.overture.ast.definitions.ANamedTraceDefinition;
import org.overture.ast.definitions.APerSyncDefinition;
import org.overture.ast.definitions.ARenamedDefinition;
import org.overture.ast.definitions.ATypeDefinition;
import org.overture.ast.definitions.AUntypedDefinition;
import org.overture.ast.definitions.AValueDefinition;
import org.overture.ast.definitions.PDefinition;
import org.overture.ast.definitions.SClassDefinition;
import org.overture.ast.modules.AAllImport;
import org.overture.ast.modules.AFromModuleImports;
import org.overture.ast.modules.AModuleImports;
import org.overture.ast.modules.AModuleModules;
import org.overture.ast.modules.ATypeImport;
import org.overture.ast.modules.PImport;
import org.overture.ast.modules.SValueImport;
import org.overture.ast.types.AFieldField;
import org.overture.ast.types.AFunctionType;
import org.overture.ast.types.ANamedInvariantType;
import org.overture.ast.types.AOperationType;
import org.overture.ast.types.AProductType;
import org.overture.ast.types.ARecordInvariantType;
import org.overture.ast.types.AUnresolvedType;
import org.overture.ast.types.AVoidType;
import org.overture.ast.types.PType;

public class VdmElementLabels {

	/**
	 * Specifies to apply color styles to labels. This flag only applies to
	 * methods taking or returning a {@link StyledString}.
	 * 
	 * @since 3.4
	 */
	public final static long COLORIZE = 1L << 55;

	/**
	 * Default options (M_PARAMETER_TYPES, M_APP_TYPE_PARAMETERS &
	 * T_TYPE_PARAMETERS enabled)
	 */
	public final static long ALL_DEFAULT = 1231;

//	/**
//	 * Returns the styled string for the given resource. The returned label is
//	 * BiDi-processed with {@link TextProcessor#process(String, String)}.
//	 * 
//	 * @param resource
//	 *            the resource
//	 * @return the styled string
//	 * @since 3.4
//	 */
	// private static StyledString getStyledResourceLabel(IResource resource) {
	// StyledString result = new StyledString(resource.getName());
	// return Strings.markLTR(result);
	// }

	// TODO: this map should be deleted when the AST fix is made
	// so that definitions contain a reference to the module they belong
	static Map<String, AModuleModules> activeModule = new HashMap<String, AModuleModules>();

	public static StyledString getStyledTextLabel(Object element, long flags) {
		// if (element instanceof IVdmElement) {
		// return getElementLabel((IVdmElement) element, flags);
		// }
		if (element instanceof SClassDefinition) {
			// activeModule = null;
			return getClassDefinitionLabel((SClassDefinition) element, flags);
		}
		if (element instanceof AModuleModules) {
			activeModule.put(((AModuleModules) element).getName().getName(), ((AModuleModules) element));
			return getModuleLabel((AModuleModules) element, flags);
		}
		if (element instanceof AInstanceVariableDefinition) {
			return getInstanceVariableDefinitionLabel((AInstanceVariableDefinition) element);
		}
		if (element instanceof ATypeDefinition) {
			return getTypeDefinitionLabel((ATypeDefinition) element);
		}
		if (element instanceof AExplicitOperationDefinition) {
			return getExplicitOperationDefinitionLabel((AExplicitOperationDefinition) element);
		}
		if (element instanceof AImplicitOperationDefinition) {
			return getImplicitOperationDefinitionLabel((AImplicitOperationDefinition) element);
		}

		if (element instanceof AExplicitFunctionDefinition) {
			return getExplicitFunctionDefinitionLabel((AExplicitFunctionDefinition) element);
		}

		if (element instanceof AImplicitFunctionDefinition) {
			return getImplicitFunctionDefinitionLabel((AImplicitFunctionDefinition) element);
		}
		if (element instanceof ALocalDefinition) {
			return getLocalDefinitionLabel((ALocalDefinition) element);
		}
		if (element instanceof AValueDefinition) {
			System.out.println("VALUE DEF");
		}
		if (element instanceof ANamedTraceDefinition) {
			return getNamedTraceDefinitionLabel((ANamedTraceDefinition) element);
		}

		if (element instanceof AUntypedDefinition) {
			return getUntypedDefinition((AUntypedDefinition) element);
		}

		if (element instanceof AModuleImports) {
			StyledString result = new StyledString();
			result.append("import definitions");
			return result;
		}

		if (element instanceof AFromModuleImports) {
			return getImportFromModuleLabel((AFromModuleImports) element);
		}

		if (element instanceof PImport) {
			return getImportLabel((PImport) element);
		}

		if (element instanceof AFieldField) {
			StyledString result = new StyledString();
			result.append(((AFieldField) element).getTag());
			result.append(" : " + processUnresolved(((AFieldField) element).getType()),
					StyledString.DECORATIONS_STYLER);
			return result;
		}

		if (element instanceof APerSyncDefinition) {
			return getPerSyncDefinitionLabel((APerSyncDefinition) element);
		}

		if (element instanceof AInheritedDefinition) {
			return getInheritedDefinition((AInheritedDefinition) element, flags);
		}

		if (element instanceof ImportsContainer) {
			return getStyledTextLabel(
					(AModuleImports) ((ImportsContainer) element).getImports(),
					flags);
		}

		if (element instanceof ARenamedDefinition) {
			return getRenamedDefinitionLabel((ARenamedDefinition) element, flags);
		}

		StyledString result = new StyledString();
		result.append("Unsupported type reached: " + element);
		return result;

	}

	private static StyledString getRenamedDefinitionLabel(
			ARenamedDefinition element, long flags) {

		return getStyledTextLabel(element.getDef(), flags);

	}

	private static StyledString getInheritedDefinition(
			AInheritedDefinition element, long flags) {

		StyledString res = getStyledTextLabel(element.getSuperdef(), flags);

		return res;
	}

	private static StyledString getPerSyncDefinitionLabel(
			APerSyncDefinition element) {
		StyledString result = new StyledString();
		result.append(element.getName().getSimpleName());
		result.append(" : sync predicate", StyledString.DECORATIONS_STYLER);
		return result;
	}

	private static StyledString getImplicitOperationDefinitionLabel(
			AImplicitOperationDefinition element) {
		StyledString result = new StyledString();

		result.append(element.getName().getSimpleName());

		if (element.getType() instanceof AOperationType) {
			AOperationType type = (AOperationType) element.getType();
			if (type.getParameters().size() == 0) {
				result.append("() ");
			} else {
				result.append("(");
				int i = 0;
				while (i < type.getParameters().size() - 1) {
					PType definition = (PType) type.getParameters().get(i);
					result.append(getSimpleTypeString(definition) + ", ");

					i++;
				}
				PType definition = (PType) type.getParameters().get(i);
				result.append(getSimpleTypeString(definition) + ")");
			}
		}

		if (element.getType().getResult() instanceof AVoidType) {
			result.append(" : ()", StyledString.DECORATIONS_STYLER);
		} else {
			result.append(" : " + getSimpleTypeString(element.getType().getResult()),
					StyledString.DECORATIONS_STYLER);
		}

		return result;
	}

	private static StyledString getImplicitFunctionDefinitionLabel(
			AImplicitFunctionDefinition element) {
		StyledString result = new StyledString();
		result.append(element.getName().getSimpleName());

		if (element.getType() instanceof AFunctionType) {
			AFunctionType type = (AFunctionType) element.getType();
			if (type.getParameters().size() == 0) {
				result.append("() ");
			} else {
				result.append("(");
				int i = 0;
				while (i < type.getParameters().size() - 1) {
					PType definition = (PType) type.getParameters().get(i);
					result.append(getSimpleTypeString(definition) + ", ");

					i++;
				}
				PType definition = (PType) type.getParameters().get(i);
				result.append(getSimpleTypeString(definition) + ")");
			}
		}

		if (element.getType().getResult() instanceof AVoidType) {
			result.append(" : ()", StyledString.DECORATIONS_STYLER);
		} else {
			result.append(" : " + getSimpleTypeString(element.getType().getResult()),
					StyledString.DECORATIONS_STYLER);

		}

		return result;
	}

	private static StyledString getImportLabel(PImport element) {

		StyledString result = new StyledString();

		if (element instanceof AAllImport) {
			result.append(element.getName().getSimpleName());
		} else if (element instanceof ATypeImport) {
			
			
			ATypeImport type = (ATypeImport) element;
			result.append(type.getName().getSimpleName());
			if(type.getDef()!=null)
			{
				result.append(" : " + getSimpleTypeString(type.getDef().getType()), StyledString.DECORATIONS_STYLER);
			}else
			{
				result.append(" : " , StyledString.DECORATIONS_STYLER);
			}
			
			result.append(type.getRenamed().name, StyledString.DECORATIONS_STYLER);
		} else if (element instanceof SValueImport) {
			SValueImport value = (SValueImport) element;
			result.append(value.getName().getSimpleName());
			if (value.getImportType() != null) {
				String typeString = value.getImportType().toString();
				typeString = typeString.replaceFirst("[(]", "");
				typeString = typeString.substring(0, typeString.length() - 1);
				result.append(" : " + typeString);
			}
		}

		if (element.getRenamed()!= null) {
			result.append(" (renamed as: " + element.getRenamed().getName() + ")",
					StyledString.DECORATIONS_STYLER);
		}
		return result;
	}

	private static StyledString getImportFromModuleLabel(
			AFromModuleImports element) {
		StyledString result = new StyledString();

		result.append(element.getName().getName());

		return result;
	}

	private static StyledString getNamedTraceDefinitionLabel(
			ANamedTraceDefinition element) {
		StyledString result = new StyledString();
		result.append(element.getName().getSimpleName());
		return result;
	}

	private static StyledString getUntypedDefinition(AUntypedDefinition element) {
		StyledString result = new StyledString();
		result.append(element.getName().getSimpleName());
		List<PDefinition> definitions = null;
		if (element.getClassDefinition() != null) {
			SClassDefinition classDef = element.getClassDefinition();
			definitions = classDef.getDefinitions();
		} else {
			if (activeModule != null && activeModule.containsKey(element.getName().getModule()))
			{
				definitions = activeModule.get(element.getName().getModule()).getDefs();
			}
				
		}
		if (definitions != null) {
			for (PDefinition def : definitions) {
				if (def instanceof AValueDefinition) {
					for (int i = 0; i < ((AValueDefinition)def).getDefs().size(); i++) {
						if (((AValueDefinition)def).getDefs().get(i).getLocation()
								.equals(element.getLocation())) {
							PType type = ((AValueDefinition) def).getType();
							if (type instanceof AProductType) {
								return result
										.append(" : "
												+ getSimpleTypeString(((AProductType) type).getTypes()
														.get(i)),
												StyledString.DECORATIONS_STYLER);
							} else if (type instanceof AUnresolvedType) {
								return result.append(" : "
										+ getSimpleTypeString(type),
										StyledString.DECORATIONS_STYLER);
							} else if (type != null) {
								return result.append(" : "
										+ getSimpleTypeString(type),
										StyledString.DECORATIONS_STYLER);
							} else {
								// System.err.println("Could not translate type");
								return result.append(" : "
										+ ((AValueDefinition) def).getExpression(),
										StyledString.DECORATIONS_STYLER);
							}
						}
					}
				}
			}
		}

		// Type elemType = element.getType();
		// result.append(" : " + getSimpleTypeString(elemType),
		// StyledString.DECORATIONS_STYLER);
		return result;
	}

	private static StyledString getLocalDefinitionLabel(ALocalDefinition element) {
		StyledString result = new StyledString();
		result.append(element.getName().getSimpleName());
		if (element.getType().getLocation().module.toLowerCase().equals("default")) {
			result.append(" : " + getSimpleTypeString(element.getType()),
					StyledString.DECORATIONS_STYLER);
		} else {
			result.append(" : " + element.getType().getLocation().module + "`"
					+ getSimpleTypeString(element.getType()),
					StyledString.DECORATIONS_STYLER);
		}
		return result;
	}

	private static StyledString getExplicitFunctionDefinitionLabel(
			AExplicitFunctionDefinition element) {
		StyledString result = new StyledString();
		result.append(element.getName().getSimpleName());

		if (element.getType() instanceof AFunctionType) {
			AFunctionType type = (AFunctionType) element.getType();
			if (type.getParameters().size() == 0) {
				result.append("() ");
			} else {
				result.append("(");
				int i = 0;
				while (i < type.getParameters().size() - 1) {
					PType definition = (PType) type.getParameters().get(i);
					result.append(getSimpleTypeString(definition) + ", ");

					i++;
				}
				PType definition = (PType) type.getParameters().get(i);
				result.append(getSimpleTypeString(definition) + ")");
			}
		}

		if (element.getType().getResult() instanceof AVoidType) {
			result.append(" : ()", StyledString.DECORATIONS_STYLER);
		} else {
			result.append(" : " + getSimpleTypeString(element.getType().getResult()),
					StyledString.DECORATIONS_STYLER);

		}

		return result;
	}

	private static StyledString getTypeDefinitionLabel(ATypeDefinition element) {
		StyledString result = new StyledString();
		if (element.getType() instanceof ARecordInvariantType) {

			result.append(element.getName().getSimpleName());
			result.append(" : record type", StyledString.DECORATIONS_STYLER);
		} else if (element.getType() instanceof ANamedInvariantType) {

			result.append(element.getName().getSimpleName());
			result.append(" : "
					+ getSimpleTypeString(((ANamedInvariantType)element.getType()).getType()),
					StyledString.DECORATIONS_STYLER);

		}
		else 
			result.append(element.getName().getName());
		return result;
	}

	private static StyledString getExplicitOperationDefinitionLabel(
			AExplicitOperationDefinition element) {
		StyledString result = new StyledString();

		result.append(element.getName().getSimpleName());

		if (element.getType() instanceof AOperationType) {
			AOperationType type = (AOperationType) element.getType();
			if (type.getParameters().size() == 0) {
				result.append("() ");
			} else {
				result.append("(");
				int i = 0;
				while (i < type.getParameters().size() - 1) {
					PType definition = (PType) type.getParameters().get(i);
					result.append(getSimpleTypeString(definition) + ", ");

					i++;
				}
				PType definition = (PType) type.getParameters().get(i);
				result.append(getSimpleTypeString(definition) + ")");
			}
		}

		if (element.getType().getResult() instanceof AVoidType) {
			result.append(" : ()", StyledString.DECORATIONS_STYLER);
		} else {
			result.append(" : " + getSimpleTypeString(element.getType().getResult()),
					StyledString.DECORATIONS_STYLER);

		}

		return result;
	}

	private static StyledString getInstanceVariableDefinitionLabel(
			AInstanceVariableDefinition element) {
		StyledString result = new StyledString();
		result.append(element.getName().getSimpleName());
		result.append(" : " + getSimpleTypeString(element.getType()),
				StyledString.DECORATIONS_STYLER);
		return result;
	}

	private static StyledString getClassDefinitionLabel(
			SClassDefinition element, long flags) {
		StyledString result = new StyledString();
		result.append(element.getName().getSimpleName());
		return result;
	}

	private static StyledString getModuleLabel(AModuleModules element, long flags) {
		StyledString result = new StyledString();
		result.append(element.getName().getName());
		return result;
	}

	public static String getTextLabel(Object element, long evaluateTextFlags) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String processUnresolved(PType definition) {

		String defString = definition.toString();

		if (defString.contains("unresolved ")) {
			defString = defString.replace("(", "");
			defString = defString.replace(")", "");
			defString = defString.replace("unresolved ", "");
			return defString;
		}
		return definition.toString();
	}

	private static String getSimpleTypeString(PType type) {
		String typeName = processUnresolved(type);
		typeName = typeName.replace("(", "");
		typeName = typeName.replace(")", "");
		return typeName;
	}
}
