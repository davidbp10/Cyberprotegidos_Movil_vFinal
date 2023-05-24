package es.uca.cyberprotegidos.ui.localizacion


import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import es.uca.cyberprotegidos.databinding.FragmentLocalizacionBinding


class LocalizacionFragment : Fragment() {

    private var _binding: FragmentLocalizacionBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocalizacionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView = binding.myTextView
        textView.movementMethod = LinkMovementMethod.getInstance()
        textView.text = "       - Enlace 1: Consorcio de transportes: https://siu.cmtbc.es/es/lineas.php"


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
