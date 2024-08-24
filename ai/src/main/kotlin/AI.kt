package com.julia

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.Socket
import java.nio.ByteBuffer
import java.util.stream.Collectors

interface AI {
    fun makeDecision(inputs: List<Float>): Action

    companion object {
        fun create(): AI = TcpAiImpl()
    }
}

internal class TcpAiImpl : AI {

    override fun makeDecision(inputs: List<Float>) =
        try {
            val client = Socket("127.0.0.1", 9999)
            val bytes = ByteBuffer.allocate(inputs.size * 4).apply {
                inputs.map(Float::toInt).forEach(::putInt)
            }.array()
            client.outputStream.write(bytes)
            val response = client.inputStream.readAllBytes()
            Action.entries[ByteBuffer.wrap(response).int].also { client.close() }
        } catch (e: Exception) {
            println(e)
            Action.NO_ACTION
        }
}