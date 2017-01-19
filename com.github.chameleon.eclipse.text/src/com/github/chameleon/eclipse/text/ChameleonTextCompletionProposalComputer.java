package com.github.chameleon.eclipse.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.ui.text.java.ContentAssistInvocationContext;
import org.eclipse.jdt.ui.text.java.IJavaCompletionProposalComputer;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;

import com.github.chameleon.core.ChameleonCompletionProposalComputer;
import com.github.chameleon.core.DocumentInfo;
import com.github.chameleon.eclipse.text.TextDocumentInfo;

/* TO DO:
 * TODO Refactor code (long methods)
 * TODO Fix performance (don't load files each time they type...)
 * 
 * TODO TEMPLATES:
 * TODO + contributor field original templates (e.g. email address)
 * TODO + remove template core/printXOops.txt (because) X now
 * TODO + update template core/printXOops.txt (just a save that overwrites?)
 * TODO + show template X (e.g. show template core/printXln.txt -- with assistance as you type, listing the options...)
 * TODO + set synonym
 * TODO + remove synonym
 * TODO + list generated (& Static) templates
 * 
 * TODO Get JavaScript & Java back up-to-date with Python version
 * TODO Add tags to templates to describe each field e.g. //\\ Sentence: (This is what the user sees when they start typing...)
 * TODO + dictionary (dynamic) keyword to list all keywords
 * TODO + synonyms (dynamic) keyword to list all synonym keywords and what they refer to
 * TODO + "What did you mean?" default action to add new synonyms to existing templates
 * TODO + "What did you mean?" default action to add new synonyms to new templates
 * TODO + Settings to select which groupings you want (e.g. check Spring, so non-Spring REST commands do NOT show up, unless specifically selected original settings)
 * 
 * TODO Natural Language Additions:
 * TODO + and support
 * TODO + it/the/a new support
 * TODO + ignore words support (Open file, or open a new file, or open a file, etc.)
 * TODO noun, verb support (e.g. File, open vs. open file)
 *
 * TODO Programming Language Support:
 * DONE Java -- HP Helion Dev Platform
 * DONE Python -- HP Helion Dev Platform
 * TODO Ruby -- HP Helion Dev Platform
 * TODO PHP -- HP Helion Dev Platform
 * TODO Node.js -- HP Helion Dev Platform
 * TODO MySQL -- HP Helion Dev Platform
 * TODO RabbitMQ -- HP Helion Dev Platform
 * TODO MemCached -- HP Helion Dev Platform
 * DONE + C/C++ Support (Workstations; Peter familiar with CDT).
 * DONE + Python Support (Cloud; OpenStack; OneView)
 * TODO + PowerShell (OneView)
 * TODO + VisualBasic, SQL?, C#, Java, Perl and also scripts within Excel and Word (VB?) (IT/ALM)
 * TODO + ABAP programming language support (for SAP)
 * TODO + Hadoop/Pig/Hive/etc. big data language support...
 * TODO + GO language support
 * TODO + Rust language support
 * TODO + R language support
 * TODO + Ruby (base for Puppet, Vagrant)
 * TODO + PSON: JSON for Puppet
 * TODO + SQL support
 * TODO + GUI to add new templates
 * 
 * TODO IDE Support:
 * TODO + support for IntelliJ
 * TODO + support for Atom.io editor
 * TODO + support for Visual Studio
 * 
 * TODO Host on GitHub
 * 
 * TODO Fix foreach (1,2,3)
 * TODO Default values
 */

/* DONE:
 * DONE JUnits
 * 2014:
 * Added //\\ (Chaemeleon legs) as new field delimiter instead of newline
 * Set up update site: http://wiki.eclipse.org/FAQ_How_do_I_create_an_update_site_%28site.xml%29%3F
 * 2015:
 * Use chameleon icon
 * Add _template.txt template file
 * Organization--Enable packaging--Team specific templates + Finance + Cloud + web + Programming Language Mapping + Learning Specific (commented) templates, etc...
 * + Dynamic templates e.g. print "Hello World!"
 * Resolved issue of updating and losing your created templates... (save added templates to user's home directory/chameleon)
 * DON'T upload (_)private templates
 * 2015-08-17 MOVE _synonyms.txt to individual template files
 */

/* HOW-TO DEPLOY:
 * 
 * See: http://www.vogella.com/tutorials/EclipsePlugIn/article.html#p2deployplugin
 * 
 * NOTE: If you make a change and re-export, to see changes such
 *       as showing up under a category requires RESTARTING ECLIPSE!
 * 
 * 1. DELETE the content original E:/test
 * 2. Export com.github.chameleon.core jar file (If out-of-sync, clean all first...)
 * 3. Export com.github.chameleon.update_site 
 *      Select Plug-original Development | Deployable features
 *        Check features (e.g. com.github.chameleon.eclipse.python.feature)
 *        Destination: Directory: E:/test (where E: maps to: \\tntattach.us.rdlabs.hpecorp.net\ )
 *        Options:
 *           * UNCHECK - Export Source
 *           * CHECK   - Package as individual JAR archives
 *           * CHECK   - Generate p2 repository
 *           * CHECK   - Categorize repository. Click Browse and select: category - com.github.chameleon.eclipse.update_site
 *           * UNCHECK - Qualifier replacement (default values is today's date)
 * 	         * CHECK   - Allow for binary cycles original target platform
 * 	         * CHECK   - Use class files compiled original the workspace
 *
 * 4. Once it has been verified, copy it to E:/latest and/or E:/stable.
 * 
 */

public class ChameleonTextCompletionProposalComputer extends
	ChameleonCompletionProposalComputer implements
	IJavaCompletionProposalComputer  {

	public ChameleonTextCompletionProposalComputer(String replacementString,
			int replacementOffset, int replacementLength, int cursorPosition,
			int priority) {
		super();
	}
	
	public ChameleonTextCompletionProposalComputer() {
		System.out.println("ChameleonTextCompletionProposalComputer()");
		BUNDLE = "com.github.chameleon.eclipse.text";
		PROGRAMMING_LANGUAGE = "text";
	}

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
			TextDocumentInfo docInfo = new TextDocumentInfo(testing, testingLine, testingOffset);
			docInfo.typedString = typedString;
			TextCompletionProposalBuilder builder = new TextCompletionProposalBuilder(
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
	public List<ICompletionProposal> computeCompletionProposals(
			final ContentAssistInvocationContext context,
			final IProgressMonitor monitor) {
		BUNDLE = "com.github.chameleon.eclipse.text";
		PROGRAMMING_LANGUAGE = "java";
		console = false;
		return getAllCompletionsList();
	}


	@Override
	public void sessionStarted() {
		// TODO Auto-generated method stub
	}

	@Override
	public List<IContextInformation> computeContextInformation(
			ContentAssistInvocationContext context, IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sessionEnded() {
		// TODO Auto-generated method stub
	}

	@Override
	public DocumentInfo getDocumentInfo() {
		try {
			return new TextDocumentInfo(testing, testingLine, testingOffset);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return null;
	}
	
    public String getErrorMessage()
    {
        return "Error message";
    }

}
