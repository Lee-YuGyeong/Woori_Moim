package com.woori.moim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.installations.InstallationTokenResult;
import com.woori.moim.Network.ApiClient;
import com.woori.moim.Network.Api;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;
import okhttp3.ResponseBody;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String token;
    Button button, button2;
    EditText test;
    String t;

    static final int REQUEST_CODE_SCAN_CARD = 1;
    private int MY_SCAN_REQUEST_CODE = 100;
    private View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getToken();
        test = findViewById(R.id.test);
        button = (Button) findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t = test.getText().toString();
                if (t.equals("1")) {//to 유경
                    sendNotificationToUser("eaO54Q75Q_G0ghOmcV9EXv:APA91bF_Wwx5TgzsNZIii6OgwB9aXkplPHNzFWMfFaAL2UAhiT-xUfd3SeUPTZ4HOvxpCNCZinFCHhwAcQBqyaX70JBwb909hFShx3zqJ61u1ocWFTbJnPPVuQwoFgu5BzZzoS2MsqwN");
                } else if (t.equals("2")) {//to 우준
                    sendNotificationToUser("dEXN6QpQRLCUT7-ZixEKKK:APA91bE0t5VduUVIs6iBbY9yLMkkoOk9uIJkpB1nFcUvvhdn3gV_aNE4Q2yWP8vptgBHv5_fPUaAzWydX7CH8MlAVDn7MhcL7M9fV-dBSB_qZv-5yR3YXDSO9rgpVpzDfJqp2ur12SUG");

                } else if (t.equals("3")) {//to both 유경 and 우준
                    sendNotificationToUser("eaO54Q75Q_G0ghOmcV9EXv:APA91bF_Wwx5TgzsNZIii6OgwB9aXkplPHNzFWMfFaAL2UAhiT-xUfd3SeUPTZ4HOvxpCNCZinFCHhwAcQBqyaX70JBwb909hFShx3zqJ61u1ocWFTbJnPPVuQwoFgu5BzZzoS2MsqwN");
                    sendNotificationToUser("dEXN6QpQRLCUT7-ZixEKKK:APA91bE0t5VduUVIs6iBbY9yLMkkoOk9uIJkpB1nFcUvvhdn3gV_aNE4Q2yWP8vptgBHv5_fPUaAzWydX7CH8MlAVDn7MhcL7M9fV-dBSB_qZv-5yR3YXDSO9rgpVpzDfJqp2ur12SUG");
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t = test.getText().toString();
                if (t.equals("1")) {//to 유경
                    sendNotificationToUser2("eaO54Q75Q_G0ghOmcV9EXv:APA91bF_Wwx5TgzsNZIii6OgwB9aXkplPHNzFWMfFaAL2UAhiT-xUfd3SeUPTZ4HOvxpCNCZinFCHhwAcQBqyaX70JBwb909hFShx3zqJ61u1ocWFTbJnPPVuQwoFgu5BzZzoS2MsqwN");
                } else if (t.equals("2")) {//to 우준
                    sendNotificationToUser2("dEXN6QpQRLCUT7-ZixEKKK:APA91bE0t5VduUVIs6iBbY9yLMkkoOk9uIJkpB1nFcUvvhdn3gV_aNE4Q2yWP8vptgBHv5_fPUaAzWydX7CH8MlAVDn7MhcL7M9fV-dBSB_qZv-5yR3YXDSO9rgpVpzDfJqp2ur12SUG");

                } else if (t.equals("3")) {//to both 유경 and 우준
                    sendNotificationToUser2("eaO54Q75Q_G0ghOmcV9EXv:APA91bF_Wwx5TgzsNZIii6OgwB9aXkplPHNzFWMfFaAL2UAhiT-xUfd3SeUPTZ4HOvxpCNCZinFCHhwAcQBqyaX70JBwb909hFShx3zqJ61u1ocWFTbJnPPVuQwoFgu5BzZzoS2MsqwN");
                    sendNotificationToUser2("dEXN6QpQRLCUT7-ZixEKKK:APA91bE0t5VduUVIs6iBbY9yLMkkoOk9uIJkpB1nFcUvvhdn3gV_aNE4Q2yWP8vptgBHv5_fPUaAzWydX7CH8MlAVDn7MhcL7M9fV-dBSB_qZv-5yR3YXDSO9rgpVpzDfJqp2ur12SUG");
                }
            }
        });

        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onScanPress(v); // 카드인식 라이브러리1
                //scanCard(); // 카드인식 라이브러리 2
            }
        });

        EditText editText = (EditText) findViewById(R.id.editText_code);
        Button button_code = (Button) findViewById(R.id.button_code);
        TextView textView_en = (TextView) findViewById(R.id.textView_en);
        TextView textView_de = (TextView) findViewById(R.id.textView_de);

        button_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String originalText = editText.getText().toString();
                    String key = "key";
                    String en;
                    en = Encrypt(originalText, key);
                    String de = Decrypt(en, key);

                    textView_en.setText(en);
                    textView_de.setText(de);


                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }
        }); //암호화, 복호화

        Button button_splash = (Button) findViewById(R.id.button_splash);
        button_splash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Splash.class);
                startActivity(intent);
            }
        });

    }

    public static String Decrypt(String text, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes("UTF-8");
        int len = b.length;
        if (len > keyBytes.length) len = keyBytes.length;
        System.arraycopy(b, 0, keyBytes, 0, len);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] results = cipher.doFinal(Base64.decode(text, 0));
        return new String(results, "UTF-8");

    }//복호화


    public static String Encrypt(String text, String key) throws Exception {

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes("UTF-8");
        int len = b.length;
        if (len > keyBytes.length) len = keyBytes.length;
        System.arraycopy(b, 0, keyBytes, 0, len);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] results = cipher.doFinal(text.getBytes("UTF-8"));

        return Base64.encodeToString(results, 0);

    }//암호화


    private void sendNotificationToUser2(String token) {
        Model model = new Model(token, new NotificationModel("모임개설", "모임 확인 부탁드립니다!!", "Attend", "MOIM"));
        Api apiService = ApiClient.getClient().create(Api.class);
        retrofit2.Call<ResponseBody> responseBodyCall = apiService.sendNotification(model);

        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                Log.d("testtest", "성공");
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                Log.d("testtest", "실패");
            }
        });
    }

    private void sendNotificationToUser(String token) {

        Model model = new Model(token, new NotificationModel("결제알림", "계좌이체 부탁드립니다!!", "MainActivity2", "보이니 나의 전송 메시지?? 여기로 계좌번호로 슝~"));
        Api apiService = ApiClient.getClient().create(Api.class);
        retrofit2.Call<ResponseBody> responseBodyCall = apiService.sendNotification(model);

        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                Log.d("testtest", "성공");
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                Log.d("testtest", "실패");
            }
        });
    }

    /***for signup***/
    public void getToken() {
        //토큰값을 받아옵니다.
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        System.out.println(task.getResult().getToken());
                    }
                });
    }

    //////////////////////////////카드인식 라이브러리1/////////////////////////////
    public void onScanPress(View v) {
        Intent scanIntent = new Intent(this, CardIOActivity.class);

        // customize these values to suit your needs.
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false

        // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
        startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_SCAN_REQUEST_CODE) {
            String resultDisplayStr;
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

                // Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
                resultDisplayStr = "Card Number: " + scanResult.getRedactedCardNumber() + "\n";

                // Do something with the raw number, e.g.:
                // myService.setCardNumber( scanResult.cardNumber );

                if (scanResult.isExpiryValid()) {
                    resultDisplayStr += "Expiration Date: " + scanResult.expiryMonth + "/" + scanResult.expiryYear + "\n";
                }

                if (scanResult.cvv != null) {
                    // Never log or display a CVV
                    resultDisplayStr += "CVV has " + scanResult.cvv.length() + " digits.\n";
                }

                if (scanResult.postalCode != null) {
                    resultDisplayStr += "Postal Code: " + scanResult.postalCode + "\n";
                }
            } else {
                resultDisplayStr = "Scan was canceled.";
            }
            // do something with resultDisplayStr, maybe display it in a textView
            // resultTextView.setText(resultDisplayStr);
        }
        // else handle other activity results
    }
//////////////////////////////////////////////////////////////////////////////


//////////////////////////////////카드인식 라이브러리2/////////////////////////////////
    //    private void scanCard() {
//        Intent intent = new ScanCardIntent.Builder(this).build();
//        startActivityForResult(intent, REQUEST_CODE_SCAN_CARD);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE_SCAN_CARD) {
//            if (resultCode == Activity.RESULT_OK) {
//                Card card = data.getParcelableExtra(ScanCardIntent.RESULT_PAYCARDS_CARD);
//                String cardData = "Card number: " + card.getCardNumberRedacted() + "\n"
//                        + "Card holder: " + card.getCardHolderName() + "\n"
//                        + "Card expiration date: " + card.getExpirationDate();
//                Log.d("testtesttest", "Card info: " + cardData);
//            } else if (resultCode == Activity.RESULT_CANCELED) {
//                Log.d("testtesttest", "Scan canceled");
//            } else {
//                Log.d("testtesttest", "Scan failed");
//            }
//        }
//    }
    /////////////////////////////////////////////////////////////////////////////
}