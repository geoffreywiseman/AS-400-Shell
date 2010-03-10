package com.codiform.as400shell.shell;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.codiform.as400shell.command.Command;
import com.codiform.as400shell.command.HelpCommand;
import com.codiform.as400shell.command.InfoCommand;
import com.codiform.as400shell.command.ListCommand;
import com.codiform.as400shell.command.QuitCommand;
import com.codiform.as400shell.command.ShowLibraryCommand;
import com.codiform.as400shell.command.UnknownCommand;
import com.ibm.as400.access.AS400;

/**
 * The main class responsible for the read-execute-print-loop cycle. Shell reads
 * in a line, locates a command to execute that line with, executes the command,
 * and repeats until a quit command has been executed.
 */
public class Shell {

	private AS400 server;
	private Map<String, Command> commands;

	public Shell(AS400 server) {
		this.server = server;
		this.commands = new HashMap<String, Command>();
		addCommand( "quit", new QuitCommand() );
		addCommand( "info", new InfoCommand() );
		addCommand( "ls", new ListCommand() );
		addCommand( "showLibrary", new ShowLibraryCommand() );

		addCommand( "help", new HelpCommand( commands ) );
	}

	private void addCommand(String commandName, Command command) {
		this.commands.put( commandName, command );
	}

	/**
	 * Starts the read-execute-print-loop loop.
	 * 
	 * @throws IOException
	 */
	public void repl() {
		ShellContext context = new ShellContext( System.in, System.out,
				System.err, server );
		do {
			printPrompt( context );
			try {
				respondToInput( context, getInput( context ) );
			} catch( IOException exception ) {
				exception.printStackTrace();
			}
		} while( context.shouldContinue() );
	}

	private void respondToInput(ShellContext context, String input) {
		if( input == null || input.length() == 0 ) {
			return;
		}

		String[] split = input.split( " " );
		if( split.length == 0 ) {
			return;
		}

		Command command = lookupCommand( split[0] );
		command.execute( context, getArguments( split ) );
		context.out().flush();
		context.err().flush();
	}

	private String[] getArguments(String[] split) {
		if( split.length == 1 ) {
			return new String[0];
		} else {
			return Arrays.copyOfRange( split, 1, split.length );
		}
	}

	private Command lookupCommand(String string) {
		if( commands.containsKey( string ) ) {
			return commands.get( string );
		} else {
			return new UnknownCommand( string );
		}
	}

	private String getInput(ShellContext context) throws IOException {
		return context.in().readLine();
	}

	private void printPrompt(ShellContext context) {
		context.out().print( String.format( "\n%s> ", server.getSystemName() ) );
	}
}
