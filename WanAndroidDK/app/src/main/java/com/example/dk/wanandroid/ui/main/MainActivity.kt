package com.example.dk.wanandroid.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import com.example.baselib.base.IBasePresenter
import com.example.baselib.base.IBaseView
import com.example.dk.wanandroid.base.AppBaseActivity
import com.example.dk.wanandroid.constants.Constants
import com.example.dk.wanandroid.ui.main.home.HomeFragment
import com.example.dk.wanandroid.ui.main.mine.MineFragment
import com.example.dk.wanandroid.ui.main.tab.TabFragment
import com.example.wanandroid_dk.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppBaseActivity<IBasePresenter<*>>(), IBaseView {


    private var lastIndex = 0
    private var fragements: MutableList<Fragment> = mutableListOf()

    override fun init(savedInstanceState: Bundle?) {
        initFragment()
        initBottom()

    }

    private fun initFragment() {
        //首页
        fragements.add(HomeFragment())
        //我的
        fragements.add(MineFragment())
        //项目
        val tab = TabFragment()
        val tabBundle = Bundle()
        tabBundle.putInt("type",Constants.PROJECT_TYPE)
        tab.arguments = tabBundle
        fragements.add(tab)

        fragements.add(HomeFragment())
        fragements.add(HomeFragment())


        setFragmentIndex(0)


    }


    private fun initBottom() {
        btmNavigation.run {
            setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_home -> setFragmentIndex(0)
                    R.id.menu_project -> setFragmentIndex(1)
                    R.id.menu_square -> setFragmentIndex(2)
                    R.id.menu_official_account -> setFragmentIndex(3)
                    R.id.menu_mine -> setFragmentIndex(4)
                }
                true
            }
        }

    }


    private fun setFragmentIndex(index: Int) {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        val currentFragment: Fragment = fragements[index]
        val lastFragement: Fragment = fragements[lastIndex]
        lastIndex = index
        ft.hide(lastFragement)
        if (!currentFragment.isAdded) {
            supportFragmentManager.beginTransaction().remove(currentFragment).commit()
            ft.add(R.id.frameLayout, currentFragment)
            ft.setMaxLifecycle(currentFragment, Lifecycle.State.RESUMED)
        }
        ft.show(currentFragment)
        ft.commit()
    }

    override fun createPresenter(): IBasePresenter<*>? {
        return null
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getContext(): Context? {
        return this
    }

    override fun onError(error: String) {

    }

}
