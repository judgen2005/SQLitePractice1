package com.example.sqlitepractice1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ProductAdapter(private val context: Context, private var productList: List<Product>) : BaseAdapter() {

    override fun getCount(): Int = productList.size

    override fun getItem(position: Int): Any = productList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false)
        val product = getItem(position) as Product

        view.findViewById<TextView>(android.R.id.text1).text = product.name
        view.findViewById<TextView>(android.R.id.text2).text = "Вес: ${product.weight}, Цена: ${product.price}"

        return view
    }

    fun updateData(newProductList: List<Product>) {
        productList = newProductList
        notifyDataSetChanged()
    }
}