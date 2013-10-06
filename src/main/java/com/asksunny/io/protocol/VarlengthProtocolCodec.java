package com.asksunny.io.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface VarlengthProtocolCodec 
{
	public static final int SYSOUT = 0;
	public static final int SYSERR = 1;
	public static final int FILEOUT = 2;
	public static final int EOF = 3;
	public void decode(ObjectInputStream in) throws IOException;
	public void encodeStdout(String rawdata, ObjectOutputStream out)
			throws IOException ;
	public void encodeStdout(byte[] rawdata, ObjectOutputStream out)
			throws IOException ;
	public void encodeStderr(int statusCode, String rawdata, ObjectOutputStream out)
			throws IOException;
	public void encodeStderr(int statusCode, byte[] rawdata, ObjectOutputStream out)
			throws IOException ;
	public void encodeFile(String filename,  ObjectOutputStream out)
			throws IOException ;
	public void encodeFile(String filename, long length, InputStream in, ObjectOutputStream out)
			throws IOException ;
	public void encodeFile(String filename, byte[] fileContent, ObjectOutputStream out)
			throws IOException ;	

}
