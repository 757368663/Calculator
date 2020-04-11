package fun.eriri.calculator.activity;

import android.os.Bundle;
import android.os.Vibrator;


import fun.eriri.calculator.R;
import fun.eriri.calculator.fragment.CalculatorFragment;
import fun.eriri.calculator.fragment.ChangeFragment;

public class MainActivity extends BaseActivity {

    CalculatorFragment calculatorFragment = new CalculatorFragment();
    ChangeFragment changeFragment = new ChangeFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    void init() {
        super.init();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.action_container,calculatorFragment)
                .add(R.id.action_container,changeFragment)
                .show(calculatorFragment)
                .hide(changeFragment)
                .commit();
    }
}
