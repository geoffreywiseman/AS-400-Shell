package com.codiform.as400shell.command;

import com.codiform.as400shell.shell.ShellContext;

/**
 * Represents a command within the AS/400 shell -- a piece of executable code
 * associated with a command name.
 */
public interface Command {
	/**
	 * Execute the command with the specified context and arguments.
	 * 
	 * @param context the context used to do input and output
	 * @param arguments the arguments used to configure the command
	 */
	void execute(ShellContext context, String[] arguments);

	/**
	 * Display the help used for this command. 
	 * 
	 * @param context the context used to print the help for the command
	 */
	void displayHelp(ShellContext context);
}
