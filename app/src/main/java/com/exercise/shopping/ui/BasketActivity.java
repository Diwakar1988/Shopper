package com.exercise.shopping.ui;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exercise.shopping.R;
import com.exercise.shopping.db.Basket;
import com.exercise.shopping.db.DataController;
import com.exercise.shopping.db.Order;
import com.exercise.shopping.db.Product;

import java.util.HashMap;


public class BasketActivity extends AppCompatActivity {
    LinearLayout card_container;
    HashMap<Button ,Card> cardMap;

    private void setupList(){
        cardMap = new HashMap<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busket_layout);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Basket");
        setupList();

        card_container = (LinearLayout) findViewById(R.id.card_container);
        LayoutInflater  layoutInflater = LayoutInflater.from(this);

        final Basket basket = Basket.getInstance();
        for(int i=0;i<basket.size();i++){

            final int position = i;
            View cardView =  layoutInflater.inflate(R.layout.busket_item, null);
            Button removeButton = (Button) cardView.findViewById(R.id.remove_item);
            Order order = basket.getOrder(position);
            Product product = order.getProduct();

            cardMap.put(removeButton,new Card(product,cardView));
            ((TextView)cardView.findViewById(R.id.product_name)).setText(product.getTitle());

//            StringBuilder sb = new StringBuilder(order.getQuantity());
//            sb.append(" X ").append(product.getPrice()).append(" = ").append((product.getPrice()*order.getQuantity()));

                    ((TextView) cardView.findViewById(R.id.product_price)).setText("INR "+product.getPrice());
            ((TextView)cardView.findViewById(R.id.product_qua)).setText("QTY: "+order.getQuantity());
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Card card = cardMap.get(v);
                    Toast.makeText(getBaseContext(), "Delete ", Toast.LENGTH_SHORT).show();
                    Product product = card.getProduct();
                    View parent = card.getParent();

                    cardMap.remove(v);
                    card_container.removeView(parent);
                    //Delete from basket
                    Order o = basket.deleteOrder(position);
                    //update DB also
                    DataController.getInstance().deleteOrder(o);

                }
            });
            card_container.addView(cardView);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                                    // Navigate up to the closest parent
                            .startActivities();
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private class Card{
        Product product;
        View parent;

        public Product getProduct() {
            return product;
        }

        public View getParent() {
            return parent;
        }

        public Card(Product product, View parent) {

            this.product = product;
            this.parent = parent;
        }
    }

}
