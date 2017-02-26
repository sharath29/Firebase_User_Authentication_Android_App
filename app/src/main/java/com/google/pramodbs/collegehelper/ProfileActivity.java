package com.google.pramodbs.collegehelper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.*;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    //private FirebaseUser user;
    //private FirebaseAnalytics mFirebaseAnalytics;

    private TextView textemail;
    private Button logoutbtn;

    private DatabaseReference mDatabase;
    private EditText usrname,branch;
    private Button save;
    private String mUserId;

    private Query queryref;

    //private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();

        textemail=(TextView)findViewById(R.id.curruser);
        logoutbtn=(Button)findViewById(R.id.btn);
        usrname=(EditText)findViewById(R.id.usersname);
        branch=(EditText)findViewById(R.id.userbrnch);
        save=(Button)findViewById(R.id.savedata);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        //userid=mDatabase.push().getKey();
        logoutbtn.setOnClickListener(this);
        save.setOnClickListener(this);

        //mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        mUserId=user.getUid();

        textemail.setText("Hello " + user.getEmail());

    }

    private void saveuserinfo(){
        String name = usrname.getText().toString();
        String branchobj = branch.getText().toString();

        /*mDatabase.child("users").child(mUserId).push().child("Name").setValue(name);
        mDatabase.child("users").child(mUserId).push().child("Branch").setValue(name);*/
        //User user = new User(name, branchobj);

        Map mParent = new HashMap();
        mParent.put("Name", name);
        mParent.put("Branch", branchobj);

        mDatabase.child("users").child(mUserId).setValue(mParent);

        Toast.makeText(this, "Saved data !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if(v==logoutbtn){
            firebaseAuth.signOut();
            Toast.makeText(this,"Signed Out successfully !",Toast.LENGTH_SHORT).show();
            Intent gotomain=new Intent(this,MainActivity.class);
            finish();
            startActivity(gotomain);
        }
        if(v==save){
            saveuserinfo();
        }
    }
}



