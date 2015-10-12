package barqsoft.footballscores;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;


public class ScoresWidgetProvider extends AppWidgetProvider {
    /*public static final String TOAST_ACTION = "com.example.android.stackwidget.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.example.android.stackwidget.EXTRA_ITEM";

//    @Override
//    public void onReceive(Context context, Intent intent) {
//        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
//        System.out.println(intent.getAction());
//        if (intent.getAction().equals(TOAST_ACTION)) {
//            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
//                    AppWidgetManager.INVALID_APPWIDGET_ID);
//            int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
//            Toast.makeText(context, "Touched view " + viewIndex, Toast.LENGTH_SHORT).show();
//        }
//        super.onReceive(context, intent);
//    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        System.out.println("ScoresWidgetProvider.onUpdate " + appWidgetIds.length);

        // update each of the widgets with the remote adapter
        for (int i = 0; i < appWidgetIds.length; ++i) {
            Intent intent = new Intent(context, FootballScoresWidgetService.class);

            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            rv.setRemoteAdapter(appWidgetIds[i], R.id.list_view, intent);

            rv.setEmptyView(R.id.list_view, R.id.empty_view);

            int appWidgetId = appWidgetIds[i];

            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            //rv.setRemoteAdapter(appWidgetId, R.id.list_view, intent);
            //rv.setTextViewText(R.id.title, "hello");


            Intent toastIntent = new Intent(context, ScoresWidgetProvider.class);
            toastIntent.setAction(ScoresWidgetProvider.TOAST_ACTION);

            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            PendingIntent toastPendingIntent = PendingIntent.getBroadcast(
                    context,
                    0,
                    toastIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.list_view, toastPendingIntent);

            //appWidgetManager.updateAppWidget(appWidgetId, null);
            appWidgetManager.updateAppWidget(appWidgetId, rv);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    *//*@Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if(intent.getAction().equalsIgnoreCase(UPDATE))
    }*//*
}
*/

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);

        }
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds,R.id.list_view);
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = "";
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        views.setTextViewText(1, widgetText);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.widget_container, pendingIntent);


        Intent rvsintent = new Intent(context, FootballScoresWidgetService.class);
        rvsintent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        rvsintent.setData(Uri.parse(rvsintent.toUri(Intent.URI_INTENT_SCHEME)));
        views.setRemoteAdapter(R.id.list_view, rvsintent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}

