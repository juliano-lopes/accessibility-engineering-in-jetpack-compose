@file:Suppress("SpellCheckingInspection")

package br.com.julianolopes.laba11yandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp

/**
 * App de avaliação com um único código-base e duas condições:
 * - Fluxo A: baseline com barreiras de acessibilidade
 * - Fluxo B: versão corrigida com engenharia semântica
 *
 * O objetivo é permitir teste com usuários mantendo a mesma estrutura visual e funcional,
 * mudando apenas a implementação de acessibilidade.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AccessibilityStudyApp()
                }
            }
        }
    }
}

private enum class AppScreen {
    Home,
    FlowA,
    FlowB
}

private data class Product(
    val id: Int,
    val name: String,
    val price: String,
    val description: String
)

private val sampleProducts = listOf(
    Product(
        1,
        "Smartphone X",
        "R$ 4.999",
        "Tela de 6,5 polegadas, bateria de longa duração e câmera tripla."
    ),
    Product(2, "Notebook Pro", "R$ 7.299", "Processador rápido, 16 GB de memória e SSD de 512 GB."),
    Product(3, "Fone Studio", "R$ 799", "Áudio de alta fidelidade com cancelamento de ruído."),
    Product(
        4,
        "Relógio Fit",
        "R$ 1.299",
        "Monitoramento de sono, batimentos e atividades físicas."
    ),
    Product(5, "Tablet Air", "R$ 3.899", "Leve, portátil e indicado para leitura e produtividade."),
    Product(
        6,
        "Caixa Sound",
        "R$ 1.049",
        "Som estéreo com resistência à água e conexão Bluetooth. "
    )
)

@Composable
private fun AccessibilityStudyApp() {
    var screen by remember { mutableStateOf(AppScreen.Home) }

    when (screen) {
        AppScreen.Home -> HomeScreen(
            onOpenFlowA = { screen = AppScreen.FlowA },
            onOpenFlowB = { screen = AppScreen.FlowB }
        )

        AppScreen.FlowA -> ProductFlowScreen(
            improved = false,
            onBack = { screen = AppScreen.Home }
        )

        AppScreen.FlowB -> ProductFlowScreen(
            improved = true,
            onBack = { screen = AppScreen.Home }
        )
    }
}

@Composable
private fun HomeScreen(
    onOpenFlowA: () -> Unit,
    onOpenFlowB: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Estudo de acessibilidade em Android",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.semantics { heading() }
        )

        Text(
            text = "Escolha um dos fluxos para iniciar a avaliação. O Fluxo A contém as barreiras de acessibilidade; o Fluxo B aplica as correções com engenharia semântica.",
            style = MaterialTheme.typography.bodyLarge
        )

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(onClick = onOpenFlowA, modifier = Modifier.fillMaxWidth()) {
                    Text("Fluxo A - com barreiras")
                }
                Button(onClick = onOpenFlowB, modifier = Modifier.fillMaxWidth()) {
                    Text("Fluxo B - corrigido")
                }
            }
        }

        Text(
            text = "O app usa o mesmo conteúdo visual nos dois fluxos; apenas as propriedades semânticas e a navegação acessível mudam.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ProductFlowScreen(
    improved: Boolean,
    onBack: () -> Unit
) {
    val products = remember { sampleProducts }
    val favoriteStates = remember { mutableStateMapOf<Int, Boolean>() }
    val completedStates = remember { mutableStateMapOf<Int, Boolean>() }

    var showingDetail by remember { mutableStateOf(false) }
    var selectedProductId by remember { mutableStateOf(products.first().id) }

    val selectedProduct = products.first { it.id == selectedProductId }

    Column(modifier = Modifier.fillMaxSize()) {
        StudyHeader(
            title = if (improved) "Fluxo B - corrigido" else "Fluxo A - com barreiras",
            improved = improved,
            onBack = onBack,
            onNotifications = { /* ação visual apenas */ }
        )

        HorizontalDivider()

        if (!showingDetail) {
            ProductListScreen(
                products = products,
                improved = improved,
                favoriteStates = favoriteStates,
                onOpenProduct = {
                    selectedProductId = it.id
                    showingDetail = true
                },
                onToggleFavorite = { productId ->
                    favoriteStates[productId] = !(favoriteStates[productId] ?: false)
                },
                onConfigure = { /* ação visual apenas */ }
            )
        } else {
            ProductDetailScreen(
                product = selectedProduct,
                improved = improved,
                completed = completedStates[selectedProduct.id] ?: false,
                onToggleCompleted = { newValue ->
                    completedStates[selectedProduct.id] = newValue
                },
                onBackToList = { showingDetail = false }
            )
        }
    }
}

