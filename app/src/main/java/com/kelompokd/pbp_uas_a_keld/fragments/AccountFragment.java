package com.kelompokd.pbp_uas_a_keld.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kelompokd.pbp_uas_a_keld.API.MorentAPI;
import com.kelompokd.pbp_uas_a_keld.DashboardActivity;
import com.kelompokd.pbp_uas_a_keld.LoginActivity;
import com.kelompokd.pbp_uas_a_keld.R;
import com.kelompokd.pbp_uas_a_keld.model.Mobil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.android.volley.Request.Method.GET;

public class AccountFragment extends Fragment {

    private String CHANNEL_ID = "Channel 1";

    private int REQUEST_IMAGE_CAPTURE = 100;
    private int RESULT_OK = -1;
    private int PERMISSION_CODE = 1001;
    private int IMAGE_PICK_CODE = 1001;

    public ImageView image_acc_view;

    SharedPreferences sharedpreferences;
    public static final String loginPreferences = "loginPreferences";
    String id, nama, email, alamat;

    public final static String TAG_ID = "id";
    public final static String TAG_FULL_NAME = "full_name";
    public final static String TAG_EMAIL = "email";
    public final static String TAG_ALAMAT = "alamat";

    public View root;
    MaterialTextView tv_name, tv_tgl_lahir, tv_jenisKelamin, tv_email, tv_alamat, editProfile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_account, container, false);

        //Ambil data user dari data yang disimpan pada sharedpreferences
        sharedpreferences = getActivity().getSharedPreferences(loginPreferences, Context.MODE_PRIVATE);
        id = sharedpreferences.getString(TAG_ID, null);
        nama = sharedpreferences.getString(TAG_FULL_NAME, null);
        email = sharedpreferences.getString(TAG_EMAIL, null);
        alamat = sharedpreferences.getString(TAG_ALAMAT, null);

        CardView btn_logout = root.findViewById(R.id.cv_logout);
        ImageButton back_btn = root.findViewById(R.id.back_btn);
        RelativeLayout image_acc_layout = root.findViewById(R.id.image_acc_layout);

        ImageButton acc_setting_corner = root.findViewById(R.id.account_settings_corner);
        image_acc_view = root.findViewById(R.id.image_acc_view);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutPopup();
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.nav_home);
            }
        });

        image_acc_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        acc_setting_corner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.nav_acc_settings);
            }
        });

        // Ini Textview untuk edit profile
        TextView editProfile = root.findViewById(R.id.editProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Jika di klik maka masuk ke fragment EditProfile
                Navigation.findNavController(root).navigate(R.id.nav_editProfile);
            }
        });

        ///////////////////////////////////////////////////////////////////////////
        init();
        ///////////////////////////////////////////////////////////////////////////
        return root;
    }

    public void init(){
        tv_name = root.findViewById(R.id.tv_name);
        tv_tgl_lahir = root.findViewById(R.id.tv_tgl_lahir);
        tv_jenisKelamin = root.findViewById(R.id.tv_jenisKelamin);
        tv_email = root.findViewById(R.id.tv_email);
        tv_alamat = root.findViewById(R.id.tv_alamat);
        editProfile = root.findViewById(R.id.editProfile);

        getCurrentUser(Integer.parseInt(id));
    }

    public void getCurrentUser(int id){
        //Pendeklarasian queue
        RequestQueue queue = Volley.newRequestQueue(root.getContext());

        //Meminta tanggapan string dari URL yang telah disediakan menggunakan method GET
        //untuk request ini tidak memerlukan parameter
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(root.getContext());
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menampilkan data akun");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, MorentAPI.URL_SHOW_USER + id
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
                progressDialog.dismiss();
                try {
                    //Mengambil data response json object yang berupa data mahasiswa
                    JSONObject jsonObject = response.getJSONObject("data");

                    int idUser        = jsonObject.optInt("id");
                    String full_name  = jsonObject.optString("full_name");
                    String email           = jsonObject.optString("email");
                    String tanggal_lahir           = jsonObject.optString("tanggal_lahir");
                    String jenis_kelamin           = jsonObject.optString("jenis_kelamin");
                    String alamat           = jsonObject.optString("alamat");

                    tv_name.setText(full_name);
                    tv_email.setText(email);
                    tv_tgl_lahir.setText(tanggal_lahir);
                    tv_jenisKelamin.setText(jenis_kelamin);
                    tv_alamat.setText(alamat);

                    Bundle data = new Bundle();
                    data.putInt("dataId", idUser);
                    data.putString("dataNama", full_name);
                    data.putString("dataEmail", email);
                    data.putString("dataTglLahir", tanggal_lahir);
                    data.putString("dataJK", jenis_kelamin);
                    data.putString("dataAlamat", alamat);

                    editProfile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Navigation.findNavController(root).navigate(R.id.nav_editProfile, data);
                        }
                    });

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Disini bagian jika response jaringan terdapat ganguan/error
                progressDialog.dismiss();
                Toast.makeText(root.getContext(), error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        //Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
    }

    // Fungsi ini akan menjalankan alert dialog untuk memilih mau lewat kamera atau external storage
    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    takePictureIntent();
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                            String[] permissions = { Manifest.permission.READ_EXTERNAL_STORAGE };
                            requestPermissions(permissions, PERMISSION_CODE);
                        }
                        else{
                            pickImageFromGallery();
                        }
                    }
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    // Ambil dari Gallery
    private void pickImageFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            pickImageFromGallery();
        }
        else{
            Toast.makeText(getContext(), "Permission Denied!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    // Ini fungsi untuk menampilkan pop up sebelum logout
    private void logoutPopup() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setMessage("Are you sure?")
                .setPositiveButton("Logout", new DialogInterface.OnClickListener()                 {

                    public void onClick(DialogInterface dialog, int which) {

                        logout(); // Last step. Logout function

                    }
                }).setNegativeButton("Cancel", null);

        AlertDialog alert1 = alert.create();
        alert1.show();
    }

    // Fungsi untuk logout
    public void logout(){
        createNotificationChannel();
        notifLogout();

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(LoginActivity.session_status, false);
        editor.putString(TAG_ID, null);
        editor.putString(TAG_FULL_NAME, null);
        editor.putString(TAG_EMAIL, null);
        editor.putString(TAG_ALAMAT, null);
        editor.commit();

        Intent ua = new Intent(getContext(), LoginActivity.class);
        ua.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        getActivity().finish();
        startActivity(ua);
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE){
            CharSequence name = "Channel 1";
            String description = "This is Channel 1";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private  void notifLogout(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentText("Goodbye\nSee you later...")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent notificationIntent = new Intent(getContext(), LoginActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    // Fungsi buat buka kamera kalau mau ubah foto profil langsung dengan kamera
    private void  takePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            image_acc_view.setImageBitmap(imageBitmap);
            handleUpload(imageBitmap);
        }
        else if(requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK){
            Uri uri = data.getData();

            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            image_acc_view.setImageURI(uri);

            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            final StorageReference ref = FirebaseStorage.getInstance().getReference()
                    .child("profileImages")
                    .child(uid + ".jpeg");

            ref.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            getDownloadUrl(ref);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), e.getCause().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    // Ini fungsi untuk mengupload foto yang diambil dengan kamera ke Firebase Storage
    private void handleUpload(Bitmap bitmap){

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child("profileImages")
                .child(uid + ".jpeg");

        reference.putBytes(byteArrayOutputStream.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        getDownloadUrl(reference);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), e.getCause().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //Ini fungsi untuk mengambil data dari firebase storage untuk kemudian ditampilkan di imageview
    private  void getDownloadUrl(StorageReference reference){
        reference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        setUserProfileUrl(uri);
                    }
                });
    }

    private void setUserProfileUrl(Uri uri){
        FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();

        user.updateProfile(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Update successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Profile image failed...", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}