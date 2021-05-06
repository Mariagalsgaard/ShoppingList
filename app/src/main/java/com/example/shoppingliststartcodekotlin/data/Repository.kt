package com.example.shoppingliststartcodekotlin.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.firestore.ktx.toObject
import io.grpc.InternalChannelz.id

object Repository {
    var products = mutableListOf<Product>()
    private lateinit var db: FirebaseFirestore

    //listener to changes that we can then use in the Activity
    private var productListener = MutableLiveData<MutableList<Product>>()


    fun getData(): MutableLiveData<MutableList<Product>> {
        if (products.isEmpty())
            //createTestData()
            readDataFromFirebase()
        productListener.value = products //we inform the listener we have new data
        return productListener
    }

    /*private fun createTestData()
    {
        //add some products to the products list - for testing purposes
        products.add(Product("Banana"))
        products.add(Product("Tomato"))
        products.add(Product("Ice Cream"))
    }*/


   fun addProduct(product:Product) {
       db = Firebase.firestore
       products.add(product)
       productListener.value = products
       db.collection("products")
               .add(product)
               .addOnSuccessListener { documentReference ->
                   Log.d("Error", "DocumentSnapshot written with ID: " + documentReference.id)
                   product.id = documentReference.id
               }
               .addOnFailureListener{ e -> Log.w("Error", "Error adding document", e)
               }
    }


    private fun readDataFromFirebase() {
        val db = Firebase.firestore
        db.collection("products").get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d("Repository", "${document.id} => ${document.data}")
                        val product = document.toObject<Product>()
                        product.id = document.id
                        products.add(product)
                    }
                    productListener.value = products //notify the listener there is new data
                }
                .addOnFailureListener{ exception ->
                    Log.d("Repository", "Error getting documents: ", exception)
                }
    }


       fun deleteProduct(index: Int) { // in the Repository
           db = Firebase.firestore
           val product = products[index]

           //delete from firebase
           db.collection("products").document(product.id).delete().addOnSuccessListener {
               Log.d("Snapshot", "DocumentSnapshot with id: ${product.id} successfully deleted!")
           }
                   .addOnFailureListener{e ->
                       Log.w("Error", "Error deleting document", e) }

           products.removeAt(index)
           productListener.value = products
       }


    fun deleteAllProducts(add: Boolean) {
        val product = products.toString()
        if (add) {

            //delete from firebase
            db.collection("products").document(product).delete().addOnSuccessListener {
                Log.d("Snapshot", "DocumentSnapshot with id: $product successfully deleted!")
            }
                    .addOnFailureListener{e ->
                        Log.w("Error", "Error deleting document", e) }

            products.clear()
            productListener.value = products

        } else {
            //nothing?
        }
    }


    }


