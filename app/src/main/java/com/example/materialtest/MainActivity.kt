package com.example.materialtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.materialtest.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    val fruits = mutableListOf<Fruit>(Fruit("Apple", R.drawable.apple), Fruit("Banana", R.drawable.banana),
            Fruit("Orange", R.drawable.orange), Fruit("Watermelon", R.drawable.watermelon),
            Fruit("Pear", R.drawable.pear), Fruit("Grape", R.drawable.grape),
            Fruit("Pineapple", R.drawable.pineapple), Fruit("Strawberry", R.drawable.strawberry),
            Fruit("Cherry", R.drawable.cherry), Fruit("Mango", R.drawable.mango))
    val fruitList = ArrayList<Fruit>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        initFruits()
        val layoutManager = GridLayoutManager(this, 2)
        activityMainBinding.recyclerView.layoutManager = layoutManager
        val adapter = FruitAdapter(this, fruitList)
        activityMainBinding.recyclerView.adapter = adapter
        setSupportActionBar(activityMainBinding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_menu)
        }
        activityMainBinding.navView.setCheckedItem(R.id.navCall)
        activityMainBinding.navView.setNavigationItemSelectedListener {
            activityMainBinding.drawerLayout.closeDrawers()
            true
        }
        activityMainBinding.fab.setOnClickListener {
           // Toast.makeText(this, "FAB clicked", Toast.LENGTH_SHORT).show()
            view -> Snackbar.make(view, "Data deleted", Snackbar.LENGTH_SHORT)
            .setAction("Undo") {
                Toast.makeText(this, "Data restored", Toast.LENGTH_SHORT).show()
            }
            .show()
        }
        activityMainBinding.swipeRefresh.setColorSchemeResources(R.color.design_default_color_primary)
        activityMainBinding.swipeRefresh.setOnRefreshListener {
            refreshFruits(adapter)
        }
    }

    private fun refreshFruits(adapter: FruitAdapter) {
        thread {
            Thread.sleep(2000)
            runOnUiThread {
                initFruits()
                adapter.notifyDataSetChanged()
                activityMainBinding.swipeRefresh.isRefreshing = false
            }
        }
    }

    private fun initFruits() {
        fruitList.clear()
        repeat(50) {
            val index = (0 until fruits.size).random()
            fruitList.add(fruits[index])
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> activityMainBinding.drawerLayout.openDrawer(GravityCompat.START)
            R.id.backup -> Toast.makeText(this, "You clicked Backup",
                Toast.LENGTH_SHORT).show()
            R.id.delete -> Toast.makeText(this, "You clicked Delete",
                Toast.LENGTH_SHORT).show()
            R.id.settings -> Toast.makeText(this, "You clicked Settings",
                Toast.LENGTH_SHORT).show()
        }
        return true
    }
}