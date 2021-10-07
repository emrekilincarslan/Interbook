package com.gan.interbook.presentation.main

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.gan.interbook.R
import com.gan.interbook.databinding.ActivityMainBinding
import com.gan.interbook.presentation.common.PhoneDialer
import com.gan.interbook.presentation.common.SessionFinisher
import com.gan.interbook.presentation.common.base.BaseActivity
import com.gan.interbook.presentation.common.ktx.createChoiceDialog
import com.gan.interbook.presentation.common.ktx.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnShowRationale
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.RuntimePermissions

@AndroidEntryPoint
@RuntimePermissions
class MainActivity : BaseActivity<ActivityMainBinding>(), SessionFinisher, PhoneDialer {
    private val viewModel: MainViewModel by viewModels()
    private var currentNavController: LiveData<NavController>? = null

    override fun inflateBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)

        setSupportActionBar(binding.toolBar)

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
        binding.viewModel = viewModel
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        val bottomNavigationView = binding.bottomNavigation

        val navGraphIds = listOf(
            R.navigation.home_graph,
            R.navigation.book_graph,
            R.navigation.special_graph,
            R.navigation.more_graph
        )

        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.main_fragments_container,
            intent = intent
        )

        controller.observe(this, { navController ->
            setupActionBarWithNavController(navController)
        })
        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    override fun endSession() {
        startLogoutIntent()
    }

    override fun dial(phoneNumber: String) {
        dialPhoneWithPermissionCheck(phoneNumber)
    }

    @NeedsPermission(Manifest.permission.CALL_PHONE)
    internal fun dialPhone(phoneNumber: String) {
        startActivity(
            Intent(
                Intent.ACTION_CALL,
                Uri.parse("tel:$phoneNumber")
            )
        )
    }

    @OnShowRationale(Manifest.permission.CALL_PHONE)
    internal fun onShowRationale(permissionRequest: PermissionRequest) {
        createChoiceDialog(
            titleId = R.string.permission_rationale_title,
            message = getString(
                R.string.call_permission_rationale,
                getString(R.string.app_name)
            ),
            positiveCallback = { permissionRequest.proceed() },
            negativeCallback = { permissionRequest.cancel() })
            .show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }
}
