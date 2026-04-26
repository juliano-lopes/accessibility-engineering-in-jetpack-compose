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
                    // Adicionamos scroll para que os experimentos não sumam da tela
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Spacer(modifier = Modifier.height(40.dp)) // Dá espaço para a barra do sistema
                        ExperimentoBarreira1()

                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                        ExperimentoBarreiraEstado()

                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))


                        ExperimentoBarreiraPapel()
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        ExperimentoAgrupamento()
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                        ExperimentoAcoesPersonalizadas()
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        //ExperimentoLiveRegion()
                        Spacer(modifier = Modifier.height(40.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ExperimentoBarreira1() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Barreira 1: Rótulos de elementos",
            style = MaterialTheme.typography.headlineSmall,
            //modifier = Modifier.semantics { heading() }
        )
        Text("O rótulo antes (Falha):", modifier = Modifier.padding(top = 8.dp))
        IconButton(onClick = { }) {
            Icon(Icons.Default.Notifications, contentDescription = null)
        }

        Text("O rótulo depois (Sucesso):", modifier = Modifier.padding(top = 8.dp))
        IconButton(onClick = { }) {
            Icon(Icons.Default.Notifications, contentDescription = "Notificações")
        }
    }
}

@Composable
fun ExperimentoAgrupamento() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Barreira 4: Falta de Agrupamento de Elementos Relacionados",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.semantics { heading() }
        )
        // Fragmentada
        Text("O antes sem agrupamento (Falha):")
        Column(modifier = Modifier.padding(top = 8.dp)) {
            Text("Preço do produto A:")
            Text("R$55,00")
        }
        // Agrupada
        Spacer(modifier = Modifier.height(20.dp))

        Text("O depois agrupado (Sucesso):")
        Column(
            modifier = Modifier
                .padding(top = 16.dp)
                .semantics(mergeDescendants = true) {}
        ) {
            Text("Preço do produto B")
            Text("R$65,00")
        }
    }
}


@Composable
fun ExperimentoBarreiraPapel() {
    val estaAtivoBarreira by remember { mutableStateOf(false) }
    var estaAtivo by remember { mutableStateOf(false) }

    // Definindo as cores para garantir que o visual seja idêntico nos dois
    val corIconeBarreira =
        if (estaAtivoBarreira) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
    val iconeStatusBarreira =
        if (estaAtivoBarreira) Icons.Default.Favorite else Icons.Default.FavoriteBorder

    val corIcone =
        if (estaAtivo) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
    val iconeStatus = if (estaAtivo) Icons.Default.Favorite else Icons.Default.FavoriteBorder

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Barreira 3: Elementos sem Função Especificada",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.semantics { heading() }
        )

        // --- CENÁRIO 1: O ANTES (Falha sem função) ---

        Text("O antes sem papel (Falha):")

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .toggleable(
                    value = estaAtivo,
                    onValueChange = { estaAtivo = it }
                )
                .semantics {
                    stateDescription = if (estaAtivo) "Item favoritado" else "Item não favoritado"
                }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = if (estaAtivo) "Desfavoritar" else "Favoritar"
            )
            Spacer(Modifier.weight(1f))
            Icon(
                imageVector = iconeStatusBarreira,
                contentDescription = null,
                tint = corIconeBarreira
            )
        }


        Spacer(modifier = Modifier.height(16.dp))

        // --- CENÁRIO 2: O DEPOIS (SUCESSO com função) ---
        // Visualmente IGUAL ao anterior, mas com a engenharia correta.
        Text("O DEPOIS com papel (Sucesso):")

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .toggleable(
                    value = estaAtivo,
                    onValueChange = { estaAtivo = it },
                    role = Role.Button // a função do elemento é definida como botão
                )
                .semantics {
                    stateDescription = if (estaAtivo) "Item favoritado" else "Item não favoritado"
                }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = if (estaAtivo) "Desfavoritar" else "Favoritar"
            )
            Spacer(Modifier.weight(1f))
            Icon(imageVector = iconeStatus, contentDescription = null, tint = corIcone)
        }

    }
}

@Composable
fun ExperimentoBarreiraEstado() {
    var estaAtivoBarreira by remember { mutableStateOf(false) }
    var estaAtivo by remember { mutableStateOf(false) }

    // Definindo as cores para garantir que o visual seja idêntico nos dois
    val corIconeBarreira =
        if (estaAtivoBarreira) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
    val iconeStatusBarreira =
        if (estaAtivoBarreira) Icons.Default.Favorite else Icons.Default.FavoriteBorder

    val corIcone =
        if (estaAtivo) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
    val iconeStatus = if (estaAtivo) Icons.Default.Favorite else Icons.Default.FavoriteBorder

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Barreira 2: Omissão e Descrição de Estado (State)",
            style = MaterialTheme.typography.headlineSmall,
            //modifier = Modifier.semantics { heading() }
        )

        // --- CENÁRIO 1: O ANTES (ERRO) ---
        // Visualmente perfeito (troca cor e ícone), mas semânticamente mudo.
        Text("O estado antes (Falha):", modifier = Modifier.padding(top = 16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    estaAtivoBarreira = !estaAtivoBarreira
                } // Erro: clickable não gerencia estado nativo
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Favoritar item")
            Spacer(Modifier.weight(1f))
            Icon(
                imageVector = iconeStatusBarreira,
                contentDescription = null, // Erro: ícone não descreve a mudança
                tint = corIconeBarreira
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- CENÁRIO 2: O DEPOIS (SUCESSO) ---
        // Visualmente IGUAL ao anterior, mas com a engenharia correta.
        Text("O DEPOIS (Sucesso):")

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .toggleable(
                    // Sucesso: toggleable mapeia o estado 'checked' no XML
                    value = estaAtivo,
                    onValueChange = { estaAtivo = it },
                )
                .semantics {
                    // Sucesso: stateDescription informa o TalkBack e popula o nó de acessibilidade
                    stateDescription = if (estaAtivo) "Item favoritado" else "Item não favoritado"
                }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = if (estaAtivo) "Desfavoritar" else "Favoritar"
            )
            Spacer(Modifier.weight(1f))
            Icon(imageVector = iconeStatus, contentDescription = null, tint = corIcone)
        }
    }
}

