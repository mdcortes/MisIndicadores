package com.prueba.misindicadores.data

import android.content.Context
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
        const val USER_IS_LOGGED_IN = "user_is_logged_in"
    }

    private val userIdsSharedPreferences = context.getSharedPreferences(
        USER_IDS_FILE_NAME, Context.MODE_PRIVATE)
    private val userPasswordsSharedPreferences = context.getSharedPreferences(
        USER_PASSWORDS_FILE_NAME, Context.MODE_PRIVATE)
    private val userDisplayNamesSharedPreferences = context.getSharedPreferences(
        USER_DISPLAY_NAMES_FILE_NAME, Context.MODE_PRIVATE)

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            if (userPasswordsSharedPreferences.contains(username)) {
                val storedPassword = userPasswordsSharedPreferences.getString(username, null)

                if (password == storedPassword) {
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

    fun logout() {
        // TODO: revoke authentication
    }
}