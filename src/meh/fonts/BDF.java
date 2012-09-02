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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;

import java.io.FileNotFoundException;
import java.io.IOException;

public class BDF
{
	public static class Properties implements Iterable<Properties.Property>
	{
		public static class Property
		{
			public Property (String name, String value)
			{
				_name  = name;
				_value = value;
			}

			public String name ()
			{
				return _name;
			}

			public int toInt ()
			{
				return Integer.parseInt(_value);
			}

			public double toDouble ()
			{
				return Double.parseDouble(_value);
			}

			public Number toNumber ()
			{
				return new Double(toDouble());
			}

			public String toString ()
			{
				return _value;
			}

			private String _name;
			private String _value;
		}

		public Properties ()
		{
			_map = new HashMap<String, Property>();
		}

		public Property get (String name)
		{
			return _map.get(name);
		}

		public Property set (String name, String value)
		{
			return _map.put(name, new Property(name, value));
		}

		public Property remove (String name)
		{
			return _map.remove(name);
		}

		public Iterator<Property> iterator ()
		{
			return _map.values().iterator();
		}

		private HashMap<String, Property> _map;
	}

	public static class BoundingBox
	{
		public int width;
		public int height;
		public int x;
		public int y;

		public BoundingBox (int a, int b, int c, int d)
		{
			width  = a;
			height = b;
			x      = c;
			y      = d;
		}
	}

	public static class Glyph
	{
		public static class Bitmap
		{
			public Bitmap (int width, int height)
			{
				_width  = width;
				_height = height;

				_bits = new long[height];
			}

			public int width ()
			{
				return _width;
			}

			public int height ()
			{
				return _height;
			}

			public boolean at (int x, int y)
			{
				return ((row(y) >> x) & 1) == 1;
			}

			public long row (int index)
			{
				return _bits[index];
			}

			public Bitmap row (int index, long bits)
			{
				_bits[index] = bits;

				return this;
			}

			private int _width;
			private int _height;

			private long _bits[];
		}

		public Glyph (String name)
		{
			_name = name;
		}

		public String name ()
		{
			return _name;
		}

		public int codePoint ()
		{
			return _code_point;
		}

		public Glyph codePoint (int value)
		{
			_code_point = value;

			return this;
		}

		public BoundingBox boundingBox ()
		{
			return _bounding_box;
		}

		public Glyph boundingBox (BoundingBox value)
		{
			_bounding_box = value;

			return this;
		}

		public Bitmap bitmap ()
		{
			return _bitmap;
		}

		public Glyph bitmap (Bitmap value)
		{
			_bitmap = value;

			return this;
		}

		private String      _name;
		private int         _code_point;
		private BoundingBox _bounding_box;
		private Bitmap      _bitmap;
	}

	public static BDF fromFile (String path) throws FileNotFoundException, IOException
	{
		return new BDF(new BufferedReader(new FileReader(path)));
	}

	public static BDF fromString (String content) throws IOException
	{
		return new BDF(new BufferedReader(new StringReader(content)));
	}

	public BDF (BufferedReader buffer) throws IOException
	{
		boolean in_font       = false;
		boolean in_properties = false;
		Glyph   in_char       = null;

		while (buffer.ready()) {
			String line = buffer.readLine();
			String id;
			String rest;

			if (line.indexOf(" ") == -1) {
				id   = line;
				rest = null;
			}
			else {
				id   = line.substring(0, line.indexOf(" "));
				rest = line.substring(line.indexOf(" ") + 1);
			}

			if (in_font) {
				if (id.equals("ENDFONT")) {
					in_font = false;

					continue;
				}

				if (in_properties) {
					if (id.equals("ENDPROPERTIES")) {
						in_properties = false;

						continue;
					}

					_properties.set(id, (rest.indexOf("\"") != -1) ? rest.substring(1, rest.length() - 1) : rest);
				}
				else if (in_char != null) {
					if (id.equals("ENDCHAR")) {
						_glyphs.put(in_char.codePoint(), in_char);
						in_char = null;

						continue;
					}

					if (id.equals("ENCODING")) {
						in_char.codePoint(Integer.parseInt(rest));
					}
					else if (id.equals("BBX")) {
						String[] result = rest.split("\\s");

						in_char.boundingBox(new BoundingBox(
							Integer.parseInt(result[0]), Integer.parseInt(result[1]),
							Integer.parseInt(result[2]), Integer.parseInt(result[1])
						));
					}
					else if (id.equals("BITMAP")) {
						in_char.bitmap(new Glyph.Bitmap(in_char.boundingBox().width, in_char.boundingBox().height));

						for (int i = 0; i < in_char.boundingBox().width; i++) {
							in_char.bitmap().row(i, Long.parseLong(buffer.readLine(), 16) >> (in_char.boundingBox().width % 4));
						}
					}
				}
				else {
					if (id.equals("STARTPROPERTIES")) {
						in_properties = true;

						continue;
					}

					if (id.equals("STARTCHAR")) {
						in_char = new Glyph(rest);

						continue;
					}

					if (id.equals("COMMENT")) {
						_comments.add(rest);
					}
					else if (id.equals("CONTENTVERSION")) {
						_version = rest;
					}
					else if (id.equals("FONT")) {
						_name = rest;
					}
					else if (id.equals("SIZE")) {
					}
					else if (id.equals("FONTBOUNDINGBOX")) {
						String[] result = rest.split("\\s");

						_bounding_box = new BoundingBox(
							Integer.parseInt(result[0]), Integer.parseInt(result[1]),
							Integer.parseInt(result[2]), Integer.parseInt(result[1])
						);
					}
					else if (id.equals("CHARS")) {
						_length = Integer.parseInt(rest);
					}
				}
			}
			else {
				if (id.equals("STARTFONT")) {
					in_font = true;
					_format = rest;
				}
			}
		}
	}

	public String format ()
	{
		return _format;
	}

	public List<String> comments ()
	{
		return _comments;
	}

	public String name ()
	{
		return _name;
	}

	public String version ()
	{
		return _version;
	}

	public BoundingBox boundingBox ()
	{
		return _bounding_box;
	}

	public Properties properties ()
	{
		return _properties;
	}

	public SortedMap<Integer, Glyph> glyphs ()
	{
		return _glyphs;
	}

	private String _format;
	private int    _length;

	private List<String> _comments = new LinkedList<String>();

	private String _name;
	private String _version;

	private BoundingBox _bounding_box;

	private Properties _properties = new Properties();

	private TreeMap<Integer, Glyph> _glyphs = new TreeMap<Integer, Glyph>();
}
