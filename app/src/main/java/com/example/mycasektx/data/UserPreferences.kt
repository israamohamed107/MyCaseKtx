package com.example.mycasektx.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


data class UserPreferencesData(val name:String? , val email:String , val phone:String ,val userToken:String?)
@Singleton
class UserPreferences @Inject constructor(@ApplicationContext private val context: Context){
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name="lawyer_details")
    val dataStorePref = context.dataStore



    val preferencesFlow = dataStorePref.data.map {preferences ->
        val name = preferences[USER_NAME]
        val email = preferences[USER_EMAIL] ?: "abc@gmail.com"
        val phone = preferences[USER_PHONE] ?: "0123456789"
        val token = preferences[USER_TOKEN]

        UserPreferencesData(name,email,phone, token)
    }

    suspend fun setUserToken(userToken:String){
        dataStorePref.edit {preferences ->
            preferences[USER_TOKEN] = userToken
        }
    }

    suspend fun setUserName(userName:String){
        dataStorePref.edit {preferences ->
            preferences[USER_NAME] = userName
        }
    }

    suspend fun setUserEmail(userEmail:String){
        dataStorePref.edit {preferences ->
            preferences[USER_EMAIL] = userEmail
        }
    }

    suspend fun setUserPhone(userPhone:Any?){
        dataStorePref.edit {preferences ->
            if (userPhone == null){
                preferences[USER_PHONE] = ""

            }else{
                preferences[USER_PHONE] = userPhone as String
            }

        }
    }
    companion object{
        val USER_NAME = stringPreferencesKey("name")
        val USER_EMAIL = stringPreferencesKey("email")
        val USER_PHONE = stringPreferencesKey("phone")
        val USER_TOKEN = stringPreferencesKey("user_token")
    }

}