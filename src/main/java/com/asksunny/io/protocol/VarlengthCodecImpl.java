package com.asksunny.io.protocol;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class VarlengthCodecImpl implements VarlengthProtocolCodec {

	ProtocolDecodeListener client = null;

	public ProtocolDecodeListener getClient() {
		return client;
	}

	public void setClient(ProtocolDecodeListener client) {
		this.client = client;
	}

	public void decode(ObjectInputStream in) throws IOException {
		int channel = in.readInt();
		switch (channel) {
		case SYSOUT:
		case SYSERR:
			decodeConsole(channel, in);
			break;
		case FILEOUT:
			decodeFile(in, new File("."));
			break;
		default:
			throw new IOException("Unrecognized protocol encoding");

		}

	}

	protected void decodeFile(ObjectInputStream in, File dir)
			throws IOException {

		int fnlen = in.readInt();
		String fileName = readString(fnlen, in);
		long contentlen = in.readLong();
		if (this.client != null && this.client instanceof RemoteFileListener) {
			RemoteFileListener fl = (RemoteFileListener) this.client;
			OutputStream fout = fl.getOutputStream(fileName);
			if (fout != null) {
				try {
					IOUtil.copy(contentlen, in, fout);
				} finally {
					fout.close();
				}
			} else {
				fl.writeStderr(1,
						"No output stream provided to persist file from remote.");
				in.skip(contentlen);
			}
		} else {
			in.skip(contentlen);
		}
		try {
			int eof = in.readInt();
			if (eof != EOF)
				throw new IOException(
						"FileStream is not terminated properly from remote, it must be terminated with EOF.");
		} catch (EOFException ex) {
			; // If system EOF is used, custom EOF is not required.
		}

	}

	protected void decodeConsole(int channel, ObjectInputStream in)
			throws IOException {
		int lchannel = channel;
		do {
			int status = in.readInt();
			long msglen = in.readLong();
			String msg = readString(msglen, in);
			if (status == 0 && this.client != null) {
				this.client.writeStdout(msg);
			} else if (status != 0 && this.client != null) {
				this.client.writeStderr(status, msg);
			}
			try {
				lchannel = in.readInt();
			} catch (EOFException e) {
				break;
			}
			if (lchannel == EOF)
				break;
		} while (lchannel != EOF);

		if (this.client != null
				&& this.client instanceof RemoteExecutionListener) {
			RemoteExecutionListener rel = (RemoteExecutionListener) this.client;
			rel.eof();
		}
	}

	protected String readString(long len, ObjectInputStream in)
			throws IOException {
		byte[] buf = new byte[(int) len];
		in.read(buf);
		return new String(buf);
	}

	public void encodeStdout(String rawdata, ObjectOutputStream out)
			throws IOException {
		encodeStdout(rawdata.getBytes(), out);
	}

	public void encodeStdout(byte[] rawdata, ObjectOutputStream out)
			throws IOException {
		out.writeInt(SYSOUT);
		out.writeInt(0); // status code;
		out.writeLong(rawdata.length);
		out.write(rawdata);
	}

	public void encodeStderr(int statusCode, String rawdata,
			ObjectOutputStream out) throws IOException {
		encodeStderr(statusCode, rawdata.getBytes(), out);
	}

	public void encodeStderr(int statusCode, byte[] rawdata,
			ObjectOutputStream out) throws IOException {
		out.writeInt(SYSOUT);
		out.writeInt(statusCode); // status code;
		out.writeLong(rawdata.length);
		out.write(rawdata);
	}

	public void encodeFile(String filename, ObjectOutputStream out)
			throws IOException {
		out.writeInt(FILEOUT);
		byte[] fnbytes = filename == null ? new byte[0] : filename.getBytes();
		int fnlen = fnbytes.length;
		out.writeInt(fnlen);
		out.write(fnbytes);
		File f = new File(filename);
		FileInputStream fin = new FileInputStream(f);
		out.writeLong(f.length());
		IOUtil.copy(f.length(), fin, out);
		if (fin != null)
			fin.close();
		out.writeInt(EOF);
	}

	public void encodeFile(String filename, long length, InputStream in,
			ObjectOutputStream out) throws IOException {
		out.writeInt(FILEOUT);

		byte[] fnbytes = filename == null ? new byte[0] : filename.getBytes();
		int fnlen = fnbytes.length;
		out.writeInt(fnlen);
		out.write(fnbytes);
		out.writeLong(length);
		IOUtil.copy(length, in, out);
		out.writeInt(EOF);
	}

	public void encodeFile(String filename, byte[] fileContent,
			ObjectOutputStream out) throws IOException {
		out.writeInt(FILEOUT);
		byte[] fnbytes = filename == null ? new byte[0] : filename.getBytes();
		int fnlen = fnbytes.length;
		out.writeInt(fnlen);
		out.write(fnbytes);
		out.writeLong(fileContent.length);
		out.write(fileContent);
		out.writeInt(EOF);
	}

}
