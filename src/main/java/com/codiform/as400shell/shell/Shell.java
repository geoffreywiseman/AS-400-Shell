package com.codiform.as400shell.shell;

import com.ibm.as400.access.AS400;

public class Shell {

	private AS400 server;

	public Shell(AS400 server) {
		this.server = server;
	}

	/**
	 * Starts the read-execute-print-loop loop.
	 */
	public void repl() {
		ShellContext context = new ShellContext();
		do {
			printPrompt(context);
			respondToInput(context, getInput(context));
		} while (context.shouldContinue());
	}

	private boolean respondToInput(ShellContext context, String[] input) {
		// TODO Auto-generated method stub
		return false;
	}

	private String[] getInput(ShellContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	private void printPrompt(ShellContext context) {
		context.out().println(String.format("%s> ", server.getSystemName()));
	}
}
