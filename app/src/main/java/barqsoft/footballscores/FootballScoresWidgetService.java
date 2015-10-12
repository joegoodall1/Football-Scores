package barqsoft.footballscores;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;


public class FootballScoresWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    class ListRemoteViewsFactory implements RemoteViewsFactory {
        Cursor c;
        private int mAppWidgetId;
        private static final int mCount = 10;
        //private ArrayList<String> mWidgetItems = new ArrayList<>();
        private Context mContext;


        public ListRemoteViewsFactory(Context context, Intent intent) {
            this.mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        public void onCreate() {
            // In onCreate() you setup any connections / cursors to your data source. Heavy lifting,
            // for example downloading or creating content etc, should be deferred to onDataSetChanged()
            // or getViewAt(). Taking more than 20 seconds in this call will result in an ANR.
            c = getContentResolver().query(DatabaseContract.BASE_CONTENT_URI, null, null, null, null);
        }


        public void onDestroy() {
            // In onDestroy() you should tear down anything that was setup for your data source,
            // eg. cursors, connections, etc.
            //mWidgetItems.clear();
        }

        public int getCount() {

            return c.getCount();
        }

        public RemoteViews getViewAt(int position) {
            // position will always range from 0 to getCount() - 1.
            // We construct a remote views item based on our widget item xml file, and set the
            // text based on the position.
            RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.row);

            if(c.moveToPosition(position)){
                String home_name = c.getString(ScoresAdapter.COL_HOME);
                String away_name = c.getString(ScoresAdapter.COL_AWAY);
                String match_time = c.getString(ScoresAdapter.COL_MATCHTIME);
                String score = Utilities.getScores(c.getInt(ScoresAdapter.COL_HOME_GOALS),
                        c.getInt(ScoresAdapter.COL_AWAY_GOALS));
                Double match_id = c.getDouble(ScoresAdapter.COL_ID);

                rv.setTextViewText(R.id.football_widget_game_name,home_name + "" + away_name);
                //rv.setContentDescription(R.id.football_widget_game_name, home_name + mContext.getString(R.string.vs) + away_name);
                rv.setTextViewText(R.id.football_widget_match_time,   match_time);
                //rv.setContentDescription(R.id.football_widget_match_time, mContext.getString(R.string.time) + match_time);
                rv.setTextViewText(R.id.football_widget_score,  score);
                //rv.setContentDescription(R.id.football_widget_score, mContext.getString(R.string.score) + score);
            }


            return rv;
        }

        public RemoteViews getLoadingView() {
            return null;
        }

        public int getViewTypeCount() {
            return 1;
        }



        @Override
        public long getItemId(int i) {
            if(c.moveToPosition(i)){
                return (long)c.getDouble(i);
            }
            return 0;

        }

        public boolean hasStableIds() {
            return true;
        }

        public void onDataSetChanged() {
            //c = getContentResolver().query(DatabaseContract.BASE_CONTENT_URI, null, null, null, null);


        }
    }

}

