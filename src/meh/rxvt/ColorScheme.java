/* Copyleft (ɔ) meh. - http://meh.schizofreni.co
 *
 * This file is part of rxvt-android - https://github.com/meh/rxvt-android
 *
 * rxvt-android is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * rxvt-android is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with rxvt-android. If not, see <http://www.gnu.org/licenses/>.
 ****************************************************************************/

package meh.rxvt;

import android.graphics.Color;
import android.util.Pair;
import java.util.TreeMap;

public class ColorScheme
{
	public Color getForeground ()
	{
		return _foreground;
	}

	public Color setForeground (Color value)
	{
		return _foreground = value;
	}

	public Color getBackground ()
	{
		return _background;
	}

	public Color setBackground (Color value)
	{
		return _background = value;
	}

	public Color getUnderline ()
	{
		return _underline;
	}

	public Color setUnderline (Color value)
	{
		return _underline = value;
	}

	public Pair<Color, Color> getCursor ()
	{
		return _cursor;
	}

	public Pair<Color, Color> setCursor (Color front, Color back)
	{
		return _cursor = new Pair(front, back);
	}

	public Pair<Color, Color> setCursor (Pair<Color, Color> value)
	{
		return _cursor = value;
	}

	public Color setColor (int index, Color value)
	{
		return _colors.put(index, value);
	}

	public Color getColor (int index)
	{
		return _colors.get(index);
	}

	private Color                   _foreground;
	private Color                   _background;
	private Color                   _underline;
	private Pair<Color, Color>      _cursor;
	private TreeMap<Integer, Color> _colors = new TreeMap<Integer, Color>();
}
