package com.example.cryptocurrency.presentation.screens
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.FragmentBiometricBinding
import com.example.cryptocurrency.utils.navigateTo
import com.example.cryptocurrency.utils.popBack
import com.example.cryptocurrency.utils.setTextColor
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.Executor

@AndroidEntryPoint
class BiometricFragment : Fragment() {
    private lateinit var binding: FragmentBiometricBinding
    private lateinit var biometricManager: BiometricManager
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var biometricPromptInfo: BiometricPrompt.PromptInfo
    private lateinit var handler: Handler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_biometric, container, false)
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handler = Handler(Looper.getMainLooper())

        checkBiometric()

        executor = ContextCompat.getMainExecutor(requireContext())
        val promptCallback = object : BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Log.d("biometric","Authentication Error $errString")
            }

            @SuppressLint("SetTextI18n")
            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                binding.apply {
                    fingerprint.isVisible = false
                    failBiometric.isVisible = true
                    biometricMsg.apply {
                        this.text = "Failed!"
                        setTextColor(requireContext(),R.color.hot_chili)
                    }
                }
                Log.d("biometric","Authentication Failed")
            }

            @SuppressLint("SetTextI18n")
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                binding.apply {
                    successBiometric.isVisible = true
                    fingerprint.isVisible = false
                    failBiometric.isVisible = false
                    biometricMsg.apply {
                        this.text = "Failed!"
                        setTextColor(requireContext(),R.color.green)
                    }
                    handler.postDelayed({
                        popBack()
                        navigateTo(R.id.nav_home)
                    },2000)
                }
                Log.d("biometric","Authentication Success $result")
            }
        }
        biometricPrompt = BiometricPrompt(this,executor,promptCallback)
        biometricPromptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("login w/ fingerprint")
            .setSubtitle("pls touch the sensor!")
            .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
            .build()

        binding.login.setOnClickListener {
            biometricPrompt.authenticate(biometricPromptInfo)
        }

    }

    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("SetTextI18n")
    private fun checkBiometric() {
        biometricManager = BiometricManager.from(requireContext())
        when(biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)){
            BiometricManager.BIOMETRIC_SUCCESS -> {
                binding.apply {
                    login.isEnabled = true
                    biometricMsg.text = "You can authenticate using Fingerprint"
                }
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                binding.apply {
                    login.isEnabled = false
                    biometricMsg.text = "Biometric feature is currently unavailable"
                }
                Handler(Looper.getMainLooper()).postDelayed({
                    navigateTo(R.id.nav_home)
                },3000)
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                val intent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                    putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED, BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
                }
                binding.login.isEnabled = false
                startActivityForResult(intent,100)
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                Log.d("APP_TAG","Unavailable")
            }
            BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
                Log.d("APP_TAG","Required Security Update")
            }
            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
                Log.d("APP_TAG","Unsupported")
            }
            BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
                Log.d("APP_TAG","Unknown")
            }
        }
    }

}