package com.usfaa.walmartlogingpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.usfaa.walmartlogingpage.data.adapters.ProductAdapter
import com.usfaa.walmartlogingpage.data.models.Product

class ProductPage : AppCompatActivity() {
    private val description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Aenean vel elit scelerisque mauris pellentesque pulvinar pellentesque. Facilisis volutpat est velit egestas dui id ornare. Quam id leo in vitae turpis massa sed elementum. Odio euismod lacinia at quis. Diam quis enim lobortis scelerisque fermentum dui faucibus in ornare. Phasellus vestibulum lorem sed risus. Amet nulla facilisi morbi tempus iaculis urna id volutpat. Phasellus egestas tellus rutrum tellus pellentesque eu tincidunt tortor. Enim nec dui nunc mattis enim ut tellus elementum. Feugiat in fermentum posuere urna nec. Et tortor consequat id porta nibh. Velit dignissim sodales ut eu sem integer vitae. Rhoncus urna neque viverra justo nec ultrices dui. Lacinia quis vel eros donec. Enim facilisis gravida neque convallis a cras semper. Egestas erat imperdiet sed euismod nisi porta lorem mollis aliquam. Sit amet consectetur adipiscing elit pellentesque habitant. In metus vulputate eu scelerisque felis imperdiet proin fermentum leo. Gravida quis blandit turpis cursus in hac habitasse platea dictumst."
    private val electronicProducts = mutableListOf(
        Product("Samsung Galaxy S22 Ultra", 1199.99, "Blue", R.drawable.s22_ultra, "s22_ultra", description),
        Product("Apple iPhone 14 Pro Max", 1399.99, "Green", R.drawable.iphone_14, "iPhone_14", description),
        Product("Samsung Odyssey Ark", 2999.99, "Purple", R.drawable.odyssey_ark, "odyssey_ark", description)
    )
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_page)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val adapter = ProductAdapter(electronicProducts) { product->
            goToProductDetails(product)
        }
        recyclerView.adapter = adapter
    }

    private fun goToProductDetails(product: Product) {
        val intent = Intent(this, ProductDetailsPage::class.java)
        intent.putExtra(PRODUCT_KEY, product)
        startActivity(intent)

    }

    companion object {
        const val PRODUCT_KEY = "product"
    }
}