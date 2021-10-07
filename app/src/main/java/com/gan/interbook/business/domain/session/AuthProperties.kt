package com.gan.interbook.business.domain.session

data class AuthProperties(
    val authorizationEndPointUri: String,
    val apiKey:String,
    val clientId: String,
    val endSession: String,
    val redirectUri: String,
    val responseType: String,
    val scope: String,
    val tokenEndPointUri: String,
    val signOutRedirectUri: String,
    val registerAction: String
) {
    override fun toString(): String {
        return "AuthProperties(authorizationEndPointUri='$authorizationEndPointUri', " +
                //"apiKey='$apiKey', " +
                "clientId='$clientId', endSession='$endSession', " +
                "redirectUri='$redirectUri', responseType='$responseType', scope='$scope', " +
                "tokenEndPointUri='$tokenEndPointUri', " +
                "signOutRedirectUri='$signOutRedirectUri', registerAction='$registerAction')"
    }
}