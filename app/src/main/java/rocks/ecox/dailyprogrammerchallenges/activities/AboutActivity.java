package rocks.ecox.dailyprogrammerchallenges.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

import rocks.ecox.dailyprogrammerchallenges.R;

public class AboutActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView content = (TextView) findViewById(R.id.about_content);
        content.setText(Html.fromHtml(getString(R.string.about_content)));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
