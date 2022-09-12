package ir.part.sdk.ara.builder.util

import ir.part.sdk.namabar.builder.Namabar
import ir.part.sdk.namabar.builder.NamabarRequest
import ir.part.sdk.namabar.builder.NamabarUiState
import ir.part.sdk.namabar.builder.entities.ReceiveRequestView
import ir.part.sdk.namabar.builder.entities.SchemaInfoView
import ir.part.sdk.namabar.builder.entities.SendRequestView
import ir.part.sdk.namabar.builder.entities.ServerInfoView


private fun namabarBuilder(
    token: String,
    userName: String,
    taskInstanceId: String,
    processInstanceId: String,
    doneRequest: () -> Unit,
    onFullScreenChangeState: (Boolean) -> Unit
): Namabar {
    val serverInfo = ServerInfoView(
        headerUserName = "namabar",
        headerPassword = "namabar"
    )

    return Namabar.DynamicBuilder()
        .setFormConfigParamForReceive(
            ReceiveRequestView(
                serverInfo,
                "namabarmobile",
                "1.0.0",
                "6d77ab18-47ac-4325-89e0-1f4eb4b56b67"
            )
        )
        .setFormValueParamForReceive(
            ReceiveRequestView(
                serverInfo,
                "namabarmobileValue",
                "1.0.0",
                "ea585dcc-082c-452a-84af-0237211e3dbb"
            )
        )
        .setFormValueParamForSend(
            SendRequestView(
                serverInfo = serverInfo,
                SchemaInfoView(
                    "namabarmobileValue", "1.0.0"
                ),
                id = "ea585dcc-082c-452a-84af-0237211e3dbb",
                tags = mapOf(Pair("platform", "android"))
            )
        ).setOnNamabarUiState(object : NamabarUiState {
            override fun onFullScreenState(fullScreen: Boolean) {
                onFullScreenChangeState(fullScreen)
            }
        })
        .setToken(token)
        .setProcessInstanceId(processInstanceId)
        .setUserName(userName)
        .setTaskInstanceId(taskInstanceId)
        .setRequest(
            object : NamabarRequest {
                override suspend fun onPostRequest(): Boolean {
                    doneRequest.invoke()
                    return true
                }

                override suspend fun onPreRequest(): Boolean {
                    return true
                }

            }
        )
        .build()
}