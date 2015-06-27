package org.overture.codegen.tests.output;

import java.io.File;
import java.util.Collection;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.overture.config.Release;
import org.overture.config.Settings;
import org.overture.core.tests.PathsProvider;

@RunWith(Parameterized.class)
public class ClassicOutputTest extends OutputTestPp
{
	public static final String ROOT = "src" + File.separatorChar + "test"
			+ File.separatorChar + "resources" + File.separatorChar
			+ "classic_specs";
	
	public ClassicOutputTest(String nameParameter, String inputParameter,
			String resultParameter)
	{
		super(nameParameter, inputParameter, resultParameter);
	}
	
	@Before
	public void init()
	{
		super.init();
		Settings.release = Release.CLASSIC;
	}
	
	@Parameters(name = "{index} : {0}")
	public static Collection<Object[]> testData()
	{
		return PathsProvider.computePaths(ROOT);
	}

	@Override
	protected String getUpdatePropertyString()
	{
		return UPDATE_PROPERTY_PREFIX + "classic";
	}
}