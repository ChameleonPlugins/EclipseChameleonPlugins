package com.github.chameleon.eclipse.text;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.content.ITextContentDescriber;

public class Describer implements ITextContentDescriber {

	public Describer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int describe(InputStream contents, IContentDescription description)
			throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public QualifiedName[] getSupportedOptions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int describe(Reader contents, IContentDescription description)
			throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

}
