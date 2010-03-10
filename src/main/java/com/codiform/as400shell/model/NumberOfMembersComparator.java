package com.codiform.as400shell.model;

import java.util.Comparator;

public class NumberOfMembersComparator implements Comparator<LibraryFile> {

	@Override
	public int compare( LibraryFile one, LibraryFile other ) {
		int sizeComparison = one.getMembers().size() - other.getMembers().size();
		if( sizeComparison != 0 )
			return sizeComparison;
		else
			return one.toString().compareTo( other.toString() );
	}

}
