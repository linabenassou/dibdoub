package dz.ubma.bookcollection_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookDetails extends AppCompatActivity {
String bookId;
   TextView title_, Description_, Year_;

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
    }

    private void loadBookDetails() {

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("book");
        ref.child(bookId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String title=""+snapshot.child("titre").getValue();
                String description=""+snapshot.child("description").getValue();
                String ann=""+snapshot.child("year").getValue();
                title_.setText(title);
                Description_.setText(description);
                Year_.setText(ann);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}