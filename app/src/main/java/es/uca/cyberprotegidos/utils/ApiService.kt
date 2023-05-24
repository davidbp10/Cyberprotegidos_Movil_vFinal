package es.uca.cyberprotegidos.utils

import android.content.Context
import android.content.res.Resources
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import es.uca.cyberprotegidos.R
import es.uca.cyberprotegidos.ui.reservas.Reserva
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlin.Exception


class ApiService (private val appContext:Context){
    private val client = HttpClient(Android){
        install(ContentNegotiation){
            json()
        }
    }

    suspend fun getReservas():List<Reserva>{
        Log.d("FETCH", "PRUEBA")
        val ip = appContext.getString(R.string.ip_address)
        val port = appContext.getString(R.string.server_port)
        try {
            Log.d("TEST", "${ip}")
            val response: HttpResponse = client.get("http://${ip}:${port}/reservas")
            Log.d("PETICION", "getCall: ${response.status.toString()}")
            Log.d("LOG GETCALL", response.body())
            val reservas : List<Reserva> = response.body()
            Log.d("LOG GETCALL", reservas.toString())
            return reservas
        }catch (ex:Exception){
            Log.e("LOG GETCALL", "getCall: ERROR ${ex.message}" )
            return emptyList()
        }
    }

    suspend fun deleteReserva (id : String):Boolean{
        val ip = appContext.getString(R.string.ip_address)
        val port = appContext.getString(R.string.server_port)
        return try {
            client.delete("http://${ip}:${port}/reservas/${id}")
            true
        } catch (ex:Exception) {
            Log.e("NETWORK ERROR", "deleteReserva: ${ex.message}" )
            false
        }
    }

    suspend fun postReserva(reserva:ReservaRequest) : Boolean{
        val ip = appContext.getString(R.string.ip_address)
        val port = appContext.getString(R.string.server_port)
        return try {
            val response: HttpResponse = client.post("http://${ip}:${port}/reservas") {
                contentType(
                    ContentType.Application.Json)
                setBody(reserva)
            }
            true
        } catch (ex:Exception){
            Log.e("FATAL ERROR", "postReserva: Error al hacer la reserva ${ex.message}")
            false
        }
    }

    suspend fun putReserva(reserva:ReservaRequest, id:String) : Boolean{
        val ip = appContext.getString(R.string.ip_address)
        val port = appContext.getString(R.string.server_port)
        return try {
            val response: HttpResponse = client.put("http://${ip}:${port}/reservas/${id}") {
                contentType(
                    ContentType.Application.Json)
                setBody(reserva)
            }
            true
        } catch (ex:Exception){
            Log.e("FATAL ERROR", "postReserva: Error al hacer la reserva ${ex.message}")
            false
        }
    }
}