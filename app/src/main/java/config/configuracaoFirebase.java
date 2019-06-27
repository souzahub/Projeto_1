package config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class configuracaoFirebase {

    private static DatabaseReference database;
    private static FirebaseAuth auth;

    public static DatabaseReference getFirebaseDatabse(){

        if ( database == null ){
            database = FirebaseDatabase.getInstance().getReference();
        }
        return database;
    }
    public static FirebaseAuth getAutentificacao(){

        if ( auth == null ){
            auth = FirebaseAuth.getInstance();
        }

        return auth;    }
}

