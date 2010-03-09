package com.codiform.as400shell.shell;

import java.io.InputStream;
import java.io.PrintStream;

public class ShellContext {

	private InputStream input;
	private PrintStream output;
	private PrintStream error;
	private boolean shouldContinue = false;
	
	public ShellContext() {
		this.input = System.in;
		this.output = System.out;
		this.error = System.err;
	}

	public InputStream in() {
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

}
