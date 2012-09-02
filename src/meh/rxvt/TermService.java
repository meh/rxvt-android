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

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import java.util.LinkedList;

public class TermService extends Service
{
	@Override
	public void onDestroy ()
	{
		for (Session session : _sessions) {
			session.finish();
		}

		_sessions.clear();
	}

	@Override
	public IBinder onBind (Intent intent)
	{
		return _binder;
	}

	private LinkedList<Session> _sessions;

	private final IBinder _binder = new Binder () {
		TermService getService() {
			return TermService.this;
		}
	};
}
