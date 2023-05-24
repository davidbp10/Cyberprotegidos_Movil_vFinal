package es.uca.cyberprotegidos.utils

import kotlinx.serialization.Serializable

@Serializable
data class ReservaRequest(var tipo: String, var nombre: String, var dni: String, var telefono: String,
                          var email: String, var fechaIni: String, var fechaFin: String,
                          var numPersonas: String, var comentario: String){

}