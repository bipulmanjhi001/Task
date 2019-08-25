package com.walmartlabs.task.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.walmartlabs.task.R
import com.walmartlabs.task.api.URLs
import com.walmartlabs.task.model.ConnectionDetector
import com.walmartlabs.task.model.ProductListAdpater
import com.walmartlabs.task.model.Products
import com.walmartlabs.task.model.VolleySingleton
import org.json.JSONException
import org.json.JSONObject
import java.util.*

/**
 * Created by Bipul on 25-08-2019.
 */
class Screen1 : Activity() {

    internal lateinit var view_product_progress: ProgressBar
    internal lateinit var view_product_list: ListView
    internal lateinit var products: ArrayList<Products>
    internal lateinit var productListAdpater: ProductListAdpater
    internal var pageNumber = 1
    internal var pageSize = 30
    internal var totalProducts: Int = 0
    internal var isInternetPresent: Boolean? = false
    internal lateinit var cd: ConnectionDetector
    internal lateinit var walmart_text: TextView
    internal lateinit var custom_font: Typeface
    internal lateinit var productName: String
    internal lateinit var productId: String
    internal lateinit var shortDescription: String
    internal lateinit var longDescription: String
    internal lateinit var price: String
    internal lateinit var productImage: String
    internal lateinit var shortDescriptions: String
    internal lateinit var reviewRatings: String
    internal lateinit var reviewCounts: String
    internal lateinit var inStock: String
    internal var inStocks: Boolean? = true
    internal var loadingMore = true

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen1)
        walmart_text = findViewById(R.id.walmart_text)
        custom_font = Typeface.createFromAsset(applicationContext.assets, "Pacifico.ttf")
        walmart_text.typeface = custom_font
        cd = ConnectionDetector(applicationContext)
        isInternetPresent = cd.isConnectingToInternet

        if (isInternetPresent!!) {
            view_product_list = findViewById(R.id.view_product_list)
            view_product_list.divider = null
            products = ArrayList()
            Authenticate()
            view_product_list.setOnScrollListener(object : AbsListView.OnScrollListener {
                override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {
                    Log.d("Valuess", productListAdpater.count.toString())
                    if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                        if (view_product_list.lastVisiblePosition >= productListAdpater.count - 1 && loadingMore) {
                            Authenticate()
                        }
                    }
                }

                override fun onScroll(view: AbsListView, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {

                }
            })
            setListViewFooter()
            view_product_list.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                productName = (view.findViewById<View>(R.id.productName) as TextView).text.toString()
                productImage = (view.findViewById<View>(R.id.saveproducturl) as TextView).text.toString()
                shortDescription = (view.findViewById<View>(R.id.shortDescription) as TextView).text.toString()
                longDescription = (view.findViewById<View>(R.id.longDescription) as TextView).text.toString()
                price = (view.findViewById<View>(R.id.price) as TextView).text.toString()
                reviewRatings = (view.findViewById<View>(R.id.reviewRating) as TextView).text.toString()
                reviewCounts = (view.findViewById<View>(R.id.reviewCount) as TextView).text.toString()
                inStock = (view.findViewById<View>(R.id.inStock) as TextView).text.toString()

                val intent = Intent(this@Screen1, DetailsActivity::class.java)
                intent.putExtra("productName", productName)
                intent.putExtra("productImage", productImage)
                intent.putExtra("shortDescription", shortDescription)
                intent.putExtra("longDescription", longDescription)
                intent.putExtra("price", price)
                intent.putExtra("reviewRatings", reviewRatings)
                intent.putExtra("reviewCounts", reviewCounts)
                intent.putExtra("inStock", inStock)
                startActivity(intent)
            }

        } else {
            setContentView(R.layout.activity_offline)
            walmart_text = findViewById(R.id.walmart_text)
            custom_font = Typeface.createFromAsset(applicationContext.assets, "Pacifico.ttf")
            walmart_text.typeface = custom_font
            val offline_more = findViewById<LinearLayout>(R.id.offline_more)
            offline_more.setOnClickListener { startActivity(Intent(Settings.ACTION_SETTINGS)) }
        }
    }

    fun Authenticate() {
        loadingMore = true
        if (pageNumber == 1) {
            val stringRequest = object : StringRequest(Request.Method.GET, URLs.URL_PRODUCTS + "/" + pageNumber + "/" + pageSize,
                    Response.Listener { response ->
                        try {
                            val obj = JSONObject(response)
                            val userJson = obj.getJSONArray("products")
                            for (i in 0 until userJson.length()) {
                                val checkobj: Any
                                val checkobj1: Any
                                val checkobj2: Any
                                val itemslist = userJson.getJSONObject(i)
                                if (itemslist.has("productId") && !itemslist.isNull("productId")) {
                                    productId = itemslist.getString("productId")
                                }
                                if (itemslist.has("productName") && !itemslist.isNull("productName")) {
                                    productName = itemslist.getString("productName")
                                }
                                if (itemslist.has("shortDescription") && !itemslist.isNull("shortDescription")) {
                                    shortDescriptions = itemslist.getString("shortDescription")
                                }
                                if (itemslist.has("longDescription") && !itemslist.isNull("longDescription")) {
                                    longDescription = itemslist.getString("longDescription")
                                }
                                if (itemslist.has("productImage") && !itemslist.isNull("productImage")) {
                                    productImage = itemslist.getString("productImage")
                                }
                                if (itemslist.has("price") && !itemslist.isNull("price")) {
                                    price = itemslist.getString("price")
                                }
                                checkobj = itemslist.getInt("reviewRating")
                                if (checkobj is Int) {
                                    val reviewRating = itemslist.getInt("reviewRating")
                                    reviewRatings = reviewRating.toString()
                                } else {
                                    val reviewRating = itemslist.getDouble("reviewRating")
                                    reviewRatings = reviewRating.toString()
                                }
                                checkobj1 = itemslist.getInt("reviewRating")
                                if (checkobj1 is Int) {
                                    val reviewCount = itemslist.getInt("reviewCount")
                                    reviewCounts = reviewCount.toString()
                                } else {
                                    val reviewCount = itemslist.getDouble("reviewCount")
                                    reviewCounts = reviewCount.toString()
                                }
                                checkobj2 = itemslist.getBoolean("inStock") == true
                                if (checkobj2 is Boolean) {
                                    inStocks = itemslist.getBoolean("inStock")
                                } else {
                                    inStocks = false
                                }
                                val proList = Products(productId, productName, shortDescriptions, longDescription, price, productImage, reviewRatings, reviewCounts, inStocks)
                                products.add(proList)
                            }
                            totalProducts = obj.getInt("totalProducts")
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }

                        productListAdpater = ProductListAdpater(products, this@Screen1)
                        view_product_list.adapter = productListAdpater
                        productListAdpater.notifyDataSetChanged()
                    },
                    Response.ErrorListener { Toast.makeText(applicationContext, "Fatching..", Toast.LENGTH_SHORT).show() }) {

            }
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest)
            pageNumber++
            if (pageNumber == 9) {
                loadingMore = false
                view_product_progress.visibility = View.GONE
            }
        } else {
            GetMore()
        }
    }

    fun GetMore() {
        if (pageNumber >= 2) {
            val stringRequest = object : StringRequest(Request.Method.GET, URLs.URL_PRODUCTS + "/" + pageNumber + "/" + pageSize,
                    Response.Listener { response ->
                        try {
                            val obj = JSONObject(response)
                            val userJson = obj.getJSONArray("products")
                            for (i in 0 until userJson.length()) {
                                val checkobj: Any
                                val checkobj1: Any
                                val checkobj2: Any
                                val itemslist = userJson.getJSONObject(i)
                                if (itemslist.has("productId") && !itemslist.isNull("productId")) {
                                    productId = itemslist.getString("productId")
                                }
                                if (itemslist.has("productName") && !itemslist.isNull("productName")) {
                                    productName = itemslist.getString("productName")
                                }
                                if (itemslist.has("shortDescription") && !itemslist.isNull("shortDescription")) {
                                    shortDescriptions = itemslist.getString("shortDescription")
                                }
                                if (itemslist.has("longDescription") && !itemslist.isNull("longDescription")) {
                                    longDescription = itemslist.getString("longDescription")
                                }
                                if (itemslist.has("productImage") && !itemslist.isNull("productImage")) {
                                    productImage = itemslist.getString("productImage")
                                }
                                if (itemslist.has("price") && !itemslist.isNull("price")) {
                                    price = itemslist.getString("price")
                                }
                                checkobj = itemslist.getInt("reviewRating")
                                if (checkobj is Int) {
                                    val reviewRating = itemslist.getInt("reviewRating")
                                    reviewRatings = reviewRating.toString()
                                } else {
                                    val reviewRating = itemslist.getDouble("reviewRating")
                                    reviewRatings = reviewRating.toString()
                                }
                                checkobj1 = itemslist.getInt("reviewRating")
                                if (checkobj1 is Int) {
                                    val reviewCount = itemslist.getInt("reviewCount")
                                    reviewCounts = reviewCount.toString()
                                } else {
                                    val reviewCount = itemslist.getDouble("reviewCount")
                                    reviewCounts = reviewCount.toString()
                                }
                                checkobj2 = itemslist.getBoolean("inStock") == true
                                if (checkobj2 is Boolean) {
                                    inStocks = itemslist.getBoolean("inStock")
                                } else {
                                    inStocks = false
                                }
                                val proList = Products(productId, productName, shortDescriptions, longDescription, price, productImage, reviewRatings, reviewCounts, inStocks)
                                products.add(proList)
                            }
                            totalProducts = obj.getInt("totalProducts")
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }

                        productListAdpater.notifyDataSetChanged()
                    },
                    Response.ErrorListener { Toast.makeText(applicationContext, "Fatching..", Toast.LENGTH_SHORT).show() }) {

            }
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest)
            pageNumber++
            if (pageNumber == 9) {
                loadingMore = false
                view_product_progress.visibility = View.GONE
            }
        }
    }

    private fun setListViewFooter() {
        val view = LayoutInflater.from(this).inflate(R.layout.footer_listview_progressbar, null)
        view_product_progress = view.findViewById(R.id.progressBar)
        view_product_list.addFooterView(view_product_progress)
    }
}
