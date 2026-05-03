@file:Suppress("SpellCheckingInspection")

package br.com.julianolopes.laba11yandroid

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp

/**
 * App de avaliação com dois fluxos:
 * - Fluxo A: baseline com barreiras de acessibilidade
 * - Fluxo B: versão corrigida com engenharia semântica
 *
 * O app mantém o mesmo conteúdo visual e a mesma navegação básica.
 * A diferença está na estrutura semântica e na forma como as ações são expostas.
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

private data class ProductSection(
    val title: String,
    val products: List<Product>
)

private val productSections = listOf(
    ProductSection(
        title = "Seção 1 - Smartphones",
        products = listOf(
            Product(
                1,
                "Smartphone X",
                "R$ 4.999",
                "Tela de 6,5 polegadas, bateria de longa duração e câmera tripla."
            ),
            Product(
                2,
                "Smartphone Plus",
                "R$ 5.799",
                "Modelo premium com melhor autonomia e câmera avançada."
            ),
            Product(
                3,
                "Smartphone Lite",
                "R$ 2.399",
                "Versão compacta para uso diário e navegação básica."
            )
        )
    ),
    ProductSection(
        title = "Seção 2 - Notebooks",
        products = listOf(
            Product(
                4,
                "Notebook Pro",
                "R$ 7.299",
                "Processador rápido, 16 GB de memória e SSD de 512 GB."
            ),
            Product(
                5,
                "Notebook Air",
                "R$ 6.199",
                "Leve, portátil e ideal para produtividade e estudos."
            ),
            Product(
                6,
                "Notebook Max",
                "R$ 8.999",
                "Alta performance para edição, desenvolvimento e multitarefa."
            )
        )
    ),
    ProductSection(
        title = "Seção 3 - Áudio",
        products = listOf(
            Product(
                7,
                "Fone Studio",
                "R$ 799",
                "Áudio de alta fidelidade com cancelamento de ruído."
            ),
            Product(
                8,
                "Caixa Sound",
                "R$ 1.049",
                "Som estéreo com resistência à água e conexão Bluetooth."
            ),
            Product(
                9,
                "Speaker Mini",
                "R$ 349",
                "Caixa portátil para uso em ambientes internos e externos."
            )
        )
    ),
    ProductSection(
        title = "Seção 4 - Wearables",
        products = listOf(
            Product(
                10,
                "Relógio Fit",
                "R$ 1.299",
                "Monitoramento de sono, batimentos e atividades físicas."
            ),
            Product(
                11,
                "Pulse Band",
                "R$ 499",
                "Pulseira com monitoramento básico de saúde e notificações."
            ),
            Product(
                12,
                "Watch Neo",
                "R$ 1.899",
                "Relógio inteligente com GPS e suporte a chamadas."
            )
        )
    ),
    ProductSection(
        title = "Seção 5 - Tablets e acessórios",
        products = listOf(
            Product(
                13,
                "Tablet Air",
                "R$ 3.899",
                "Leve, portátil e indicado para leitura e produtividade."
            ),
            Product(
                14,
                "Tablet Pro",
                "R$ 5.499",
                "Tela maior, caneta compatível e ótimo para criação de conteúdo."
            ),
            Product(
                15,
                "Dock Station",
                "R$ 799",
                "Acessório para ampliar conexões e organização do setup."
            )
        )
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
            text = "Escolha um dos fluxos para iniciar a avaliação.",
            style = MaterialTheme.typography.bodyLarge
        )

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(onClick = onOpenFlowA, modifier = Modifier.fillMaxWidth()) {
                    Text("Fluxo A")
                }
                Button(onClick = onOpenFlowB, modifier = Modifier.fillMaxWidth()) {
                    Text("Fluxo B")
                }
            }
        }

        Text(
            text = "Este app é exclusivo para fins de estudo. Nenhum dado pessoal é coletado.",
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
    val context = LocalContext.current
    //val completedStates = remember { mutableStateMapOf<Int, Boolean>() }
    val completedStates = remember {
    mutableStateMapOf<Int, MutableState<Boolean>>()
}

    var showingDetail by remember { mutableStateOf(false) }
    var selectedProduct by remember { mutableStateOf(productSections.first().products.first()) }
    var lastFocusedId by remember { mutableStateOf<Int?>(null) }
    val focusRequesters = remember { mutableStateMapOf<Int, FocusRequester>() }

    Column(modifier = Modifier.fillMaxSize()) {
        StudyHeader(
            title = if (improved) "Tela Fluxo B" else "Tela Fluxo A",
            improved = improved,
            onBack = onBack,
            onNotifications = {
                Toast.makeText(
                    context,
                    "Acessou as Notificações",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )

        HorizontalDivider()
        LaunchedEffect(showingDetail) {
            if (improved && !showingDetail && lastFocusedId != null) {
                focusRequesters[lastFocusedId]?.requestFocus()
            }
        }
        if (!showingDetail) {
            ProductSectionsScreen(
                sections = productSections,
                improved = improved,
                focusRequesters = focusRequesters,
                onOpenProduct = {
                    lastFocusedId = it.id
                    selectedProduct = it
                    showingDetail = true
                },
                onToggleCompleted = { /* não usado na tela de seção */ },
                onBuy = { product ->
                    Toast.makeText(context, "Comprado produto ${product.name}", Toast.LENGTH_SHORT)
                        .show()
                },
                onConfigure = { product ->
                    Toast.makeText(
                        context,
                        "Configurado produto ${product.name}",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                onDelete = { product ->
                    Toast.makeText(context, "Excluído produto ${product.name}", Toast.LENGTH_SHORT)
                        .show()
                }
            )
        } else {
            val completedState = completedStates.getOrPut(selectedProduct.id) {
    mutableStateOf(false)
}
            ProductDetailScreen(
                product = selectedProduct,
                improved = improved,
                completedState = completedState,
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
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = if (improved) "Voltar" else null
            )
        }

        Spacer(modifier = Modifier.width(4.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.semantics { heading() }
            )
            Text(
                text = "Produtos de alta qualidade",
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
private fun ProductSectionsScreen(
    sections: List<ProductSection>,
    improved: Boolean,
    focusRequesters: MutableMap<Int, FocusRequester>,
    onOpenProduct: (Product) -> Unit,
    onToggleCompleted: (Int) -> Unit,
    onBuy: (Product) -> Unit,
    onConfigure: (Product) -> Unit,
    onDelete: (Product) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp)
    ) {
        sections.forEachIndexed { index, section ->
            SectionBlock(
                section = section,
                improved = improved,
                focusRequesters = focusRequesters,

                onOpenProduct = onOpenProduct,
                onBuy = onBuy,
                onConfigure = onConfigure,
                onDelete = onDelete
            )

            if (index < sections.lastIndex) {
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
            }
        }
    }
}

