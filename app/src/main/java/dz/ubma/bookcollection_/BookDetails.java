package dz.ubma.bookcollection_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class BookDetails extends AppCompatActivity {
String bookId;
   TextView title_, Description_, Year_;
    Button add_button,Home_button,Mycollection_button,confirm_button,loadImage_button;

ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        Intent intent=getIntent();
        bookId=intent.getStringExtra("BookId");
        loadBookDetails();
        title_ = findViewById(R.id.title1);
        Description_ = findViewById(R.id.description1);
        Year_ = findViewById(R.id.year1);
        add_button = findViewById(R.id.add);
        Home_button = findViewById(R.id.home);
        Mycollection_button = findViewById(R.id.mycollection);
        img=findViewById(R.id.image);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookDetails.this, addbook.class);
                startActivity(intent);
            }
        });

        Home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookDetails.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Mycollection_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookDetails.this,mycollection.class);
                startActivity(intent);
            }
        });
    }


    private void loadBookDetails() {

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("book");
        ref.child(bookId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String title=""+snapshot.child("titre").getValue();
                String description=""+snapshot.child("description").getValue();
                String ann=""+snapshot.child("year").getValue();
                String im=""+snapshot.child("image").getValue();
                title_.setText(title);
                Description_.setText(description);
                Year_.setText(ann);
                Picasso.get().load(im).into(img);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}