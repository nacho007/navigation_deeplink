package com.test.androiddevelopersexample.ui.fragments.jumio

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.jumio.defaultui.JumioActivity
import com.jumio.sdk.JumioSDK
import com.jumio.sdk.enums.JumioDataCenter
import com.jumio.sdk.result.JumioFaceResult
import com.jumio.sdk.result.JumioIDResult
import com.jumio.sdk.result.JumioResult
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.MyActivityJumioBinding


/**
 * Created by ignaciodeandreisdenis on 12/10/21.
 */
class MyJumioActivity :  AppCompatActivity() {

    private lateinit var binding: MyActivityJumioBinding

    lateinit var jumioToken: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MyActivityJumioBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        jumioToken = getString(R.string.jumio_token)

        if (!isSupportedPlatform()) {
            finishFlow(KYC_RESULT_UNSUPPORTED_PLATFORM)
        }

        if (!checkPermissions()) return

        binding.button2.setOnClickListener {
            startJumio()
        }

        observeViewModel()
    }

    /**
     * Check and request missing permissions for the SDK.
     *
     * @param requestCode the request code for the SDK
     */
    private fun checkPermissions(requestCode: Int = PERMISSION_REQUEST_CODE) =
        if (!JumioSDK.hasAllRequiredPermissions(this)) { //Acquire missing permissions.
            val mp = JumioSDK.getMissingPermissions(this)
            ActivityCompat.requestPermissions(
                this,
                mp,
                requestCode
            )
            false
        } else {
            true
        }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults.isEmpty()
            && grantResults[0] != PackageManager.PERMISSION_GRANTED
        ) {
            finishFlow(KYC_PERMISSIONS_DENIED)
        }
    }

    private fun isSupportedPlatform() = JumioSDK.isSupportedPlatform(this)

    private fun observeViewModel() {

    }

    private fun finishFlow(result: Int, intent: Intent = Intent()) {
        setResult(result, intent)
        finish()
    }

    private fun startJumio() {
        val intent = Intent(this, JumioActivity::class.java)
        val dataCenter: String = JumioDataCenter.US.toString()
        intent.putExtra(JumioActivity.EXTRA_TOKEN, jumioToken)
        intent.putExtra(JumioActivity.EXTRA_DATACENTER, dataCenter)
//        intent.putExtra(JumioActivity.EXTRA_CUSTOM_THEME, R.style.AppThemeCustomJumio)
        sdkForResultLauncher.launch(intent)
    }

    private val sdkForResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val jumioResult: JumioResult? =
                result.data?.getSerializableExtra(JumioActivity.EXTRA_RESULT) as JumioResult?

            if (jumioResult?.isSuccess == true) {
                jumioResult.credentialInfos?.forEach {
                    when (jumioResult.getResult(it)) {
                        is JumioIDResult -> {}
                        is JumioFaceResult -> {

                        }
                    }
                }
            } else {
                jumioResult?.error?.let {
                    finishFlow(KYC_RESULT_CANCELED)
                }
            }
        }

    companion object {
        private const val PERMISSION_REQUEST_CODE: Int = 303
        const val KYC_RESULT = "KYC_RESULT"
        const val KYC_RESULT_CANCELED = 303
        const val KYC_RESULT_UNSUPPORTED_PLATFORM = 304
        const val KYC_RESULT_ERROR = 305
        const val KYC_UPDATE_RESULT_ERROR = 306
        const val KYC_PERMISSIONS_DENIED = 307
        private const val JUMIO = "JUMIO"
    }
}