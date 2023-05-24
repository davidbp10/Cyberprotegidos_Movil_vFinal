package es.uca.cyberprotegidos.ui.reservas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReservasViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Reservas Fragment"
    }
    val text: LiveData<String> = _text
}