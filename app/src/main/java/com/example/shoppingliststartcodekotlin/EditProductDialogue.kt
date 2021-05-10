package com.example.shoppingliststartcodekotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.shoppingliststartcodekotlin.data.Product
import com.example.shoppingliststartcodekotlin.data.Repository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.addproductdialogue_layout.*
import kotlinx.android.synthetic.main.addproductdialogue_layout.editText_Number1
import kotlinx.android.synthetic.main.addproductdialogue_layout.editText_title1
import kotlinx.android.synthetic.main.addproductdialogue_layout.view.*
import kotlinx.android.synthetic.main.editproductdialogue_layout.*
import kotlinx.android.synthetic.main.editproductdialogue_layout.view.*

class EditProductDialogue(val addFunc: (String, Int) -> (Unit)) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.editproductdialogue_layout, container)
        dialog?.setTitle(R.string.dialog_remove_add_title)


        view.button_addChanges.setOnClickListener {
            val title = editText_title1
            val qty = Integer.valueOf(editText_Number1.text.toString())
            dismiss()
            addChanges()
        }


        view.button_cancelChanges.setOnClickListener {
            dismiss()
        }


        return view
    }

    private fun addChanges() {
        val newProduct = Product(
                name = editText_editTitle.text.toString(),
                qty = editText_editNumber.text.toString().toInt()
        )
        Repository.addProduct(newProduct)
    }

}