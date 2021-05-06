package com.example.shoppingliststartcodekotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingliststartcodekotlin.R
import com.example.shoppingliststartcodekotlin.data.Product
import com.example.shoppingliststartcodekotlin.data.Repository


class ProductAdapter(var products: MutableList<Product>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_layout, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.productTitle.text = products[i].name
        viewHolder.itemQty.text = products[i].qty.toString()


    }

    override fun getItemCount(): Int = products.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var productTitle: TextView = itemView.findViewById(R.id.textView_products)
        var itemQty: TextView = itemView.findViewById(R.id.qty_product_textview)
        var itemDelete: ImageView = itemView.findViewById(R.id.imageView_product_delete)

        init {
            itemDelete.setOnClickListener { v: View ->
                val position = adapterPosition
                Repository.deleteProduct(position)
                notifyItemRemoved(position) //this line notify the adapter

            }
        }

    }

}
