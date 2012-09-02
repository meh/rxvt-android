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

import meh.rxvt.TermView;

import android.view.View;
import android.content.Context;
import android.widget.ViewFlipper;
import android.util.AttributeSet;
import android.graphics.Rect;
import android.view.Gravity;

public class TermViewFlipper extends ViewFlipper
{
	public TermViewFlipper (Context context)
	{
		super(context);

		measure(0, 0);
	}

	public TermViewFlipper(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		measure(0, 0);
	}

	@Override
	protected void onMeasure (int width, int height)
	{
		Rect visible = null;
		Rect window  = null;

		// Get rectangle representing visible area of this view, as seen by
		// the activity (takes other views in the layout into account, but
		// not space used by the IME)
		getGlobalVisibleRect(visible);

		// Get rectangle representing visible area of this window (takes
		// IME into account, but not other views in the layout)
		getWindowVisibleDisplayFrame(window);

		if (visible.width() == 0 && visible.height() == 0) {
			visible.left = window.left;
			visible.top  = window.top;
		}
		else {
			if (visible.left < window.left) {
				visible.left = window.left;
			}

			if (visible.top < window.top) {
				visible.top = window.top;
			}
		}

		visible.right  = window.right;
		visible.bottom = window.bottom;

		if (_width != visible.width() || _height != visible.height()) {
			_width  = visible.width();
			_height = visible.height();

			_params = new LayoutParams(visible.width(), visible.height(), Gravity.TOP | Gravity.LEFT);

			for (int i = 0; i < getChildCount(); i++) {
				updateViewLayout(getChildAt(i), _params);
			}

			if (getCurrentView() != null) {
				((TermView) getCurrentView()).updateSize(false);
			}
		}

		super.onMeasure(width, height);
	}

	@Override
	public void showPrevious ()
	{
		// TODO: implement it
	}

	@Override
	public void showNext ()
	{
		// TODO: implement it
	}

	@Override
	public void setDisplayedChild (int index)
	{
		// TODO: implement it
	}

	@Override
	public void addView (View v, int index)
	{
		super.addView(v, index, _params);
	}

	@Override
	public void addView (View v)
	{
		super.addView(v, _params);
	}

	private int _width;
	private int _height;

	private LayoutParams _params;
}
