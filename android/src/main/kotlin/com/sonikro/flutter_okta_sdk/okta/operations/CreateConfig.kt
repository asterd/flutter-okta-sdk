package com.sonikro.flutter_okta_sdk.okta.operations

import android.content.Context
import com.okta.oidc.CustomConfiguration
import com.okta.oidc.OIDCConfig
import com.okta.oidc.Okta
import com.okta.oidc.storage.SharedPreferenceStorage
import com.sonikro.flutter_okta_sdk.HttpClientImpl
import com.sonikro.flutter_okta_sdk.okta.entities.Errors
import com.sonikro.flutter_okta_sdk.okta.entities.OktaClient
import com.sonikro.flutter_okta_sdk.okta.entities.OktaRequestParameters
import com.sonikro.flutter_okta_sdk.okta.entities.PendingOperation


fun createConfig(arguments: Map<String, Any>, context: Context) {
    try {
        val params = processOktaRequestArguments(arguments)

        println("Config contains token url: ${params.tokenUrl}")
        println("Config contains authorizationUrl url: ${params.authorizationUrl}")
        println("Config contains clientId url: ${params.clientId}")

        val config: OIDCConfig
        if (params.tokenUrl.isNotEmpty()) {
            val customConfig = CustomConfiguration.Builder()
                    .tokenEndpoint(params.tokenUrl)
                    .authorizationEndpoint(params.authorizationUrl)
                    .create()
            config = OIDCConfig.Builder()
                    .clientId(params.clientId)
                    .redirectUri(params.redirectUri)
                    .endSessionRedirectUri(params.endSessionRedirectUri)
                    .scopes(*params.scopes.toTypedArray())
                    .customConfiguration(customConfig)
                    .create()
        } else {
            config = OIDCConfig.Builder()
                    .clientId(params.clientId)
                    .redirectUri(params.redirectUri)
                    .endSessionRedirectUri(params.endSessionRedirectUri)
                    .scopes(*params.scopes.toTypedArray())
                    .discoveryUri(params.discoveryUri)
                    .create()
        }

        println("loaded config is: $config")

        val webClient = Okta.WebAuthBuilder()
                .withConfig(config)
                .withContext(context)
                .withStorage(SharedPreferenceStorage(context))
                .withOktaHttpClient(HttpClientImpl(params.userAgentTemplate))
                .setRequireHardwareBackedKeyStore(params.requireHardwareBackedKeyStore)
                .create()

        val authClient = Okta.AuthBuilder()
                .withConfig(config)
                .withContext(context)
                .withStorage(SharedPreferenceStorage(context))
                .withOktaHttpClient(HttpClientImpl(params.userAgentTemplate))
                .setRequireHardwareBackedKeyStore(params.requireHardwareBackedKeyStore)
                .create()

        OktaClient.init(config, webClient, authClient)
        PendingOperation.success(true)
    } catch (ex: java.lang.Exception) {
        PendingOperation.error(Errors.OKTA_OIDC_ERROR)
    }
}

private fun processOktaRequestArguments(arguments: Map<String, Any>): OktaRequestParameters {
    return OktaRequestParameters(
        clientId = (arguments["clientId"] as String?)!!,
        discoveryUri = (arguments["discoveryUrl"] as String?)!!,
        endSessionRedirectUri = (arguments["endSessionRedirectUri"] as String?)!!,
        redirectUri = (arguments["redirectUrl"] as String?)!!,
        requireHardwareBackedKeyStore = (arguments["requireHardwareBackedKeyStore"] as Boolean?)
                ?: false,
        scopes = arguments["scopes"] as ArrayList<String>,
        userAgentTemplate = (arguments["userAgentTemplate"] as String?) ?: "",
        tokenUrl = (arguments["tokenUrl"] as String?) ?: "",
        authorizationUrl = (arguments["authorizationUrl"] as String?) ?: ""
    )
}
