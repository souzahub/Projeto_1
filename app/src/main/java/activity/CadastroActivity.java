package activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.example.mapsactivite.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import config.configuracaoFirebase;
import mofel.Usuario;

public class CadastroActivity extends AppCompatActivity {
    private TextInputEditText campoNome, campoEmail, campoSenha;
    private Button btnCadastrar;
    private Switch SwitchTipoUsuario;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        campoNome = findViewById(R.id.editCadastroNome);
        campoEmail = findViewById(R.id.editCadastroEmail);
        campoSenha = findViewById(R.id.editCadastroSenha);
        btnCadastrar = findViewById(R.id.btn_cadastrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CadastroActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
    public void validarCadastroUsuario(View view){

        String textoNome = campoNome.getText().toString();
        String textoEmail = campoEmail.getText().toString();
        String textoSenha = campoSenha.getText().toString();

        if( !textoNome.isEmpty() ){
            if( !textoEmail.isEmpty() ){
                if( !textoSenha.isEmpty() ){

                    mofel.Usuario usuario = new Usuario();
                    usuario.setNome( textoNome);
                    usuario.setEmail( textoEmail);
                    usuario.setSenha( textoSenha);
                    usuario.setTipo(verificaTipoUsuario());

                    cadastrarUsuario (usuario);

                }else {
                    Toast.makeText(CadastroActivity.this,
                            "Preencha a Senha !",
                            Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(CadastroActivity.this,
                        "Preencha o Email !",
                        Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(CadastroActivity.this,
                    "Preencha o Nome !",
                    Toast.LENGTH_SHORT).show();
        }



    }

    public void cadastrarUsuario ( Usuario usuario ){
        autenticacao = configuracaoFirebase.getAutentificacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if ( task.isSuccessful()){
                    Toast.makeText(CadastroActivity.this,
                            "Sucesso ao Cadastrar o Usuário", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(CadastroActivity.this, "Ocorreu um erro", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    public String verificaTipoUsuario(){
        return SwitchTipoUsuario.isChecked() ?"M":"P";
    }
}
