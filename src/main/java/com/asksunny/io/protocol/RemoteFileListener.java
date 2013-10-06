package com.asksunny.io.protocol;

import java.io.IOException;
import java.io.OutputStream;

public interface RemoteFileListener extends ProtocolDecodeListener
{
	OutputStream getOutputStream(String remoteFileName) throws IOException;
}
