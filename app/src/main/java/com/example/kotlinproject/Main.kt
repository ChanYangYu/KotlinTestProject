package com.example.kotlinproject

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import tech.gusavila92.websocketclient.WebSocketClient
import java.net.URI
import java.net.URISyntaxException

class Main : AppCompatActivity() {
    private var webSocketClient: WebSocketClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val uri = "wss://api.sample.com"
        createWebSocketClient()
        //webSocketClient?.send("hi")
    }

    private fun createWebSocketClient() {
        val uri: URI
        try {
            uri = URI("wss://echo.websocket.org/")
        } catch (e: URISyntaxException) {
            e.printStackTrace()
            return
        }
        webSocketClient = object : WebSocketClient(uri) {
            override fun onOpen() {
                Log.e("test","onOpen")
                webSocketClient?.send("/topic/ic")
            }

            override fun onTextReceived(message: String?) {
                Log.e("test","onTextReceived")
            }

            override fun onBinaryReceived(data: ByteArray?) {
                Log.e("test","onBinaryReceived")
            }

            override fun onPingReceived(data: ByteArray?) {
                Log.e("test","onPingReceived")
                object : Thread() {
                    override fun run() {
                        //
                        runOnUiThread {
                            Log.e("test","hello")
                            main_text.setText(data.toString())
                        }
                    }
                }.start()

            }

            override fun onPongReceived(data: ByteArray?) {
                Log.e("test","onPongReceived")
            }

            override fun onException(e: Exception) {
                Log.e("test",e.message.toString())
            }

            override fun onCloseReceived() {
                Log.e("test","onCloseReceived")
            }
        }
        (webSocketClient as WebSocketClient).setConnectTimeout(10000)
        (webSocketClient as WebSocketClient).setReadTimeout(60000)
        //(webSocketClient as WebSocketClient).addHeader("Origin", "http://developer.example.com")
        (webSocketClient as WebSocketClient).enableAutomaticReconnection(5000)
        (webSocketClient as WebSocketClient).connect()
    }
}