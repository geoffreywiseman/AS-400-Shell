package com.codiform.as400shell.command;

import com.codiform.as400shell.shell.ShellContext;
import com.ibm.as400.access.IFSFile;

/**
 * Displays a list of commands.
 */
public class ListCommand implements Command {

	@Override
	public void execute(ShellContext context, String[] arguments) {
		switch( arguments.length ) {
		case 0:
			list( context, "/" );
			break;
		case 1:
			list( context, arguments[0] );
			break;
		default:
			context.out().printf(
					"Cannot list more than one path at once." );
		}
	}

	private void list(ShellContext context, String path) {
		IFSFile file = new IFSFile( context.getServer(), path );
		try {
			if( file.exists() ) {
				displayPathInfo( context, file );
				if( file.isDirectory() ) {
					displayChildren( context, file.listFiles() );
				}
			} else {
				context.err().println( "Cannot find specified path." );
			}
		} catch( Exception exception ) {
			exception.printStackTrace( context.err() );
		}
	}

	private void displayChildren(ShellContext context, IFSFile[] list) {
		for( IFSFile child : list ) {
			displayChild( context, child );
		}
	}

	private void displayChild(ShellContext context, IFSFile child) {
		context.out().printf( "\t%s\n", child.getName() );
	}

	private void displayPathInfo(ShellContext context, IFSFile file) {
		context.out().printf( "Path: %s\n", file.getCanonicalPath() );
	}

	@Override
	public void displayHelp(ShellContext context) {
		context.out().println(
				"SYNTAX: ls [path]\nDisplays information about the specified (or current) path and the children of it." );
	}

}
