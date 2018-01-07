package test.palpay;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.Iterator;
import java.util.Set;

public class ViewCart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        Cart cart = MainActivity.m_cart;
        LinearLayout cartLayout = (LinearLayout) findViewById(R.id.cart);

        Log.e("siz",""+cart.getSize());
        Set<Product> products = cart.getProduct();

        Iterator iterable = products.iterator();
        while(iterable.hasNext()){
            Product product = (Product) iterable.next();

            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            TextView name = new TextView(this);
            TextView quantity = new TextView(this);

            name.setText(product.getName());
            quantity.setText(Integer.toString(cart.getQuantity(product)));

            name.setTextSize(40);
            quantity.setTextSize(40);

            linearLayout.addView(name);
            linearLayout.addView(quantity);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    200, Gravity.CENTER);
            layoutParams.setMargins(20,50,20,50);
            linearLayout.setLayoutParams(layoutParams);

            name.setLayoutParams(new TableLayout.LayoutParams(0, ActionBar.LayoutParams.WRAP_CONTENT,1));

            quantity.setLayoutParams(new TableLayout.LayoutParams(0,
                    ActionBar.LayoutParams.WRAP_CONTENT,1));
            name.setGravity(Gravity.CENTER);
            quantity.setGravity(Gravity.CENTER);
            cartLayout.addView(linearLayout);
        }
    }
}
