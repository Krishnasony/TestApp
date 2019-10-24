package com.example.testapp

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.Toast
import com.example.testapp.adapter.DataListAdapter
import com.example.testapp.model.Data
import com.example.testapp.model.Place
import com.example.testapp.utils.RetrofitObject
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private var adapter:DataListAdapter? = null
    private var prefs: SharedPreferences? = null
    private val gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prefs = this.getSharedPreferences("sharedPrefs", MODE_PRIVATE) ?: error("err")
        init()
        getData()
        setToolbar()
    }

    private fun setToolbar() {
        toolbar.title = "Api Test"
    }

    private fun init() {
        rv_data_list.layoutManager = GridLayoutManager(this,2)
        swipeToRefresh.setOnRefreshListener {
            swipeToRefresh.isRefreshing = true
            getData()
        }
    }
    private fun getData() {
        val dataList = fetchArrayList()
        dataList?.let {
            if (it.size>1){
                adapter = DataListAdapter(this@MainActivity,it)
                rv_data_list.adapter = adapter
                adapter!!.notifyDataSetChanged()
                progressBar.visibility = View.GONE
                swipeToRefresh.isRefreshing = false
            }else{
                getDataFromApi()
            }

        }?:run{
            getDataFromApi()
        }

    }

    private fun getDataFromApi() {
        val apiService =RetrofitObject.apiService
        val call = apiService.getData()
        progressBar.visibility = View.VISIBLE
        call.enqueue(object : retrofit2.Callback<Place> {
            override fun onFailure(call: Call<Place>, t: Throwable) {
                showToast("Error: ${t.localizedMessage}")
                progressBar.visibility = View.GONE
                swipeToRefresh.isRefreshing = false


            }
            override fun onResponse(call: Call<Place>, response: Response<Place>) {
                if (response.isSuccessful){
                    response.body()?.let {
                        val data = it.data
                        data?.forEachIndexed { index, placeData ->
                            saveObjectToArrayList(index,placeData)
                        }
                        adapter = DataListAdapter(this@MainActivity,data as ArrayList<Data>)
                        rv_data_list.adapter = adapter
                        adapter!!.notifyDataSetChanged()
                        progressBar.visibility = View.GONE
                        swipeToRefresh.isRefreshing = false


                    }?:run{
                        showToast("Getting Null data")
                        progressBar.visibility = View.GONE
                        swipeToRefresh.isRefreshing = false


                    }
                }
            }

        })
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    fun showToast(message:String){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    private fun saveObjectToArrayList(index:Int,data:Data) {
        val bookmarks = fetchArrayList()
        bookmarks?.add(index, data)
        val prefsEditor = prefs?.edit()

        val json = gson.toJson(bookmarks)
        prefsEditor?.putString("place_data", json)
        prefsEditor?.apply()
    }

  private  fun fetchArrayList(): ArrayList<Data>? {
        val yourArrayList: ArrayList<Data>
        val json = prefs?.getString("place_data", "")

        yourArrayList = when {
            json.isNullOrEmpty() -> ArrayList()
            else -> gson.fromJson(json, object : TypeToken<List<Data>>() {}.type)
        }

        return yourArrayList
    }
}
