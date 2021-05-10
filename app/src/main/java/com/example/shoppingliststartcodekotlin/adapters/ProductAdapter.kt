package com.example.shoppingliststartcodekotlin.adapters

import android.content.Context
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingliststartcodekotlin.AddProductDialogue
import com.example.shoppingliststartcodekotlin.MainActivity
import com.example.shoppingliststartcodekotlin.PopupDialogue
import com.example.shoppingliststartcodekotlin.R
import com.example.shoppingliststartcodekotlin.data.Product
import com.example.shoppingliststartcodekotlin.data.Repository
import com.google.android.material.snackbar.Snackbar


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
        var itemEdit: ImageView = itemView.findViewById(R.id.imageView_product_edit)


        init {
            itemDelete.setOnClickListener { v: View ->
                val parent = v.rootView.findViewById<View>(R.id.main_layout)
                val position = adapterPosition
                var savedProduct = Repository.products[position]
                Repository.deleteProduct(position)
                notifyItemRemoved(position) //this line notify the adapter

                //Making a snackbar for the user to undo the item delete
                val snackbar = Snackbar
                        .make(parent, "Mistake? Hit undo to get your product back", Snackbar.LENGTH_LONG)
                        .setAction("UNDO") {
                            //This code will ONLY be executed in case that
                            //the user has hit the UNDO button

                            Repository.addProduct(savedProduct)
                            val snackbar = Snackbar.make(parent, "Product has been restored", Snackbar.LENGTH_SHORT)

                            snackbar.show()
                        }

                snackbar.show()

            }

            /*itemEdit.setOnClickListener{v: View ->
                val dialog = PopupDialogue(Repository::deleteAllProducts, true)
                dialog.show(supportFragmentManager, "yesnodialogfragment")
            }

             */

        }

    }

}
