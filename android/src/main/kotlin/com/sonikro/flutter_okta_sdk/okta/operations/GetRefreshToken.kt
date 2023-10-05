package com.sonikro.flutter_okta_sdk.okta.operations

import com.sonikro.flutter_okta_sdk.okta.entities.Errors
import com.sonikro.flutter_okta_sdk.okta.entities.OktaClient
import com.sonikro.flutter_okta_sdk.okta.entities.PendingOperation

fun getRefreshToken() {

    var sessionClient = OktaClient.getWebClient().sessionClient
    var tokens = sessionClient.tokens

    if (tokens.refreshToken == null) {
        PendingOperation.error(Errors.NO_REFRESH_TOKEN)
    } else {
        PendingOperation.success(tokens.refreshToken)
    }
}
