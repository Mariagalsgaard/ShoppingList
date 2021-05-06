package com.example.shoppingliststartcodekotlin

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.popupdialogue_layout.view.*

class PopupDialogue (val addRemoveAllFunc: (Boolean) -> (Unit), val add:Boolean) : DialogFragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.popupdialogue_layout, container)
        view.yesButton.setOnClickListener {
            dismiss()
            addRemoveAllFunc(add)
        }
        view.noButton.setOnClickListener {
            dismiss()
        }
        return view
    }
}
