package uk.co.crystalcube.aatemplate.ui;

/**
 * Created by tanny on 04/02/15.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import uk.co.crystalcube.aatemplate.R;

/**
 * A placeholder fragment containing a simple view.
 */
@EFragment(R.layout.fragment_main)
public class MainFragment extends Fragment {

    @ViewById(R.id.checkbox)
    protected CheckBox check;

    @AfterViews
    protected void setup() {

    }

    @Click(R.id.checkbox)
    protected void onCheckBoxClicked(View view) {

    }

}
