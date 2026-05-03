package br.com.accessibleandroid.laba11yandroid

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import org.junit.Rule
import org.junit.Test

class A11yTreeLogTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun gerarLogDeAcessibilidade() {
        // useUnmergedTree = true mostra tudo (inclusive o que está "escondido" no agrupamento)
        // useUnmergedTree = false mostra exatamente o que o TalkBack narra (árvore unificada)
        
        println("--- ÁRVORE SEMÂNTICA COMPLETA ---")
        composeTestRule.onRoot(useUnmergedTree = false).printToLog("LAB_A11Y_VERBOSE")

        //println("--- ÁRVORE COMO O TALKBACK VÊ (MERGED) ---")
        //composeTestRule.onRoot(useUnmergedTree = false).printToLog("LAB_A11Y_TALKBACK")
    }
}