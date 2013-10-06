package com.asksunny.io.protocol;

public class RemoteExecutionListnerImpl implements RemoteExecutionListener {

	
	
	public RemoteExecutionListnerImpl() {
		
	}

	public RemoteExecutionListnerImpl(String string) {
		
	}

	public void writeStdout(String out) {
		System.out.print(out);
	}

	public void writeStderr(int status, String out) {
		System.err.print(out);
	}

	public void eof() {
		System.out.println("---------------------DONE----------------------");

	}

}
