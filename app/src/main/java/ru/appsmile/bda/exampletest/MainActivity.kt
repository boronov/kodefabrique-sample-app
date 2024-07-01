package ru.appsmile.bda.exampletest

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.appsmile.bda.exampletest.ui.theme.ExampleTestTheme


class MainActivity : ComponentActivity() {

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            handleIntentActivityResult(it)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExampleTestTheme {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(onClick = {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.setData(Uri.parse("example://demo.app"))
                        intent.putExtra(ACTION_EXTRA_KEY, ACTION_LOGIN_EXTRA_VALUE)
                        intent.putExtra(ACTION_LOGIN_EXTRA_KEY, "9999") // TODO: test data
                        intent.putExtra(ACTION_PASSWORD_EXTRA_KEY, "8888") // TODO: test data

                        resultLauncher.launch(intent)

                    }) { Text("OPEN LOGIN") }
                }
            }
        }
    }

    private fun handleIntentActivityResult(result: ActivityResult) {
        if (result.resultCode == RESULT_OK) {
            Toast.makeText(applicationContext, getString(R.string.all_right_text), Toast.LENGTH_SHORT).show()
        } else if (result.resultCode == RESULT_CANCELED) {
            val message = result.data?.getStringExtra(ACTION_MESSAGE_EXTRA_KEY)
            val code = result.data?.getStringExtra(ACTION_CODE_EXTRA_KEY)

            Toast.makeText(applicationContext, "$message ($code)", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val ACTION_EXTRA_KEY = "action"
        private const val ACTION_LOGIN_EXTRA_VALUE = "login"

        private const val ACTION_LOGIN_EXTRA_KEY = "login"
        private const val ACTION_PASSWORD_EXTRA_KEY = "password"
        
        private const val ACTION_MESSAGE_EXTRA_KEY = "message"
        private const val ACTION_CODE_EXTRA_KEY = "code"
    }
}