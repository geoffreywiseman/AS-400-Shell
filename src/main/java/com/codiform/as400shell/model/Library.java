package com.codiform.as400shell.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.access.IFSFile;

public class Library {
	String name;
	String ifsPath;
	List<String> dataAreas;
	List<String> sqlPackages;
	List<LibraryFile> files;
	List<String> others;
	
	private IFSFile rootFile;

	public Library( String libraryName, IFSFile file ) throws IOException, AS400SecurityException {
		this.name = libraryName;
		this.rootFile = file;
		
		ifsPath = file.getAbsolutePath();
		
		dataAreas = new ArrayList<String>();
		files = new ArrayList<LibraryFile>();
		others = new ArrayList<String>();
		sqlPackages = new ArrayList<String>();
		
		analyzeContents( file.list() );
		
	}
	
	private void analyzeContents( String[] list ) throws IOException, AS400SecurityException {
		for( String item : list ) {
			if( item.endsWith( ".DTAARA" ) ) {
				addDataArea( item );
			} else if( item.endsWith( ".FILE" ) ) {
				addFile( item );
			} else if( item.endsWith( ".SQLPKG" ) ) {
				addSqlPackage( item );
			} else {
				addOther( item );
			}
		}
	}

	private void addSqlPackage( String item ) {
		sqlPackages.add( item );
	}

	private void addOther( String item ) {
		others.add( item );
	}

	private void addFile( String item ) throws IOException, AS400SecurityException {
		LibraryFile fileItem = new LibraryFile( rootFile, item );
		files.add( fileItem );
	}

	private void addDataArea( String item ) {
		dataAreas.add( item );
	}

	public String getName() {
		return name;
	}
	
	public String getPath() {
		return ifsPath;
	}

	public List<LibraryFile> getFiles() {
		return files;
	}
	
	public List<String> getDataAreas() {
		return dataAreas;
	}
	
	public List<String> getOthers() {
		return others;
	}

	public List<String> getSqlPackages() {
		return sqlPackages;
	}

	public SortedSet<LibraryFile> getMultiMemberFiles() {
		SortedSet<LibraryFile> sorted = new TreeSet<LibraryFile>(new NumberOfMembersComparator());
		for( LibraryFile item : files ) {
			if( item.isMultiMember() ) {
				sorted.add( item );
			}
		}
		return sorted;
	}

}
