package dz.ubma.bookcollection_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import dz.ubma.bookcollection_.fragments.favorisAdapter;
import dz.ubma.bookcollection_.fragments.listAdapter;

public class mycollection extends AppCompatActivity {
    RecyclerView books;
    private favorisAdapter l;
    private ArrayList<Information> infos;
    Button add_button,Home_button,Mycollection_button,confirm_button,loadImage_button;

    ImageButton star;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycollection);
        books = findViewById(R.id.rvBooks1);
        add_button = findViewById(R.id.add);
        Home_button = findViewById(R.id.home);
        Mycollection_button = findViewById(R.id.mycollection);
        Mycollection_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"You are already in the My collection page", Toast.LENGTH_SHORT).show();

            }
        });
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mycollection.this, addbook.class);
                startActivity(intent);
            }
        });
        Home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mycollection.this,MainActivity.class);
                startActivity(intent);
            }
        });

        loadcategories();

    }

        public void loadcategories() {
                infos=new ArrayList<>();
                DatabaseReference ref = (DatabaseReference) FirebaseDatabase.getInstance().getReference("favoris");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        infos.clear();

                        for (DataSnapshot ds:snapshot.getChildren()){
                            Information infor=ds.getValue(Information.class);


                            infos.add(infor);
                            l=new favorisAdapter(mycollection.this,infos);
                            books.setAdapter(l);




                    }}

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }


}














