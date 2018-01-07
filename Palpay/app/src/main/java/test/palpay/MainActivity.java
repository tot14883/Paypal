package test.palpay;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity
{
    TextView m_response;
    PayPalConfiguration m_configuration;
    String m_paypalClientId = "AcK1v_UJFjSusJgC0PqqwmDAHltZgZx34HsWp7O_3sch19tTr27yy5C-lC075s7B47PVFLI6vfxRgpOf";
    Intent m_service;
    int m_paypalRequestCode = 999;
    static Cart m_cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        m_response = (TextView) findViewById(R.id.response);

        m_configuration = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(m_paypalClientId);

        m_service = new Intent(this, PayPalService.class);
        m_service.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,m_configuration);
        startService(m_service);

        m_cart = new Cart();

        Product product[]= {
          new Product("product 1",15.20),
          new Product("product 2 ",19.30),
                new Product("product 3",8.13),
                new Product("product 4",55.42),
                new Product("product 5",23.99),
                new Product("product 6",15.42),
                new Product("product 7",99.33)
        };
        LinearLayout list = (LinearLayout) findViewById(R.id.list);
        for(int i = 0;i < product.length;i++){
            Button button = new Button(this);
            button.setText(product[i].getName()+ "---"+product[i].getValue()+"$");
            button.setTag(product[i]);
            button.setTextSize(40);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
            ,200, Gravity.CENTER);
            layoutParams.setMargins(20,50,20,50);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 Button button = (Button) view;
                 Product product = (Product) button.getTag();

                 m_cart.addToCart(product);
                 m_response.setText("Total cart value ="+String.format("%.2f",m_cart.getValue())+" $");
                }
            });
            list.addView(button);
        }
    }
    void pay(View view){
        PayPalPayment cart = new PayPalPayment(new BigDecimal(m_cart.getValue()),"USD","Cary",
                PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this,PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,m_configuration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,cart);
        startActivityForResult(intent,m_paypalRequestCode);
    }
    void reset(View view){
        m_response.setText("Total cart value = 0 $");
        m_cart.empty();
    }
    void viewCart(View view){
        Intent intent = new Intent(this,ViewCart.class);
        m_cart = m_cart;
        startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == m_paypalRequestCode){
            if(resultCode == Activity.RESULT_OK){
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if(confirmation != null){
                    String state = confirmation.getProofOfPayment().getState();
                    if(state.equals("approved"))
                        m_response.setText("payment approved");
                    else
                        m_response.setText("error in the payment");
                }
            }
            else
                m_response.setText("confirmation is null");
        }
    }
}
