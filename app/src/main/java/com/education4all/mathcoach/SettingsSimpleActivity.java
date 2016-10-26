package com.education4all.mathcoach;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.test.ActivityUnitTestCase;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.education4all.mathcoach.ComplexityActivity;
import com.education4all.mathcoach.MathCoachAlg.DataReader;
import com.education4all.mathcoach.R;

import java.text.DecimalFormatSymbols;

public class SettingsSimpleActivity extends AppCompatActivity {

    public static final String COMPLEXITY_SETTINGS = "ComplexitySettings";
    public static final String NODISAPEARCHAR = "-"; //DecimalFormatSymbols.getInstance().getInfinity()
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_simple);

        ActionBar myActionBar = getSupportActionBar();
        myActionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        EditText field = (EditText) findViewById(R.id.editText);
        field.setText(Long.toString((long)DataReader.GetRoundTime(this)));
        EditText field2 = (EditText)findViewById(R.id.editText2);
        if (DataReader.GetDisapRoundTime(this) > 0) {
            field2.setText(Long.toString((long)DataReader.GetDisapRoundTime(this)));
        } else {
            field2.setText(NODISAPEARCHAR);
        }
        Button button = (Button)findViewById(R.id.firstMinus);
        final View v =  new View(this);
        button.setOnTouchListener(new RepeatListener(400, 200, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstMinus(v);
            }
        }));
        button = (Button)findViewById(R.id.secondMinus);
        button.setOnTouchListener(new RepeatListener(400, 200, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                secondMinus(v);
            }
        }));
        button = (Button)findViewById(R.id.firstPlus);
        button.setOnTouchListener(new RepeatListener(400, 200, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstPlus(v);
            }
        }));
        button = (Button)findViewById(R.id.secondPlus);
        button.setOnTouchListener(new RepeatListener(400, 200, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                secondPlus(v);
            }
        }));

        final CheckBox newDivisionCHeckbox = (CheckBox) findViewById(R.id.checkBoxDiv2);
        newDivisionCHeckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newDivisionCHeckbox.setChecked(!newDivisionCHeckbox.isChecked());
                //Creating the instance of PopupMenu
                final PopupMenu popup = new PopupMenu(SettingsSimpleActivity.this, newDivisionCHeckbox);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.divdropdown, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            item.setChecked(!item.isChecked());
                            boolean one = false;
                            boolean two = false;
                            if (popup.getMenu().getItem(0).isChecked()) {
                                one = true;
                            }
                            if (popup.getMenu().getItem(1).isChecked()) {
                                two = true;
                            }
                            addComplexityFromPopup(one,two);
                            if (one || two) {
                                newDivisionCHeckbox.setChecked(true);
                            } else {
                                newDivisionCHeckbox.setChecked(false);
                            }
                            return true;
                        }
                    });

                    popup.show(); //showing popup menu
                MenuItem item1 = (MenuItem) findViewById(R.id.one);
                if (DataReader.checkComplexity(3, 1, SettingsSimpleActivity.this)) {
                    popup.getMenu().getItem(0).setChecked(true);
                }
                if (DataReader.checkComplexity(3, 4, SettingsSimpleActivity.this)) {
                    popup.getMenu().getItem(1).setChecked(true);
                }
             }
            }

            ); //closing the setOnClickListener method

        }
    void addComplexityFromPopup(boolean one, boolean two) {
        StringBuilder currentComplexity = new StringBuilder();
        SharedPreferences.Editor editor = getSharedPreferences(COMPLEXITY_SETTINGS, MODE_PRIVATE).edit();
        String action = "";
        action = "Div";
        String name = "checkBox" + action + "1";
        int resID = getResources().getIdentifier(name, "id", "com.education4all.mathcoach");
        CheckBox chk = (CheckBox)findViewById(resID);
        if (chk.isChecked()) {
            currentComplexity.append("0").append(",");
        }
        if (one) {
            currentComplexity.append("1").append(",");
        }
        name = "checkBox" + action + "3";
        resID = getResources().getIdentifier(name, "id", "com.education4all.mathcoach");
        chk = (CheckBox)findViewById(resID);
        if (chk.isChecked()) {
            currentComplexity.append("2").append(",");
        }
        name = "checkBox" + action + "4";
        resID = getResources().getIdentifier(name, "id", "com.education4all.mathcoach");
        chk = (CheckBox)findViewById(resID);
        if (chk.isChecked()) {
            currentComplexity.append("3").append(",");
        }
        if (two) {
            currentComplexity.append("4").append(",");
        }


        editor.putString(action, currentComplexity.toString());
        editor.apply();
    }
        @Override
    protected void onResume() {
            super.onResume();
        for (int i = 1; i <= 4; ++i) {
            String name = "checkBoxAdd" + Integer.toString(i);
            int resID = getResources().getIdentifier(name, "id", "com.education4all.mathcoach");
            CheckBox cb = (CheckBox)findViewById(resID);
            if (DataReader.checkComplexity(0,i - 1,this)) {
                cb.setChecked(true);
            } else {
                cb.setChecked(false);
            }
        }
        for (int i = 1; i <= 4; ++i) {
            String name = "checkBoxSub" + Integer.toString(i);
            int resID = getResources().getIdentifier(name, "id", "com.education4all.mathcoach");
            CheckBox cb = (CheckBox)findViewById(resID);
            if (DataReader.checkComplexity(1,i - 1,this)) {
                cb.setChecked(true);
            } else {
                cb.setChecked(false);
            }
        }
        for (int i = 1; i <= 4; ++i) {
            String name = "checkBoxMul" + Integer.toString(i);
            int resID = getResources().getIdentifier(name, "id", "com.education4all.mathcoach");
            CheckBox cb = (CheckBox)findViewById(resID);
            if (DataReader.checkComplexity(2,i - 1,this)) {
                cb.setChecked(true);
            } else {
                cb.setChecked(false);
            }
        }
        for (int i = 1; i <= 4; ++i) {
            if (i != 2) {
                String name = "checkBoxDiv" + Integer.toString(i);
                int resID = getResources().getIdentifier(name, "id", "com.education4all.mathcoach");
                CheckBox cb = (CheckBox) findViewById(resID);
                if (DataReader.checkComplexity(3, i - 1, this)) {
                    cb.setChecked(true);
                } else {
                    cb.setChecked(false);
                }
            } else {
                String name = "checkBoxDiv" + Integer.toString(i);
                int resID = getResources().getIdentifier(name, "id", "com.education4all.mathcoach");
                CheckBox cb = (CheckBox) findViewById(resID);
                if (DataReader.checkComplexity(3, 1, this) || DataReader.checkComplexity(3, 4, this)) {
                    cb.setChecked(true);
                } else {
                    cb.setChecked(false);
                }
            }
        }
        CheckBox cb = (CheckBox)findViewById(R.id.checkBoxAdd1);
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(metrics);
        final float width = metrics.widthPixels/2 - 20;
        final float height = metrics.heightPixels / 8;
//        LinearLayout.LayoutParams layoutParamsWidth50 = new LinearLayout.LayoutParams(
//                (int) (width) , (int) (height) );
//        Button currentButton = (Button)findViewById(R.id.buttonAdd);
//        currentButton.setLayoutParams(layoutParamsWidth50);
//        currentButton = (Button)findViewById(R.id.buttonSub);
//        currentButton.setLayoutParams(layoutParamsWidth50);
//        currentButton = (Button)findViewById(R.id.buttonMul);
//        currentButton.setLayoutParams(layoutParamsWidth50);
//        currentButton = (Button)findViewById(R.id.buttonDiv);
//        currentButton.setLayoutParams(layoutParamsWidth50);
    }

    public void startCompl(View view) {
        Intent intent = new Intent(this,  ComplexityActivity.class);
        int p = Integer.parseInt((String) view.getTag());
        intent.putExtra("Type", p);
        startActivity(intent);
    }

    public void goToNumber(View p_v) {
        EditText field = (EditText)findViewById(R.id.editText);
        field.requestFocus();
    }

    @Override
    public void onBackPressed() {
        EditText field = (EditText)findViewById(R.id.editText);
        if (field.getText().length() > 0) {
            DataReader.SaveRoundTime(Float.parseFloat(field.getText().toString()), this);
        } else {
            DataReader.SaveRoundTime(5, this);
        }
        EditText field2 = (EditText)findViewById(R.id.editText2);
        if (!field2.getText().toString().equals(NODISAPEARCHAR) && !field2.getText().toString().equals("")) {
            DataReader.SaveDisapRoundTime(Float.parseFloat(field2.getText().toString()), this);
        } else {
            DataReader.SaveDisapRoundTime(-1, this);
        }
        finish();
    }

    public void firstPlus(View p_v) {
        EditText field = (EditText)findViewById(R.id.editText);
        float text = Float.parseFloat(field.getText().toString());
        if (text < 60) {
            ++text;
        } else {
            text = 1;
        }
        field.setText(Long.toString((long) text));
    }
    public void secondPlus(View p_v) {
        EditText field = (EditText)findViewById(R.id.editText2);
        String str = field.getText().toString();
        if (!str.equals(NODISAPEARCHAR)) {
            float text = Float.parseFloat(field.getText().toString());
            if (text < 60) {
                ++text;
                field.setText(Long.toString((long)text));
            } else {
                field.setText(NODISAPEARCHAR);
            }
        } else {
            field.setText("1");
        }
    }
    public void firstMinus(View p_v) {
        EditText field = (EditText)findViewById(R.id.editText);
        float text = Float.parseFloat(field.getText().toString());
        if (text > 1) {
            --text;
        } else {
            text = 60;
        }
        field.setText(Long.toString((long)text));
    }
    public void secondMinus(View p_v) {
        EditText field = (EditText)findViewById(R.id.editText2);
        String str = field.getText().toString();
        if (!str.equals(NODISAPEARCHAR)) {
            float text = Float.parseFloat(field.getText().toString());
            if (text > 1) {
                --text;
                field.setText(Long.toString((long)text));
            } else {
                field.setText(NODISAPEARCHAR);
            }
        } else {
            float text = 60;
            field.setText(Long.toString((long)text));
        }


    }
    public void onCheckBoxClick(View view) {
        StringBuilder currentComplexity = new StringBuilder();
        SharedPreferences.Editor editor = getSharedPreferences(COMPLEXITY_SETTINGS, MODE_PRIVATE).edit();
        String action = "";
        int tag = Integer.parseInt(view.getTag().toString());
        switch (tag) {
            case 1:
                action = "Add";
                break;
            case 2:
                action = "Sub";
                break;
            case 3:
                action = "Mul";
                break;
            case 4:
                action = "Div";
                break;
        }
        String name = "checkBox" + action + "1";
        int resID = getResources().getIdentifier(name, "id", "com.education4all.mathcoach");
        CheckBox chk = (CheckBox)findViewById(resID);
        if (chk.isChecked()) {
            currentComplexity.append("0").append(",");
        }
        name = "checkBox" + action + "2";
        resID = getResources().getIdentifier(name, "id", "com.education4all.mathcoach");
        chk = (CheckBox)findViewById(resID);
        if (chk.isChecked()) {
            currentComplexity.append("1").append(",");
        }

        name = "checkBox" + action + "3";
        resID = getResources().getIdentifier(name, "id", "com.education4all.mathcoach");
        chk = (CheckBox)findViewById(resID);
        if (chk.isChecked()) {
            currentComplexity.append("2").append(",");
        }
        name = "checkBox" + action + "4";
        resID = getResources().getIdentifier(name, "id", "com.education4all.mathcoach");
        chk = (CheckBox)findViewById(resID);
        if (chk.isChecked()) {
            currentComplexity.append("3").append(",");
        }
//        if (action.equals("Div")) {
//            name = "checkBox" + action + "5";
//            resID = getResources().getIdentifier(name, "id", "com.education4all.mathcoach");
//            chk = (CheckBox)findViewById(resID);
//            if (chk.isChecked()) {
//                currentComplexity.append("4").append(",");
//            }
//        }

        editor.putString(action, currentComplexity.toString());
        editor.apply();
    }

}