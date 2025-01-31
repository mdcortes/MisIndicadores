package com.prueba.misindicadores.data

import com.prueba.misindicadores.data.model.LoggedInUser
import com.prueba.misindicadores.di.subcomponents.UserComponent
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
@Singleton
class UserManager @Inject constructor(private val dataSource: UserDataSource,
                                      private val userComponentFactory: UserComponent.Factory) {

    private var userComponent: UserComponent? = null

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = userComponent != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        if (dataSource.isLoggedInUser()) {
            setLoggedInUser(dataSource.getCurrentUser()!!)
        }
        else {
            user = null
        }
    }

    fun logout(): Result<Unit> {
        val result = dataSource.logout()

        if (result is Result.Success) {
            user = null
            userComponent = null
        }

        return result
    }

    fun login(username: String, password: String): Result<LoggedInUser> {
        // handle login
        val result = dataSource.login(username, password)

        if (result is Result.Success) {
            setLoggedInUser(result.data)
        }

        return result
    }

    fun register(username: String, displayName: String, password: String): Result<LoggedInUser> {
        val result = dataSource.register(username, displayName, password)

        if (result is Result.Success) {
            setLoggedInUser(result.data)
        }

        return result
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        user = loggedInUser
        userComponent = userComponentFactory.create()
    }
}