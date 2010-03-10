package com.codiform.as400shell.command;

import com.codiform.as400shell.shell.ShellContext;

public class QuitCommand implements Command {

	@Override
	public void execute(ShellContext context, String[] arguments) {
		context.out().println( "Goodbye." );
		context.exitAfterCommand();
	}

	@Override
	public void displayHelp(ShellContext context) {
		context.out().println( "Exits the shell." );
	}

}
