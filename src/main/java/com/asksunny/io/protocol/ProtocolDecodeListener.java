package com.asksunny.io.protocol;

/**
 * 
 * 
 * @author SunnyLiu
 *
 */
public interface ProtocolDecodeListener {

	void writeStdout(String out);
	void writeStderr(int status, String out);
	
}
