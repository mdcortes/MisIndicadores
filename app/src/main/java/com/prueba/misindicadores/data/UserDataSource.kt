package com.prueba.misindicadores.data

import android.content.Context
import androidx.core.content.edit
import com.prueba.misindicadores.data.model.LoggedInUser
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
@Singleton
class UserDataSource @Inject constructor(private val context: Context){
    companion object {
        const val USER_IDS_FILE_NAME = "user_ids"
        const val USER_PASSWORDS_FILE_NAME = "user_passwords"
        const val USER_DISPLAY_NAMES_FILE_NAME = "user_display_names"
        const val LOGGED_IN_USER = "logged_in_user"

        const val CURRENT_LOGGED_IN_USER = "current_logged_in_user"
    }

    private fun getPrivateSharedPreferences(fileName: String)
            = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)

    private val userIdsSharedPreferences
        get() =  getPrivateSharedPreferences(USER_IDS_FILE_NAME)

    private val userPasswordsSharedPreferences
        get() = getPrivateSharedPreferences( USER_PASSWORDS_FILE_NAME)

    private val userDisplayNamesSharedPreferences
        get()  = getPrivateSharedPreferences(USER_DISPLAY_NAMES_FILE_NAME)

    private val loggedInUserSharedPreferences
        get() = getPrivateSharedPreferences(LOGGED_IN_USER)

    fun register(username: String, displayName: String, password: String): Result<LoggedInUser> {
        if(userIdsSharedPreferences.contains(username)) {
            return Result.Error(IOException("Error on registration"))
        }

        val uuid = java.util.UUID.randomUUID().toString()

        userIdsSharedPreferences.edit {
            putString(username, uuid)
            apply()
        }

        userDisplayNamesSharedPreferences.edit {
            putString(uuid, displayName)
            apply()
        }

        userPasswordsSharedPreferences.edit() {
            putString(uuid, password)
            apply()
        }

        loggedInUserSharedPreferences.edit {
            putString(CURRENT_LOGGED_IN_USER, uuid)
            apply()
        }

        return Result.Success(
            LoggedInUser(
                uuid,
                displayName
            )
        )
    }

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            if (userIdsSharedPreferences.contains(username)) {
                val userId = userIdsSharedPreferences.getString(username, null)
                val storedPassword = userPasswordsSharedPreferences.getString(userId, null)

                if (password == storedPassword) {
                    loggedInUserSharedPreferences.edit {
                        putString(CURRENT_LOGGED_IN_USER, userId)
                        apply()
                    }

                    return Result.Success(
                        LoggedInUser(
                            userIdsSharedPreferences.getString(username, "")!!,
                            userDisplayNamesSharedPreferences.getString(username, "")!!
                        )
                    )
                }
            }

        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }

        return Result.Error(IOException("Error logging in"))
    }

    fun isLoggedInUser() = loggedInUserSharedPreferences.contains(CURRENT_LOGGED_IN_USER)

    fun getCurrentUser(): LoggedInUser? {
        return if (isLoggedInUser()) {
            val userId = loggedInUserSharedPreferences.getString(CURRENT_LOGGED_IN_USER, "")
            LoggedInUser(
                userId!!,
                userDisplayNamesSharedPreferences.getString(userId, "")!!
            )
        } else null
    }

    fun logout(): Result<Unit> {
        if (isLoggedInUser()) {
            loggedInUserSharedPreferences.edit {
                remove(CURRENT_LOGGED_IN_USER)
                apply()
            }

            return Result.Success(Unit)
        }

        return Result.Error(IOException("No user logged in"))
    }
}