@Composable
private fun SectionBlock(
    section: ProductSection,
    improved: Boolean,
    focusRequesters: MutableMap<Int, FocusRequester>,

    onOpenProduct: (Product) -> Unit,
    onBuy: (Product) -> Unit,
    onConfigure: (Product) -> Unit,
    onDelete: (Product) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = section.title,
            style = MaterialTheme.typography.titleMedium,
            modifier = if (improved) Modifier.semantics { heading() } else Modifier.semantics {}

        )

        section.products.forEach { product ->
            ProductCard(
                product = product,
                improved = improved,
                focusRequesters = focusRequesters,
                onOpenProduct = { onOpenProduct(product) },
                onBuy = { onBuy(product) },
                onConfigure = { onConfigure(product) },
                onDelete = { onDelete(product) }
            )
        }
    }
}

@Composable
private fun ProductCard(
    product: Product,
    improved: Boolean,
    focusRequesters: MutableMap<Int, FocusRequester>,
    onOpenProduct: () -> Unit,
    onBuy: () -> Unit,
    onConfigure: () -> Unit,
    onDelete: () -> Unit
) {
    val focusRequester = focusRequesters.getOrPut(product.id) {
        FocusRequester()
    }
    val context = LocalContext.current
    val baseModifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)

    val focusModifier = if (improved) {
        baseModifier
            .focusRequester(focusRequester)
            .focusable()
    } else {
        baseModifier
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        if (improved) {
            // Fluxo B: tudo agrupado semanticamente; ações ficam apenas em custom actions.
            Row(
                focusModifier
                    .clickable(onClickLabel = "Detalhes do produto") {

                        onOpenProduct()

                    }

                    .padding(16.dp)
                    .semantics(mergeDescendants = true) {

                        customActions = listOf(
                            CustomAccessibilityAction("Comprar produto") {
                                onBuy()
                                true
                            },
                            CustomAccessibilityAction("Ver detalhes") {
                                onOpenProduct()
                                true
                            },
                            CustomAccessibilityAction("Configurar produto") {
                                onConfigure()
                                true
                            },
                            CustomAccessibilityAction("Excluir produto") {
                                onDelete()
                                true
                            }
                        )
                        role = Role.Button
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = product.price,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )


                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null,
                    modifier = Modifier.pointerInput(product.id) {
                        detectTapGestures {
                            onBuy()
                        }
                    }
                )


                /*
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null
                                )
                                    */
            }

            Spacer(modifier = Modifier.width(8.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(modifier = Modifier.clearAndSetSemantics { }, onClick = onOpenProduct) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                    )
                }


                IconButton(modifier = Modifier.clearAndSetSemantics { }, onClick = onConfigure) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = null
                    )
                }

                IconButton(modifier = Modifier.clearAndSetSemantics { }, onClick = onDelete) {

                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
            }

        } else {
            // Fluxo A: apenas o agrupamento nome + ícone Comprar fica junto;
            // o restante permanece solto, com ações separadas.
            Row(
                focusModifier
                    .semantics(mergeDescendants = true) {},
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = product.price,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Comprar",
                    modifier = Modifier.pointerInput(product.id) {
                        detectTapGestures {
                            onBuy()
                        }
                    }
                )


            }


            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onOpenProduct) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null
                    )
                }


                IconButton(onClick = onConfigure) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Configurar"
                    )
                }

                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
            }

        }
    }
}