@Composable
private fun StudyHeader(
    title: String,
    improved: Boolean,
    onBack: () -> Unit,
    onNotifications: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (true) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = if (improved) "Voltar" else null
                )
            }
        }

        Spacer(modifier = Modifier.width(4.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.semantics { heading() }
            )
            Text(
                text = if (improved) "Versão com correções semânticas" else "Versão com barreiras de acessibilidade",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        IconButton(onClick = onNotifications) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = if (improved) "Notificações" else null
            )
        }
    }
}

@Composable
private fun ProductListScreen(
    products: List<Product>,
    improved: Boolean,
    favoriteStates: Map<Int, Boolean>,
    onOpenProduct: (Product) -> Unit,
    onToggleFavorite: (Int) -> Unit,
    onConfigure: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(products, key = { it.id }) { product ->
            ProductCard(
                product = product,
                improved = improved,
                favorite = favoriteStates[product.id] ?: false,
                onOpenProduct = { onOpenProduct(product) },
                onToggleFavorite = { onToggleFavorite(product.id) },
                onConfigure = { onConfigure(product.id) }
            )
        }
    }
}

@Composable
private fun ProductCard(
    product: Product,
    improved: Boolean,
    favorite: Boolean,
    onOpenProduct: () -> Unit,
    onToggleFavorite: () -> Unit,
    onConfigure: () -> Unit
) {
    val openModifier = if (improved) {
        Modifier
            .fillMaxWidth()
            .clickable { onOpenProduct() }
            .semantics(mergeDescendants = true) {
                role = Role.Button
                customActions = listOf(
                    CustomAccessibilityAction(if (favorite) "Desfavoritar" else "Favoritar") {
                        onToggleFavorite()
                        true
                    },
                    CustomAccessibilityAction("Configurar produto") {
                        onConfigure()
                        true
                    }
                )
            }
    } else {
        Modifier
            .fillMaxWidth()
            .clickable { onOpenProduct() }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Barreira 4: agrupamento de informações relacionadas
            if (improved) {
                Column(
                    modifier = Modifier.semantics(mergeDescendants = true) {}
                ) {
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.semantics { heading() }
                    )
                    Text(
                        text = product.price,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                Column {
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = product.price,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Barreira 3: função do elemento/card (role)
            // Barreira 5: custom actions no Fluxo B
            Row(
                modifier = openModifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Abrir detalhes",
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.primary
                )

                // Barreira 1: rótulos de elementos (ícone sem contentDescription no A)
                IconButton(
                    onClick = onToggleFavorite,
                    modifier = if (improved) Modifier.clearAndSetSemantics { } else Modifier
                ) {
                    Icon(
                        imageVector = if (favorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = if (improved) null else null,
                        tint = if (favorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                IconButton(
                    onClick = onConfigure,
                    modifier = if (improved) Modifier.clearAndSetSemantics { } else Modifier
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = if (improved) null else "Configurar produto"
                    )
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
private fun ProductDetailScreen(
    product: Product,
    improved: Boolean,
    completed: Boolean,
    onToggleCompleted: (Boolean) -> Unit,
    onBackToList: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (improved) {
            Column(modifier = Modifier.semantics(mergeDescendants = true) {}) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.semantics { heading() }
                )
                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            Column {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Card(modifier = Modifier.fillMaxWidth()) {
            if (improved) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .toggleable(
                            value = completed,
                            onValueChange = onToggleCompleted,
                            role = Role.Button
                        )
                        .semantics {
                            stateDescription = if (completed) {
                                "Item concluído"
                            } else {
                                "Item não concluído"
                            }
                        }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (completed) "Desmarcar como concluído" else "Marcar como concluído",
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = if (completed) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = if (completed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onToggleCompleted(!completed) }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (completed) "Desmarcar como concluído" else "Marcar como concluído",
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = if (completed) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = if (completed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = onBackToList) {
                Text("Voltar para a lista")
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
        )
    }
}
