package com.example.bakingtime.view.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViewsService;

public class BakingTimeRemoteViewsService extends RemoteViewsService {

	public static Intent getIntent(Context context) {
		return new Intent(context, BakingTimeRemoteViewsService.class);
	}

	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {
		return new BakingTimeRemoteViewsFactory(getApplicationContext());
	}
}
