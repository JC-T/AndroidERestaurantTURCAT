package fr.isen.turcat.androiderestaurantturcat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import fr.isen.turcat.androiderestaurantturcat.databinding.FragmentPhotoBinding


class PhotoFragment : Fragment() {
    private lateinit var binding: FragmentPhotoBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = arguments?.getString(URL)
        url?.let { if (url.isNotEmpty()) Picasso.get().load(url).into(binding.photo) }
    }

    companion object{
        fun newinstance(url: String)=PhotoFragment().apply { arguments=Bundle().apply{putString(URL,url)} }
        const val URL = "URL"
    }
}