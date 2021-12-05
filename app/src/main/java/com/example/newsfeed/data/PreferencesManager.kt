package com.example.newsfeed.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

data class UserPreferences(val language: String)

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

enum class Language{
    EN, RU, CH, JA
}

@Singleton
class PreferencesManager @Inject constructor( @ApplicationContext context: Context) {
    private val dataStore = context.dataStore
    val preferencesFlow: Flow<UserPreferences> = dataStore.data
        .map { preferences ->
               val language = preferences[PreferencesKeys.LANGUAGE] ?: ""
               UserPreferences(language)

        }

    suspend fun changeLanguage(language: Language){
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.LANGUAGE] = language.name
        }
    }

    private object PreferencesKeys{
       val LANGUAGE = stringPreferencesKey("language")
    }
}