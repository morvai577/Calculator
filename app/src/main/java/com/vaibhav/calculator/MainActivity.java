package com.vaibhav.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //Fields
    private EditText result;
    private EditText newNumber;
    private TextView displayOperation;

    //Variables to hold the operands and type of calculations
    private Double operand1;
    private String pendingOperation = "=";

    // Used for bundling
    private static final String STATE_PENDING_OPERATION = "PendingOperation";
    private static final String STATE_OPERAND1 = "Operand1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Linking widgets to the variables
        result = (EditText) findViewById(R.id.result);
        newNumber = (EditText) findViewById(R.id.newNumber);
        displayOperation = (TextView) findViewById(R.id.operation);

        //Assigning the 16 buttons
        Button button0 = (Button) findViewById(R.id.button0);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);
        Button button7 = (Button) findViewById(R.id.button7);
        Button button8 = (Button) findViewById(R.id.button8);
        Button button9 = (Button) findViewById(R.id.button9);


        Button buttonDot = (Button) findViewById(R.id.buttonDot);

        Button buttonEquals = (Button) findViewById(R.id.buttonEquals);
        Button buttonDivide = (Button) findViewById(R.id.buttonDivide);
        Button buttonMultiply = (Button) findViewById(R.id.buttonMultiply);
        Button buttonMinus = (Button) findViewById(R.id.buttonMinus);
        Button buttonPlus = (Button) findViewById(R.id.buttonPlus);

        //Listener for operands
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) { // v holds reference to the button that was clicked
                Button b = (Button) v; // This casting is required to getText in following line
                newNumber.append(b.getText().toString()); // Add new number to edit text widget.
            }
        };
        // Assigning the listener
        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);

        buttonDot.setOnClickListener(listener);

        //Listener for operations
        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String op = b.getText().toString();
                String value = newNumber.getText().toString();
                try {
                    Double doubleValue = Double.valueOf(value);
                    performOperation(doubleValue, op);
                } catch (NumberFormatException e) {
                    newNumber.setText("");
                }
                pendingOperation = op;
                displayOperation.setText(pendingOperation);
            }
        };

        buttonEquals.setOnClickListener(opListener);
        buttonDivide.setOnClickListener(opListener);
        buttonMultiply.setOnClickListener(opListener);
        buttonMinus.setOnClickListener(opListener);

        //Negative number functionality
        Button buttonNeg = (Button) findViewById(R.id.buttonNeg);
        View.OnClickListener neg = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String value = newNumber.getText().toString();
                if(value.length() == 10){
                    newNumber.setText("-");
                } else {
                    try {
                        Double doubleValue = Double.valueOf(value);
                        doubleValue *= -1;
                        newNumber.setText(doubleValue.toString());
                    } catch (NumberFormatException e){
                        // newNumber was "-" or ".", so clear it
                        newNumber.setText("");
                    }
                }
            }
        };

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_PENDING_OPERATION, pendingOperation); // Save value of pendingOperation
        if (operand1 != null){
            outState.putDouble(STATE_OPERAND1, operand1); // Save value of operand1
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore Variables from bundles
        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION); // Restore value of pendingOperation
        operand1 = savedInstanceState.getDouble(STATE_OPERAND1); // Restore value of operand1
        displayOperation.setText(pendingOperation); // update display so that user can see what operation is pending
    }

    private void performOperation(Double value, String operation) {
        if (null == operand1) {
            operand1 = value;
        } else {
            value = value;

            if (pendingOperation.equals("=")) {
                pendingOperation = operation;
            }

            switch (pendingOperation) {
                case "=":
                    operand1 = value;
                    break;
                case "/":
                    if (value == 0) { // Check division by zero
                        operand1 = 0.0;
                    } else {
                        operand1 /= value;
                    }
                case "*":
                    operand1 *= value;
                    break;
                case "-":
                    operand1 -= value;
                    break;
                case "+":
                    operand1 += value;
                    break;
            }
        }

        result.setText(operand1.toString());
        newNumber.setText("");
    }
}
