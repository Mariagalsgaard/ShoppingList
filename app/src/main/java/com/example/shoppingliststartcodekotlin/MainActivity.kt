package com.example.shoppingliststartcodekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.FirebaseApp
import com.example.shoppingliststartcodekotlin.adapters.ProductAdapter
import com.example.shoppingliststartcodekotlin.data.Product
import com.example.shoppingliststartcodekotlin.data.Repository
import com.example.shoppingliststartcodekotlin.data.Repository.addProduct
import com.example.shoppingliststartcodekotlin.data.Repository.deleteAllProducts
import com.example.shoppingliststartcodekotlin.data.Repository.products
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.product_layout.*

class MainActivity : AppCompatActivity() {

    //you need to have an Adapter for the products
    private lateinit var adapter: ProductAdapter
    private val spinnerItems = arrayOf("Name", "Qty")
    private val RESULT_CODE_PREFERENCES = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(applicationContext)

        Repository.getData().observe(this, Observer {
            Log.d("Products","Found ${it.size} products")
            updateUI()
        })

        //welcome message in a toast
        val toast = Toast.makeText(this, "Welcome ${PreferenceHandler.getName(this)}", Toast.LENGTH_LONG)
        toast.show()


        //The spinner
        val adapter1 = ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, spinnerItems)

        spinner.adapter = adapter1

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            //The AdapterView<?> type means that this can be any type,
            //so we can use both AdapterView<String> or any other
            override fun onItemSelected(adapterView: AdapterView<*>, view: View,
                                        position: Int, id: Long) {
                //So this code is called when ever the spinner is clicked
                if(position == 0) {
                    Log.d("sorting","name chosen")
                    products.sortBy { it.name }
                    adapter.notifyDataSetChanged()
                }else{
                    Log.d("sorting", "Qtychosen")
                    products.sortByDescending { it.qty }
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onNothingSelected(arg0: AdapterView<*>) {
            }
        }
    }


    fun showaddproductdialogue(v: View) {
        val dialog = AddProductDialogue(::addproducts)
        dialog.show(supportFragmentManager, "addrangedialogfragment")
    }


    //Callback function from add/remove dialog
    private fun addproducts(title: String, qty: Int) {
        var modify = "add"

        val toast = Toast.makeText(
            this,
            "You choose to $modify records starting from $title and ending at $qty",
            Toast.LENGTH_LONG
        )
        toast.show()
        //Change data
        //update UI
    }

/*  //converting list to string for sharing option
    private fun convertListToString(): String
    {
        var result = "";
        for (var i = 0; i < adapter.getCount(); i++)
        {
            var p = (Product) adapter.getItem(i);
            result = result + p.toString() + "\n"; //adding newline

        }
        return result;
    }*/


    private fun updateUI() {
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        adapter = ProductAdapter(products)
        recyclerView.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RESULT_CODE_PREFERENCES)
        //the code means we came back from settings
        {
            //I can can these methods like this, because they are static
            val female = PreferenceHandler.isFemale(this)
            val name = PreferenceHandler.getName(this)
            val notifications = PreferenceHandler.useNotifications(this)
            val message = "Welcome, $name, You are female? $female and you have notifications on? $notifications"
            val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
            toast.show()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.d("icon_pressed", "${item.itemId}")
        when (item.itemId) {
            R.id.item_delete -> {
                val dialog = PopupDialogue(::deleteAllProducts, true)
                dialog.show(supportFragmentManager, "yesnodialogfragment")
                //deleteAllProducts()
                return true
            }
            R.id.item_share -> {
                //Share content
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "Share your shoppinglist: $products")
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, "Write title...")
                startActivity(shareIntent)
                return true
            }
            R.id.action_settings -> {
                //Start settingsactivity and listen to result
                val intent = Intent(this, SettingsActivity::class.java)
                startActivityForResult(intent, RESULT_CODE_PREFERENCES)
                return true
            }
            else ->
                return false
        }
    }
}