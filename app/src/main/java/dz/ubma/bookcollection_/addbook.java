package dz.ubma.bookcollection_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addbook extends AppCompatActivity {
    EditText title_input, Description_input, Year_input;
    Button add_button,Home_button,Mycollection_button,confirm_button;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbook);
        title_input = findViewById(R.id.title);
        Description_input = findViewById(R.id.description);
        Year_input = findViewById(R.id.year);
        add_button = findViewById(R.id.add);
        Home_button = findViewById(R.id.home);
        Mycollection_button = findViewById(R.id.mycollection);
        confirm_button=findViewById(R.id.confirmer);
        image=findViewById(R.id.image);


        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"You are already in the page add", Toast.LENGTH_SHORT).show();

            }
        });
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Titre =title_input.getText().toString();
                String Description =Description_input.getText().toString();
                String Year =Year_input.getText().toString();
                DatabaseReference data= FirebaseDatabase.getInstance().getReference("book");
                String id=data.push().getKey();
                addInfos(id,Titre,Description,Year);
                Toast.makeText(getApplicationContext(),"Added successfully", Toast.LENGTH_SHORT).show();

            }
        });
        Home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(addbook.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Mycollection_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(addbook.this,mycollection.class);
                startActivity(intent);
            }
        });

    }
    public void addInfos(String id,String Titre,String Description,String Year){
        DatabaseReference data= FirebaseDatabase.getInstance().getReference();
        Information info=new Information(id,Titre,Description,Year);//,Description,
        data.child("book").child(id).setValue(info);

    }
}