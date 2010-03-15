package com.codiform.as400shell.command;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

import com.codiform.as400shell.shell.ShellContext;

public abstract class ParsedArgumentCommand implements Command {

	protected OptionParser parser;

	public ParsedArgumentCommand() {
	}
	
	public ParsedArgumentCommand(OptionParser parser) {
		this.parser = parser;
	}
	
	protected void setParser( OptionParser parser ) {
		this.parser = parser;
	}

	@Override
	public void execute(ShellContext context, String[] arguments) {
		OptionSet options = parser.parse( arguments );
		execute( context, options );
	}

	public abstract void execute(ShellContext context, OptionSet options);

}
