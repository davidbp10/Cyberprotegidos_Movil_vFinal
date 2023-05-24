package es.uca.cyberprotegidos.ui.reservas

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import es.uca.cyberprotegidos.R
import es.uca.cyberprotegidos.UpdateFormActivity
import es.uca.cyberprotegidos.utils.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReservaAdapter (private val reservas:List<Reserva>, private val recyclerView: RecyclerView): RecyclerView.Adapter<ReservaAdapter.ReservaViewHolder>() {
    class ReservaViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val tipoTextView: TextView = itemView.findViewById(R.id.reserva_text_tipo)
        val nombreTextView: TextView = itemView.findViewById(R.id.reserva_text_nombre)
        val dniTextView: TextView = itemView.findViewById(R.id.reserva_text_dni)
        val telefonoTextView: TextView = itemView.findViewById(R.id.reserva_text_telefono)
        val emailTextView: TextView = itemView.findViewById(R.id.reserva_text_email)
        val idTextView: TextView = itemView.findViewById((R.id.reserva_text_id))
        val id = idTextView.text
        val fechaIniTextView : TextView = itemView.findViewById(R.id.reserva_text_fechaIni)
        val fechaFinTextView : TextView = itemView.findViewById(R.id.reserva_text_fechaFin)
        val numPersonasTextView : TextView = itemView.findViewById(R.id.reserva_text_numPersonas)
        val comentarioTextView : TextView = itemView.findViewById(R.id.reserva_text_comentario)
        val verMasButton : Button = itemView.findViewById(R.id.reserva_button_ver_mas)
        val extraFields : RelativeLayout = itemView.findViewById(R.id.reserva_relative_2)
        val deleteButton : Button = itemView.findViewById(R.id.reserva_button_eliminar)
        val editButton : Button = itemView.findViewById(R.id.reserva_button_edit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservaViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_reserva,parent,false)
        return ReservaViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: ReservaViewHolder,
        position: Int,
    ) {
        val currentReserva = reservas[position]
        holder.idTextView.text = currentReserva._id
        holder.tipoTextView.text = "Tipo: " + currentReserva.tipo
        holder.nombreTextView.text = "Nombre: " + currentReserva.nombre
        holder.dniTextView.text = "DNI: " + currentReserva.dni
        holder.telefonoTextView.text = "Telefono: " + currentReserva.telefono
        holder.emailTextView.text = "Email: " + currentReserva.email
        holder.fechaIniTextView.text = "Inicio: " +  currentReserva.fechaIni
        holder.fechaFinTextView.text = "Fin: " + currentReserva.fechaFin
        holder.numPersonasTextView.text = "Personas: " + currentReserva.numPersonas
        holder.comentarioTextView.text = "Sala: " + currentReserva.comentario

        holder.verMasButton.setOnClickListener {
            val gone = holder.extraFields.visibility == View.GONE
            if(gone){
                holder.extraFields.visibility = View.VISIBLE
                holder.verMasButton.text = "Ver menos"
            }else{
                holder.extraFields.visibility = View.GONE
                holder.verMasButton.text = "Ver Más"
            }
        }

        holder.editButton.setOnClickListener{
            val context = holder.itemView.context
            val intent = Intent(context, UpdateFormActivity::class.java)
            intent.putExtra("id", currentReserva._id)
            intent.putExtra("tipo", currentReserva.tipo)
            intent.putExtra("nombre", currentReserva.nombre)
            intent.putExtra("dni", currentReserva.dni)
            intent.putExtra("telefono", currentReserva.telefono)
            intent.putExtra("email", currentReserva.email)
            intent.putExtra("fechaIni", currentReserva.fechaIni)
            intent.putExtra("fechaFin",currentReserva.fechaFin)
            intent.putExtra("numPersonas", currentReserva.numPersonas)
            intent.putExtra("comentario", currentReserva.comentario)
            context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val apiService = ApiService(holder.itemView.context)
                val id = holder.idTextView.text as String
                val result : Boolean = apiService.deleteReserva(id)
                withContext(Dispatchers.Main){
                    val snack : Snackbar
                    if(result){
                        snack = Snackbar.make(it, "Eliminado con éxito", Snackbar.LENGTH_LONG)
                        snack.show()
                        val newReservas = reservas.filter { item -> item._id != id }
                        val adapter = ReservaAdapter(newReservas, recyclerView)
                        recyclerView.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }
                    else{
                        snack = Snackbar.make(it, "Ha habido un error en la operación :(", Snackbar.LENGTH_LONG)
                        snack.show()
                    }
                }
            }
        }
    }
    override fun getItemCount(): Int {
        return reservas.size
    }
}