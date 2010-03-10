package com.codiform.as400shell.command;

import com.codiform.as400shell.shell.ShellContext;

/**
 * Represents a command that could not be found. This is essentially the null
 * object pattern for commands.
 */
public class UnknownCommand implements Command {

	/**
	 * The name of the command that could not be found.
	 */
	private String commandName;

	/**
	 * Create an UnknownCommand to represent a particular unknown command by
	 * name.
	 * 
	 * @param commandName
	 *            the name of the command that is unknown
	 */
	public UnknownCommand(String commandName) {
		this.commandName = commandName;
	}

	@Override
	public void execute(ShellContext context, String[] arguments) {
		context.out().printf(
				"Unknown Command: %s ('help' to see list of commands)",
				commandName );
	}

	@Override
	public void displayHelp(ShellContext context) {
		// ignore
	}

}
