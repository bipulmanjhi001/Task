package com.walmartlabs.task.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.walmartlabs.task.R;
import com.walmartlabs.task.api.URL;
import com.walmartlabs.task.model.ConnectionDetector;
import com.walmartlabs.task.model.ProductListAdpater;
import com.walmartlabs.task.model.Products;
import com.walmartlabs.task.model.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Screen1 extends Activity {

    ProgressBar view_product_progress;
    ListView view_product_list;
    ArrayList<Products> products;
    ProductListAdpater productListAdpater;
    int pageNumber=1;
    int pageSize=30;
    int totalProducts;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    TextView walmart_text;
    Typeface custom_font;
    String productName, productId, shortDescription, longDescription, price, productImage, shortDescriptions;
    String reviewRatings, reviewCounts;
    String inStock;
    Boolean inStocks = true;
    boolean loadingMore = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen1);
        walmart_text = findViewById(R.id.walmart_text);
        custom_font = Typeface.createFromAsset(getApplicationContext().getAssets(),  "Pacifico.ttf");
        walmart_text.setTypeface(custom_font);
        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();

        if (isInternetPresent) {
            view_product_list = findViewById(R.id.view_product_list);
            view_product_list.setDivider(null);
            products = new ArrayList<Products>();
            Authenticate();
            view_product_list.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    Log.d("Valuess", String.valueOf(productListAdpater.getCount()));
                    if (scrollState == SCROLL_STATE_IDLE) {
                        if (view_product_list.getLastVisiblePosition() >= productListAdpater.getCount() - 1 && (loadingMore)) {
                            Authenticate();
                        }
                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });
            setListViewFooter();
            view_product_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    productName=((TextView) view.findViewById(R.id.productName)).getText().toString();
                    productImage=((TextView) view.findViewById(R.id.saveproducturl)).getText().toString();
                    shortDescription=((TextView) view.findViewById(R.id.shortDescription)).getText().toString();
                    longDescription=((TextView) view.findViewById(R.id.longDescription)).getText().toString();
                    price=((TextView) view.findViewById(R.id.price)).getText().toString();
                    reviewRatings=((TextView) view.findViewById(R.id.reviewRating)).getText().toString();
                    reviewCounts=((TextView) view.findViewById(R.id.reviewCount)).getText().toString();
                    inStock=((TextView) view.findViewById(R.id.inStock)).getText().toString();

                    Intent intent=new Intent(Screen1.this,DetailsActivity.class);
                    intent.putExtra("productName",productName);
                    intent.putExtra("productImage",productImage);
                    intent.putExtra("shortDescription",shortDescription);
                    intent.putExtra("longDescription",longDescription);
                    intent.putExtra("price",price);
                    intent.putExtra("reviewRatings",reviewRatings);
                    intent.putExtra("reviewCounts",reviewCounts);
                    intent.putExtra("inStock",inStock);
                    startActivity(intent);
                }
            });

        }else {
            setContentView(R.layout.activity_offline);
            walmart_text = findViewById(R.id.walmart_text);
            custom_font = Typeface.createFromAsset(getApplicationContext().getAssets(),  "Pacifico.ttf");
            walmart_text.setTypeface(custom_font);
            LinearLayout offline_more = findViewById(R.id.offline_more);
            offline_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Settings.ACTION_SETTINGS));
                }
            });
        }
    }

    public void Authenticate() {
        loadingMore = true;
        if (pageNumber == 1) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL.URL_PRODUCTS + "/" + pageNumber + "/" + pageSize,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                JSONArray userJson = obj.getJSONArray("products");
                                for (int i = 0; i < userJson.length(); i++) {
                                    Object checkobj, checkobj1, checkobj2;
                                    JSONObject itemslist = userJson.getJSONObject(i);
                                    if (itemslist.has("productId") && !itemslist.isNull("productId")) {
                                        productId = itemslist.getString("productId");
                                    }
                                    if (itemslist.has("productName") && !itemslist.isNull("productName")) {
                                        productName = itemslist.getString("productName");
                                    }
                                    if (itemslist.has("shortDescription") && !itemslist.isNull("shortDescription")) {
                                        shortDescriptions = itemslist.getString("shortDescription");
                                    }
                                    if (itemslist.has("longDescription") && !itemslist.isNull("longDescription")) {
                                        longDescription = itemslist.getString("longDescription");
                                    }
                                    if (itemslist.has("productImage") && !itemslist.isNull("productImage")) {
                                        productImage = itemslist.getString("productImage");
                                    }
                                    if (itemslist.has("price") && !itemslist.isNull("price")) {
                                        price = itemslist.getString("price");
                                    }
                                    checkobj = itemslist.getInt("reviewRating");
                                    if (checkobj instanceof Integer) {
                                        Integer reviewRating = itemslist.getInt("reviewRating");
                                        reviewRatings = String.valueOf(reviewRating);
                                    } else {
                                        Double reviewRating = itemslist.getDouble("reviewRating");
                                        reviewRatings = String.valueOf(reviewRating);
                                    }
                                    checkobj1 = itemslist.getInt("reviewRating");
                                    if (checkobj1 instanceof Integer) {
                                        Integer reviewCount = itemslist.getInt("reviewCount");
                                        reviewCounts = String.valueOf(reviewCount);
                                    } else {
                                        Double reviewCount = itemslist.getDouble("reviewCount");
                                        reviewCounts = String.valueOf(reviewCount);
                                    }
                                    checkobj2 = itemslist.getBoolean("inStock") == true;
                                    if (checkobj2 instanceof Boolean) {
                                        inStocks = itemslist.getBoolean("inStock");
                                    } else {
                                        inStocks = false;
                                    }
                                    Products proList = new Products(productId, productName, shortDescriptions, longDescription, price, productImage, reviewRatings, reviewCounts, inStocks);
                                    products.add(proList);
                                }
                                totalProducts = obj.getInt("totalProducts");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            productListAdpater = new ProductListAdpater(products, Screen1.this);
                            view_product_list.setAdapter(productListAdpater);
                            productListAdpater.notifyDataSetChanged();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Fatching..", Toast.LENGTH_SHORT).show();
                        }
                    }) {
            };
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
            pageNumber++;
            if (pageNumber == 9) {
                loadingMore = false;
                view_product_progress.setVisibility(View.GONE);
            }
        } else {
            GetMore();
        }
    }

    public void GetMore() {
        if (pageNumber >= 2) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL.URL_PRODUCTS + "/" + pageNumber + "/" + pageSize,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                JSONArray userJson = obj.getJSONArray("products");
                                for (int i = 0; i < userJson.length(); i++) {
                                    Object checkobj, checkobj1, checkobj2;
                                    JSONObject itemslist = userJson.getJSONObject(i);
                                    if (itemslist.has("productId") && !itemslist.isNull("productId")) {
                                        productId = itemslist.getString("productId");
                                    }
                                    if (itemslist.has("productName") && !itemslist.isNull("productName")) {
                                        productName = itemslist.getString("productName");
                                    }
                                    if (itemslist.has("shortDescription") && !itemslist.isNull("shortDescription")) {
                                        shortDescriptions = itemslist.getString("shortDescription");
                                    }
                                    if (itemslist.has("longDescription") && !itemslist.isNull("longDescription")) {
                                        longDescription = itemslist.getString("longDescription");
                                    }
                                    if (itemslist.has("productImage") && !itemslist.isNull("productImage")) {
                                        productImage = itemslist.getString("productImage");
                                    }
                                    if (itemslist.has("price") && !itemslist.isNull("price")) {
                                        price = itemslist.getString("price");
                                    }
                                    checkobj = itemslist.getInt("reviewRating");
                                    if (checkobj instanceof Integer) {
                                        Integer reviewRating = itemslist.getInt("reviewRating");
                                        reviewRatings = String.valueOf(reviewRating);
                                    } else {
                                        Double reviewRating = itemslist.getDouble("reviewRating");
                                        reviewRatings = String.valueOf(reviewRating);
                                    }
                                    checkobj1 = itemslist.getInt("reviewRating");
                                    if (checkobj1 instanceof Integer) {
                                        Integer reviewCount = itemslist.getInt("reviewCount");
                                        reviewCounts = String.valueOf(reviewCount);
                                    } else {
                                        Double reviewCount = itemslist.getDouble("reviewCount");
                                        reviewCounts = String.valueOf(reviewCount);
                                    }
                                    checkobj2 = itemslist.getBoolean("inStock") == true;
                                    if (checkobj2 instanceof Boolean) {
                                        inStocks = itemslist.getBoolean("inStock");
                                    } else {
                                        inStocks = false;
                                    }
                                    Products proList = new Products(productId, productName, shortDescriptions, longDescription, price, productImage, reviewRatings, reviewCounts, inStocks);
                                    products.add(proList);
                                }
                                totalProducts = obj.getInt("totalProducts");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            productListAdpater.notifyDataSetChanged();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Fatching..", Toast.LENGTH_SHORT).show();
                        }
                    }) {
            };
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
            pageNumber++;
            if (pageNumber == 9) {
                loadingMore = false;
                view_product_progress.setVisibility(View.GONE);
            }
        }
    }
    private void setListViewFooter(){
        View view = LayoutInflater.from(this).inflate(R.layout.footer_listview_progressbar, null);
        view_product_progress = view.findViewById(R.id.progressBar);
        view_product_list.addFooterView(view_product_progress);
    }
}
