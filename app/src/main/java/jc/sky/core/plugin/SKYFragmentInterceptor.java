package jc.sky.core.plugin;

import android.os.Bundle;

import jc.sky.view.SKYFragment;

/**
 * @author sky
 * @version 版本
 */
public interface SKYFragmentInterceptor {

	void onFragmentCreated(SKYFragment SKYFragment, Bundle bundle, Bundle savedInstanceState);

	void onFragmentStart(SKYFragment SKYFragment);

	void onFragmentResume(SKYFragment SKYFragment);

	void onFragmentPause(SKYFragment SKYFragment);

	void onFragmentStop(SKYFragment SKYFragment);

	void onFragmentDestroy(SKYFragment SKYFragment);

	public SKYFragmentInterceptor NONE = new SKYFragmentInterceptor() {
		@Override
		public void onFragmentCreated(SKYFragment SKYFragment, Bundle bundle, Bundle savedInstanceState) {

		}

		@Override
		public void onFragmentStart(SKYFragment SKYFragment) {

		}

		@Override
		public void onFragmentResume(SKYFragment SKYFragment) {

		}

		@Override
		public void onFragmentPause(SKYFragment SKYFragment) {

		}

		@Override
		public void onFragmentStop(SKYFragment SKYFragment) {

		}

		@Override
		public void onFragmentDestroy(SKYFragment SKYFragment) {

		}
	};
}
