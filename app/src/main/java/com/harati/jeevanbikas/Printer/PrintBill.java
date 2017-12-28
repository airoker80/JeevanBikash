package com.harati.jeevanbikas.Printer;

import android.content.Context;

import com.smartdevice.sdk.printer.PrintService;

/**
 * Created by Sameer on 12/28/2017.
 */

public class PrintBill {
    Context context;
    String bill_info;

    public PrintBill(Context context, String bill_info) {
        this.context = context;
        this.bill_info = bill_info;
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
