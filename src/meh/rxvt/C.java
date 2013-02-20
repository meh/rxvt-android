package meh.rxvt;

import org.bridj.*;
import org.bridj.ann.*;

import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;

import static org.bridj.Pointer.*;

@Library("c")
public class C
{
	public static class Native
	{
		static {
			BridJ.register();
		}

		public static int O_RDWR = 02;

		public static int FD_CLOEXEC = 1;
		public static int F_SETFD    = 2;

		public static native int open (Pointer<Byte> path, int flags);
		public static native int close (int fd);
		public static native int fcntl (int fildes, int cmd, Object... args);
		public static native int setsid ();

		public static native int grantpt (int fd);
		public static native int unlockpt (int fd);
		public static native Pointer<Byte> ptsname (int fd);
		public static native int dup2 (int fildes, int fildes2);

		public static int WIFEXITED (int value)
		{
			return value & 0x7f;
		}

		public static int WEXITSTATUS (int value)
		{
			return (value & 0xff00) >> 8;
		}

		public static int SIGHUP = 1;

		public static native int execve (Pointer<Byte> path, Pointer<Pointer<Byte>> argv, Pointer<Pointer<Byte>> env);
		public static native int fork ();
		public static native void exit (int status);
		public static native int kill (int pid, int sig);
		public static native int waitpid (int pid, Pointer<Integer> location, int options);

		public static class winsize extends StructObject
		{
			public winsize ()
			{
				super();
			}

			@Field(0)
			public short row ()
			{
				return this.io.getShortField(this, 0);
			}

			@Field(0)
			public winsize row (short value)
			{
				this.io.setShortField(this, 0, value);

				return this;
			}

			@Field(1)
			public short column ()
			{
				return this.io.getShortField(this, 1);
			}

			@Field(1)
			public winsize column (short value)
			{
				this.io.setShortField(this, 1, value);

				return this;
			}

			@Field(2)
			public short x ()
			{
				return this.io.getShortField(this, 2);
			}

			@Field(2)
			public winsize x (short value)
			{
				this.io.setShortField(this, 2, value);

				return this;
			}

			@Field(3)
			public short y ()
			{
				return this.io.getShortField(this, 3);
			}

			@Field(3)
			public winsize y (short value)
			{
				this.io.setShortField(this, 3, value);

				return this;
			}
		}

		public static int TIOCSWINSZ = 0x5414;

		public static native int ioctl (int fildes, int request, Object... args);

		public static class termios extends StructObject
		{
			public termios ()
			{
				super();
			}

			@Field(0)
			public int input_flags ()
			{
				return this.io.getIntField(this, 0);
			}

			@Field(0)
			public termios input_flags (int value)
			{
				this.io.setIntField(this, 0, value);

				return this;
			}

			@Field(1)
			public int output_flags ()
			{
				return this.io.getIntField(this, 1);
			}

			@Field(0)
			public termios output_flags (int value)
			{
				this.io.setIntField(this, 1, value);

				return this;
			}

			@Field(2)
			public int control_flags ()
			{
				return this.io.getIntField(this, 2);
			}

			@Field(2)
			public termios control_flags (int value)
			{
				this.io.setIntField(this, 2, value);

				return this;
			}

			@Field(3)
			public int local_flags ()
			{
				return this.io.getIntField(this, 3);
			}

			@Field(3)
			public termios local_flags (int value)
			{
				this.io.setIntField(this, 3, value);

				return this;
			}

			@Field(4)
			public byte line ()
			{
				return this.io.getByteField(this, 4);
			}

			@Field(4)
			public termios line (byte value)
			{
				this.io.setByteField(this, 4, value);

				return this;
			}

			@Field(5)
			@Array({32})
			public Pointer<Byte> control_characters ()
			{
				return this.io.getPointerField(this, 5);
			}

			@Field(6)
			public int input_speed ()
			{
				return this.io.getIntField(this, 6);
			}

