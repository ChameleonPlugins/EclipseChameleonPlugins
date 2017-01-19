package com.github.chameleon.eclipse.text.editor;

import java.util.ResourceBundle;

import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.TextOperationAction;

import com.github.chameleon.eclipse.text.activator.Activator;


public class TextTextEditor extends TextEditor {
	TextTextEditor() {
		setSourceViewerConfiguration(new TextConfiguration());
		setDocumentProvider(new TextDocumentProvider());		
	}
	/**
	 * Needed for content assistant
	 */
	protected void createActions()
	{
		super.createActions();
		ResourceBundle bundle = Activator.getDefault().getResourceBundle();
		setAction("ContentFormatProposal", new TextOperationAction(bundle, "ContentFormatProposal.", this,
				ISourceViewer.FORMAT));
		setAction("ContentAssistProposal", new TextOperationAction(bundle, "ContentAssistProposal.", this,
				ISourceViewer.CONTENTASSIST_PROPOSALS));
		setAction("ContentAssistTip", new TextOperationAction(bundle, "ContentAssistTip.", this,
				ISourceViewer.CONTENTASSIST_CONTEXT_INFORMATION));

	}
	
}
