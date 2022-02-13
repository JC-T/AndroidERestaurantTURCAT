package fr.isen.turcat.androiderestaurantturcat

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import org.json.JSONObject
import fr.isen.turcat.androiderestaurantturcat.APIConstants
import fr.isen.turcat.androiderestaurantturcat.RegisterResult
import fr.isen.turcat.androiderestaurantturcat.User
import fr.isen.turcat.androiderestaurantturcat.R
import fr.isen.turcat.androiderestaurantturcat.databinding.ActivityUserBinding


interface UserActivityFragmentInteraction {
    fun showLogin()
    fun showRegister()
    fun makeRequest(email: String?, password: String?, lastname: String?, firstname: String?, isfromLogin: Boolean)
}

class UserActivity : AppCompatActivity(), UserActivityFragmentInteraction {
    lateinit var binding: ActivityUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = RegisterFragment()
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, fragment).commit()
    }

    fun verifyInformations(
        email: String?,
        password: String?,
        lastname: String?,
        firstname: String?,
        fromLogin: Boolean
    ): Boolean {
        var verified: Boolean =  (email?.isNotEmpty() == true && password?.count() ?: 0 >= 6)

        if(!fromLogin) {
            verified = verified && (firstname?.isNotEmpty() == true && lastname?.isNotEmpty() == true)
        }
        return  verified
    }

    override fun showLogin() {
        val fragment = LoginFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }

    override fun showRegister() {
        val fragment = RegisterFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }

    private fun launchRequest(
        email: String?,
        password: String?,
        lastname: String?,
        firstname: String?,
        fromLogin: Boolean
    ) {
        val queue = Volley.newRequestQueue(this)
        var path = APIConstants.PATH_REGISTER
        if(fromLogin) {
            path = APIConstants.PATH_LOGIN
        }
        val url = APIConstants.BASE_URL + path

        val jsonData = JSONObject()
        jsonData.put(APIConstants.ID_SHOP, "1")
        jsonData.put(APIConstants.EMAIL, email)
        jsonData.put(APIConstants.PASSWORD, password)
        if(!fromLogin) {
            jsonData.put(APIConstants.FIRSTNAME, firstname)
            jsonData.put(APIConstants.LASTNAME, lastname)
        }

        var request = JsonObjectRequest(
            Request.Method.POST,
            url,
            jsonData,
            { response ->
                val userResult = GsonBuilder().create().fromJson(response.toString(), RegisterResult::class.java)
                if(userResult.data != null) {
                    saveUser(userResult.data)
                    Log.d("Success","API OK")
                } else {
                    Snackbar.make(binding.root, "Problème survenue", Snackbar.LENGTH_LONG).show()
                }
            },
            { error ->
                error.message?.let {
                    Log.d("request", it)
                } ?: run {
                    Log.d("request error API", error.toString())
                    //Log.d("request", String(error.networkResponse.data))
                }
            }
        )
        queue.add(request)
    }

    fun saveUser(user: User) {
        val sharedPreferences = getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(ID_USER, user.id)
        editor.apply()

        setResult(Activity.RESULT_FIRST_USER)
        finish()
    }

    override fun makeRequest(
        email: String?,
        password: String?,
        lastname: String?,
        firstname: String?,
        isfromLogin: Boolean
    ) {
        if(verifyInformations(email, password, lastname, firstname, isfromLogin)) {
            launchRequest(email, password, lastname, firstname, isfromLogin)
        } else {
            Snackbar.make(binding.root, "Problème survenu", Snackbar.LENGTH_LONG).show()
        }
    }

    companion object {
        const val REQUEST_CODE = 111
        const val ID_USER = "ID_USER"
        const val USER_PREFERENCES_NAME = "USER_PREFERENCES_NAME"
    }
}