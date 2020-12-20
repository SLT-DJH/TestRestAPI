package com.jinhyun.testrestapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    val retrofit = Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val service = retrofit.create(JsonPlaceHolderApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val call = service.getPosts()
        call.enqueue(object : Callback<List<Post>>{
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if(!response.isSuccessful){
                    tv_result.text = "Code: ${response.code()}"
                    return
                }

                val posts = response.body()

                for(i in 0 until posts!!.size){
                    var content = ""
                    content += "ID: ${posts[i].id} \n"
                    content += "User ID: ${posts[i].userId} \n"
                    content += "Title: ${posts[i].title} \n"
                    content += "Text: ${posts[i].text} \n"
                    content += "\n"

                    tv_result.append(content)
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                tv_result.text = t.message
            }
        })

    }
}
