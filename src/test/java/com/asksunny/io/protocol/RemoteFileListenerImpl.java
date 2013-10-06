package com.asksunny.io.protocol;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class RemoteFileListenerImpl implements RemoteFileListener {

	String path;
	
	public RemoteFileListenerImpl(String path)
	{
		this.path = path;
	}
	
	public RemoteFileListenerImpl() {		
	}

	public void writeStdout(String out) {
		System.out.println(out);

	}

	public void writeStderr(int status, String out) {
		System.err.println(out);
	}

	public OutputStream getOutputStream(String remoteFileName)
			throws IOException 
	{		
		return new FileOutputStream(path);
	}

	

}
