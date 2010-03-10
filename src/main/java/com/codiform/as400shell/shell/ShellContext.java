package com.codiform.as400shell.shell;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class ShellContext {

	private BufferedReader input;
	private PrintStream output;
	private PrintStream error;
	private boolean shouldContinue;

	public ShellContext(InputStream input, PrintStream output, PrintStream error) {
		this.input = new BufferedReader( new InputStreamReader( input ) );
		this.output = output;
		this.error = error;
		shouldContinue = true;
	}

	public BufferedReader in() {
		return input;
	}

	public PrintStream out() {
		return output;
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

}
