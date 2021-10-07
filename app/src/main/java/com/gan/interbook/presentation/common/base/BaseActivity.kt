package com.gan.interbook.presentation.common.base

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.gan.interbook.framework.auth.AuthManager
import com.gan.interbook.presentation.auth.AuthActivity
import com.gan.interbook.presentation.common.ErrorConsumer
import com.gan.interbook.presentation.common.ktx.createErrorDialog
import net.openid.appauth.AuthorizationException
import net.openid.appauth.EndSessionResponse
import timber.log.Timber
import javax.inject.Inject

abstract class BaseActivity<BINDING : ViewBinding> : AppCompatActivity(), ErrorConsumer {
    protected lateinit var binding: BINDING

    @Inject
    lateinit var
            authManager: AuthManager

    abstract fun inflateBinding(): BINDING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateBinding()
        setContentView(binding.root)
    }

    protected fun startLogoutIntent() {
        Timber.d("Logout")

        val logoutIntent = authManager.getEndSessionRequestIntent()
        logoutLauncher.launch(logoutIntent)
    }

    private val logoutLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val resp = EndSessionResponse.fromIntent(it.data!!)
            val ex = AuthorizationException.fromIntent(it.data)

            if (ex == null) {
                authManager.nullifyAuthState()

                //todo move it elsewhere
                startActivity(
                    Intent(this, AuthActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                )
            } else {
                ex.message?.let { errorMessage ->
                    displayErrorDialog(errorMessage)
                }
            }
        }

    override fun displayErrorDialog(message: String) {
        createErrorDialog(message)
            .show()
    }
}