package com.sonikro.flutter_okta_sdk.okta.operations

import android.app.Activity
import com.okta.oidc.RequestCallback
import com.okta.oidc.Tokens
import com.okta.oidc.util.AuthorizationException
import com.sonikro.flutter_okta_sdk.okta.entities.OktaClient

fun signOut(activity: Activity) {
    registerCallback(activity)
    OktaClient.getWebClient().signOutOfOkta(activity)
}