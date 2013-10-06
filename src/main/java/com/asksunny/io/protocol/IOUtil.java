package com.asksunny.io.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtil {

	public static final int SIZE_1M = 100;
	
	public static void copy(long length, InputStream in, OutputStream out) throws IOException
	{
		int bsize = (in.available()<SIZE_1M)?SIZE_1M:SIZE_1M*2;		
		byte[] buf = new byte[bsize];
		long len = -1;
		long acclength = 0;
		long lftlen = length - acclength;
		long readBytes = (lftlen<bsize)?lftlen:bsize;
		while((len=in.read(buf, 0, (int)readBytes))!=-1){
			lftlen = length - acclength;
			if(len>lftlen) len = lftlen;			
			out.write(buf, 0, (int)len);			
			acclength +=len;
			readBytes = (lftlen<bsize)?lftlen:bsize;
			if(acclength>=length) break;
		}
	}
	
	
}
