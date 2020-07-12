package pk.edu.pucit.mcproject;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String Share_Pref_Name="session";
    String SESSION_KEY="session_user";

    public SessionManagement(Context context){
        sharedPreferences=context.getSharedPreferences(Share_Pref_Name,context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }
    public void saveSession(User user){
        //save session of user whenever user is logged in
        String mail=user.getEmail();
        editor.putString(SESSION_KEY,mail).commit();

    }
    public void deleteSession(){
        //save session of user whenever user is logged in
        editor.putString(SESSION_KEY,null).commit();
    }
    public String getSession(){
        //return user whose session is saved
        return sharedPreferences.getString(SESSION_KEY,null);
    }
}
