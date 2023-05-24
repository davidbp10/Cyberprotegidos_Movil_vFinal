package es.uca.cyberprotegidos.ui.reservas

import kotlinx.serialization.Serializable

@Serializable
data class Reserva(val _id: String, val tipo: String, val nombre: String, val dni: String, val telefono: String,
                   val email: String, val fechaIni: String, val fechaFin: String,
                   val numPersonas: String, val comentario: String){

}