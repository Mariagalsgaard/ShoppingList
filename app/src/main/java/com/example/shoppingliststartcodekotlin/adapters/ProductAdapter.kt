package com.example.shoppingliststartcodekotlin.adapters

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingliststartcodekotlin.R
import com.example.shoppingliststartcodekotlin.data.Product
import com.example.shoppingliststartcodekotlin.data.Repository
import com.google.android.material.snackbar.Snackbar
import io.grpc.InternalChannelz.id


class ProductAdapter(var products: MutableList<Product>, val c: Context) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

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

            itemEdit.setOnClickListener{editProductPopup(it)}
        }


        private fun editProductPopup(v:View) {
            val position = products[adapterPosition]
            val v = LayoutInflater.from(c).inflate(R.layout.editproductpopup_layout, null)
            val title = v.findViewById<EditText>(R.id.editText_editTitle)
            val qty = v.findViewById<EditText>(R.id.editText_editNumber)
                AlertDialog.Builder(c)
                        .setView(v)
                        .setPositiveButton("Update"){
                            dialog,_->
                            position.name = title.text.toString()
                            position.qty = qty.text.toString().toInt()
                            notifyDataSetChanged()
                            Repository.updateProduct(position, title.text.toString(), qty.text.toString().toInt())
                            dialog.dismiss()
                            Toast.makeText(c,"Your product has been updated", Toast.LENGTH_SHORT).show()
                        }
                        .setNegativeButton("Cancel"){
                            dialog,_->
                            dialog.dismiss()
                        }
                        .create()
                        .show()
            true
        }
    }
}
