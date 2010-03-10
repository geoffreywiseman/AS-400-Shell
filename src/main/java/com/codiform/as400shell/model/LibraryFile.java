package com.codiform.as400shell.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.access.IFSFile;

public class LibraryFile {
	private String name;
	private IFSFile ifs;
	private List<LibraryFileMember> members;
	private FileType type;

	public LibraryFile( IFSFile parent, String name ) throws IOException, AS400SecurityException {
		this.name = name;
		this.ifs = new IFSFile( parent.getSystem(), parent, name );
		analyze();
	}

	private void analyze() throws IOException, AS400SecurityException {
		if ( !ifs.exists() ) {
			throw new IllegalArgumentException( "File " + ifs.getAbsolutePath() + " does not exist." );
		} else if ( !ifs.isDirectory() ) {
			throw new IllegalArgumentException( "Library 'file' " + name + " is not a directory; it should be." );
		}

		members = new ArrayList<LibraryFileMember>();
		for ( String item : ifs.list() ) {
			members.add( new LibraryFileMember( ifs, item ) );
		}

		type = FileType.fromCode( ifs.getSubtype() );
	}

	public String toString() {
		return name;
	}

	public List<LibraryFileMember> getMembers() {
		return members;
	}

	public boolean isMultiMember() {
		return getMembers().size() > 1;
	}

	public String getName() {
		return name;
	}

	public FileType getType() {
		return type;
	}

}
