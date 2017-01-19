package com.github.chameleon.eclipse.java;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.github.chameleon.eclipse.java.ChameleonJavaCompletionProposalComputer;

/**
 * @author Jesse Olsen
 *
 */
public class ChameleonJavaCompletionProposalComputerTest {

	ChameleonJavaCompletionProposalComputer computer;
	ArrayList<ICompletionProposal> proposals;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		String replacementString = "replacementString";
		int replacementOffset = 0;
		int replacementLength = 0;
		int cursorPosition = 0;
		int priority = 0;
		try {
			computer = new ChameleonJavaCompletionProposalComputer(
					replacementString, replacementOffset, replacementLength,
					cursorPosition, priority);
			computer.setTesting(true);
			proposals = new ArrayList<ICompletionProposal>();
		} catch (Exception e) {
			// Ignore exception caused by file not being there during test
			// runs...
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateDirectories() {
		computer.createDirectories("/tmp/a/b/c/d.txt");
		File dir = new File("/tmp/a/b/c/");
		assertEquals("Directory created correctly", dir.exists(), true);
		dir.delete();
		dir = new File("/tmp/a/b/");
		dir.delete();
		dir = new File("/tmp/a/");
		dir.delete();
	}

	@Test
	public void testShowTemplate() {
		int expectedReplacementOffset = 0;
		int expectedReplacementLength = 6;
		addEntry("show", "show template", "//template");
		checkEntry(0, "show template", "    \n//template\n\n",
				expectedReplacementOffset, expectedReplacementLength);
	}

	@Test
	public void testSayX() {
		int expectedReplacementOffset = 0;
		int expectedReplacementLength = 18;
		addEntry("say HELLO and go", "say X and go to the next line",
				"print \"X1\"");
		checkEntry(0, "go to the next line",
				"                \nprint \"HELLO\"\n\n", expectedReplacementOffset,
				expectedReplacementLength);
	}

	@Test
	public void testSayX2() {
		int expectedReplacementOffset = 0;
		int expectedReplacementLength = 11;
		addEntry("say HELLO", "say X and go to the next line", "print \"X1\"");
		checkEntry(0, "HELLO and go to the next line",
				"         \nprint \"HELLO\"\n\n", expectedReplacementOffset,
				expectedReplacementLength);
	}

	@Test
	public void testSay() {
		int expectedReplacementOffset = 0;
		int expectedReplacementLength = 12;
		addEntry("say a line", "say a line of text", "print(\"text\")");
		checkEntry(0, "line of text", "          \nprint(\"text\")\n\n",
				expectedReplacementOffset, expectedReplacementLength);
	}

	@Test
	public void testSayXSOMETHING1() {
		int expectedReplacementOffset = 0;
		int expectedReplacementLength = 5;
		addEntry("say", "say X:SOMETHING and go to the next line",
				"print \"X1\"");
		checkEntry(0, "say SOMETHING and go to the next line",
				"   \nprint \"X1\"\n\n", expectedReplacementOffset,
				expectedReplacementLength);
	}


	@Test
	public void testOpenFileDefault() {
		int expectedReplacementOffset = 0;
		int expectedReplacementLength = 11;
		addEntry("open file", "open file X:FILE as X:NAME",
				"print \"X1\"", "X1=test");
		checkEntry(0, "file FILE as NAME",
				"         \nprint \"test\"\n\n", expectedReplacementOffset,
				expectedReplacementLength);
	}

	
	@Test
	public void testSayXSOMETHING2() {
		int expectedReplacementOffset = 0;
		int expectedReplacementLength = 18;
		addEntry("say HELLO and go", "say X:SOMETHING and go to the next line",
				"print \"X1\"");
		checkEntry(0, "go to the next line",
				"                \nprint \"HELLO\"\n\n", expectedReplacementOffset,
				expectedReplacementLength);
	}

	@Test
	public void testAskXtoX() {
		int expectedReplacementOffset = 0;
		int expectedReplacementLength = 21;
		addEntry("ask \"Name?\" to name", "ask X to X variable",
				"X2 = raw_input(X1)");
		checkEntry(0, "name variable",
				"                   \nname = raw_input(\"Name?\")\n\n",
				expectedReplacementOffset, expectedReplacementLength);
	}

	@Test
	public void testAskQUESTIONtoVARIABLE() {
		int expectedReplacementOffset = 0;
		int expectedReplacementLength = 21;
		addEntry("ask \"Name?\" to name", "ask X:QUESTION to X:VARIABLE",
				"X2 = raw_input(X1)");
		checkEntry(0, "name",
				"                   \nname = raw_input(\"Name?\")\n\n",
				expectedReplacementOffset, expectedReplacementLength);
	}

	@Test
	public void testAskXtoXVariable() {
		int expectedReplacementOffset = 0;
		int expectedReplacementLength = 30;
		addEntry("ask \"Name?\" to name variable", "ask X to X variable",
				"X2 = raw_input(X1)");
		checkEntry(0, "variable",
				"                            \nname = raw_input(\"Name?\")\n\n",
				expectedReplacementOffset, expectedReplacementLength);
	}

	@Test
	public void testIfXThenXElseXDone() {
		int expectedReplacementOffset = 0;
		int expectedReplacementLength = 45;
		addEntry("if x>0 then print \">\" else print \"not\" done",
				"if X then X else X done", "if X1:\n\tX2\nelse:\n\tX3");
		checkEntry(
				0,
				// "\"not\" done",
				"done",
				"                                           \nif x>0:\n\tprint \">\"\nelse:\n\tprint \"not\"\n\t\n\t",
				expectedReplacementOffset, expectedReplacementLength);
	}

	@Test
	public void testIfXThenXElseX() {
		int expectedReplacementOffset = 0;
		int expectedReplacementLength = 40;
		addEntry("if x>0 then print \">\" else print \"not\"",
				"if X then X else X", "if X1:\n\tX2\nelse:\n\tX3");
		checkEntry(
				0,
				"\"not\"",
				"                                      \nif x>0:\n\tprint \">\"\nelse:\n\tprint \"not\"\n\t\n\t",
				expectedReplacementOffset, expectedReplacementLength);
	}

	@Test
	public void testIfXThenXElseXDefaults() {
		int expectedReplacementOffset = 0;
		int expectedReplacementLength = 4;
		addEntry("if",
				"if X then X else X", "if X1:\n\tX2\nelse:\n\tX3",
				"X1=x>0\nX2=print \">\"\nX3=print \"not\"");
		checkEntry(
				0,
				"if X then X else X",
				"  \nif x>0:\n\tprint \">\"\nelse:\n\tprint \"not\"\n\t\n\t",
				expectedReplacementOffset, expectedReplacementLength);
	}

	@Test
	public void testIfXThenXElifX() {
		int expectedReplacementOffset = 0;
		int expectedReplacementLength = 50;
		addEntry("if x>0 then print \">\" else if x<0 then print \"<\"",
				"if X then X( else if X then X)*",
				"if X1:\n\tX2\n(elif X3:\n\tX4)*");
		checkEntry(
				0,
				"\"<\"",
				"                                                \nif x>0:\n\tprint \">\" else if x<0 then print \"<\"\n\t\n\t",
				expectedReplacementOffset, expectedReplacementLength);
		checkEntry(
				1,
				"\"<\"",
				"                                                \nif x>0:\n\tprint \">\"\nelif x<0:\n\tprint \"<\"\n\t\n\t",
				expectedReplacementOffset, expectedReplacementLength);
	}

	@Test
	public void testIfXThenXElseXQuestionMark1() {
		int expectedReplacementOffset = 0;
		int expectedReplacementLength = 23;
		addEntry("if x>0 then print \">\"", "if X then X( else X)?",
				"if X1:\n\tX2\n(else:\n\tX3)?");
		checkEntry(0, "\">\"",
				"                     \nif x>0:\n\tprint \">\"\n\t\n\t",
				expectedReplacementOffset, expectedReplacementLength);
		checkEntry(1, "\">\" else X",
				"                     \nif x>0:\n\tprint \">\"\nelse:\n\tX3\n\t\n\t",
				expectedReplacementOffset, expectedReplacementLength);
	}

	@Test
	public void testIfXThenXElseXQuestionMark2() {
		int expectedReplacementOffset = 0;
		int expectedReplacementLength = 40;
		System.out.println("hi");

		addEntry("if x>0 then print \">\" else print \"not\"",
				"if X then X( else X)?", "if X1:\n\tX2\n(else:\n\tX3)?");
		// Proposal[0] not ideal...
		checkEntry(
				0,
				"\"not\"",
				"                                      \nif x>0:\n\tprint \">\" else print \"not\"\n\t\n\t",
				expectedReplacementOffset, expectedReplacementLength);
		checkEntry(
				1,
				"\"not\"",
				"                                      \nif x>0:\n\tprint \">\"\nelse:\n\tprint \"not\"\n\t\n\t",
				expectedReplacementOffset, expectedReplacementLength);
	}

	// @Test
	// public void testIfXThenXElseXStar1() {
	// int expectedReplacementOffset = 0;
	// int expectedReplacementLength = 23;
	// addEntry("if x>0 then print \">\"",
	// "if X then X(( else if X then X)* else X)?",
	// "if X1:\n\tX2\n((\nelif X1*:\n\tX2*)*\nelse:\n\tX3)?");
	// checkEntry(0, "\">\"",
	// "if x>0:\n\tprint \">\"\n                     \n",
	// expectedReplacementOffset, expectedReplacementLength);
	// checkEntry(1, "\">\" else X",
	// "if x>0:\n\tprint \">\"\nelse:\n\tX3                     \n",
	// expectedReplacementOffset, expectedReplacementLength);
	// }


	// @Test
	// public void testIfXThenXElseXStar1() {
	// int expectedReplacementOffset = 0;
	// int expectedReplacementLength = 23;
	// addEntry("if x>0 then print \">\"",
	// "if X then X(( else if X then X)* else X)?",
	// "if X1:\n\tX2\n((\nelif X1*:\n\tX2*)*\nelse:\n\tX3)?");
	// checkEntry(0, "\">\"",
	// "if x>0:\n\tprint \">\"\n                     \n",
	// expectedReplacementOffset, expectedReplacementLength);
	// checkEntry(1, "\">\" else X",
	// "if x>0:\n\tprint \">\"\nelse:\n\tX3                     \n",
	// expectedReplacementOffset, expectedReplacementLength);
	// }


	public void addEntry(String typed, String template,
			String templateReplacementString) {
		addEntry( typed,  template,
				 templateReplacementString, "");
	}
	
	public void addEntry(String typed, String template,
			String templateReplacementString, String defaults) {
		String language = "english";
		String displayString = template;
		try {
			// String replacementString =
			// " //\\ //// code completion entries (1+ lines):...";
			String additionalProposalInfo = " //\\ //// code completion entries (1+ lines):...";
			String message = "Make needed changes then save template to appropriately named .txt file.";
			computer.testingLine = typed;
			computer.addEntry(language, displayString, typed,
					templateReplacementString, additionalProposalInfo, message,
					defaults, proposals);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void checkEntry(int entry, String expectedOutput,
			String expectedReplacementString, int expectedReplacementOffset,
			int expectedReplacementLength) {
		boolean match = true; // default
		checkEntry(entry, expectedOutput, expectedReplacementString,
				expectedReplacementOffset, expectedReplacementLength, match);
	}

	public void checkEntry(int entry, String expectedOutput,
			String expectedReplacementString, int expectedReplacementOffset,
			int expectedReplacementLength, Boolean match) {
		if (proposals.size() == 0 && match == false) {
			return;
		}
		try {

			ICompletionProposal proposal = proposals.get(entry);

//			boolean isInstance = proposal instanceof JavaCompletionProposal;
			boolean isInstance = true;
			if (isInstance) {
//				assertEquals("ReplacementOffsets must match",
//						expectedReplacementOffset,
//						((JavaCompletionProposal) proposal)
//								.getReplacementOffset());

				Class<?> myClass = proposal.getClass();
				Field myField = getField(myClass, "fReplacementLength");
				myField.setAccessible(true); // required if field is not
												// normally accessible

				System.out.println("proposal.fReplacementLength: "
						+ myField.get(proposal));
//				assertEquals("ReplacementLengths must match",
//				// expectedReplacementLength,
//				// actualReplacementString.length());
//						expectedReplacementLength, myField.get(proposal));

				myField = getField(myClass, "fReplacementString");
				myField.setAccessible(true); // required if field is not
												// normally accessible
				System.out.println("proposal.fReplacementString: "
						+ myField.get(proposal));
				String actualReplacementString = (String) myField.get(proposal);
				assertEquals("ReplacementStrings must match",
						expectedReplacementString, actualReplacementString);

			}
			assertEquals("Display strings must match", expectedOutput,
					proposal.getDisplayString());
			System.out.println("Proposals Computer.");
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static Field getField(Class<?> clazz, String fieldName)
			throws NoSuchFieldException {
		try {
			return clazz.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			Class<?> superClass = clazz.getSuperclass();
			if (superClass == null) {
				throw e;
			} else {
				return getField(superClass, fieldName);
			}
		}
	}

//	// @Test -- skip this for now... it's a system test...
//	public void SystemTestComputer() throws IOException {
//
//		// Open a file so we have text to work with...
//
//		File fileToOpen = new File("/tmp/externalfile.txt");
//		if (!fileToOpen.exists()) {
//			fileToOpen.createNewFile();
//		}
//
//		if (fileToOpen.exists() && fileToOpen.isFile()) {
//			IFileStore fileStore = EFS.getLocalFileSystem().getStore(
//					fileToOpen.toURI());
//			IWorkbenchPage page = PlatformUI.getWorkbench()
//					.getActiveWorkbenchWindow().getActivePage();
//
//			try {
//				IDE.openEditorOnFileStore(page, fileStore);
//			} catch (PartInitException e) {
//				// Put your exception handler here if you wish to
//				e.printStackTrace();
//			}
//		} else {
//			// Do something if the file does not exist
//		}
//
//	}

}
