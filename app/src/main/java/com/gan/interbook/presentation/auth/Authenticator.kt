package com.gan.interbook.presentation.auth

interface Authenticator {
    fun register()

    fun authorize()
}