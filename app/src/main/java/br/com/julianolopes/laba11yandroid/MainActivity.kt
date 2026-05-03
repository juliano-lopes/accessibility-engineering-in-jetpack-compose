@file:Suppress("SpellCheckingInspection")

package br.com.julianolopes.laba11yandroid

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Spacer(modifier = Modifier.height(40.dp))
                        BarrierLabel()

                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                        BarrierState()

                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))


                        BarrierRole()
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        BarrierRelatedContent()
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                        BarrierGestures()
                        Spacer(modifier = Modifier.height(40.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun BarrierLabel() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Barrier 1: Element Labels",
            style = MaterialTheme.typography.headlineSmall,
        )
        Text("The label before (Failure):", modifier = Modifier.padding(top = 8.dp))
        IconButton(onClick = { }) {
            Icon(Icons.Default.Notifications, contentDescription = null)
        }

        Text("The label after (Success):", modifier = Modifier.padding(top = 8.dp))
        IconButton(onClick = { }) {
            Icon(Icons.Default.Notifications, contentDescription = "Notifications")
        }
    }
}

@Composable
fun BarrierRelatedContent() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Barrier 4: Lack of Grouping of Related Elements",
            style = MaterialTheme.typography.headlineSmall,
        )
        Text("The before without grouping (Failure):")
        Column(modifier = Modifier.padding(top = 8.dp)) {
            Text("Price of product A:")
            Text("R$55,00")
        }
        Spacer(modifier = Modifier.height(20.dp))

        Text("The after grouped (Success):")
        Column(
            modifier = Modifier
                .padding(top = 16.dp)
                .semantics(mergeDescendants = true) {}
        ) {
            Text("Price of product B")
            Text("R$65,00")
        }
    }
}


@Composable
fun BarrierRole() {
    val estaAtivoBarreira by remember { mutableStateOf(false) }
    var estaAtivo by remember { mutableStateOf(false) }

    val corIconeBarreira =
        if (estaAtivoBarreira) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
    val iconeStatusBarreira =
        if (estaAtivoBarreira) Icons.Default.Favorite else Icons.Default.FavoriteBorder

    val corIcone =
        if (estaAtivo) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
    val iconeStatus = if (estaAtivo) Icons.Default.Favorite else Icons.Default.FavoriteBorder

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Barrier 3: Elements without Specified role",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.semantics { heading() } // the element role is defined as heading
        )

        Text("The Before with no role (Failure):")

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .toggleable(
                    value = estaAtivo,
                    onValueChange = { estaAtivo = it }
                )
                .semantics {
                    stateDescription = if (estaAtivo) "Favorite item" else "Unfavorite item"
                }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = if (estaAtivo) "Unfavorite" else "Favorite"
            )
            Spacer(Modifier.weight(1f))
            Icon(
                imageVector = iconeStatusBarreira,
                contentDescription = null,
                tint = corIconeBarreira
            )
        }


        Spacer(modifier = Modifier.height(16.dp))

        Text("THE AFTER with a role (Success):")

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .toggleable(
                    value = estaAtivo,
                    onValueChange = { estaAtivo = it },
                    role = Role.Button // the element role is defined as button
                )
                .semantics {
                    stateDescription = if (estaAtivo) "Favorite item" else "Unfavorite item"
                }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = if (estaAtivo) "Unfavorite" else "Favorite"
            )
            Spacer(Modifier.weight(1f))
            Icon(imageVector = iconeStatus, contentDescription = null, tint = corIcone)
        }

    }
}

@Composable
fun BarrierState() {
    var estaAtivoBarreira by remember { mutableStateOf(false) }
    var estaAtivo by remember { mutableStateOf(false) }

    val corIconeBarreira =
        if (estaAtivoBarreira) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
    val iconeStatusBarreira =
        if (estaAtivoBarreira) Icons.Default.Favorite else Icons.Default.FavoriteBorder

    val corIcone =
        if (estaAtivo) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
    val iconeStatus = if (estaAtivo) Icons.Default.Favorite else Icons.Default.FavoriteBorder

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Barrier 2: Lack of State and its Description",
            style = MaterialTheme.typography.headlineSmall,
        )
        Text("The state before (Failed):", modifier = Modifier.padding(top = 16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    estaAtivoBarreira = !estaAtivoBarreira
                } //  clickable doesn't manage native state
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Favorite item")
            Spacer(Modifier.weight(1f))
            Icon(
                imageVector = iconeStatusBarreira,
                contentDescription = null, // icon does not describe the change
                tint = corIconeBarreira
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("THE AFTER (Success):")

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .toggleable(
                    // toggleable maps the 'checked' state in XML
                    value = estaAtivo,
                    onValueChange = { estaAtivo = it },
                )
                .semantics {
                    // stateDescription informs TalkBack and populates the accessibility node
                    stateDescription = if (estaAtivo) "Favorite item" else "Unfavorite item"
                }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = if (estaAtivo) "Unfavorite" else "Favorite"
            )
            Spacer(Modifier.weight(1f))
            Icon(imageVector = iconeStatus, contentDescription = null, tint = corIcone)
        }
    }
}