			@Field(6)
			public termios input_speed (int value)
			{
				this.io.setIntField(this, 6, value);

				return this;
			}

			@Field(7)
			public int output_speed ()
			{
				return this.io.getIntField(this, 7);
			}

			@Field(7)
			public termios output_speed (int value)
			{
				this.io.setIntField(this, 7, value);

				return this;
			}
		}

		public static int TCSANOW = 0;
		public static int IUTF8   = 0040000;

		public static native int tcgetattr (int fd, Pointer<termios> p);
		public static native int tcsetattr (int fd, int actions, Pointer<termios> p);
	}

	public static class FileDescriptor
	{
		public static FileDescriptor Invalid = new FileDescriptor(-1);

		public FileDescriptor (int fd)
		{
			_fd = fd;
		}

		public boolean valid ()
		{
			return _fd != -1;
		}

		public int fd ()
		{
			return _fd;
		}

		private int _fd;
	}

	public static class Process
	{
		public static FileDescriptor create (String command, String[] args, String[] env, int[] id)
		{
			int ptm;
			{
				Pointer<Byte> path_ = pointerToCString("/dev/ptmx");
				ptm = Native.open(path_, Native.O_RDWR);
				path_.release();
			}

			if (ptm < 0) {
				return FileDescriptor.Invalid;
			}

			Native.fcntl(ptm, Native.F_SETFD, Native.FD_CLOEXEC);

			if (Native.grantpt(ptm) != 0 || Native.unlockpt(ptm) != 0) {
				return FileDescriptor.Invalid;
			}

			Pointer<Byte> path = Native.ptsname(ptm);
			if (path == null) {
				return FileDescriptor.Invalid;
			}

			Pointer<Byte>          command_ = pointerToCString(command);
			Pointer<Pointer<Byte>> args_    = pointerToCStrings(args);
			Pointer<Pointer<Byte>> env_     = pointerToCStrings(env);

			int pid = Native.fork();
			if (pid == 0) {
				Native.close(ptm);
				Native.setsid();

				int pts = Native.open(path, Native.O_RDWR);

				if (pts < 0) {
					Native.exit(-1);
				}

				Native.dup2(pts, 0);
				Native.dup2(pts, 1);
				Native.dup2(pts, 2);

				Native.execve(command_, args_, env_);
				Native.exit(-1);
			}
			else {
				id[0] = pid;
			}

			command_.release();

			for (Pointer<Byte> p : args_) {
				p.release();
			}
			args_.release();

			for (Pointer<Byte> p : env_) {
				p.release();
			}
			env_.release();

			return new FileDescriptor(ptm);
		}

		public static void close (FileDescriptor process)
		{
			Native.close(process.fd());
		}

		public static int waitFor (int process)
		{
			Pointer<Integer> status = allocateInt();

			Native.waitpid(process, status, 0);

			try {
				if (Native.WIFEXITED(status.getInt()) == 0) {
					return Native.WEXITSTATUS(status.getInt());
				}

				return 0;
			}
			finally {
				status.release();
			}
		}

		public static void hangup (int process)
		{
			Native.kill(-process, Native.SIGHUP);
		}
	}

	public static class PTY
	{
		public static void size (FileDescriptor desc, short row, short column, short x, short y)
		{
			Native.ioctl(desc.fd(), Native.TIOCSWINSZ, pointerTo(new Native.winsize().row(row).column(column).x(x).y(y)));
		}

		public static void utf8 (FileDescriptor desc, boolean mode)
		{
			Native.termios term = new Native.termios();
			Native.tcgetattr(desc.fd(), pointerTo(term));

			if (mode) {
				term.input_flags(term.input_flags() | Native.IUTF8);
			}
			else {
				term.input_flags(term.input_flags() & ~Native.IUTF8);
			}

			Native.tcsetattr(desc.fd(), Native.TCSANOW, pointerTo(term));
		}
	}
}
