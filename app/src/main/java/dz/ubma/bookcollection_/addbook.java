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
import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageManager;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class addbook extends AppCompatActivity {
    private static final int REQUEST = 103;
    EditText title_input, Description_input, Year_input;
    Button add_button,Home_button,Mycollection_button,confirm_button,loadImage_button;
    ImageView images;
    private int imageCount = 0;
    DatabaseReference mDatabase;
    addbook mFirebaseMethods;
    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    String currentPhotoPath;
    private StorageReference mStorageReference;
    private Context context;
    int iconStar=0;
    public addbook() {
    }

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
        images=findViewById(R.id.image);
        loadImage_button=findViewById(R.id.limage);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mFirebaseMethods=new addbook(addbook.this);


        mStorageReference = FirebaseStorage.getInstance().getReference();

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"You are already in the page add", Toast.LENGTH_SHORT).show();

            }
        });
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                imageCount = mFirebaseMethods.getImageCount(dataSnapshot);
                Log.d(TAG, "onDataChange: image count: " + imageCount);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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

                addInfos(id,Titre,Description,Year,iconStar);

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
       /* loadImage_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
dispatchTakePictureIntent();            }
        });
*/
    }

    @SuppressLint("LongLogTag")
    private void askCameraPermissions() {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
            String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST );

        }else {
            Log.d("liliwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww1", String.valueOf(1));

            dispatchTakePictureIntent();

        }}

    @SuppressLint("LongLogTag")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERM_CODE: {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("liliwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww2", String.valueOf(2));

                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "Camera Permission is Required to Use camera.", Toast.LENGTH_SHORT).show();
            }}
            case REQUEST:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dispatchTakePictureIntent();

                } else {
                    Toast.makeText(this, "The app was not allowed to read your store.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    @SuppressLint({ "LongLogTag"})
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d("liliwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww3", String.valueOf(3));

        super.onActivityResult(requestCode, resultCode, data);

                Log.d("liliwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww3", String.valueOf(3));

                File f = new File(currentPhotoPath);
                images.setImageURI(Uri.fromFile(f));
                Log.d("tag", "Absolute Url of Image is " + Uri.fromFile(f));

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);
                DatabaseReference dat = FirebaseDatabase.getInstance().getReference("book");
                String bookId = dat.push().getKey();

                uploadImageToFirebase(f.getName(), contentUri, imageCount, bookId);




        }

    @SuppressLint("LongLogTag")
    private File createImageFile() throws IOException {
        // Create an image file name

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        Log.d("liliwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww4", String.valueOf(4));

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        Log.d("liliwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww6", String.valueOf(4));

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
    public int getImageCount(DataSnapshot dataSnapshot){
        int count = 0;
        Intent intent=getIntent();
        DatabaseReference data= FirebaseDatabase.getInstance().getReference("book");
        String s=data.push().getKey();
    for(DataSnapshot ds: dataSnapshot
                .child("photo")
                .child(s)
                .getChildren()){
            count++;
        }
        return count;
    }
    @SuppressLint("LongLogTag")
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            Log.d("liliwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww5", String.valueOf(5));

            File photoFile = null;
            try {
                photoFile = createImageFile();

            } catch (IOException ex) {
                ex.printStackTrace();

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                File f = new File(currentPhotoPath);
                images.setImageURI(Uri.fromFile(f));
                Log.d("tag", "Absolute Url of Image is " + Uri.fromFile(f));

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);
                DatabaseReference dat = FirebaseDatabase.getInstance().getReference("book");
                String bookId = dat.push().getKey();

                uploadImageToFirebase(f.getName(), contentUri, imageCount, bookId);

            }
        }
    }
    private void uploadImageToFirebase(String name, final Uri contentUri, int count,String id) {

        FilePath filePaths = new FilePath();

        Log.d(TAG, "uploadNewPhoto: uploading NEW photo.");


        final StorageReference image = mStorageReference
                .child(filePaths.FIREBASE_IMAGE_STORAGE + "/" + id + "/photo" + (count + 1));
        image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("tag", "onSuccess: Uploaded Image URl is " + uri.toString());
                        Picasso.get().load(uri).into(images);
                        addPhotoToDatabase(uri.toString(),id);
                        // addPhotoToDatabase(image.getDownloadUrl().toString());
                    }
                });

                Toast.makeText(addbook.this, "Image Is Uploaded.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(addbook.this, "Upload Failed.", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void addPhotoToDatabase(String url,String id){
        Log.d(TAG, "addPhotoToDatabase: adding photo to database.");

        String l=url;
        //insert into database
        mDatabase.child("book")
                .child(id).child("image_path").setValue(l);

    }
    public addbook(Context context) {

        mDatabase= FirebaseDatabase.getInstance().getReference();
        context = context;

    }

    public void addInfos(String id,String Titre,String Description,String Year,int icstar){
        DatabaseReference data= FirebaseDatabase.getInstance().getReference();
        Information info=new Information(id,Titre,Description,Year,icstar);//,Description,
        data.child("book").child(id).setValue(info);

    }
}