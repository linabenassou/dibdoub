package dz.ubma.bookcollection_;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.MissingResourceException;

import dz.ubma.bookcollection_.fragments.listAdapter;
import dz.ubma.bookcollection_.recycler.home_fragment2;

public class MainActivity extends AppCompatActivity {
    Button u;
    RecyclerView books;
    private listAdapter l;
    private ArrayList<Information> infos;
    Button add_button,Home_button,Mycollection_button,confirm_button,loadImage_button;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        books=findViewById(R.id.rvBooks);
        add_button = findViewById(R.id.add);
        Home_button = findViewById(R.id.home);
        Mycollection_button = findViewById(R.id.mycollection);
        Home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"You are already in the Home page", Toast.LENGTH_SHORT).show();

            }
        });
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, addbook.class);
                startActivity(intent);
            }
        });
        Mycollection_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,mycollection.class);
                startActivity(intent);
            }
        });
        loadcategories();
       /* FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl, new home_fragment2());
        ft.addToBackStack(null);
        ft.commit();*/








        /*ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    setInfo(dataSnapshot);



                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                    setInfo(dataSnapshot);


                }
            }


            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d(TAG, "Failed to read value.", error.toException());
            }
        });*/
    }

    private void loadcategories() {
        infos=new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("book");
        ref.addValueEventListener(new ValueEventListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                infos.clear();
                for (DataSnapshot ds:snapshot.getChildren()){
                    Information infor=ds.getValue(Information.class);
                    Log.d("liliwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww", String.valueOf(infor));


                    infos.add(infor);
                    l=new listAdapter(MainActivity.this,infos);
                    books.setAdapter(l);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    }


