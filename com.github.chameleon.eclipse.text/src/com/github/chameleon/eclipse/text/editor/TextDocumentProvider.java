package com.github.chameleon.eclipse.text.editor;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.editors.text.FileDocumentProvider;

//import com.github.chameleon.eclipse.text.editors.XMLPartitionScanner;

public class TextDocumentProvider extends FileDocumentProvider {
 

	protected IDocument createDocument(Object element) throws CoreException {
		IDocument document = super.createDocument(element);
//		if (document != null) {
//			IDocumentPartitioner partitioner =
//				new FastPartitioner(
//					new XMLPartitionScanner(),
//					new String[] {
//						XMLPartitionScanner.XML_TAG,
//						XMLPartitionScanner.XML_COMMENT });
//			partitioner.connect(document);
//			document.setDocumentPartitioner(partitioner);
//		}
		return document;
	}
}
