package com.example.projekmobileyohana

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CatalogAdapter(
    private val productList: List<Product>
) : RecyclerView.Adapter<CatalogAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val badge: TextView = itemView.findViewById(R.id.tvBadge)
        val image: ImageView = itemView.findViewById(R.id.imgProduct)
        val name: TextView = itemView.findViewById(R.id.tvProductName)
        val rating: TextView = itemView.findViewById(R.id.tvRating)
        val price: TextView = itemView.findViewById(R.id.tvPrice)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_catalog, parent, false)

        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {
        val product = productList[position]

        holder.badge.text = product.badge
        holder.image.setImageResource(product.image)
        holder.name.text = product.name
        holder.rating.text = product.rating
        holder.price.text = product.price
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}