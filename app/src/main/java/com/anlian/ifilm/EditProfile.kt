package com.anlian.ifilm

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.anlian.ifilm.architecture.EditProfileView
import com.anlian.ifilm.databinding.FragmentEditProfileBinding
import kotlinx.android.synthetic.main.fragment_edit_profile.*


class EditProfile : Fragment(), EditProfileView {

    private lateinit var binding: FragmentEditProfileBinding
    private val args: EditProfileArgs by navArgs()
    private var id = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding()
        id = args.id
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saveNewProfile()
    }

    override fun binding() {
        binding = FragmentEditProfileBinding.inflate(layoutInflater)
    }

    @SuppressLint("HardwareIds")
    override fun saveNewProfile() {
        addHadwareBtn.setOnClickListener {
            val hardwareID = Settings
                .Secure
                .getString(requireActivity()
                    .contentResolver,
                    Settings
                        .Secure
                        .ANDROID_ID)
            hardwareBoxField.editText?.hint = hardwareID
        }
    }

}