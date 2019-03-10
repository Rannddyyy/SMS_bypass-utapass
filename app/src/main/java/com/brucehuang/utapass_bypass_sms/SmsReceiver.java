package com.brucehuang.utapass_bypass_sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.Objects;

public class SmsReceiver extends BroadcastReceiver {
    private static SmsListener mListener;

    public static void bindListener(SmsListener paramSmsListener) {
        mListener = paramSmsListener;
    }

    public void onReceive(Context paramContext, Intent paramIntent) {
        if (Objects.equals(paramIntent.getAction(), "android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = paramIntent.getExtras();
            if (bundle != null) {
                try {
                    Object[] arrayOfObject = (Object[]) bundle.get("pdus");
                    SmsMessage[] arrayOfSmsMessage = new SmsMessage[arrayOfObject.length];
                    String message = "";
                    int i = 0;
                    while (i < arrayOfSmsMessage.length) {
                        String str = bundle.getString("format");
                        arrayOfSmsMessage[i] = SmsMessage.createFromPdu((byte[]) (byte[]) arrayOfObject[i], str);
                        message = message + arrayOfSmsMessage[i].getMessageBody();
                        str = arrayOfSmsMessage[i].getDisplayOriginatingAddress();
                        Log.e("---------SmsReceiver", str + paramContext);
                        Log.e("---------message", message);
                        mListener.messageReceived(message);
                        i += 1;
                    }
                } catch (Exception e) {
                    Log.e("---------msReceiver", "Exception smsReceiver " + e);
                }
            }
        }
    }
}