@Composable
fun BarrierGestures() {
    var favoritado by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Barrier 5: Specific gestures and Grouping that Prevents Interaction",
            style = MaterialTheme.typography.headlineSmall,
        )
        Text("The before, fragmented (Failure):", modifier = Modifier.padding(top = 16.dp))
        Card(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Product A", modifier = Modifier.weight(1f))

                // separated buttons: generate excessive focus stops
                IconButton(onClick = { favoritado = !favoritado }) {
                    Icon(
                        if (favoritado) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = if (favoritado) "Unfavorite" else "Favorite"
                    )

                }
                IconButton(onClick = { /* Configurar */ }) {
                    Icon(Icons.Default.Settings, contentDescription = "Configure product")
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Grouping that prevents individual interaction (Failure):",
            color = Color(0xFF2E7D32)
        )

        val contexto = LocalContext.current
        var offsetX by remember { mutableStateOf(0f) }
        var eFavorito by remember { mutableStateOf(false) }

        Box(modifier = Modifier
            .fillMaxWidth()
            .background(Color.Red)) {
            Icon(
                Icons.Default.Delete,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(16.dp)
            )

            Row(
                modifier = Modifier
                    .offset { IntOffset(offsetX.roundToInt(), 0) }
                    .fillMaxWidth()
                    .background(Color.White)
                    // DRAG GESTURE (Accessibility Barrier 1)
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures(
                            onDragEnd = {
                                if (offsetX < -300f) {
                                    Toast.makeText(contexto, "Deleted Item", Toast.LENGTH_SHORT)
                                        .show()
                                }
                                offsetX = 0f
                            },
                            onHorizontalDrag = { change, dragAmount ->
                                change.consume()
                                offsetX += dragAmount
                            }
                        )
                    }
                    .clickable {
                                Toast.makeText(contexto, "Product Details", Toast.LENGTH_SHORT)
                                    .show()

                    }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Smartphone X", modifier = Modifier.weight(1f))
                Icon(
                    imageVector = if (eFavorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (eFavorito) Color.Red else Color.Gray,
                    modifier = Modifier
                        .size(40.dp)
                        .pointerInput(Unit) {
                            detectTapGestures {
                                eFavorito = !eFavorito
                                Toast.makeText(contexto, "Favorite changed", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                )

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Configure",
                    modifier = Modifier
                        .size(40.dp)
                        .pointerInput(Unit) {
                            detectTapGestures {
                                Toast.makeText(contexto, "Settings", Toast.LENGTH_SHORT).show()
                            }
                        }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "The after with personalized actions (Success):",
            color = Color(0xFF2E7D32)
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .semantics(mergeDescendants = true) {

                        // This maps the button functions to the custom actions
                        customActions = listOf(
                            CustomAccessibilityAction(if (favoritado) "Unfavorite" else "Favorite") {
                                favoritado = !favoritado
                                true
                            },
                            CustomAccessibilityAction("Configure produto") { true }
                        )
                    },

                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Product B (accessible)", modifier = Modifier.weight(1f))

                // The buttons are still here for users who do not use assistive technology
                IconButton(
                    onClick = { favoritado = !favoritado },
                    // cleans the semantics of the button because the functionality is as a custom action in the context menu, so Talkback does not need to focus on that button individually.
                    modifier = Modifier.clearAndSetSemantics { }
                ) {
                    // the icon also doesn't need contentDescription:
                    Icon(
                        if (favoritado) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        null
                    )
                }
                IconButton(
                    onClick = { /* Configurar */ },
                    modifier = Modifier.clearAndSetSemantics { }
                ) {
                    Icon(Icons.Default.Settings, null)
                }
            }
        }
    }
}