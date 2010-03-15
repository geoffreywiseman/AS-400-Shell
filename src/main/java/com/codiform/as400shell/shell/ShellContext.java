package com.codiform.as400shell.shell;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import com.ibm.as400.access.AS400;

public class ShellContext {

	private BufferedReader baseInput;
	private PrintStream baseOutput;
	private PrintStream currentOutput;
	private PrintStream error;
	private boolean shouldContinue;
	private AS400 server;

	public ShellContext(InputStream input, PrintStream output,
			PrintStream error, AS400 server) {
		this.baseInput = new BufferedReader( new InputStreamReader( input ) );
		this.baseOutput = output;
		this.currentOutput = output;
		this.error = error;
		this.server = server;
		shouldContinue = true;
	}

	public BufferedReader in() {
		return baseInput;
	}

	public PrintStream out() {
		return currentOutput;
	}

	public PrintStream err() {
		return error;
	}

	public boolean shouldContinue() {
		return shouldContinue;
	}

	public void exitAfterCommand() {
		shouldContinue = false;
	}
	
	public AS400 getServer() {
		return server;
	}

	/**
	 * Reset the shell context back to its original state, removing any command-specific customizations.
	 */
	public void reset() {
		removeRedirect();
	}

	private void removeRedirect() {
		if( currentOutput != baseOutput ) {
			currentOutput.close();
			currentOutput = baseOutput;
		}
	}

	public void redirectTo(File file) throws FileNotFoundException {
		currentOutput = new PrintStream( file );
	}

}
