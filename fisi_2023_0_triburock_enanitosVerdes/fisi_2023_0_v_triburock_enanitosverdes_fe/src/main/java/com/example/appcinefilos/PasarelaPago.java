package com.example.appcinefilos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

public class PasarelaPago extends AppCompatActivity {
    Button stripeButton;
    EditText amountEditText;
    PaymentSheet paymentSheet;
    String paymentIntentClientSecret, amount;
    PaymentSheet.CustomerConfiguration customerConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasarela_pago);
        stripeButton = findViewById(R.id.stripeButton);
        amountEditText = findViewById(R.id.amountEditText);

        stripeButton.setOnClickListener(view ->{
            if (TextUtils.isEmpty(amountEditText.getText()
                    .toString())){
                System.out.println("llega primero");
                Toast.makeText(this, "La cantidad no puede estar vacía!", Toast.LENGTH_SHORT).show();
            } else {
                System.out.println("llega Segundo");
                amount = amountEditText.getText().toString() + "00";
                getDetails();
            }
        });

        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);
    }

    void getDetails(){
        Fuel.INSTANCE.post("https://us-central1-pago-31d4b.cloudfunctions.net/stripePayment?amt="
                + amount, null).responseString(new Handler<String>() {
            @Override
            public void success(String s) {
                try {
                    JSONObject result = new JSONObject(s);
                    customerConfig = new PaymentSheet.CustomerConfiguration(
                            result.getString("customer"),
                            result.getString("ephemeralKey")
                    );
                    paymentIntentClientSecret = result.getString("paymentIntent");
                    PaymentConfiguration.init(getApplicationContext(), result.getString("publishableKey"));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showStripePaymentSheet();
                        }
                    });

                } catch (JSONException e){
                    Toast.makeText(PasarelaPago.this, e.getMessage() , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(@NonNull FuelError fuelError) {

            }
        });
    }

    void showStripePaymentSheet(){
        System.out.println("llega 10");
        final PaymentSheet.Configuration configuration = new PaymentSheet.Configuration.Builder("coDeR")
                .customer(customerConfig)
                .allowsDelayedPaymentMethods(true)
                .build();
        paymentSheet.presentWithPaymentIntent(
                paymentIntentClientSecret,
                configuration
        );

    }

    void onPaymentSheetResult(final PaymentSheetResult paymentSheetResult){
        System.out.println("llega 11");
        if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            System.out.println("llega 12");
            Toast.makeText(this, "Pago cancelado", Toast.LENGTH_SHORT).show();
        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            System.out.println("llega 13");
            Toast.makeText(this, ((PaymentSheetResult.Failed) paymentSheetResult).getError().toString() , Toast.LENGTH_SHORT).show();
        } else if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            System.out.println("llega 14");
            Toast.makeText(this, "Pago realizado correctamente", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(PasarelaPago.this, MainActivity.class);
            startActivity(i);
        }

    }



}