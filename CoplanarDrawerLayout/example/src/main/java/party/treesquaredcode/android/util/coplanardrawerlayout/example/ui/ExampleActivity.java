package party.treesquaredcode.android.util.coplanardrawerlayout.example.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import party.treesquaredcode.android.util.coplanardrawerlayout.CoplanarDrawerLayout;
import party.treesquaredcode.android.util.coplanardrawerlayout.example.R;

/**
 * Created by rht on 7/31/15.
 */
public class ExampleActivity extends Activity {

    CoplanarDrawerLayout coplanarDrawerLayout;
    ToggleButton leftDrawerViewCoplanarToggleButton;
    ToggleButton rightDrawerViewCoplanarToggleButton;
    Button leftDrawerViewSetWidthButton;
    Button rightDrawerViewSetWidthButton;

    boolean leftDrawerUsingRemainderWidth = false;
    boolean rightDrawerUsingRemainderWidth = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__example);
        coplanarDrawerLayout = (CoplanarDrawerLayout) findViewById(R.id.coplanarDrawerLayout);
        leftDrawerViewCoplanarToggleButton = (ToggleButton) findViewById(R.id.leftDrawerViewCoplanarToggleButton);
        rightDrawerViewCoplanarToggleButton = (ToggleButton) findViewById(R.id.rightDrawerViewCoplanarToggleButton);

        leftDrawerViewCoplanarToggleButton.setChecked(coplanarDrawerLayout.isLeftDrawerCoplanar());
        rightDrawerViewCoplanarToggleButton.setChecked(coplanarDrawerLayout.isRightDrawerCoplanar());

        leftDrawerViewCoplanarToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                coplanarDrawerLayout.setLeftDrawerCoplanar(isChecked);
            }
        });
        rightDrawerViewCoplanarToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                coplanarDrawerLayout.setRightDrawerCoplanar(isChecked);
            }
        });

        leftDrawerViewSetWidthButton = (Button) findViewById(R.id.leftDrawerViewSetWidthButton);
        rightDrawerViewSetWidthButton = (Button) findViewById(R.id.rightDrawerViewSetWidthButton);

        leftDrawerViewSetWidthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftDrawerUsingRemainderWidth = !leftDrawerUsingRemainderWidth;
                if (leftDrawerUsingRemainderWidth) {
                    coplanarDrawerLayout.setLeftDrawerWidthRemainderPixels(getDrawerWidthRemainderPixels());
                    ((TextView) v).setText("-");
                } else {
                    coplanarDrawerLayout.setLeftDrawerWidthPixels(getDrawerWidthPixels());
                    ((TextView) v).setText("+");
                }
            }
        });
        rightDrawerViewSetWidthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightDrawerUsingRemainderWidth = !rightDrawerUsingRemainderWidth;
                if (rightDrawerUsingRemainderWidth) {
                    coplanarDrawerLayout.setRightDrawerWidthRemainderPixels(getDrawerWidthRemainderPixels());
                    ((TextView) v).setText("-");
                }
                else {
                    coplanarDrawerLayout.setRightDrawerWidthPixels(getDrawerWidthPixels());
                    ((TextView) v).setText("+");
                }
            }
        });
    }

    private int getDrawerWidthRemainderPixels() {
        return getResources().getDimensionPixelSize(R.dimen.drawer_width_remainder);
    }

    private int getDrawerWidthPixels() {
        return getResources().getDimensionPixelSize(R.dimen.drawer_width);
    }
}
