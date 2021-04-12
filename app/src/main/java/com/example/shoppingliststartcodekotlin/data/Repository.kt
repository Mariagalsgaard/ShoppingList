package com.example.shoppingliststartcodekotlin.data

import androidx.lifecycle.MutableLiveData

object Repository {
    var products = mutableListOf<Product>()

    //listener to changes that we can then use in the Activity
    private var productListener = MutableLiveData<MutableList<Product>>()


    fun getData(): MutableLiveData<MutableList<Product>> {
        if (products.isEmpty())
            createTestData()
        productListener.value = products //we inform the listener we have new data
        return productListener
    }

    private fun createTestData()
    {
        //products.add(1, "")
        //add some products to the products list - for testing purposes

    }


   fun addProduct(product:Product) {
        //val product =
    }


    fun deleteProduct(index: Int) { // in the Repository
        products.removeAt(index)
    }


}

