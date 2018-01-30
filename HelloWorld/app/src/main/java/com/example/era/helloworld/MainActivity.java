package com.example.era.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mHelloTextView;
    private TextView mCrowsCounterCat;
    private EditText mNameEditText;
    private Button mCrowsCounterButton;
    private int mCount=0;
    private int mCountCat=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCrowsCounterButton=(Button)findViewById(R.id.buttonCrowsCounter);
        mHelloTextView=(TextView)findViewById(R.id.textView);
        mNameEditText=(EditText)findViewById(R.id.editText);
        mCrowsCounterButton.setOnClickListener(new View.OnClickListener(){
            @Override
                    public void onClick(View v){
                mHelloTextView.setText("Я насчитал "+ ++mCount+" ворон");
            }
        });
    }



    public void onClick(View view) {
        if(mNameEditText.getText().length()==0){
            mHelloTextView.setText("hello by_gaipov");
        } else {
          mHelloTextView.setText("hello, "+mNameEditText.getText());
        }

    }

    public void onClicked(View view) {
        TextView helloTetxView=(TextView)findViewById(R.id.textView);
        helloTetxView.setText("salam");
    }

    public void clickedCat(View view) {
        mCrowsCounterCat=(TextView)findViewById(R.id.textViewCat);
        mCrowsCounterCat.setText("Я насчитал "+ ++mCountCat+" котов");
    }
}
