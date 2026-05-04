# Accessibility Engineering in Jetpack Compose: RemovingSemantic Barriers in TalkBack Navigation

This repository contains the artifacts used in the paper:
"Accessibility Engineering in Jetpack Compose: RemovingSemantic Barriers in TalkBack Navigation"

## 📦 Repository Structure

This repository is organized into two branches:

### 🔹 main (Prototype)
[The main branch](https://anonymous.4open.science/r/accessibility-engineering-in-jetpack-compose/) contains the controlled prototype used for:
- technical inspection
- barrier identification
- semantics tree analysis

Implements:
- flawed and corrected UI components
- examples of the five accessibility barrier categories

---

### 🔹 experimental_app (User Study Application)
[The experimental_app branch](https://anonymous.4open.science/r/accessibility-engineering-in-jetpack-compose-experimentalapp) contains the application used in the empirical evaluation with users.

Features:
- Flow A (baseline with accessibility barriers)
- Flow B (improved version with semantic fixes)
- task-based interaction scenarios

---

## 🧪 How to Run

### Requirements
- Android Studio
- Android SDK (API 34+ recommended)
- TalkBack enabled on device or emulator


### Steps
1. Clone the repository
2. Checkout desired branch:
   ```bash
   git checkout main
or
Bash
Copiar
git checkout experimental_app
3. 
Open in Android Studio
4. 
Build and run on a device/emulator
 
♿ Accessibility Evaluation
The experimental application was designed based on five categories of accessibility barriers:
1. 
Labeling
2. 
State feedback
3. 
Role definition
4. 
Semantic grouping
5. 
Gesture-based interaction and discoverability of controls grouping
Flow A intentionally includes these barriers, while Flow B implements semantic improvements.
 
⚠️ Notes
* Behavior may vary depending on TalkBack version and device
* Especially for Barrier 5 (gesture-based interaction), interaction results may differ across configurations.
 
📄 License
This project is licensed under the MIT License.