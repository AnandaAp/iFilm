package com.anlian.ifilm

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.anlian.ifilm.api.RetrofitConnection
import com.anlian.ifilm.databinding.FragmentRegisterPageBinding
import com.anlian.ifilm.model.DefaultResponse
import com.anlian.ifilm.model.UploadImageResponse
import com.bumptech.glide.Glide
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterPage : Fragment(),EasyPermissions.PermissionCallbacks {
    private lateinit var binding: FragmentRegisterPageBinding
    private lateinit var path: String
    private val RC_DIRECTORY = 313

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = bindView()
        choosePicture()
        moveToLogin()
        return view
    }

    private fun moveToLogin() {
       binding.goToSignInBtn.setOnClickListener{
           findNavController().navigate(R.id.action_registerPage_to_loginPage)
       }
    }

    private fun bindView(): View {
        binding = FragmentRegisterPageBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun hasDirectoryPermission():Boolean {
        return EasyPermissions
            .hasPermissions(
                requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
    }

    private fun choosePicture(){
        binding.photoProfileImgBtn.setOnClickListener{
            if(hasDirectoryPermission()) {
                val openGalleryIntent = Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                openGalleryIntent.type = "image/*"
                startActivityForResult(openGalleryIntent,RC_DIRECTORY)
            }
            else{
                EasyPermissions
                    .requestPermissions(
                        requireActivity(),
                        getString(R.string.directory_permissions),
                        RC_DIRECTORY,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions
            .onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults,
                this
            )
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Log.d(
            "Permission",
            "onPermissionsGranted: $requestCode : ${perms.size}"
        )
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Log.d(
            "Permission",
            "onPermissionsDenied: $requestCode : ${perms.size}"
        )

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms))
        {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_DIRECTORY)
        {
            data?.data?.let { uri ->
                Glide
                    .with(requireActivity())
                    .load(uri)
                    .into(binding.photoProfileImgBtn)
                registerProccess(uri)
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun uploadPicture(uri: Uri, fullname: String, email: String, pass: String) {
        val stream = requireActivity().contentResolver.openInputStream(uri) ?: return
        val request = RequestBody.create("image/*".toMediaTypeOrNull(), stream.readBytes()) // read all bytes using kotlin extension
        val filePart = MultipartBody.Part.createFormData(
            "file",
            "$fullname.jpg",
            request
        )
        RetrofitConnection
            .getService()
            .uploadFotoPengguna(filePart)
            .enqueue(object : Callback<UploadImageResponse>{
                override fun onResponse(
                    call: Call<UploadImageResponse>,
                    response: Response<UploadImageResponse>
                ) {
                    if (response.isSuccessful){
                        path = response.body()?.picturePath.toString()
                        uploadUserData(fullname,email,pass,path)
//                                Toast.makeText(
//                                    requireActivity(),
//                                    "Sign Up Sukses",
//                                    Toast.LENGTH_LONG
//                                ).show()
//                                findNavController().navigate(R.id.action_registerPage_to_loginPage)
                    }else{
                        Toast.makeText(
                            requireActivity(),
                            "Reponse Gagal",
                            Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<UploadImageResponse>, t: Throwable) {
                    Log.e("Upload File","Upload gagal")
                }

            })
    }

    private fun uploadUserData(
        fullname: String,
        email: String,
        pass: String,
        picturePath: String?
    ) {
        println("picture path : $picturePath")
        val function = "insert_pengguna"
        if (picturePath != null) {
            RetrofitConnection
                .getService()
                .insertPengguna(
                    fullname,
                    email,
                    pass,
                    picturePath,
                    function
                )
                .enqueue(object : Callback<DefaultResponse>{
                    override fun onResponse(
                        call: Call<DefaultResponse>,
                        response: Response<DefaultResponse>
                    ) {
                        if (response.isSuccessful){
                            Toast.makeText(
                                requireActivity(),
                                "Sign Up Sukses",
                                Toast.LENGTH_LONG
                            ).show()
                            findNavController().navigate(R.id.action_registerPage_to_loginPage)
                        }else{
                            Toast.makeText(
                                requireActivity(),
                                "Sign Up Gagal",
                                Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(
                        call: Call<DefaultResponse>,
                        t: Throwable
                    ) {
                        Log.d("Upload User Data","Upload User Data Failed")
                    }
                })
        }
    }


    private fun registerProccess(uri: Uri) {
        binding.signUpBtnConfirm.setOnClickListener{
            val email = binding.registerEmailField.text.toString().trim()
            val pass = binding.registerPasswordField.text.toString().trim()
            val fullname = binding.registerFullnameField.text.toString().trim()
            uploadPicture(uri,fullname,email,pass)
        }
    }
}