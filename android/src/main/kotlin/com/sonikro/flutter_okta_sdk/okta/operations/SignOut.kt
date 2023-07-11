package com.sonikro.flutter_okta_sdk.okta.operations

import android.app.Activity
import com.okta.oidc.RequestCallback
import com.okta.oidc.Tokens
import com.okta.oidc.util.AuthorizationException
import com.sonikro.flutter_okta_sdk.okta.entities.OktaClient


fun signOut(activity: Activity) {
    registerCallback(activity)
    // sign out of okta
    OktaClient.getWebClient().signOutOfOkta(activity)

    // revoke tokens
    val token: Tokens = OktaClient.getWebClient().sessionClient.tokens
    OktaClient.getWebClient().sessionClient.revokeToken(token.refreshToken,
        object : RequestCallback<Boolean, AuthorizationException?> {
            override fun onSuccess(result: Boolean) {
                //handle result
            }

            override fun onError(error: String, exception: AuthorizationException?) {
                //handle request error
            }
        })

    // clear session
    OktaClient.getWebClient().sessionClient?.clear()
}