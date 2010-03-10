package com.codiform.as400shell.command;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

import com.codiform.as400shell.model.Library;
import com.codiform.as400shell.model.LibraryFile;
import com.codiform.as400shell.shell.ShellContext;
import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.access.IFSFile;

public class ShowLibraryCommand extends ParsedArgumentCommand {

	public ShowLibraryCommand() {
		super( getParser() );
	}

	private static OptionParser getParser() {
		OptionParser parser = new OptionParser();
		parser.accepts( "multi-member", "Show how many files how multiple members, and what their member names are." );
		return parser;
	}

	@Override
	public void displayHelp(ShellContext context) {
		try {
			context.out().println(
					"SYNTAX: showLibrary [options] <Library Name>\nDisplays information about the specified library.\n" );
			parser.printHelpOn( context.out() );
		} catch( IOException exception ) {
			exception.printStackTrace( context.err() );
		}
	}

	@Override
	public void execute(ShellContext context, OptionSet options) {

		if( options.nonOptionArguments().size() == 1 ) {
			try {
				show( context, options );
			} catch( Exception exception ) {
				exception.printStackTrace( context.err() );
			}
		} else {
			displayHelp( context );
		}
	}

	public void show(ShellContext context, OptionSet options)
			throws IOException,
			AS400SecurityException {
		String libraryName = options.nonOptionArguments().get( 0 );
		IFSFile file = new IFSFile( context.getServer(), "/QSYS.LIB/"
				+ libraryName + ".LIB" );
		if( file.exists() ) {
			show( context, options, libraryName, file );
		} else {
			context.err().printf( "Library %s cannot be found at: %s\n",
					libraryName, file.getAbsolutePath() );
		}
	}

	private void show(ShellContext context, OptionSet options, String libraryName, IFSFile file)
			throws IOException,
			AS400SecurityException {
		Library lib = new Library( libraryName, file );

		context.out().printf( "Library %s (%s):\n\n", lib.getName(),
				lib.getPath() );
		context.out().printf(
				"Files: %d\nData Areas: %d\nSQL Packages: %d\nOther: %d (%s)\n\n",
				lib.getFiles().size(), lib.getDataAreas().size(), lib
						.getSqlPackages().size(), lib.getOthers().size(),
				lib.getOthers() );

		int totalMembers = 0;
		SortedSet<LibraryFile> files = lib.getMultiMemberFiles();
		for( LibraryFile item : files ) {
			totalMembers += item.getMembers().size();
		}
		context.out().printf(
				"%d of files have multiple members (an average of %f members per multi-member file, for a total of %d file members)",
				files.size(), ((float) totalMembers) / ((float) files.size()),
				totalMembers );

		if( options.has( "multi-member" ) ) {
			context.out().println(":");
		int tier = 0;
		Set<LibraryFile> filesInTier = new HashSet<LibraryFile>();
		for( LibraryFile item : files ) {
			if( item.getMembers().size() > tier ) {
				showTier( context, tier, filesInTier );
				filesInTier.clear();
			}
			tier = item.getMembers().size();
			filesInTier.add( item );
		}
		showTier( context, tier, filesInTier );} else {
			context.out().println(".");
		}
	}

	private void showTier(ShellContext context, int tier,
			Set<LibraryFile> filesInTier) {
		context.out().printf( "\t%d files have %d members:\n",
				filesInTier.size(),
				tier );
		for( LibraryFile item : filesInTier ) {
			context.out().printf( "\t\t%s: %s\n", item.getName(),
					item.getMembers() );
		}
	}
}
