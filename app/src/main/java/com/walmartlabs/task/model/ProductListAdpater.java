package com.walmartlabs.task.model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.walmartlabs.task.R;
import com.walmartlabs.task.api.URL;

import java.util.ArrayList;

public class ProductListAdpater extends BaseAdapter {
    ArrayList<Products> mylist = new ArrayList<>();
    private Context mContext;

    public ProductListAdpater(ArrayList<Products> itemArray, Context mContext) {
        super();
        this.mContext = mContext;
        mylist = itemArray;
    }

    @Override
    public int getCount() {
        return mylist.size();
    }

    @Override
    public String getItem(int position) {
        return mylist.get(position).toString();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final ViewHolder view;
        LayoutInflater inflator = null;
        if (convertView == null) {
            view = new ViewHolder();
            try {
                inflator = ((Activity) mContext).getLayoutInflater();

                convertView = inflator.inflate(R.layout.product_list, null);
                view.productId = convertView.findViewById(R.id.productId);
                view.productName = convertView.findViewById(R.id.productName);
                view.shortDescription = convertView.findViewById(R.id.shortDescription);
                view.longDescription = convertView.findViewById(R.id.longDescription);
                view.price = convertView.findViewById(R.id.price);
                view.saveproducturl = convertView.findViewById(R.id.saveproducturl);
                view.productImage = convertView.findViewById(R.id.productImage);
                view.reviewRating = convertView.findViewById(R.id.reviewRating);
                view.reviewCount = convertView.findViewById(R.id.reviewCount);
                view.inStock = convertView.findViewById(R.id.inStock);
                convertView.setTag(view);

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            view = (ViewHolder) convertView.getTag();
        }
        try {
            view.productId.setTag(position);
            view.productName.setText(mylist.get(position).getProductName());
            view.shortDescription.setText(mylist.get(position).getShortDescription());
            view.longDescription.setText(mylist.get(position).getLongDescription());
            view.price.setText(mylist.get(position).getPrice());
            view.saveproducturl.setText(mylist.get(position).getProductImage());
            view.reviewRating.setText("Rating : "+mylist.get(position).getReviewRatings() + ".0 " + "* ");
            view.reviewCount.setText(mylist.get(position).getReviewCounts());

            if (String.valueOf(mylist.get(position).getInStock()).equals("true")) {
                view.inStock.setText("In Stock");
            }
            if (String.valueOf(mylist.get(position).getInStock()).equals("false")) {
                view.inStock.setText("Out Of Stock ");
                //view.inStock.setTextColor(Color.parseColor("#FF0000"));
            }
            try {
                Glide.with(mContext)
                        .load(URL.URL_ROOT+mylist.get(position).getProductImage())
                        .centerCrop()
                        .error(R.drawable.default_background)
                        .animate(android.R.anim.fade_in)
                        .into(view.productImage);
            }catch (NullPointerException e){
                e.printStackTrace();
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return convertView;
    }

    public class ViewHolder {
        private TextView productId, productName, shortDescription, longDescription, price;
        private TextView reviewRating, reviewCount, inStock, saveproducturl;
        private ImageView productImage;

    }
}