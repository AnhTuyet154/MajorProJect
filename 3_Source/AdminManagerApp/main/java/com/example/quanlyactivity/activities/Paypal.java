package com.example.quanlyactivity.activities;

import android.app.Application;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.paypal.checkout.PayPalCheckout;
import com.paypal.checkout.config.CheckoutConfig;
import com.paypal.checkout.config.Environment;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.UserAction;
import com.paypal.checkout.paymentbutton.PaymentButtonContainer;

public class Paypal extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PayPalCheckout.setConfig(new CheckoutConfig(
                this,
                "AQu8ltWmSSDKxDDXMpXt8d4DNNhXbigBT8a6yRL_79XQ5DFFFEhToxFE8wL5xvgZrg2bgg130-GHe1fx",
                Environment.SANDBOX,
                CurrencyCode.USD,
                UserAction.PAY_NOW,
                "com.example.quanlyactivity://paypalpay"
        ));

    }
}
