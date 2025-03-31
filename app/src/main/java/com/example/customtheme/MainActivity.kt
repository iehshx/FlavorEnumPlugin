package com.example.customtheme

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.customtheme.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

//    fun runFlavorFun(
//        flavor: Flavor,
//        defaultFunction: (() -> Unit)? = null,
//        zuoyebangFunction: (() -> Unit)? = null,
//        zuoyebangInkFunction: (() -> Unit)? = null,
//        zuoyebangInk2Function: (() -> Unit)? = null,
//        unKunction: (() -> Unit)? = null,
//    ) {
//        when (flavor) {
//            Flavor.Default -> {
//                defaultFunction?.invoke()
//            }
//
//            Flavor.Zuoyebang -> {
//                zuoyebangFunction?.invoke()
//            }
//
//            Flavor.ZuoyebangInk -> {
//                zuoyebangInkFunction?.invoke()
//            }
//
//            Flavor.ZuoyebangInk2 -> {
//                zuoyebangInk2Function?.invoke()
//            }
//
//            Flavor.UnKnow -> {
//                unKunction?.invoke()
//            }
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
//        runFlavorFun(Flavor.from(BuildConfig.FLAVOR), defaultFunction = {
//            Log.e("iehshx", "当前版本是主版本")
//        }, zuoyebangFunction = {
//            Log.e("iehshx", "当前版本是作业帮版本")
//        })
//        when(Flavor.from(BuildConfig.FLAVOR)){
//            Flavor.Default->{
//                Log.e("iehshx","主版本")
//            }
//            Flavor.Zuoyebang->{
//                Log.e("iehshx","作业帮")
//            }
//            Flavor.ZuoyebangInk->{
//                Log.e("iehshx","作业帮墨水屏")
//            }
//            Flavor.UnKnow->{
//                Log.e("iehshx","未知")
//            }
//            else->{}
//        }
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}