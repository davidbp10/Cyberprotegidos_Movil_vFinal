package es.uca.cyberprotegidos

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import es.uca.cyberprotegidos.utils.ApiService
import es.uca.cyberprotegidos.utils.ReservaRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class FormActivity : AppCompatActivity() {
    private lateinit var editTextTipo: EditText
    private lateinit var editTextNombre: EditText
    private lateinit var editTextDni: EditText
    private lateinit var editTextTelefono: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextFechaIni: EditText
    private lateinit var editTextFechaFin: EditText
    private lateinit var editTextNumPersonas: EditText
    private lateinit var editTextComentario: EditText
    private lateinit var buttonGuardar: Button
    private lateinit var buttonCancelar: Button

    private val calendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        editTextTipo = findViewById(R.id.tipo_sala_edittext)
        editTextNombre = findViewById(R.id.nombre_edittext)
        editTextDni = findViewById(R.id.dni_edittext)
        editTextTelefono = findViewById(R.id.telefono_edittext)
        editTextFechaIni = findViewById(R.id.fecha_inicio_edittext)
        editTextFechaFin = findViewById(R.id.fecha_fin_edittext)
        editTextNumPersonas = findViewById(R.id.num_personas_edittext)
        editTextComentario = findViewById(R.id.comentario_edittext)
        buttonGuardar = findViewById(R.id.buttonGuardar)
        buttonCancelar = findViewById(R.id.buttonCancelar)

        editTextFechaIni.setOnClickListener {
            showFechaIniPicker()
        }

        editTextFechaFin.setOnClickListener {
            showFechaFinPicker()
        }

        buttonGuardar.setOnClickListener {

            if(validateFields())  {
                val reservaRequest = ReservaRequest(
                    editTextTipo.text.toString().trim(),
                    editTextNombre.text.toString().trim(),
                    editTextDni.text.toString().trim(),
                    editTextTelefono.text.toString().trim(),
                    editTextEmail.text.toString().trim(),
                    editTextFechaIni.text.toString().trim(),
                    editTextFechaFin.text.toString().trim(),
                    editTextNumPersonas.text.toString().trim(),
                    editTextComentario.text.toString().trim()
                )
                val context = this
                GlobalScope.launch(Dispatchers.IO) {
                    if(postBooking(reservaRequest,context)){
                        withContext(Dispatchers.Main){
                            Toast.makeText(context, "Reserva añadida", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        withContext(Dispatchers.Main){
                            Toast.makeText(context, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                        }
                    }
                    withContext(Dispatchers.Main){
                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }

        }
        buttonCancelar.setOnClickListener {
            finish()
        }
    }

    private fun showFechaIniPicker() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)
                val formattedDate = formatDate(selectedDate.time)
                editTextFechaIni.setText(formattedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    private fun showFechaFinPicker() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)
                val formattedDate = formatDate(selectedDate.time)
                editTextFechaFin.setText(formattedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    private fun formatDate(date: Date): String {
        val dateFormat = android.text.format.DateFormat.getDateFormat(this)
        return dateFormat.format(date)
    }

    private fun validateFields(): Boolean {
        val tipo = editTextTipo.text.toString().trim()
        val nombre = editTextNombre.text.toString().trim()
        val dni = editTextDni.text.toString().trim()
        val telefono = editTextTelefono.text.toString().trim()
        val email = editTextEmail.text.toString().trim()
        val fechaIni = editTextFechaIni.text.toString().trim()
        val fechaFin = editTextFechaFin.text.toString().trim()
        val numPersonas = editTextNumPersonas.text.toString().trim()
        val comentario = editTextComentario.text.toString().trim()

        if (tipo.isEmpty()) {
            editTextTipo.error = "Ingrese un apellido"
            editTextTipo.requestFocus()
            return false
        }

        if (nombre.isEmpty()) {
            editTextNombre.error = "Ingrese un nombre"
            editTextNombre.requestFocus()
            return false
        }

        if (dni.isEmpty()) {
            editTextDni.error = "Ingrese un apellido"
            editTextDni.requestFocus()
            return false
        }

        if (telefono.isEmpty()) {
            editTextTelefono.error = "Seleccione una fecha"
            editTextTelefono.requestFocus()
            return false
        }

        if (email.isEmpty()) {
            editTextEmail.error = "Seleccione una fecha"
            editTextEmail.requestFocus()
            return false
        }

        if (fechaIni.isEmpty()) {
            editTextFechaIni.error = "Seleccione una hora de inicio"
            editTextFechaIni.requestFocus()
            return false
        }

        if (fechaFin.isEmpty()) {
            editTextFechaFin.error = "Seleccione una hora de fin"
            editTextFechaFin.requestFocus()
            return false
        }

        if (numPersonas.isEmpty()) {
            editTextNumPersonas.error = "Ingrese el número de personas"
            editTextNumPersonas.requestFocus()
            return false
        }

        if (comentario.isEmpty()) {
            editTextComentario.error = "Ingrese el número de sala"
            editTextComentario.requestFocus()
            return false
        }

        // Validación de la fecha de inicio no antes que hoy
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+1"))
        val currentDate = calendar.time
        val selectedFechaIni = parseDate(fechaIni)
        if (selectedFechaIni != null && selectedFechaIni.before(currentDate)) {
            editTextFechaIni.error = "La fecha de inicio no puede ser antes que hoy"
            editTextFechaIni.requestFocus()
            return false
        }

        val selectedFechaFin = parseDate(fechaFin)
        if (selectedFechaFin != null && selectedFechaFin.after(selectedFechaIni)) {
            editTextFechaFin.error = "La fecha de inicio no puede ser antes que hoy"
            editTextFechaFin.requestFocus()
            return false
        }

        return true
    }

    private fun parseDate(dateString: String): Date? {
        val format = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
        format.timeZone = TimeZone.getTimeZone("GMT+1")
        return try {
            format.parse(dateString)
        } catch (e: ParseException) {
            null
        }
    }

    private suspend fun postBooking(request: ReservaRequest, context: Context) : Boolean{
        val service = ApiService(context)
        return service.postReserva(request)
    }
}