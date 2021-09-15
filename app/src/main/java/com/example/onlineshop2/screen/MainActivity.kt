package com.example.onlineshop2.screen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.onlineshop2.R
import com.example.onlineshop2.screen.card.CardFragment
import com.example.onlineshop2.screen.changelanguage.ChangeLanguageFragment
import com.example.onlineshop2.screen.favorite.FavoriteFragment
import com.example.onlineshop2.screen.home.HomeFragment
import com.example.onlineshop2.screen.profile.ProfileFragment
import com.example.onlineshop2.utils.LocaleManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val homeFragment = HomeFragment.newInstance()
    val favoriteFragment = FavoriteFragment.newInstance()
    val cartFragment = CardFragment.newInstance()
    val profileFragment = ProfileFragment.newInstance()
    var activeFragment: Fragment = homeFragment

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = MainViewModel()

        viewModel.productsData.observe(this, Observer {
            viewModel.insertAllProducts2Db(it)
            homeFragment.loadData()
        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, homeFragment, homeFragment.tag).hide(homeFragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, favoriteFragment, favoriteFragment.tag).hide(favoriteFragment)
            .commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, cartFragment, cartFragment.tag).hide(cartFragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, profileFragment, profileFragment.tag).hide(profileFragment)
            .commit()



        supportFragmentManager.beginTransaction().show(activeFragment).commit()

        bottomNavigationView.setOnNavigationItemSelectedListener {
            if (it.itemId == R.id.actionHome) {
                supportFragmentManager.beginTransaction().hide(activeFragment).show(homeFragment)
                    .commit()
                activeFragment = homeFragment
            } else if (it.itemId == R.id.actionCart) {
                supportFragmentManager.beginTransaction().hide(activeFragment).show(cartFragment)
                    .commit()
                activeFragment = cartFragment
            } else if (it.itemId == R.id.actionFavorite) {
                supportFragmentManager.beginTransaction().hide(activeFragment)
                    .show(favoriteFragment).commit()
                activeFragment = favoriteFragment
            } else
                if (it.itemId == R.id.actionProfile) {
                    supportFragmentManager.beginTransaction().hide(activeFragment)
                        .show(profileFragment).commit()
                    activeFragment = profileFragment
                }


            return@setOnNavigationItemSelectedListener true
        }

        btnMenu.setOnClickListener {
            val fragment = ChangeLanguageFragment.newInstance()
            fragment.show(supportFragmentManager, fragment.tag)


        }


    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(newBase))
    }


}