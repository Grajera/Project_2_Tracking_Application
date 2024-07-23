import com.example.shippingsimulator.ShipmentTracker
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

object Server {
    private suspend fun updateShipment(data: String) {
        var shipmentTracker = ShipmentTracker.instance
        data.replace(" ", "")
        val shipmentAction = data.split(",")
        shipmentTracker.processUpdates(data)
    }

    fun startServer() {
        embeddedServer(Netty, 8956) {
            routing {
                get("/") {
                    call.respondText(File("./src/main/web/index.html").readText(), ContentType.Text.Html)
                }

                post("/data") {
                    val data = call.receiveText()
                    println(data)
                    updateShipment(data)
                    call.respondText { "POST Data Successful" }
                }
            }
        }.start(wait = false)
    }
}