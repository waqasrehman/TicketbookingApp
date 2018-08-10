package com.example.waqasur_rehman.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class Feedback extends Fragment {

    private EditText feedbackEmail;
    private EditText feedbackMessage;

    public Feedback() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        getActivity().setTitle(R.string.FeedBack);
        View view = inflater.inflate(R.layout.fragment_feedback,container,false);


        feedbackMessage =(EditText)view.findViewById(R.id.feedback_text);



        Button button = (Button)view.findViewById(R.id.submit_feedback);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendmessage();
            }
        });




        return view;
    }

    public void sendmessage(){

        String email ="email you want to send email from..";
        String subject = "From My APP";
        String message = feedbackMessage.getText().toString();


        Intent Email = new Intent(Intent.ACTION_SEND);
        Email.putExtra(Intent.EXTRA_EMAIL, new String[]{ email});

        Email.putExtra(Intent.EXTRA_SUBJECT, subject);
        Email.putExtra(Intent.EXTRA_TEXT, message);

        //need this to prompts email client only
        Email.setType("message/rfc822");

        startActivity(Intent.createChooser(Email, "Choose an Email client :"));


    }













}
