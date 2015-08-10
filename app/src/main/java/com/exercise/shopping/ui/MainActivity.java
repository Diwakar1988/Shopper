package com.exercise.shopping.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.exercise.shopping.R;
import com.exercise.shopping.db.Product;
import com.exercise.shopping.db.ProductList;

class ProductAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;

    ProductAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return ProductList.getInstance().size();
    }

    @Override
    public Product getItem(int position) {
        return ProductList.getInstance().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;
        Product product = getItem(position);
        if (view == null) {
            view = inflater.inflate(R.layout.product_item, null, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.product_photo);
            viewHolder.name = (TextView) view.findViewById(R.id.product_name);
            viewHolder.tvPrice = (TextView) view.findViewById(R.id.product_desc);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.name.setText(product.getTitle());
        viewHolder.tvPrice.setText(("INR " + product.getPrice()));
        return view;
    }

    class ViewHolder {
        ImageView imageView;
        TextView name;
        TextView tvPrice;

    }
}

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list);
        productAdapter = new ProductAdapter(this);
        listView.setAdapter(productAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
