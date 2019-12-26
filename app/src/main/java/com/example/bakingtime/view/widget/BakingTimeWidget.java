package com.example.bakingtime.view.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.bakingtime.PreferenceManagerClient;
import com.example.bakingtime.R;
import com.example.bakingtime.view.MainActivity;

public class BakingTimeWidget extends AppWidgetProvider implements PreferenceManagerClient {

	public static final BakingTimeWidget INSTANCE = new BakingTimeWidget();

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		updateAppWidget(context, appWidgetManager, appWidgetIds);
	}

	public void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		for (int appWidgetId : appWidgetIds) {
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_time_widget);
			views.setTextViewText(R.id.widget_textview_title, INSTANCE.getWidgetTitle(context));
			views.setRemoteAdapter(R.id.widget_listview_ingredients, BakingTimeRemoteViewsService.getIntent(context));
			views.setPendingIntentTemplate(R.id.widget_listview_ingredients, pendingIntent);
			views.setOnClickPendingIntent(R.id.widget_parent_layout, pendingIntent);
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}
}

