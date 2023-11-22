package connection

import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import edu.austral.ingsis.clientserver.ClientConnectionListener
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.MessageListener
import edu.austral.ingsis.clientserver.ServerConnectionListener
import edu.austral.ingsis.clientserver.netty.client.NettyClientBuilder
import edu.austral.ingsis.clientserver.netty.server.NettyServerBuilder
import java.net.InetSocketAddress

fun main(){
    val PORT = 3000
    val messageType = "hello"
    val rawMessageType = "raw-type"

    //collectors
    val serverDataCollector = MessageCollectorListener<Data>()
    val client1DataCollector = MessageCollectorListener<Data>()
    val client2DataCollector = MessageCollectorListener<Data>()

    val serverRowCollector = MessageCollectorListener<String>()
    val clientRawCollector = MessageCollectorListener<String>()


    val serverConnectionListener = ServerGameConnectionListener()
    val server = NettyServerBuilder.createDefault()
        .withPort(PORT)
        .addMessageListener(messageType, jacksonTypeRef(), serverDataCollector)
        .addMessageListener(messageType, jacksonTypeRef(), serverRowCollector)
        .build()

    val clientConnectionListener =ClientGameConnectionListener()
    val client1 = NettyClientBuilder.createDefault()
        .withAddress(InetSocketAddress(PORT))
        .withConnectionListener(clientConnectionListener)
        .addMessageListener(messageType, jacksonTypeRef(), client1DataCollector)
        .addMessageListener(rawMessageType, jacksonTypeRef(), clientRawCollector)
        .build()

    val client2ConnectionListener =ClientGameConnectionListener()
    val client2 = NettyClientBuilder.createDefault()
        .withAddress(InetSocketAddress(PORT))
        .withConnectionListener(client2ConnectionListener)
        .addMessageListener(messageType, jacksonTypeRef(), client2DataCollector)
        .build()

    server.start()

    client1.connect()
    Thread.sleep(20)
    println("Server client 1: ${serverConnectionListener.clients.size} ")

    client2.connect()
    Thread.sleep(20)
    println("Server client 2: ${serverConnectionListener.clients.size} ")

    client1.send(Message(rawMessageType, "Hellow!"))
    Thread.sleep(50)

    println("server messages: $serverRowCollector.messages")

    client1.send(Message(messageType, Data("Hellow!")))
    Thread.sleep(50)

    println("server messages: $serverDataCollector.messages")


    server.broadcast(Message(messageType, Data("This is a broadcast")))


    client2.closeConnection()
    Thread.sleep(20)
    println("Server clients: ${serverConnectionListener.clients.size} ")

    client1.closeConnection()
    Thread.sleep(20)
    println("Server clients: ${serverConnectionListener.clients.size} ")

    server.stop()
}

class ServerGameConnectionListener: ServerConnectionListener {
    public val clients: MutableList<String> = mutableListOf()
    override fun handleClientConnection(clientId: String) {
        println("Client connected: $clientId")
        clients.add(clientId)
    }

    override fun handleClientConnectionClosed(clientId: String) {
        println("Client disconnected $clientId")
        clients.remove(clientId)
    }
}

class ClientGameConnectionListener: ClientConnectionListener {
    private var connected = false;
    override fun handleConnection() {
        connected = true
    }

    override fun handleConnectionClosed() {
        connected = false
    }
}

class MessageCollectorListener<P : Any>: MessageListener<P>{
    val messages = mutableListOf<P>()

    override fun handleMessage(message: Message<P>) {
        println("Message recived ${message.payload}")
        messages.add(message.payload)
    }

}

data class Data(val field: String)