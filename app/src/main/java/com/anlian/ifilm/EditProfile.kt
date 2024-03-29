package com.anlian.ifilm

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.anlian.ifilm.architecture.EditProfilePresenter
import com.anlian.ifilm.architecture.EditProfileView
import com.anlian.ifilm.databinding.FragmentEditProfileBinding
import kotlinx.android.synthetic.main.fragment_edit_profile.*

const val function = "addHardwareID"
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
        Log.d(TAG, "id: $id")
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
            EditProfilePresenter(this).senData(id,hardwareID,function)
        }
    }

    override fun onStart() {
        checkHardwareID()
        super.onStart()
    }

    override fun checkHardwareID() {
        val hardwareID = args.hardwareID
        Log.d(TAG, "checkHardwareID: $hardwareID")
        if(hardwareID.isNotBlank()) {
            hardwareField.hint = hardwareID
        }
    }

    override fun onUpdateSuccess(result: String) {
        Toast
            .makeText(requireActivity(),
                result,
                Toast.LENGTH_SHORT)
            .show()
    }

    override fun onUpdateFailure(message: String) {
        Toast
            .makeText(requireActivity(),
                message,
                Toast.LENGTH_SHORT)
            .show()
    }
}