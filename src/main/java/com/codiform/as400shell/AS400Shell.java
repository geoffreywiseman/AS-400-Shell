package com.codiform.as400shell;

import java.beans.PropertyVetoException;
import java.io.IOException;

import com.codiform.as400shell.shell.Shell;
import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400SecurityException;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

public class AS400Shell {

	public static void main(String[] arguments) throws IOException, PropertyVetoException, AS400SecurityException {
		System.out.println("AS/400 Shell v1.0\n");
		OptionParser parser = getParser();
		OptionSet options = parser.parse(arguments);
		if (options.hasArgument("host") && options.hasArgument("username")
				&& options.hasArgument("password")) {
			AS400 server = new AS400((String) options.valueOf("host"),
					(String) options.valueOf("username"), (String) options
							.valueOf("password"));
			server.setGuiAvailable(false);
			System.out.println( "Connected to: " + getDescription( server ) + "\n" );
			new Shell( server ).repl();
		} else {
			parser.printHelpOn(System.out);
		}
	}

	private static String getDescription(AS400 server) throws AS400SecurityException, IOException {
		int vrm = server.getVRM();
		int modification = vrm & 0xF;
		vrm >>= 8;
		int release = vrm & 0xF; 
		vrm >>= 8;
		
		return String.format("%s (v%dr%dm%d) as %s", server.getSystemName(), vrm, release, modification, server.getUserId() );
	}

	private static OptionParser getParser() {
		return new OptionParser() {
			{
				accepts("host").withRequiredArg().describedAs(
						"The ip name/address of the AS/400 host.");
				accepts("username").withRequiredArg().describedAs(
						"Username to authenticate with the AS/400.");
				accepts("password").withRequiredArg().describedAs(
						"Password to authenticate with the AS/400.");
			}
		};
	}
}
