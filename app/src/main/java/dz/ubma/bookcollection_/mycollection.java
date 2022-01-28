package dz.ubma.bookcollection_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import dz.ubma.bookcollection_.fragments.listAdapter;

public class mycollection extends AppCompatActivity {
    RecyclerView books;
    private listAdapter l;
    private ArrayList<Information> infos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycollection);
        books = findViewById(R.id.rvBooks1);
        loadcategories();
    }

        private void loadcategories() {
                infos=new ArrayList<>();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("favoris");
                ref.addValueEventListener(new ValueEventListener() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        infos.clear();
                        for (DataSnapshot ds:snapshot.getChildren()){
                            Information infor=ds.getValue(Information.class);
                            Log.d("liliwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww", String.valueOf(infor));


                            infos.add(infor);
                            l=new listAdapter(mycollection.this,infos);
                            books.setAdapter(l);


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }


        }













