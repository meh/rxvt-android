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

import meh.rxvt.LibC;
import meh.rxvt.TermService;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;

import android.widget.TextView;

import org.bridj.Pointer;
import org.bridj.Pointer.StringType;
import java.nio.charset.Charset;

public class TermActivity extends Activity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		TextView test = new TextView(this);
		test.setText(Integer.toString(LibC.atoi(
			(Pointer<Byte>) Pointer.pointerToString("1234", StringType.C, Charset.forName("UTF-8")))));

		setContentView(test);

 /*   _service_intent = new Intent(this, TermService.class);*/

		/*setContentView(R.layout.main);*/
	}

	private Intent _service_intent;
}
