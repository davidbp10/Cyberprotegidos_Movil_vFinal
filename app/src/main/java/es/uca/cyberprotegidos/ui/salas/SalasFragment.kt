package es.uca.cyberprotegidos.ui.salas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import es.uca.cyberprotegidos.databinding.FragmentSalasBinding

class SalasFragment : Fragment() {

    private var _binding: FragmentSalasBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(SalasViewModel::class.java)

        _binding = FragmentSalasBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Envuelve el contenido en un ScrollView
        val scrollView = ScrollView(requireContext())
        scrollView.addView(root)

        return scrollView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
