package com.github.chameleon.eclipse.text.editor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

public class TextConfiguration extends SourceViewerConfiguration {
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer)
	{

	    ContentAssistant assistant = new ContentAssistant();

	    IContentAssistProcessor tagContentAssistProcessor 
	        = new TagContentAssistProcessor();//getXMLTagScanner());
	    assistant.setContentAssistProcessor(tagContentAssistProcessor,
	            //"XMLPartitionScanner.XML_START_TAG"
	    		IDocument.DEFAULT_CONTENT_TYPE);
	    assistant.enableAutoActivation(true);
	    assistant.setAutoActivationDelay(500);
	    assistant.setProposalPopupOrientation(IContentAssistant.CONTEXT_INFO_BELOW);
	    assistant.setContextInformationPopupOrientation(IContentAssistant.CONTEXT_INFO_BELOW);
	    return assistant;

	}
}
