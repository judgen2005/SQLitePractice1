package com.example.sqlitepractice1

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "products.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_PRODUCTS = "products"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_WEIGHT = "weight"
        private const val COLUMN_PRICE = "price"
    }
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_PRODUCTS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_NAME TEXT, "
                + "$COLUMN_WEIGHT REAL, "
                + "$COLUMN_PRICE REAL)")
        db.execSQL(createTable)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUCTS")
        onCreate(db)
    }
    fun insertProduct(product: Product): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, product.name)
            put(COLUMN_WEIGHT, product.weight)
            put(COLUMN_PRICE, product.price)
        }
        return db.insert(TABLE_PRODUCTS, null, values)
    }
    fun getAllProducts(): List<Product> {
        val products = mutableListOf<Product>()
        val db = this.readableDatabase
        val cursor = db.query(TABLE_PRODUCTS, null, null, null, null, null, null)
        with(cursor) {
            while (moveToNext()) {
                val name = getString(getColumnIndexOrThrow(COLUMN_NAME))
                val weight = getFloat(getColumnIndexOrThrow(COLUMN_WEIGHT))
                val price = getFloat(getColumnIndexOrThrow(COLUMN_PRICE))
                products.add(Product(name, weight, price))
            }
            close()
        }
        return products
    }
}