package com.usfaa.walmartlogingpage.data.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.usfaa.walmartlogingpage.R
import com.usfaa.walmartlogingpage.data.models.Product

class ProductAdapter(private var products: List<Product>,
                     private val callBack: (product: Product) -> Unit):
    RecyclerView.Adapter<ProductAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_product, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.populateView(products[position])
        holder.cardViewProduct.setOnClickListener { callBack(products[position]) }
    }

    override fun getItemCount() = products.size

    class Holder(view: View): RecyclerView.ViewHolder(view) {
        val cardViewProduct = view.findViewById<CardView>(R.id.card_view_product)
        val img = view.findViewById<ImageView>(R.id.img_product)
        val lblPrice = view.findViewById<TextView>(R.id.lbl_price)
        val lblName = view.findViewById<TextView>(R.id.lbl_name)
        val lblColor = view.findViewById<TextView>(R.id.lbl_color)

        fun populateView(product: Product) {
            img.setImageResource(product.image)
            lblPrice.text = "Price : $${product.price}"
            lblName.text = product.title
            lblColor.text = "Color : ${product.color}"
        }
    }
}