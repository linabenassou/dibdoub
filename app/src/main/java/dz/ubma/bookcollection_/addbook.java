package dz.ubma.bookcollection_;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageManager;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.protobuf.Empty;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class addbook extends AppCompatActivity {
    EditText title_input, Description_input, Year_input;
    Button add_button,Home_button,Mycollection_button,confirm_button,uploadImage_button;
    ImageView images;
    DatabaseReference Ref;

    int iconStar=0;
    int PICK_IMAGE_REQUEST = 111;
    Uri filePath;
    ProgressDialog pd;

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
        confirm_button = findViewById(R.id.confirmer);
        images = findViewById(R.id.image);
        uploadImage_button = findViewById(R.id.limage);
        Ref = FirebaseDatabase.getInstance().getReference().child("book");
        pd=new ProgressDialog(this);
        pd.setMessage("Uploading....");

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://bibliotheques-a8f0e.appspot.com");

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "You are already in the Add book page", Toast.LENGTH_SHORT).show();

            }
        });
        uploadImage_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);


            }
        });



        confirm_button.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View view) {
                                                  String Titre = title_input.getText().toString();
                                                  String Description = Description_input.getText().toString();
                                                  String Year = Year_input.getText().toString();
                                                  // DatabaseReference data= FirebaseDatabase.getInstance().getReference("book");
                                                  //String id=data.push().getKey();

                                                  if (TextUtils.isEmpty(Titre)) {
                                                      title_input.setError("Title is Required.");
                                                      return;
                                                  }

                                                  if (TextUtils.isEmpty(Description)) {
                                                      Description_input.setError("Description is Required.");
                                                      return;
                                                  }
                                                  if (TextUtils.isEmpty(Year)) {
                                                      Year_input.setError("Year is Required.");
                                                      return;
                                                  }
                                                  String id = Ref.push().getKey();



                                                  Ref.orderByChild("titre").equalTo(Titre)
                                                          .addListenerForSingleValueEvent(new ValueEventListener() {

                                                              @Override
                                                              public void onDataChange(DataSnapshot data) {
                                                                  //If email exists then toast shows else store the data on new key
                                                                  if (data.exists()){


                                                                      Toast.makeText(getApplicationContext(), "Book already exist!", Toast.LENGTH_SHORT).show();
                                                                  }
                                                                  else{
                                                                      // postRef.child(id).setValue(new Information(id, Titre, Description, Year, iconStar));
                                                                      if (filePath != null) {
                                                                          pd.show();
                                                                          FilePath filePaths = new FilePath();
                                                                          StorageReference childRef = storageRef.child(filePaths.FIREBASE_IMAGE_STORAGE + "/" + id + "/photo 1");
                                                                          //uploading the image
                                                                          UploadTask uploadTask = childRef.putFile(filePath);

                                                                          uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                              public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                                  pd.dismiss();

                                                                                  childRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                      @Override
                                                                                      public void onSuccess(Uri uri) {
                                                                                          Ref.child(id).setValue(new Information(id, Titre, Description, Year, iconStar, uri.toString()));
                                                                                          Toast.makeText(getApplicationContext(), "Book Added Successfully", Toast.LENGTH_SHORT).show();

                                                                                      }
                                                                                  });


                                                                              }
                                                                          }).addOnFailureListener(new OnFailureListener() {
                                                                              @Override
                                                                              public void onFailure(@NonNull Exception e) {
                                                                                  pd.dismiss();
                                                                                  Toast.makeText(addbook.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                                                                              }

                                                                          });
                                                                      } else
                                                                          Toast.makeText(getApplicationContext(), "Please select an image", Toast.LENGTH_SHORT).show();

                                                                  }
                                                              }
                                                              // }

                                                              @Override
                                                              public void onCancelled(@NonNull DatabaseError error) {

                                                              }


                                                          });





                                              }










                                          }
        );
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
                Intent intent = new Intent(addbook.this, mycollection.class);
                startActivity(intent);
            }
        });

    }



   /* public void addInfos(String id,String Titre,String Description,String Year,int icstar){
        DatabaseReference data= FirebaseDatabase.getInstance().getReference();
        Information info=new Information(id,Titre,Description,Year,icstar);//,Description,
        data.child("book").child(id).setValue(info);
        Toast.makeText(getApplicationContext(),"Book added successfully", Toast.LENGTH_SHORT).show();


    }*/
   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);

       if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
           filePath = data.getData();

           try {
               //getting image from gallery
               Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

               //Setting image to ImageView
               images.setImageBitmap(bitmap);
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
   }
}