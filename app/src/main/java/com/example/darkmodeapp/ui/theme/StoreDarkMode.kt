package com.example.darkmodeapp.ui.theme

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreDarkMode(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("UserMail")
        val OSCURO = booleanPreferencesKey("modo_oscuro")
    }

    val getModoOscuro: Flow<Boolean> = context.dataStore.data
        .map { preference->
            preference[OSCURO] ?: false
        }
    suspend fun saveModoOscuro(isOscActivo: Boolean){
        context.dataStore.edit { preference ->
            preference[OSCURO] = isOscActivo
        }
    }
}