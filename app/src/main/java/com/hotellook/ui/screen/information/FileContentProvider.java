package com.hotellook.ui.screen.information;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import java.io.File;
import java.io.FileNotFoundException;
import pl.charmas.android.reactivelocation.C1822R;

public class FileContentProvider extends ContentProvider {
    public static final String AUTHORITY = "com.hotellook.provider";
    private UriMatcher uriMatcher;

    public boolean onCreate() {
        this.uriMatcher = new UriMatcher(-1);
        this.uriMatcher.addURI(AUTHORITY, "*", 1);
        return true;
    }

    public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
        switch (this.uriMatcher.match(uri)) {
            case C1822R.styleable.SignInButton_colorScheme /*1*/:
                return ParcelFileDescriptor.open(new File(getContext().getCacheDir() + File.separator + uri.getLastPathSegment()), 268435456);
            default:
                throw new FileNotFoundException("Unsupported uri: " + uri.toString());
        }
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
