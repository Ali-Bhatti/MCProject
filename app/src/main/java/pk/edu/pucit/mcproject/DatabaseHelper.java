package pk.edu.pucit.mcproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;

public class DatabaseHelper extends SQLiteOpenHelper {

    Context context;

    public DatabaseHelper(Context context) {
        super(context, "User.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table user(Name text,Email text primary key,Password text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table If Exists user");
    }

    //inserting in database
    public boolean insert(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", name);
        contentValues.put("Email", email);
        contentValues.put("Password", password);
        long ins = db.insert("user", null, contentValues);
        if (ins == -1) {
            return false;
        } else {
            return true;
        }
    }

    //checking if email exists
    public boolean checkEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("Select * from user where Email=?", new String[]{email});
            if (cursor.getCount() > 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
            // exception handling
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    //checking the email and password
    public boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("Select * from user where Email=? and Password=?", new String[]{email, password});
            if (cursor.getCount() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
            // exception handling
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public String getName(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor cursor = db.rawQuery("Select Name from user where Email=?", new String[]{email})) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                return cursor.getString(cursor.getColumnIndex("Name"));
            }
        } catch (Exception e) {
            return "Please Login First";
            // exception handling
        }
        return "";
    }

    public String getPassword(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor cursor = db.rawQuery("Select Password from user where Email=?", new String[]{email})) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                return cursor.getString(cursor.getColumnIndex("Password"));
            }
        } catch (Exception e) {
            return "Please Login First";
            // exception handling
        }
        return "";
    }

    public void changePassword(String email, String NewPass) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("Password", NewPass);
        int countUpdated = this.getWritableDatabase().update("user", contentValues, "Email=?", new String[]{email});
    }
}
