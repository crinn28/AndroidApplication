package com.example.crimeslostsreport.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.crimeslostsreport.Classes.AnnouncementActivityHelper;
import com.example.crimeslostsreport.Interfaces.RecyclerViewInterface;
import com.example.crimeslostsreport.Models.User;
import com.example.crimeslostsreport.R;

import java.io.ByteArrayOutputStream;

public class AnnouncementActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener,
        RecyclerViewInterface {

    double latitude, longitude;
    private AnnouncementActivityHelper announcementHelper;
    byte[] byteArray;

    private Spinner category, status;
    private TextView statusTextView;
    private Uri imageUri;
    private EditText editTextTitle, editTextDescription;
    private Button buttonPost, buttonCancel, buttonChooseImage, buttonOpenCamera, buttonChangeLocation;
    private ImageView imageView, backImageView;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);

        User currentUser = (User) getIntent().getSerializableExtra("user");
        int announcementID = getIntent().getIntExtra("announcementID", 0);

        latitude = getIntent().getDoubleExtra("latitude", -1);
        longitude = getIntent().getDoubleExtra("longitude", -1);

        if (latitude == -1 && longitude == -1) {
            Intent intent = new Intent(AnnouncementActivity.this, MyLocationActivity.class);
            intent.putExtra("user", currentUser);
            intent.putExtra("announcementID", announcementID);
            startActivity(intent);
            return;
        }

        byteArray = new byte[0];

        announcementHelper = new AnnouncementActivityHelper(currentUser, announcementID);

        category = findViewById(R.id.categorySelect);
        status = findViewById(R.id.statusSelect);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        imageView = findViewById(R.id.image_view);
        statusTextView = findViewById(R.id.statusTextView);

        buttonPost = findViewById(R.id.buttonPost);
        buttonCancel = findViewById(R.id.buttonCancel);
        buttonChooseImage = findViewById(R.id.buttonChooseImage);
        buttonOpenCamera = findViewById(R.id.buttonOpenCamera);
        buttonChangeLocation = findViewById(R.id.changeLocation);
        backImageView = findViewById(R.id.imageViewBack);

        category.setOnItemSelectedListener(this);
        buttonPost.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
        buttonChooseImage.setOnClickListener(this);
        buttonOpenCamera.setOnClickListener(this);
        buttonChangeLocation.setOnClickListener(this);
        backImageView.setOnClickListener(this);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, announcementHelper.setCategories());
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, announcementHelper.setStatuses());

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        category.setAdapter(dataAdapter);
        status.setAdapter(statusAdapter);

        statusTextView.setVisibility(announcementHelper.setVisibility());
        status.setVisibility(announcementHelper.setVisibility());
        buttonChangeLocation.setVisibility(announcementHelper.setVisibility());
        initializeAnnouncement();
        buttonPost.setText(announcementHelper.setButtonText());
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void initializeAnnouncement() {
        if (announcementHelper.getAnnouncementID() != 0) {
            editTextTitle.setText(announcementHelper.getCurrentAnnouncement().getTitle());
            editTextDescription.setText(announcementHelper.getCurrentAnnouncement().getDescription());
            category.setSelection(0);
            if (announcementHelper.getCurrentAnnouncement().getIs_crime())
                category.setSelection(1);
            imageView.setImageBitmap(announcementHelper.getAnnouncementImage());
        }
    }

    private void updateAnnouncement() {
        announcementHelper.updateAnnouncement(editTextTitle.getText().toString(),
                editTextDescription.getText().toString(),
                Boolean.valueOf(category.getSelectedItem().equals("CRIME")),
                status, announcementHelper.convertImageUriToBinaryArray(imageUri, this.getContentResolver()),
                latitude, longitude);
        Intent intent = new Intent(AnnouncementActivity.this, MainActivity.class);
        intent.putExtra("user", announcementHelper.getCurrentUser());
        startActivity(intent);
        finish();
    }

    private void postAnnouncement() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        boolean selectedCategory = String.valueOf(category.getSelectedItem()).equals("CRIME");
        byte[] image = announcementHelper.convertImageUriToBinaryArray(imageUri, this.getContentResolver());
        if (byteArray.length != 0)
            image = byteArray;

        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, "Please enter title", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(description)) {
            Toast.makeText(this, "Please enter description", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            announcementHelper.addAnnouncement(image, description, title, selectedCategory,
                    latitude, longitude);

            Intent intent = new Intent(AnnouncementActivity.this, MainActivity.class);
            intent.putExtra("user", announcementHelper.getCurrentUser());
            startActivity(intent);
            finish();
        } catch (Exception ex) {
            String error = ex.getMessage();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        } else if (requestCode == 123) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
            photo = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            announcementHelper.setByteArray(byteArray);
            imageUri = null;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonPost:
                if (announcementHelper.getAnnouncementID() == 0)
                    postAnnouncement();
                else
                    updateAnnouncement();
                break;
            case R.id.buttonChooseImage:
                openFileChooser();
                break;
            case R.id.buttonCancel:
            case R.id.imageViewBack:
                Intent intent = new Intent(AnnouncementActivity.this, MainActivity.class);
                intent.putExtra("user", announcementHelper.getCurrentUser());
                startActivity(intent);
                finish();
                break;
            case R.id.buttonOpenCamera:
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera_intent, 123);
                break;
            case R.id.changeLocation:
                Intent map = new Intent(this, MapActivity.class);
                map.putExtra("announcementID", announcementHelper.getAnnouncementID());
                map.putExtra("user", announcementHelper.getCurrentUser());
                startActivity(map);
                break;

            default:
                break;
        }
    }


    @Override
    public void onDeleteClick(int position) {
    }

    @Override
    public void onEditClick(int position) {
    }

    @Override
    public void onItemClick(int position) {
    }

}