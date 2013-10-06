package com.asksunny.io.protocol;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class VarlengthDecoderTest {

	InputStream fin = null;
	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		if(fin!=null) fin.close();
	}

	@Test
	public void testFile() throws Exception {
		fin = getClass().getResourceAsStream("/fileout1.dat");
		VarlengthCodecImpl decoder = new VarlengthCodecImpl();
		decoder.setClient(new RemoteFileListenerImpl("C:\\Java\\workspace\\protocol\\src\\test\\resources\\file.txt"));
		decoder.decode(new ObjectInputStream(fin));		
	}
	
	@Test
	public void testConsole() throws Exception {
		fin = getClass().getResourceAsStream("/consoleout1.dat");
		VarlengthCodecImpl decoder = new VarlengthCodecImpl();
		decoder.setClient(new RemoteExecutionListnerImpl());
		decoder.decode(new ObjectInputStream(fin));		
	}

}
