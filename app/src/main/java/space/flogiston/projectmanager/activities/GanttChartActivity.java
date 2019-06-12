package space.flogiston.projectmanager.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import space.flogiston.projectmanager.ProjectManager;
import space.flogiston.projectmanager.R;
import space.flogiston.projectmanager.data.Repository;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class GanttChartActivity extends AppCompatActivity {

    // public this_canvas this_canvas;
    public DrawView draw_view;
    public ScrollView scroll_view;
    public HorizontalScrollView h_scroll_view;
    public LinearLayout lin_layout;
    public Canvas this_canvas;
    private Repository repository;
    private SharedPreferences sPref;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        DrawView main_draw_view;

        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        ActionBar actionBar = getSupportActionBar();
        try {
            // actionBar.setTitle();
            actionBar.setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {

        }
        repository = ((ProjectManager)getApplication()).getRepository();
        sPref = getSharedPreferences("project_manager", MODE_PRIVATE);

        lin_layout = new LinearLayout(this);
        scroll_view = new ScrollView(this);
        h_scroll_view = new HorizontalScrollView(this);

        scroll_view.setLayoutParams(new ScrollView.LayoutParams(
                ScrollView.LayoutParams.WRAP_CONTENT,
                ScrollView.LayoutParams.WRAP_CONTENT));

        h_scroll_view.setLayoutParams(new HorizontalScrollView.LayoutParams(
                HorizontalScrollView.LayoutParams.MATCH_PARENT,
                HorizontalScrollView.LayoutParams.MATCH_PARENT));

        lin_layout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        lin_layout.setOrientation(LinearLayout.VERTICAL);

        Bitmap mBitmap = Bitmap.createBitmap(630, 870, Bitmap.Config.ARGB_8888);
        this_canvas = new Canvas(mBitmap);

        main_draw_view = new DrawView(this);

        main_draw_view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        scroll_view.addView(main_draw_view);
        h_scroll_view.addView(scroll_view);

        setContentView(h_scroll_view);
    }

//////////////////////////////////////////////////////////////////////////////////////

    public class DrawView extends View
    {

        Context app_context;

        public DrawView(Context context)
        {
            super(context);

            app_context = context;

            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
        }

// onMeasure must be included otherwise one or both scroll views will be compressed to zero pixels
// and the scrollview will then be invisible

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
        {

            int width = 2000;
            int height = 3000 + 50; // Since 3000 is bottom of last Rect to be drawn added and 50 for padding.
            setMeasuredDimension(width, height);
        }

        @Override
        public void onDraw(Canvas canvas)
        {
            this_canvas = canvas;

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_log) {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), ErrorActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_exit) {
            repository.exit();
            SharedPreferences.Editor ed = sPref.edit();
            ed.putString("api_token", "");
            ed.apply();
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            return true;
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
