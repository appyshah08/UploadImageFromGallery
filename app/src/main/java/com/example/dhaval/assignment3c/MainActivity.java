package com.example.dhaval.assignment3c;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    Button btn;
    public static final int SELECT_PICTURE=1;
    private String selectedImagePath;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.button);
        img= (ImageView) findViewById(R.id.imageView);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");

                //this intent.createChoser will again display the screen showing which app to use even though u have set the
                //default app to open your app.
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            }
        });
    }
      @Override
      /*
      This is the method where the result will be shown.
      Intent will hold the data.

       */
        public void onActivityResult(int requestCode,int resultCode,Intent data)
        {
           if(resultCode==RESULT_OK)
           {
               if(requestCode==SELECT_PICTURE)
               {
                   Uri selectedImageUri = data.getData();
                   selectedImagePath = getPath(selectedImageUri);
                   System.out.println("Image Path : " + selectedImagePath);
                   img.setImageURI(selectedImageUri);
               }
           }
        }
    public String getPath(Uri uri) {
        //A "projection" defines the columns that will be returned for each row
        String[] projection = { MediaStore.Images.Media.DATA };
      //
        //Cursor cursor = managedQuery(uri, projection, null, null, null);
        /*
        * params for query method
        * uri,projection,selection,selection args,sort order.
        * */
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        //Cursor the interface provides read write access to the result set returned by database query.
        //getColumnIndexOrThrow checks whether the column exist or not.
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}


