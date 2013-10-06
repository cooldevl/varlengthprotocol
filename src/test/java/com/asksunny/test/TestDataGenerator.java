package com.asksunny.test;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import com.asksunny.io.protocol.VarlengthCodecImpl;
import com.asksunny.io.protocol.VarlengthProtocolCodec;

public class TestDataGenerator {

	public TestDataGenerator() {
		// TODO Auto-generated constructor stub
	}

	VarlengthCodecImpl codec = new VarlengthCodecImpl();
	
	public TestDataGenerator genXmlRespoonse() throws Exception {
		FileOutputStream f = new FileOutputStream(
				"C:\\Java\\workspace\\protocol\\src\\test\\resources\\xmlresponse1.dat");
		ObjectOutputStream out = new ObjectOutputStream(f);
		String xml = "<root>\n<container>this is a text line1\nthis is text line2\n</container>\n</root>";
		codec.encodeStdout(xml, out);
		out.writeInt(VarlengthProtocolCodec.EOF); // end of stream
		out.flush();
		f.close();
		return this;
	}

	public TestDataGenerator genConsoleResponse() throws Exception {
		FileOutputStream f = new FileOutputStream(
				"C:\\Java\\workspace\\protocol\\src\\test\\resources\\consoleout1.dat");
		ObjectOutputStream out = new ObjectOutputStream(f);
		String cout = "This is output line1\n";
		codec.encodeStdout(cout, out);
		cout = "This is err message output line1\n";
		codec.encodeStderr(1, cout, out);
		cout = "This is output line3\n";
		codec.encodeStderr(2, cout, out);
		cout = "<root>\n<container>this is a text line1\nthis is text line2\n</container>\n</root>\n";
		codec.encodeStdout(cout, out);
		cout = "This is output linex\n";
		codec.encodeStdout(cout, out);
		out.writeInt(VarlengthProtocolCodec.EOF);
		out.flush();
		f.close();
		return this;
	}

	public TestDataGenerator genFileResponse() throws Exception {
		FileOutputStream f = new FileOutputStream(
				"C:\\Java\\workspace\\protocol\\src\\test\\resources\\fileout1.dat");
		ObjectOutputStream out = new ObjectOutputStream(f);
		String fname = "/this/is/the/file/path/in/the/server/myfile";
		String fileContent = "";
		fileContent += "this is the file content line 1" + "\n";
		fileContent += "this is the file content line 2" + "\n";
		fileContent += "this is the file content line 3" + "\n";
		fileContent += "this is the file content line 4" + "\n";
		fileContent += "this is the file content line 5" + "\n";
		fileContent += "this is the file content line 6" + "\n";
		fileContent += "this is the file content line 7" + "\n";
		fileContent += "this is the file content line 8" + "\n";
		fileContent += "this is the file content line 9" + "\n";
		codec.encodeFile(fname, fileContent.getBytes(), out);
		out.writeInt(VarlengthProtocolCodec.EOF);
		out.flush();
		f.close();
		return this;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		(new TestDataGenerator()).genConsoleResponse().genFileResponse()
				.genXmlRespoonse();

	}

}
