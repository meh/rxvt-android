/*
 *            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *                    Version 2, December 2004
 *
 *            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 *  0. You just DO WHAT THE FUCK YOU WANT TO.
 **/

package meh.fonts;

import java.util.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import java.io.File;
import java.io.FileReader;

import java.io.FileNotFoundException;
import java.io.IOException;

public class PCF
{
	public static class WrongFileFormat extends RuntimeException
	{
		public static final long serialVersionUID = 234958345;

		public WrongFileFormat ()
		{
			super("not a PCF font");
		}
	}

	public static final int PROPERTIES       = (1 << 0);
	public static final int ACCELERATORS     = (1 << 1);
	public static final int METRICS          = (1 << 2);
	public static final int BITMAPS          = (1 << 3);
	public static final int INK_METRICS      = (1 << 4);
	public static final int BDF_ENCODINGS    = (1 << 5);
	public static final int SWIDTHS          = (1 << 6);
	public static final int GLYPH_NAMES      = (1 << 7);
	public static final int BDF_ACCELERATORS = (1 << 8);

	public static final int DEFAULT_FORMAT     = 0x000;
	public static final int INKBOUNDS          = 0x200;
	public static final int ACCEL_W_INKBOUNDS  = 0x100;
	public static final int COMPRESSED_METRICS = 0x100;

	public static final int GLYPH_PAD_MASK = (3 << 0);
	public static final int BYTE_MASK      = (1 << 2);
	public static final int BIT_MASK       = (1 << 3);
	public static final int SCAN_UNIT_MASK = (3 << 4);

	public static int parseLSB32 (char[] buffer)
	{
		return buffer[0] << 24 | buffer[1] << 16 | buffer[2] << 8 | buffer[3];
	}

	public static PCF fromFile (String path) throws FileNotFoundException, IOException
	{
		char[]     bytes  = null;
		File       file   = new File(path);
		FileReader reader = new FileReader(path);

		reader.read(bytes, 0, (int) file.length());

		return fromString(new String(bytes));
	}

	public static PCF fromString (String content) throws IOException
	{
		return new PCF(ByteBuffer.wrap(content.getBytes()));
	}

	public PCF (ByteBuffer buffer) throws IOException
	{
		byte[] tmp = null;

		buffer.get(tmp, 0, 4);
		if (Arrays.equals(tmp, "\001fcp".getBytes())) {
			throw new WrongFileFormat();
		}

		int count = buffer.order(ByteOrder.LITTLE_ENDIAN).getInt();

		for (int i = 0; i < count; i++) {
			int type   = buffer.order(ByteOrder.LITTLE_ENDIAN).getInt();
			int format = buffer.order(ByteOrder.LITTLE_ENDIAN).getInt();
			int size   = buffer.order(ByteOrder.LITTLE_ENDIAN).getInt();
			int offset = buffer.order(ByteOrder.LITTLE_ENDIAN).getInt();
		}
	}
}
