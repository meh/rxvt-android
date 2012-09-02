package meh.rxvt;

import org.bridj.*;
import org.bridj.ann.*;

import org.bridj.Pointer;
import static org.bridj.Pointer.*;

@Library("c")
public class LibC
{
	static {
		BridJ.register();
	}

	public static native int atoi (Pointer<Byte> value);
}