@Composable
fun ExperimentoAcoesPersonalizadas() {
    var favoritado by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Barreira 5: gestos específicos e Agrupamento que Impede Interação",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.semantics { heading() }
        )

        // --- CENÁRIO 1: O ANTES (DIFICULDADE DE NAVEGAÇÃO) ---
        // Aqui, o TalkBack vai focar em CADA elemento. Imagine uma lista com 20 cards desses.
        // O usuário cego teria que dar 3 toques para passar por um único item.
        Text("O antes fragmentado (Falha):", modifier = Modifier.padding(top = 16.dp))
        Card(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Produto A", modifier = Modifier.weight(1f))

                // Botões soltos: geram excesso de paradas de foco
                IconButton(onClick = { favoritado = !favoritado }) {
                    Icon(
                        if (favoritado) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = if (favoritado) "Desfavoritar" else "Favoritar"
                    )

                }
                IconButton(onClick = { /* Configurar */ }) {
                    Icon(Icons.Default.Settings, contentDescription = "Configurar produto")
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // BARREIRA 5: Agrupamento que impede a interação individual
        Text(
            "Agrupamento que impede a interação individual (Falha):",
            color = Color(0xFF2E7D32)
        )

        val contexto = LocalContext.current
        var offsetX by remember { mutableStateOf(0f) }
        var eFavorito by remember { mutableStateOf(false) }

// Box para o fundo da lixeira
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(Color.Red)) {
            // Ícone de lixeira que fica atrás (opcional para o visual)
            Icon(
                Icons.Default.Delete,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(16.dp)
            )

            // O CARD QUE DESLIZA
            Row(
                modifier = Modifier
                    .offset { IntOffset(offsetX.roundToInt(), 0) } // AQUI ESTAVA O ERRO
                    .fillMaxWidth()
                    .background(Color.White)
                    // GESTO DE ARRASTAR (Barreira de Acessibilidade 1)
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures(
                            onDragEnd = {
                                if (offsetX < -300f) {
                                    Toast.makeText(contexto, "Item Apagado", Toast.LENGTH_SHORT)
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
                                Toast.makeText(contexto, "Detalhes do produto", Toast.LENGTH_SHORT)
                                    .show()

                    }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Smartphone X", modifier = Modifier.weight(1f))
                // ÍCONE FAVORITAR
                Icon(
                    imageVector = if (eFavorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favoritar",
                    tint = if (eFavorito) Color.Red else Color.Gray,
                    modifier = Modifier
                        .size(40.dp)
                        .pointerInput(Unit) {
                            detectTapGestures {
                                eFavorito = !eFavorito
                                Toast.makeText(contexto, "Favorito alterado", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                )

                Spacer(modifier = Modifier.width(8.dp))

                // ÍCONE CONFIGURAR
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Configurar",
                    modifier = Modifier
                        .size(40.dp)
                        .pointerInput(Unit) {
                            detectTapGestures {
                                Toast.makeText(contexto, "Configurações", Toast.LENGTH_SHORT).show()
                            }
                        }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        // --- CENÁRIO 2: O DEPOIS (SUCESSO ACADÊMICO) ---
        // Para quem enxerga: Visualmente IGUAL. Os botões estão lá e funcionam.
        // Para o TalkBack: O Card é UM só. As ações dos botões foram movidas para o "Menu de Ações".
        Text(
            "O depois com ações personalizadas (Sucesso):",
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
                        // 1. Unifica o foco na linha  do card (Navegação rápida)

                        // 2. Mapeia as funções dos botões para o menu de contexto
                        customActions = listOf(
                            CustomAccessibilityAction(if (favoritado) "Desfavoritar" else "Favoritar") {
                                favoritado = !favoritado
                                true
                            },
                            CustomAccessibilityAction("Configurar produto") { true }
                        )
                    },

                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Produto B (Acessível)", modifier = Modifier.weight(1f))

                // Os botões continuam aqui para o usuário que não utiliza tecnologia assistiva
                IconButton(
                    onClick = { favoritado = !favoritado },
                    // limpa a semântica do botão porque a funcionalidade está como ação personalizada no menu de contexto, assim o Talkback não precisa focar nesse botão individualmente.
                    modifier = Modifier.clearAndSetSemantics { }
                ) {
                    // o ícone também não precisa de contentDescription:
                    Icon(
                        if (favoritado) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        null
                    )
                }
                IconButton(
                    onClick = { /* Configurar */ },
                    // limpa a semântica do botão porque a funcionalidade está como ação personalizada no menu de contexto, assim o Talkback não precisa focar nesse botão individualmente.
                    modifier = Modifier.clearAndSetSemantics { }
                ) {
                    // o ícone também não precisa de contentDescription:
                    Icon(Icons.Default.Settings, null)
                }
            }
        }
    }
}