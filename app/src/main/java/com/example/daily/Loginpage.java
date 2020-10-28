package com.example.daily;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.*;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.gun0912.tedpermission.TedPermission.TAG;


public class Loginpage extends AppCompatActivity {

    TextView nickname;
    TextView emailset;
    TextView pomotext;
    ImageView back;
    ImageView profile;
    Handler handler;
    String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int PICK_FROM_ALBUM = 2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);

         back = findViewById(R.id.backsetting);
         profile = findViewById(R.id.profile);
         nickname = findViewById(R.id.nickname);
         emailset = findViewById(R.id.emailset);
         pomotext = findViewById(R.id.pomotext);
         handler = new Handler();
         setting();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        SharedPreferences sharedPreferences= getSharedPreferences("뽀모횟수",MODE_PRIVATE);
        int i= sharedPreferences.getInt("매일",0);

        pomotext.setText("오늘 : "+i+"개");


        nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nickname_dialog dialog = new Nickname_dialog(Loginpage.this);
                dialog.show();

            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "권한 설정 완료");
                    } else {
                        Log.d(TAG, "권한 설정 요청");
                        ActivityCompat.requestPermissions(Loginpage.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    }
                }
            photoDialog();

            }
        });

    }

    private void photoDialog() {
        final CharSequence[] photomodel = {"갤러리에서 가져오기", "사진 찍기"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("프로필 사진");
        builder.setSingleChoiceItems(photomodel, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                Toast.makeText(getApplicationContext(), photomodel[item] + "선택", Toast.LENGTH_SHORT).show();
                if (item == 0) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                    intent.setType("image/*");
                    startActivityForResult(intent, PICK_FROM_ALBUM);

                } else {
                    takepicture();
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("아이디", MODE_PRIVATE);
        String name = sharedPreferences.getString("UserName", null);

        nickname.setText(name);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICK_FROM_ALBUM:
                if (resultCode == Activity.RESULT_OK) {
                    if (data.getData() != null) {
                        try {

                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                            profile.setImageBitmap(bitmap);
                            String image = BitmapToString(bitmap);
                            SharedPreferences sharedPreferences= getSharedPreferences("아이디",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("이미지",image);
                            editor.commit();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;

            case REQUEST_TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {

                        try {
                            File file = new File(mCurrentPhotoPath);
                            Bitmap bitmap = MediaStore.Images.Media
                                    .getBitmap(getContentResolver(), Uri.fromFile(file));
                            if (bitmap != null) {
                                profile.setImageBitmap(bitmap);
                                String image = BitmapToString(bitmap);
                                SharedPreferences sharedPreferences= getSharedPreferences("아이디",MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("이미지",image);
                                editor.commit();

                            }
                        }catch (Exception e){
                            Log.e("TAKE_ALBUM_SINGLE ERROR", e.toString());
                        }
                    }
                break;
        }
    }
    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );


        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    private void takepicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "androidx.core.content.FileProvider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    public String BitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public Bitmap StringToBitmap(String encodeString){
        try{
          byte[] encedeByte=Base64.decode(encodeString,Base64.DEFAULT);
          Bitmap bitmap = BitmapFactory.decodeByteArray(encedeByte,0,encedeByte.length);
          return bitmap;
        }catch(Exception e){
          e.getMessage();
          return null;
        }

    }
    public void setting() {
       new Thread(new Runnable() {
       @Override
       public void run() {
               while(true) {

                   SharedPreferences sharedPreferences = getSharedPreferences("아이디", MODE_PRIVATE);
                   String name = sharedPreferences.getString("UserName", null);
                   String email = sharedPreferences.getString("UserEmail",null);
                   String image = sharedPreferences.getString("이미지", null);

               handler.post(new Runnable() {
       @Override
       public void run() {
           nickname.setText(name);
           emailset.setText(email);
           if(image!=null){
               Bitmap bitmap = StringToBitmap(image);
               profile.setImageBitmap(bitmap);
           }
               }
               });
               try {
               Thread.sleep(100);
               } catch (InterruptedException interruptedException) {
               interruptedException.printStackTrace();}
               }
       }
       }).start();

    }

}





