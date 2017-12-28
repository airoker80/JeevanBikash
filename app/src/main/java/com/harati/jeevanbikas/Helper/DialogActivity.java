package com.harati.jeevanbikas.Helper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.Printer.MainPrinterActivity;
import com.harati.jeevanbikas.R;
import com.smartdevice.sdk.printer.PrintService;


public class DialogActivity extends AppCompatActivity {
    TextView msgDetail;
     Button ok;
     ImageButton print;
     String extraMesage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        extraMesage = getIntent().getStringExtra("msg");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);
        print = (ImageButton) findViewById(R.id.print);
        msgDetail=(TextView)findViewById(R.id.msgDetail);
        if (getIntent().getStringExtra("print").equals("print")){
            print.setVisibility(View.VISIBLE);
        }
        try {
            msgDetail.setText(extraMesage);
        }catch (Exception e){
            e.printStackTrace();
        }
        ok= (Button)findViewById(R.id.ok);
        ok.setOnClickListener(view -> {
//            startActivity(new Intent(DialogActivity.this, MainActivity.class));
            finish();
        });
        print.setOnClickListener(v -> {
            if (extraMesage.equals("")){
                Toast.makeText(this, "No text to print", Toast.LENGTH_SHORT).show();
            }else {
                Intent intent = new Intent(this,MainPrinterActivity.class);
//                intent.putExtra("printMsg","Printing testing");
                intent.putExtra("printMsg",extraMesage);
//                startActivity(new Intent(this, MainPrinterActivity.class));
            startActivity(intent);
            }

        });
    }

    public void print_text(String bill_info){
        String message = bill_info;
        PrintService.pl().printText(message);

        String textStr="";


        byte[] btStr = null;
        btStr = textStr.getBytes();

        int msgSize=btStr.length;


        byte[] btcmd = new byte[4+msgSize];
        btcmd[0] = 0x1F;
        btcmd[1] = 0x11;
        btcmd[2] = (byte) (msgSize >>> 8);
        btcmd[3] = (byte) (msgSize & 0xff);

        System.arraycopy(btStr, 0, btcmd, 4, btStr.length);

        String sendString=new String(btcmd);


        PrintService.pl().printText(sendString);
    }
}
