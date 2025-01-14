package com.example.sqlitepractice1

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etProductName: EditText
    private lateinit var etProductWeight: EditText
    private lateinit var etProductPrice: EditText
    private lateinit var btnSave: Button
    private lateinit var lvProducts: ListView
    private lateinit var dbHelper: DBHelper
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        etProductName = findViewById(R.id.etProductName)
        etProductWeight = findViewById(R.id.etProductWeight)
        etProductPrice = findViewById(R.id.etProductPrice)
        btnSave = findViewById(R.id.btnSave)
        lvProducts = findViewById(R.id.lvProducts)
        dbHelper = DBHelper(this)
        productAdapter = ProductAdapter(this, dbHelper.getAllProducts())
        lvProducts.adapter = productAdapter
        btnSave.setOnClickListener {
            saveProduct()
        }
    }

    private fun saveProduct() {
        val name = etProductName.text.toString()
        val weight = etProductWeight.text.toString().toFloatOrNull() ?: 0f
        val price = etProductPrice.text.toString().toFloatOrNull() ?: 0f
        if (name.isNotBlank() && weight > 0 && price > 0) {
            val product = Product(name, weight, price)
            dbHelper.insertProduct(product)
            productAdapter.updateData(dbHelper.getAllProducts())
            etProductName.text.clear()
            etProductWeight.text.clear()
            etProductPrice.text.clear()
            Toast.makeText(this, "Продукт сохранён", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Пожалуйста, заполните все поля правильно", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_exit -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}