package com.github.chameleon.eclipse.text.editor;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.ui.text.java.ContentAssistInvocationContext;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

import com.github.chameleon.eclipse.text.ChameleonTextCompletionProposalComputer;

//import com.github.chameleon.eclipse.text.editors.TagContentAssistProcessor.TextInfo;

public class TagContentAssistProcessor implements IContentAssistProcessor {

	ChameleonTextCompletionProposalComputer computer = null;
	
	@Override
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
			int offset) {
		if (computer == null) {
			computer = new ChameleonTextCompletionProposalComputer();
		} 
		List<ICompletionProposal> resultList = computer.getAllCompletionsList();
		// TODO Auto-generated method stub

//		IDocument document = viewer.getDocument();
//		TextInfo currentText = currentText(document, offset);
//		String text = "Hi Trevor";
//		ICompletionProposal[] result = new ICompletionProposal[1]; 
//		result[0] = new CompletionProposal(text,
//			currentText.documentOffset, currentText.text.length(),
//			text.length());
		
//		BUNDLE = "com.github.chameleon.eclipse.text";
//		PROGRAMMING_LANGUAGE = "text";
//		console = false;
//		return getAllCompletionsList();
		
		
		
		ICompletionProposal [] resultArray = resultList.toArray(new ICompletionProposal[resultList.size()]);
		return resultArray;
	}

//	@Override
//	public List<ICompletionProposal> computeCompletionProposals(
//			final ContentAssistInvocationContext context,
//			final IProgressMonitor monitor) {
//		BUNDLE = "com.github.chameleon.eclipse.text";
//		PROGRAMMING_LANGUAGE = "java";
//		console = false;
//		return getAllCompletionsList();
//	}
	
	
    private TextInfo currentText(IDocument document, int documentOffset)
    {

        try
        {

            ITypedRegion region = document.getPartition(documentOffset);

            int partitionOffset = region.getOffset();
            int partitionLength = region.getLength();

            int index = documentOffset - partitionOffset;

            String partitionText = document.get(partitionOffset, partitionLength);

            System.out.println("Partition text: " + document.get(partitionOffset, region.getLength()));
            char c = partitionText.charAt(index);

            if (Character.isWhitespace(c) || Character.isWhitespace(partitionText.charAt(index - 1)))
            {
                return new TextInfo("", documentOffset, true);
            }
            else if (c == '<')
            {
                return new TextInfo("", documentOffset, true);
            }
            else
            {
                int start = index;
                c = partitionText.charAt(start);

                while (!Character.isWhitespace(c) && c != '<' && start >= 0)
                {
                    start--;
                    c = partitionText.charAt(start);
                }
                start++;

                int end = index;
                c = partitionText.charAt(end);

                while (!Character.isWhitespace(c) && c != '>' && end < partitionLength - 1)
                {
                    end++;
                    c = partitionText.charAt(end);
                }

                String substring = partitionText.substring(start, end);
                return new TextInfo(substring, partitionOffset + start, false);

            }

        }
        catch (BadLocationException e)
        {
            e.printStackTrace();
        }
        return null;
    }

	@Override
	public IContextInformation[] computeContextInformation(ITextViewer viewer,
			int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public char[] getCompletionProposalAutoActivationCharacters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public char[] getContextInformationAutoActivationCharacters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IContextInformationValidator getContextInformationValidator() {
		// TODO Auto-generated method stub
		return null;
	}
    static class TextInfo
    {
        TextInfo(String text, int documentOffset, boolean isWhiteSpace)
        {
            this.text = text;
            this.isWhiteSpace = isWhiteSpace;
            this.documentOffset = documentOffset;
        }

        String text;

        boolean isWhiteSpace;

        int documentOffset;
    }

}
