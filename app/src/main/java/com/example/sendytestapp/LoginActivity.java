package com.example.sendytestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.com.sapereaude.maskedEditText.MaskedEditText;
import land.sendy.pfe_sdk.api.API;
import land.sendy.pfe_sdk.model.types.ApiCallback;
import land.sendy.pfe_sdk.model.types.LoaderError;

import static land.sendy.pfe_sdk.api.API.api;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        String SERVER_URL = "https://testwallet.sendy.land/";
        api = API.getInsatce(SERVER_URL);
        Looper lp = Looper.getMainLooper();


        Button button = findViewById(R.id.button_resume);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login(v);
            }
        };

        button.setOnClickListener(onClickListener);



    }


    public void Login(View view) {

        MaskedEditText telephoneNumber = findViewById(R.id.phone_input);
        String phone = removeSpace(telephoneNumber.getText().toString());
        if (phone.length() < 11) {
            Toast.makeText(getApplicationContext(),
                    "Проверьте правильность ввода телефона",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(),
                    phone,
                    Toast.LENGTH_SHORT).show();


            API.outLog("Тест: WS. Попытка старта активации кошелька: " + phone);
            api.loginAtAuth(getApplicationContext(), phone, new ApiCallback() {
                @Override
                public void onCompleted(boolean res) {
                    if (!res || getErrNo() != 0) {
                        // Обработка ошибки и извещение клиента об ошибке:
                        API.outLog("Ошибка: " + this.toString());
                    } else {
                        // Обработка результатов запроса.
                        // this.oResponse тип AuthLoginRs
                    }
                }
            });
        }
    }


    static String removeSpace(String str) {
        str = str.replaceAll("\\s", "");
        str = str.replaceAll("[+]", "");
        System.out.println(str);
        return str;
    }
}