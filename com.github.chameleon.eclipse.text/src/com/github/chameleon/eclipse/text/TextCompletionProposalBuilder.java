package com.github.chameleon.eclipse.text;

import java.util.Collection;

import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ContextInformation;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import com.github.chameleon.core.CompletionProposalBuilder;
import com.github.chameleon.core.DocumentInfo;

public class TextCompletionProposalBuilder extends CompletionProposalBuilder {

	final String BUNDLE = "com.github.chameleon.eclipse.text";
	final static String TARGET_LANGUAGE = "Java";
	final String COMMENT_STARTER = "//// ";

	public TextCompletionProposalBuilder(final String language,
			String displayString,
			String replacementString,
			String additionalProposalInfo, final String message,
			final String defaults,
			final Collection<ICompletionProposal> proposals, boolean testing,
			String testingLine, int testingOffset, boolean console, DocumentInfo docInfo, String typedString) {
		super(language, displayString, replacementString,
				additionalProposalInfo, message, defaults, proposals, testing,
				testingLine, testingOffset, console, docInfo, typedString);
	}

	protected String getCommentStarter() {
		return COMMENT_STARTER;
	}
	
	public CompletionProposal createProposal() {
		preCreateProposal();
		org.eclipse.swt.graphics.Image image = null;
		if (!testing) {
			image = new org.eclipse.swt.graphics.Image(getDisplay(),
					locateFile(BUNDLE, "icons/Chameleon.gif").getPath());
		}
		ContextInformation javaContextInformation = 
				new ContextInformation(help,
				contextInformation.getInformationDisplayString());
		replacementOffset = getReplacementOffset();

		// TEXT:
		replacementOffset -= replacementLength;
		
		CompletionProposal proposal =
                new CompletionProposal(paddedReplacementString, 
                		replacementOffset, replacementLength, 
                		paddedReplacementString.length(),
                        image, remainingString, javaContextInformation,
                        additionalProposalInfo);
        return proposal;
	}

	/**
	 * Get the current document information including: * Document *
	 * DocumentTextSelection
	 * 
	 * @throws CoreException
	 */
//	@Override
//	protected void getDocumentInfo() throws CoreException {
//		if (!testing) {
//			IEditorPart editorPart = PlatformUI.getWorkbench()
//					.getActiveWorkbenchWindow().getActivePage()
//					.getActiveEditor();
//			ITextEditor editor = (ITextEditor) editorPart
//					.getAdapter(ITextEditor.class);
//			IDocumentProvider provider = editor.getDocumentProvider();
//			document = provider.getDocument(editorPart.getEditorInput());
//			documentTextSelection = (ITextSelection) editorPart.getSite()
//					.getSelectionProvider().getSelection();
//
//			IEditorInput input = editorPart.getEditorInput();
//			if (input instanceof FileEditorInput) {
//				// IFile file = ((FileEditorInput) input).getFile();
//				// InputStream is = file.getContents();
//				// TODO get contents from InputStream
//			}
//
//			if (!console) {
//				globalOffset = documentTextSelection.getOffset();
//				globalLine = documentTextSelection.getText();
//				try {
//					lineNumber = document.getLineOfOffset(globalOffset);
//					lineLength = document.getLineLength(lineNumber);
//					globalLine = document.get(
//							document.getLineOffset(lineNumber), lineLength);
//				} catch (org.eclipse.jface.text.BadLocationException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			} else {
//				PydevConsole myConsole = (PydevConsole) PydevConsole
//						.getActiveScriptConsole();
//				ScriptConsoleHistory history = myConsole.getHistory();
//				editor = (ITextEditor) editorPart.getAdapter(ITextEditor.class);
//				documentTextSelection = (ITextSelection) editorPart.getSite()
//						.getSelectionProvider().getSelection();
//				document = history.getAsDoc();
//				// globalLine = history.get();
//				globalLine = history.getBufferLine();
//				String session = myConsole.getSession().toString();
//				lineNumber = countLines(session);
//				lineLength = globalLine.length();
//				globalOffset = session.length()
//						+ myConsole.getPrompt().toString().length()
//						+ history.getAsList().size() + 2;
//
//				// globalOffset += globalLine.length();
//			}
//		} else {
//			globalLine = testingLine;
//			globalOffset = testingOffset;
//		}
//	}

}
