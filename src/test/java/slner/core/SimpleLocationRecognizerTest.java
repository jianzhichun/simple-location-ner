package slner.core;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import slner.core.SimpleLocationRecognizer;
/**
 * 
 * @author jianzhichun
 *
 */
public class SimpleLocationRecognizerTest {
	
	SimpleLocationRecognizer simpleLocationRecognizer;
	@Before
	public void setUp() throws Exception {
		simpleLocationRecognizer = SimpleLocationRecognizer.getInstance();
	}

	@Test
	public void testRecogniceLocation() {
		assertEquals("{area=[合川区], province=[重庆], city=[重庆]}", simpleLocationRecognizer.recognizeLocationFormat("重庆市合川区义乌市场 负一楼C区0807号").toString());
	}

}
