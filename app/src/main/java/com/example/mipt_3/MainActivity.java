package com.example.mipt_3;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    TextView resultTV, solutionTv;
    MaterialButton buttonC, buttonOpenBracket, buttonCloseBracket;
    MaterialButton buttonDivide, buttonMultiply, buttonPlus, buttonMinus;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonAc;
    MaterialButton buttonEquals;
    MaterialButton buttonSqrt;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        resultTV = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);
        assignIds(buttonEquals, R.id.button_equals);
        assignIds(buttonC, R.id.button_c);
        assignIds(buttonOpenBracket, R.id.button_open_bracket);
        assignIds(buttonCloseBracket, R.id.button_close_bracket);
        assignIds(buttonDivide, R.id.button_divide);
        assignIds(buttonMultiply, R.id.button_multiply);
        assignIds(buttonPlus, R.id.button_plus);
        assignIds(buttonMinus, R.id.button_minus);
        assignIds(button0, R.id.button_0);
        assignIds(button1, R.id.button_1);
        assignIds(button2, R.id.button_2);
        assignIds(button3, R.id.button_3);
        assignIds(button4, R.id.button_4);
        assignIds(button5, R.id.button_5);
        assignIds(button6, R.id.button_6);
        assignIds(button7, R.id.button_7);
        assignIds(button8, R.id.button_8);
        assignIds(button9, R.id.button_9);
        assignIds(buttonAc, R.id.button_ac);
        assignIds(buttonSqrt, R.id.button_sqrt);

    }

    void assignIds(MaterialButton btn, int id)
    {
        btn = findViewById(id);
        btn.setOnClickListener(this);
        buttonSqrt = findViewById(R.id.button_sqrt);
        buttonSqrt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();

        if (buttonText.equals("AC"))
        {
            solutionTv.setText("");
            resultTV.setText("0");
            return;
        }
        if (buttonText.equals("="))
        {
            solutionTv.setText(resultTV.getText());
            return;
        }
        if (buttonText.equals("C"))
        {
            if (dataToCalculate.length() > 0)
            {
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
            }
            if (view.getId() == R.id.button_sqrt)
            {

                if (!dataToCalculate.isEmpty())
                {
                    try
                    {
                        double value = Double.parseDouble(dataToCalculate);

                        if (value >= 0)
                        {
                            double result = Math.sqrt(value);
                            resultTV.setText(String.valueOf(result));
                            solutionTv.setText(String.valueOf(result));
                        } else
                        {
                            resultTV.setText("Err");
                        }
                    } catch (NumberFormatException e)
                    {
                        resultTV.setText("Err");
                    }
                }
            }
        } else
        {
            dataToCalculate = dataToCalculate + buttonText;
        }

        solutionTv.setText(dataToCalculate);

        String finalResult = getResult(dataToCalculate);
        if (!finalResult.equals("Err"))
        {
            resultTV.setText(finalResult);
        }
    }

    String getResult(String data)
    {
        try
        {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
            if (finalResult.endsWith(".0"))
            {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        } catch (Exception e)
        {
            return "Err";
        }
    }
}