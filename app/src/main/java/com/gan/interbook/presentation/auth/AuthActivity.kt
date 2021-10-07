package com.gan.interbook.presentation.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.navigation.findNavController
import com.gan.interbook.R
import com.gan.interbook.databinding.ActivityAuthBinding
import com.gan.interbook.presentation.common.base.BaseActivity
import com.gan.interbook.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import timber.log.Timber

@AndroidEntryPoint
open class AuthActivity : BaseActivity<ActivityAuthBinding>(), Authenticator, FreeCountryChecker {
    private val viewModel: AuthViewModel by viewModels()
    private var handledAuthorization: Boolean = false

    override fun inflateBinding(): ActivityAuthBinding =
        ActivityAuthBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)

        viewModel.mustWarnUser.observe(this, {
            val navController = findNavController(R.id.auth_fragments_container)

            when (navController.currentDestination?.id) {
                R.id.splashScreen ->
                    navController.navigate(
                        R.id.action_splashScreen_to_addServiceFragment
                    )
                R.id.introFragment ->
                    navController.navigate(
                        R.id.action_introFragment_to_addServiceFragment
                    )
            }
        })

        viewModel.userHasRights.observe(this, {
            proceedWithWarning()
        })

        viewModel.errorEvent.observe(this, {
            displayErrorDialog(it)
        })

        binding.viewModel = viewModel
    }

    override fun onResume() {
        super.onResume()

        CoroutineScope(Dispatchers.Main).launch {
            if (!handledAuthorization) {
                delay(SPLASH_DELAY_MILLISECONDS)
                ensureAuthorized()
            } else {
                handledAuthorization = false
            }
        }
    }

    override fun register() {
        startAuthIntent(true)
    }

    override fun authorize() {
        startAuthIntent()
    }

    override fun proceedWithWarning() {
        startActivity(
            Intent(this, MainActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        )
    }

    private fun ensureAuthorized() {
        Timber.d("calling ensure authorized")

        authManager.ensureAuthorised(
            {
                Timber.d("ensure authorized - success")

                viewModel.getBooks()
            },
            {
                Timber.d("ensure authorized - fail")

                navigateToIntro()
            }
        )
    }

    private fun navigateToIntro() {
        findNavController(R.id.auth_fragments_container)
            .navigate(R.id.action_global_to_introFragment)
    }

    private fun startAuthIntent(register: Boolean = false) {
        Timber.d("Authorize - with registerL$register")

        val authIntent = authManager.getAuthorizationRequestIntent(register)
        authLauncher.launch(authIntent)
    }

    private val authLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            val resp = AuthorizationResponse.fromIntent(result.data!!)
            val ex = AuthorizationException.fromIntent(result.data)

            handledAuthorization = true

            authManager.handleAuthorizationResponse(resp, ex,
                {
                    ensureAuthorized()
                },
                {
                    displayErrorDialog("Unable to authorize")
                }
            )
        }

    companion object {
        private const val SPLASH_DELAY_MILLISECONDS = 1000L
    }
}