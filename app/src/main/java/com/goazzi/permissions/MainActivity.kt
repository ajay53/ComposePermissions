package com.goazzi.permissions

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.goazzi.permissions.ui.theme.PermissionsTheme
import com.goazzi.permissions.util.d
import com.goazzi.permissions.util.hasLocationPermission
import com.goazzi.permissions.util.isGpsEnabled

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PermissionsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    BaseScreen(modifier = Modifier.padding(innerPadding))
                    /*Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )*/
                }
            }
        }
    }
}

@Composable
fun BaseScreen(modifier: Modifier = Modifier) {

    val context = LocalContext.current
    Column(modifier = modifier.fillMaxSize()) {

        // this variable will be set or initialised to true
        // as all mandatory permission are granted at runtime or at launch respectively.
        var isPermissionGranted by remember {
            mutableStateOf(
                value = hasLocationPermission(context = context) && isGpsEnabled(
                    context = context
                )
            )
        }

        //Set the shouldShowDialog for adding/removing dialog from composition (common practice in Compose)
        /*var shouldShowDialog by remember {
            mutableStateOf(true)
        }*/

        if (!isPermissionGranted) {
            PermissionDialogStateful(
                //            permissionEnum = PermissionEnum.LOCATION,
//                shouldShowDialog = shouldShowDialog,
                onPermissionGranted = {
                    if (isGpsEnabled(context) && hasLocationPermission(context)) {
                        TAG.d(message = "All perm Granted")
//                        shouldShowDialog = false
                        isPermissionGranted = true
                    }
                })
        } else {
            Greeting(
                name = "Android",
//                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PermissionsTheme {
        Greeting("Android")
    }
}