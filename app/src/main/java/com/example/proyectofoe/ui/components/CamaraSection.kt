package com.example.proyectofoe.ui.components

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.example.proyectofoe.ui.theme.FoePrimary
import com.example.proyectofoe.ui.theme.ModuloVerde
import java.io.File

@Composable
fun CamaraSection(
    fotoUri: String?,
    onFotoCapturada: (String) -> Unit
) {
    val context = LocalContext.current

    var uriPendiente by remember {
        mutableStateOf<Uri?>(null)
    }

    val camara = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { guardada ->
        if (guardada) {
            uriPendiente?.let {
                onFotoCapturada(it.toString())
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = {
                val archivo = File(
                    context.filesDir,
                    "foto_${System.currentTimeMillis()}.jpg"
                )

                val uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.fileprovider",
                    archivo
                )

                uriPendiente = uri
                camara.launch(uri)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (fotoUri != null) ModuloVerde else FoePrimary
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (fotoUri != null) "Repetir foto de fachada" else "Tomar foto de la fachada"
            )
        }

        fotoUri?.let { textoUri ->
            val uri = remember(textoUri) {
                Uri.parse(textoUri)
            }

            val bitmap = remember(uri) {
                try {
                    context.contentResolver.openInputStream(uri)?.use {
                        BitmapFactory.decodeStream(it)
                    }
                } catch (e: Exception) {
                    null
                }
            }

            bitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Fotografía de la fachada",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}
