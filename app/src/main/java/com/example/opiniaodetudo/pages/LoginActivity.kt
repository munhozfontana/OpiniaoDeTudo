package com.example.opiniaodetudo.pages

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.opiniaodetudo.R
import com.example.opiniaodetudo.online.BASE_URL
import com.example.opiniaodetudo.online.LOGIN_URL
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONObject


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.fragment_container_login)



        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val login = findViewById<TextView>(R.id.login)
        val senha = findViewById<TextView>(R.id.senha)

        buttonLogin.setOnClickListener{

            try {



                val (response, jsonReponse) = loginBackend(login, senha)

                if (response.code == 401) {
                    throw Exception(jsonReponse.getString("message"))
                }


                if (response.code == 200 || response.code == 201) {
                    Snackbar.make(findViewById(R.id.fragment_container_login), "Bem vindo!", Snackbar.LENGTH_LONG)
                        .show()
                    navigateParaMain()
                }


            }  catch (e: Exception) {
                Log.e(
                    "ERROR",
                    "Erro",
                    e
                )
                Snackbar.make(findViewById(R.id.fragment_container_login),"error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Ok", {}).show()
            }
        }
    }

    private fun loginBackend(
        login: TextView,
        senha: TextView
    ): Pair<Response, JSONObject> {
        val jsonObject = JSONObject().apply {
            put("login", login.text)
            put("senha", senha.text)
        }

        val httpClient = OkHttpClient()

        val body =
            RequestBody.create("application/json".toMediaType(), jsonObject.toString())

        val request = Request.Builder().url("$BASE_URL/$LOGIN_URL").post(body).build()
        val response = httpClient.newCall(request).execute()

        val jsonReponse = JSONObject(response.body!!.string())
        return Pair(response, jsonReponse)
    }

    private fun navigateParaMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
