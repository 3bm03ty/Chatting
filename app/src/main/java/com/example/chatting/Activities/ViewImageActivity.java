package com.example.chatting.Activities;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.chatting.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import static com.example.chatting.Activities.MainActivity.isApplicationSentToBackground;
import static com.example.chatting.Activities.MainActivity.updateUserStatus;

public class ViewImageActivity extends AppCompatActivity {
    private String receiverUserID;
    private ImageView userProfileImage;
    private DatabaseReference UserRef;
    private Toolbar ChatToolBar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_layout);

        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        receiverUserID = getIntent().getExtras().get("visit_user_id").toString();
        userProfileImage = findViewById(R.id.visit_profile_image);
        RetrieveUserInfo();
        IntializeControllers();
    }

    private void RetrieveUserInfo() {
        UserRef.child(receiverUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if ((dataSnapshot.exists())  &&  (dataSnapshot.hasChild("image"))) {
                    String userImage = dataSnapshot.child("image").getValue().toString();
                    Picasso.get().load(userImage).placeholder(R.drawable.profile_image).into(userProfileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStop() {
        if (isApplicationSentToBackground(this)){
            updateUserStatus("offline");
        }
        super.onStop();
    }

    private void IntializeControllers() {
        ChatToolBar = (Toolbar) findViewById(R.id.image_tool_bar);
        setSupportActionBar(ChatToolBar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        /*LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        View actionBarView = layoutInflater.inflate(R.layout.image_tool_bar, null);
        actionBar.setCustomView(actionBarView);*/
    }

}
