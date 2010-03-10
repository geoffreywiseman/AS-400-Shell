package com.codiform.as400shell.command;

import com.codiform.as400shell.shell.ShellContext;
import com.ibm.as400.access.AS400;

public class InfoCommand implements Command {

	@Override
	public void execute(ShellContext context, String[] arguments) {
		try {
			AS400 server = context.getServer();
			context.out().printf( "Server: %s (V%d R%d M%d)\n", server.getSystemName(), server.getVersion(),
					server.getRelease(), server.getModification() );
			context.out().printf( "Signed on as: %s\n", server.getUserId() );
			context.out().printf( "Signed on at: %s\n", server.getSignonDate().getTime() );
		} catch( Exception exception ) {
			exception.printStackTrace();
		}
	}

	@Override
	public void displayHelp(ShellContext context) {
		context.out().println(
				"Provides information about the server to which you're connected." );
	}

}
