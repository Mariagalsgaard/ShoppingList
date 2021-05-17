package com.example.shoppingliststartcodekotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.shoppingliststartcodekotlin.data.Product
import com.example.shoppingliststartcodekotlin.data.Repository
import kotlinx.android.synthetic.main.addproductdialogue_layout.editText_Number1
import kotlinx.android.synthetic.main.addproductdialogue_layout.editText_title1
import kotlinx.android.synthetic.main.addproductdialogue_layout.view.*

class AddProductDialogue : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.addproductdialogue_layout, container)
        dialog?.setTitle(R.string.dialog_remove_add_title)

        view.button_add.setOnClickListener {
           addNewProduct()
        }


        view.button_cancel.setOnClickListener {
            dismiss()
        }

        return view
    }

    private fun addNewProduct() {
        val newProduct = Product(
            name = editText_title1.text.toString(),
            qty = editText_Number1.text.toString().toInt()
        )
        Repository.addProduct(newProduct)
    }
}