@Composable
private fun ProductDetailScreen(
    product: Product,
    improved: Boolean,
    //completed: Boolean,
    completedState: MutableState<Boolean>,
    onBackToList: () -> Unit
) {

    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(improved) {
        if (improved) {
            focusRequester.requestFocus()
        }
    }
        var completed by remember { mutableStateOf(false) }
        completed = completedState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        if (improved) {
            Column(modifier = Modifier.semantics() {}) {
                Text(
                    text = "Detalhes  do produto: " + product.name,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .semantics { heading() }
                        .focusRequester(focusRequester)
                        .focusable()
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
                            value = completedState.value,
                            onValueChange = {
                                 completedState.value = it
                                 completed = !completed
                                 },
                            role = Button
                        )
                        .semantics {
                            stateDescription = if (completedState.value) {
                                "Produto favoritado. Desmarcar como favorito."

                            } else {
                                "Produto não favoritado. Marcar como favorito."
                            }
                        }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if(completed) "Desfavoritar" else "Favoritar",
                        modifier = Modifier.weight(1f)
                        .clearAndSetSemantics {}

                    )
                    Icon(
                        imageVector = if (completedState.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = if (completedState.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { completedState.value = !completedState.value }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (completedState.value) "Desfavoritar produto" else "Favoritar produto",
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = if (completedState.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = if (completedState.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Button(onClick = onBackToList) {
            Text(text = if (improved) "Voltar para a lista de produtos" else "Voltar")
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
        )
    }
}
