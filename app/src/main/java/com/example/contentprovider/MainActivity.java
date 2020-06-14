package com.example.contentprovider;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        listView = findViewById( R.id.list );
        if (ContextCompat.checkSelfPermission( getApplicationContext(), Manifest.permission.READ_CONTACTS )
                != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions( this, new String[]
                    {Manifest.permission.READ_CONTACTS,}, 1 );
        }
        fetchContacts();
    }

    private void fetchContacts() {
        ArrayList<String> contacts = new ArrayList<>();
        
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = null;
        
        ContentResolver resolver = getContentResolver();
        
        Cursor cursor = resolver.query( 
                uri, 
                projection, 
                selection, 
                selectionArgs, 
                sortOrder 
        );
        
        while (cursor.moveToNext()) {
            String name = cursor.getString( cursor.getColumnIndex( 
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME ) );
            
            String number = cursor.getString( cursor.getColumnIndex( 
                    ContactsContract.CommonDataKinds.Phone.NUMBER ) );
            
            contacts.add( name + "\n" + number );
        }
        listView.setAdapter( new ArrayAdapter<>( this, android.R.layout.simple_list_item_1,contacts) );
    }
}
