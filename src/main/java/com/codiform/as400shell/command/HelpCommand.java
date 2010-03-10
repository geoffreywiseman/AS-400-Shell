package com.codiform.as400shell.command;

import java.util.Iterator;
import java.util.Map;

import com.codiform.as400shell.shell.ShellContext;

/**
 * Displays a list of commands.
 */
public class HelpCommand implements Command {

	private String commandList;
	private Map<String, Command> commands;

	public HelpCommand(Map<String, Command> commands) {
		this.commands = commands;
		this.commandList = join( commands.keySet().iterator() );
	}

	private String join(Iterator<String> iterator) {
		StringBuilder builder = new StringBuilder();
		while( iterator.hasNext() ) {
			builder.append( iterator.next() );
			if( iterator.hasNext() ) {
				builder.append( ", " );
			}
		}
		return builder.toString();
	}

	@Override
	public void execute(ShellContext context, String[] arguments) {
		switch( arguments.length ) {
		case 0:
			context.out().printf(
					"The following commands are available: %s\n\nEnter 'help <command>' for more information about a particular command.\n",
					commandList );
			break;
		case 1:
			Command command = commands.get( arguments[0] );
			if( command == null ) {
				context.out().println( "Unknown Command: " + arguments[0] );
			} else {
				command.displayHelp( context );
			}
			break;
		default:
			context.out().printf(
					"Cannot provide help on more than one command at once." );
		}
	}

	@Override
	public void displayHelp(ShellContext context) {
		context.out().println(
				"SYNTAX: help [commandName]\nProvides general help on the list of commands, or specific help about a particular command." );
	}

}
