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

import android.view.View;
import android.view.GestureDetector;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.AttributeSet;

public class TermView extends View // implements GestureDetector.OnGestureListener
{
	public TermView (Context context)
	{
		super(context);
	}

	public TermView (Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public TermView (Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	public TermView (Context context, Session session, DisplayMetrics metrics)
	{
		super(context);
	}


	private void updateSize (int w, int h) {
 /*   mColumns = Math.max(1, (int) (((float) w) / mCharacterWidth));*/
		//mVisibleColumns = (int) (((float) mVisibleWidth) / mCharacterWidth);

		//mTopOfScreenMargin = mTextRenderer.getTopMargin();
		//mRows = Math.max(1, (h - mTopOfScreenMargin) / mCharacterHeight);
		//mTermSession.updateSize(mColumns, mRows);

		//// Reset our paging:
		//mTopRow = 0;
		//mLeftColumn = 0;

		invalidate();
	}

	public void updateSize (boolean force) {
 /*   if (mKnownSize) {*/
			//int w = getWidth();
			//int h = getHeight();

			//if (force || w != mVisibleWidth || h != mVisibleHeight) {
				//mVisibleWidth = w;
				//mVisibleHeight = h;
				//updateSize(mVisibleWidth, mVisibleHeight);
			//}
		/*}*/
	}

}
