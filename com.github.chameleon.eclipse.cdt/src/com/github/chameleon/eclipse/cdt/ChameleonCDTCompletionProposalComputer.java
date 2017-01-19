package com.github.chameleon.eclipse.cdt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
//import org.eclipse.cdt.internal.ui.editor.CEditor;
import org.eclipse.cdt.ui.text.contentassist.ContentAssistInvocationContext;
import org.eclipse.cdt.ui.text.contentassist.ICompletionProposalComputer;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.ui.PlatformUI;

import com.github.chameleon.core.ChameleonCompletionProposalComputer;
import com.github.chameleon.core.DocumentInfo;
//import com.github.chameleon.eclipse.java.JavaCompletionProposalBuilder;
//import com.github.chameleon.eclipse.java.JavaDocumentInfo;

// CDT imports (C/C++ Development Tools)

/* TO DO:
 * TODO Host on GitHub
 * TODO Set up update site: http://wiki.eclipse.org/FAQ_How_do_I_create_an_update_site_%28site.xml%29%3F
 * TODO Organization--Enable packaging--Team specific templates + Finance + Cloud + web + Programming Language Mapping + Learning Specific (commented) templates, etc...
 *
 * TODO Get JavaScript back up-to-date with Java version
 * 
 * TODO Add tags to templates to describe each field e.g. //\\ Sentence: (This is what the user sees when they start typing...)
 * TODO Fix performance (don't load files each time they type...)
 * TODO + dictionary (dynamic) keyword to list all keywords
 * TODO + synonyms (dynamic) keyword to list all synonym keywords and what they refer to
 * TODO + "What did you mean?" default action to add new synonyms to existing templates
 * TODO + "What did you mean?" default action to add new synonyms to new templates
 * TODO + Dynamic templates e.g. print "Hello World!"
 * TODO + Settings to select which groupings you want (e.g. check Spring, so non-Spring REST commands do NOT show up, unless specifically selected in settings)
 * 
 * TODO + C/C++ Support (Workstations; Peter familiar with CDT).
 * TODO + Python Support (Cloud; OpenStack; OneView)
 * TODO + PowerShell (OneView)
 * TODO + VisualBasic, SQL?, C#, Java, Perl and also scripts within Excel and Word (VB?) (IT/ALM)
 * TODO + ABAP programming language support (for SAP)
 * TODO + Hadoop/Pig/Hive/etc. big data language support...
 * TODO Add GUI to add new templates
 * TODO Add support for IntelliJ
 */

/* DONE:
 * Added //\\ (Chaemeleon legs) as new field delimeter instead of newline
 */

public class ChameleonCDTCompletionProposalComputer extends
		ChameleonCompletionProposalComputer implements
		ICompletionProposalComputer {
	final static String DELIMETER = "//\\\\"; // (//\\) = Delimiter for fields

	@Override
	public void sessionStarted() {
	}

	@Override
	public List<ICompletionProposal> computeCompletionProposals(
			final ContentAssistInvocationContext context,
			final IProgressMonitor monitor) {

		if (PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().getActiveEditor().getTitle().toLowerCase()
				.endsWith(".c")) {
			PROGRAMMING_LANGUAGE_GENERIC_VERSION = "c";
		} else {
			PROGRAMMING_LANGUAGE_GENERIC_VERSION = "cpp";
		}
		PROGRAMMING_LANGUAGE = "cdt";
		BUNDLE = "com.github.chameleon.eclipse.cdt";
		console = false;
		return getAllCompletionsList();
	}

	@Override
	public DocumentInfo getDocumentInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String addExpandedEntry(
			final String language, String displayString,
			String typedString,
			String replacementString, String additionalProposalInfo,
			final String message,
			final String defaults,
			final Collection<ICompletionProposal> proposals)
			throws IOException, BadLocationException {
		String tempReplacementString = "";
		String returnReplacementString = "";
		if ( language.contains("Intermediate") && typedString == "") {
			System.out.println("Intermediate");
			//For each line... substitute...
			BufferedReader bufReader = new BufferedReader(new StringReader(replacementString));
			String line = null;
			replacementString = "";
			while( (line=bufReader.readLine()) != null){
				returnReplacementString = "";
				ArrayList<ICompletionProposal> completions = new ArrayList<ICompletionProposal>();
				tempReplacementString = getCompletionsFromDirectory(pluginLanguagesDirectory, line, completions);
				if ( returnReplacementString == "") {
					returnReplacementString = tempReplacementString;
				}
				tempReplacementString = getCompletionsFromDirectory(homeLanguagesDirectory, line, completions);
				if ( returnReplacementString == "") {
					returnReplacementString = tempReplacementString;
				}
				if ( returnReplacementString != "" ) {
					replacementString += returnReplacementString+"\n";							
				}
			}
		}
		try {
			CDTDocumentInfo docInfo = new CDTDocumentInfo(testing, testingLine, testingOffset);
			docInfo.typedString = typedString;
			CDTCompletionProposalBuilder builder = new CDTCompletionProposalBuilder(
					language, displayString, replacementString,
					additionalProposalInfo, message, defaults, proposals, testing,
					testingLine, testingOffset, console, docInfo, typedString);
			if (builder.isMatch()) {
				if ( returnReplacementString == "") {
					returnReplacementString = replacementString;
				}
				CompletionProposal proposal = builder.createProposal();
				proposals.add(proposal);
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return returnReplacementString;
	}


	@Override
	public List<IContextInformation> computeContextInformation(
			ContentAssistInvocationContext context, IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sessionEnded() {
		// TODO Auto-generated method stub
		
	}

}
