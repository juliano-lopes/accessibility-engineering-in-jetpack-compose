# Accessibility Engineering in Jetpack Compose: Removing Semantic Barriers in TalkBack Navigation

Este repositório contém os artefatos técnicos, protótipos e experimentos desenvolvidos para o artigo e Trabalho de Conclusão de Curso (TCC) na Pós-graduação em Desenvolvimento de Aplicações Web e Mobile (**IF Sudeste MG**):

> **"Accessibility Engineering in Jetpack Compose: Removing Semantic Barriers in TalkBack Navigation"**  
> Autor: **Juliano Lopes**

O estudo investiga como aplicar princípios de Engenharia de Software e Acessibilidade Digital no framework moderno **Jetpack Compose** (Android), identificando, catalogando e corrigindo barreiras na árvore semântica que prejudicam a navegação por gestos de usuários do leitor de telas **TalkBack**.

---

## 📦 Estrutura do Repositório

O projeto está organizado em duas branches principais para apoiar a reprodutibilidade dos experimentos:

### 🔹 Branch `main` (Protótipo de Inspeção Técnica)
Contém o protótipo controlado utilizado para:
- Inspeção técnica da árvore semântica do Android;
- Identificação sistemática de barreiras de acessibilidade;
- Implementação comparativa de componentes com falhas e suas respectivas correções semânticas;
- Testes instrumentados automatizados (`A11yTreeLogTest.kt`) para validação da acessibilidade.

### 🔹 Branch `experimental_app` (Aplicação de Estudo com Usuários)
Contém a aplicação construída para avaliação empírica de usabilidade e acessibilidade com usuários reais:
- **Fluxo A:** Baseline da aplicação contendo as barreiras de acessibilidade identificadas;
- **Fluxo B:** Versão aprimorada com as devidas correções semânticas aplicadas;
- Cenários de tarefas práticas de interação orientada a leitor de telas.

---

## 🧪 Como Executar o Projeto

### Pré-requisitos
- **Android Studio** instalado (versão recente recomendada);
- **Android SDK** (API 34+ recomendada);
- Dispositivo físico Android ou emulador com o **TalkBack** ativado.

### Passos
1. Clone o repositório:
   ```bash
   git clone https://github.com/juliano-lopes/accessibility-engineering-in-jetpack-compose.git
   cd accessibility-engineering-in-jetpack-compose
   ```
2. Alterne para a branch que deseja inspecionar:
   ```bash
   git checkout main
   # ou para a aplicação experimental:
   git checkout experimental_app
   ```
3. Abra a pasta do projeto no **Android Studio**;
4. Aguarde o download das dependências e a sincronização do **Gradle**;
5. Execute a aplicação no dispositivo de testes ou emulador.

---

## ♿ Avaliação de Acessibilidade

Os componentes e fluxos foram projetados com foco rigoroso em:
- Anúncio fidedigno de estados, ações e nomes acessíveis;
- Agrupamento semântico adequado de elementos interativos;
- Eliminação de nós redundantes ou invisíveis na hierarquia do TalkBack;
- Total conformidade com as diretrizes da **WCAG (Web Content Accessibility Guidelines)** adaptadas para o ecossistema Android nativo.

---

## Autor

Desenvolvido por **[Juliano Lopes](https://julianolopes.com.br)** — Especialista em Acessibilidade Digital (**IAAP WAS** | **NVDA Certified Expert**).
