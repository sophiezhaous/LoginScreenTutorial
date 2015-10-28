package com.example.juzhao.loginscreentutorial;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;

public class MainActivity extends Activity {
    public final static String EXTRA_MESSAGE = "com.example.juzhao.loginscreentutorial.MESSAGE";
    Button b1,b2;
    EditText username,password;
    String userID, pass;
    Firebase myFirebaseRef;
    TextView tx1;
    int counter = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);
        myFirebaseRef = new Firebase("https://vivid-torch-3238.firebaseio.com/");
        b1=(Button)findViewById(R.id.button);




        b2=(Button)findViewById(R.id.button2);
        //tx1=(TextView)findViewById(R.id.textView3);
        //tx1.setVisibility(View.GONE);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=(EditText)findViewById(R.id.editText);
                password=(EditText)findViewById(R.id.editText2);
                userID = username.getText().toString();
                pass = password.getText().toString();
                System.out.println("username and pass "+userID+ " "+ pass);

                myFirebaseRef.authWithPassword(userID, pass, new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        sendMessage();
                        //System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());

                    }
                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        Toast.makeText(getApplicationContext(), "Failed to Login",Toast.LENGTH_SHORT).show();
                        //System.out.println("Login Failed!: " + firebaseError);
                    }
                });
            }});

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username=(EditText)findViewById(R.id.editText);
                password=(EditText)findViewById(R.id.editText2);
                userID = username.getText().toString();
                pass = password.getText().toString();
                System.out.println("username and pass "+userID+ " "+ pass);

                myFirebaseRef.createUser(userID, pass, new Firebase.ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> result) {
                        Toast.makeText(getApplicationContext(), "Account Created!",Toast.LENGTH_SHORT).show();

                        //System.out.println("Successfully created user account with uid: " + result.get("uid"));
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        Toast.makeText(getApplicationContext(), "Failed!",Toast.LENGTH_SHORT).show();
                        //System.out.println("Fail!: " + firebaseError);
                    }
                });
            }});

    }

              public void sendMessage() {
                  Intent intent = new Intent(this, Main2Activity.class);
                  startActivity(intent);

              }

              @Override
              public boolean onCreateOptionsMenu(Menu menu) {
                  // Inflate the menu; this adds items to the action bar if it is present.
                  getMenuInflater().inflate(R.menu.menu_main, menu);
                  return true;
              }


              @Override
              public boolean onOptionsItemSelected(MenuItem item) {
                  // Handle action bar item clicks here. The action bar will
                  // automatically handle clicks on the Home/Up button, so long
                  // as you specify a parent activity in AndroidManifest.xml.
                  int id = item.getItemId();

                  //noinspection SimplifiableIfStatement
                  if (id == R.id.action_settings) {
                      return true;
                  }

                  return super.onOptionsItemSelected(item);
              }
          }
