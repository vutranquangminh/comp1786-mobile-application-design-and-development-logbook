# 📱 COMP1786 Coursework – Android App Logbook

## 👨‍💻 Author Information

**Author:** Vũ Trần Quang Minh  
**Greenwich UK Student ID:** 001325733  
**FPT Greenwich Student ID:** GCS220006  
**Class:** COS1103  
**Subject:** COMP1786 - Mobile Application Design and Development  
**Contact:** minhvtqgcs220006@fpt.edu.vn  
**GitHub:** [vutranquangminh](https://github.com/vutranquangminh)

---

## 🗂️ Repository Structure

```
📁 UnitConverter/
📁 TodoListWithOutDb/
📁 TodoListWithDb/
📄 README.md
```

---

## 📘 Logbook Summary

Each app corresponds to one exercise, accompanied by evidence and reflection in `CW2_Logbook.pdf`.

| Exercise | App Name             | Description                                 |
|----------|----------------------|---------------------------------------------|
| 1        | UnitConverter        | Convert between metre, millimetre, mile, ft |
| 2        | TodoListWithOutDb    | Task list app with add/edit/delete features |
| 3        | TodoListWithDb       | Persistent ToDo app with completed status   |

---

## 📱 App Overviews

### 1. 📏 Unit Converter App
A simple Android app that allows users to convert a length value between **Metres, Millimetres, Miles, and Feet** using a **Spinner** for unit selection.

- **Input validation** and error handling with NumberFormatException
- **Real-time conversion** using TextWatcher for live updates
- **Smart unit selection** preventing duplicate unit selection
- Clean and responsive UI with ConstraintLayout
- Resource-based string definitions and layout styling

---

### 2. 📝 ToDo List App (Without Database)
An interactive task management app featuring:

- **MainActivity**: Displays the task list using RecyclerView
- **Task management** with add/edit/delete functionality
- **RecyclerView with custom adapter** for dynamic list updates
- UI designed with **ConstraintLayout**, margins, and clean design principles
- **In-memory storage** using ArrayList for task persistence during session

---

### 3. 💾 Persistent ToDo List with SQLite
An enhanced version of the ToDo app with:

- **Room Database** integration (SQLite abstraction layer)
- **Task "Completed" status** toggle functionality
- **Data stored persistently** across sessions using Room persistence library
- **Bottom Navigation** with Home and View fragments
- **Separate adapters** for active and completed tasks
- **DAO pattern** implementation with TaskDao interface
- **Multi-threading** for database operations (background threads for DB operations)

---

## 🛠️ Tools & Technologies

### **Core Technologies:**
- **Language:** Java 11
- **IDE:** Android Studio
- **Build System:** Gradle with Kotlin DSL
- **Android Gradle Plugin:** 8.7.1

### **UI Components:**
- **Layouts:** ConstraintLayout, LinearLayout
- **Views:** RecyclerView, Spinner, EditText, TextView, Button
- **Navigation:** Bottom Navigation with Navigation Fragment
- **Material Design:** Material Components 1.12.0

### **Database & Persistence:**
- **Room Database:** 2.7.2 (for TodoListWithDb)
- **SQLite:** Underlying database engine
- **DAO Pattern:** Data Access Object implementation

### **Testing:**
- **JUnit:** 4.13.2 for unit testing
- **Espresso:** 3.6.1 for UI testing
- **AndroidX Test:** 1.2.1 for instrumentation testing

### **Android SDK:**
- **Compile SDK:** 35
- **Target SDK:** 35
- **Min SDK:** 35
- **AppCompat:** 1.7.1
- **Activity:** 1.10.1
- **ConstraintLayout:** 2.2.1

### **Version Control:**
- **Git** + **GitHub**

---

## 📄 Coursework Requirements

✔ GUI designed with usability in mind  
✔ Proper use of views (Spinners, Buttons, TextViews, RecyclerView)  
✔ App functionality aligned with brief  
✔ Appropriate use of styles, themes, and resources  
✔ Clean, commented, and functional Java code  
✔ Logbook reflects app development and decisions made  

---

## 📥 How to Run the Apps

1. Clone the repo or download the ZIP.
2. Open each app folder individually in **Android Studio**.
3. Run on an **emulator** or **physical Android device** (API 35+).
4. For the SQLite version, test by adding tasks and toggling status.

---
