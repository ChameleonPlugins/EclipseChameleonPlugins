package com.github.chameleon.eclipse.python;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.BadLocationException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.ui.texteditor.ITextEditor;
import org.python.pydev.debug.newconsole.PydevConsole;
import org.python.pydev.shared_interactive_console.console.ScriptConsoleHistory;

import com.github.chameleon.core.DocumentInfo;

public class PythonDocumentInfo extends DocumentInfo {
	
	public PythonDocumentInfo(boolean testing, String testingLine, int testingOffset) throws CoreException {
		super(testing, testingLine, testingOffset);
		getDocumentInfo();
	}
	
	public void getDocumentInfo() throws CoreException {
		super.getDocumentInfo();
		if (!testing ) {
			if (console ) {
				PydevConsole myConsole = (PydevConsole) PydevConsole
						.getActiveScriptConsole();
				ScriptConsoleHistory history = myConsole.getHistory();
				editor = (ITextEditor) editorPart.getAdapter(ITextEditor.class);
				documentTextSelection = (ITextSelection) editorPart.getSite()
						.getSelectionProvider().getSelection();
				document = history.getAsDoc();
				// globalLine = history.get();
				globalLine = history.getBufferLine();
				String session = myConsole.getSession().toString();
				lineNumber = countLines(session);
				lineLength = globalLine.length();
				globalOffset = session.length()
						+ myConsole.getPrompt().toString().length()
						+ history.getAsList().size() + 2;

				// globalOffset += globalLine.length();
			}
		} else {
			setGlobalLine(testingLine);
			globalOffset = testingOffset;
		}
	}
	private static int countLines(String str) {
		String[] lines = str.split("\r\n|\r|\n");
		return lines.length;
	}
	public String getGlobalLine() {
		return globalLine;
	}

	public void setGlobalLine(String globalLine) {
		this.globalLine = globalLine;
	}
	
	protected Matcher matchLastToken(final String pattern)
			throws BadLocationException {
		final Pattern LINE_DATA_PATTERN = Pattern.compile(pattern);
		// final String data = getCurrentLine();
		final Matcher matcher = LINE_DATA_PATTERN.matcher(globalLine);
		matcher.matches();
		return matcher;
	}
	


}

