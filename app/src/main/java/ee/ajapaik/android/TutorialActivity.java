package ee.ajapaik.android;

import android.os.Handler;
import ee.ajapaik.android.test.R;
import ee.ajapaik.android.util.WebActivity;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

public class TutorialActivity extends WebActivity {
    void waitForMenuToBeCreatedAndShowTutorial() {
        Handler myHandler = new Handler();
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tutorial();            }
        }, 100);
    }

    private void tutorial() {
        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, "ThisValueIsStoredAndTutorialIsContinuedWhereLeftPreviousTime");
        if (sequence.hasFired()) return;

        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view
        sequence.setConfig(config);

        sequence.addSequenceItem(new MaterialShowcaseView.Builder(this)
                .setTarget(findViewById(R.id.tutorial_layout_up))
                .setContentText(R.string.tutorial_opacity_zoom)
                .setDismissText(R.string.tutorial_dismiss)
                .setTargetTouchable(true)
                .setDismissOnTargetTouch(false)
                .setFadeDuration(500)
                .withRectangleShape()
                .build());

        sequence.addSequenceItem(new MaterialShowcaseView.Builder(this)
                .setTarget(findViewById(R.id.tutorial_layout_up))
                .setContentText(R.string.tutorial_hide_show_flip)
                .setDismissText(R.string.tutorial_dismiss)
                .setTargetTouchable(true)
                .setDismissOnTargetTouch(false)
                .setFadeDuration(500)
                .withRectangleShape()
                .build());

        sequence.addSequenceItem(new MaterialShowcaseView.Builder(this)
                .setTarget(findViewById(R.id.tutorial_layout_down))
                .setContentText(R.string.tutorial_take_picture)
                .setDismissText(R.string.tutorial_dismiss)
                .setTargetTouchable(true)
                .setDismissOnTargetTouch(false)
                .setFadeDuration(500)
                .withRectangleShape()
                .build());

        sequence.start();
    }
}
