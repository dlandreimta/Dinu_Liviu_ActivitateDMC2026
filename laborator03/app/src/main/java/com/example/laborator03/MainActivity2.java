package com.example.laborator03;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btn = findViewById(R.id.buttonGoTo3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);

                Bundle bundle = new Bundle();
                bundle.putString("mesaj_cheie", "Salutare din Activitatea 2!");
                bundle.putInt("numar1", 10);
                bundle.putInt("numar2", 20);

                intent.putExtras(bundle);

                startActivityForResult(intent, 1);
            }
        });
    }

    private static final String TAG = "Lifecycle_MA2";

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart apelat (Info)");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume apelat (Debug)");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG, "onPause apelat (Warning)");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop apelat (Error)");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy apelat (Verbose)");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                String mesajPrimit = data.getStringExtra("mesaj_retur");
                int sumaPrimita = data.getIntExtra("valoare_suma", 0);

                Toast.makeText(this, mesajPrimit + sumaPrimita, Toast.LENGTH_LONG).show();
            }
        }
    }

}