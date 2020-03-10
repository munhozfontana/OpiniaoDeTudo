//package com.example.opiniaodetudo.pages.fragments
//
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.EditText
//import androidx.fragment.app.Fragment
//import com.example.opiniaodetudo.R
//import com.example.opiniaodetudo.online.BASE_URL
//import com.example.opiniaodetudo.online.LOGIN_URL
//import com.google.android.material.snackbar.Snackbar
//import okhttp3.OkHttpClient
//import okhttp3.Request
//import okhttp3.RequestBody
//import org.json.JSONObject
//
//class LoginFragment : Fragment() {
//
//    private lateinit var loginView: View
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        loginView = inflater.inflate(R.layout.fragment_container_login, null)
//
//        val buttonLogin = loginView.findViewById<Button>(R.id.buttonLogin)
//        val login = loginView.findViewById<EditText>(R.id.login)
//        val pass = loginView.findViewById<EditText>(R.id.senha)
//
//
//        buttonLogin.setOnClickListener {
//
//
//            try {
//                val jsonObject = JSONObject().apply {
//                    put("login", login.text)
//                    put("senha", pass.text)
//                }
//
//                val httpClient = OkHttpClient()
//
//                val body = RequestBody.create("application/json".toMediaType(), jsonObject.toString())
//                val request = Request.Builder().url("$BASE_URL/$LOGIN_URL").post(body).build()
//                val response = httpClient.newCall(request).execute()
//
//                val jsonReponse = JSONObject(response.body!!.string())
//
//                if (response.code == 401) {
//                    throw Exception(jsonReponse.getString("message"))
//                }
//
//                Snackbar.make(loginView, "Bem vindo!", Snackbar.LENGTH_LONG)
//                    .show()
//
//            }  catch (e: Exception) {
//                Log.e(
//                    "ERROR",
//                    "Erro",
//                    e
//                )
//                Snackbar.make(loginView, e.message.toString(), Snackbar.LENGTH_INDEFINITE)
//                    .setAction("Ok", {}).show()
//            }
//
//            true
//        }
//
//
//        return loginView
//    }
//
//}