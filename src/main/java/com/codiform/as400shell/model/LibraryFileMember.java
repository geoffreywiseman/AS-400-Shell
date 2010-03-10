package com.codiform.as400shell.model;

import java.io.IOException;

import com.ibm.as400.access.AS400Exception;
import com.ibm.as400.access.AS400FileRecordDescription;
import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.access.IFSFile;
import com.ibm.as400.access.RecordFormat;
import com.ibm.as400.access.SequentialFile;

public class LibraryFileMember {
	private String name;
	private IFSFile ifs;

	public LibraryFileMember( IFSFile parent, String memberName ) {
		name = memberName;
		ifs = new IFSFile( parent.getSystem(), parent, memberName );
	}
	
	public String getName() {
		return name;
	}
	
	public String getAbsolutePath() {
		return ifs.getAbsolutePath();
	}
	
	public SequentialFile getSequentialFile() {
		return new SequentialFile( ifs.getSystem(), ifs.getAbsolutePath() );
	}

	public RecordFormat[] getRecordFormats() throws AS400Exception, AS400SecurityException, InterruptedException, IOException {
		AS400FileRecordDescription description = new AS400FileRecordDescription( ifs.getSystem(), ifs.getAbsolutePath() );
		return description.retrieveRecordFormat();
	}
	
	public String toString() {
		return name;
	}
}
