package com.example.madlevel4task1

import android.content.Context

class ProductRepository(context: Context) {
    private val productDao: ProductDao

    init {
        val database = ShoppingListRoomDatabase.getDatabase(context)
        productDao = database!!.productDao()
    }

    suspend fun getAllProducts(): List<Product> {
        return productDao.getAllProducts()
    }

    suspend fun insertProduct(product: Product) {
        return productDao.insertProduct(product)
    }

    suspend fun deleteProduct(product: Product) {
        return productDao.deleteProduct(product)
    }

    suspend fun deleteAllProduct() {
        return productDao.deleteAllProducts()
    }
}