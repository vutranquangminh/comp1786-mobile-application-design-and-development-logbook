# 📱 COMP1786 Coursework – Android App Logbook

This repository contains my coursework submission for **COMP1786 – Mobile Application Design and Development** at the University of Greenwich. The submission includes **three Android applications**, each designed and developed in Android Studio using Java, XML layouts, and Android SDK components.

> 🧾 Coursework contributes **20%** of the module grade and demonstrates core competencies in GUI design, event handling, resource management, and data persistence in Android development.

---

## 🗂️ Repository Structure

```
📁 UnitConverterApp/
📁 ToDoListApp/
📁 ToDoListWithSQLite/
📄 README.md
```

---

## 📘 Logbook Summary

Each app corresponds to one exercise, accompanied by evidence and reflection in `CW2_Logbook.pdf`.

| Exercise | App Name             | Description                                 | Weight |
|----------|----------------------|---------------------------------------------|--------|
| 1        | UnitConverterApp     | Convert between metre, millimetre, mile, ft | 30%    |
| 2        | ToDoListApp          | Task list app with add/edit/delete features | 30%    |
| 3        | ToDoListWithSQLite   | Persistent ToDo app with completed status   | 40%    |

---

## 📱 App Overviews

### 1. 📏 Unit Converter App
A simple Android app that allows users to convert a length value between **Metres, Millimetres, Miles, and Feet** using a **Spinner** for unit selection.

- Input validation and error handling
- Clean and responsive UI
- Resource-based string definitions and layout styling

---

### 2. 📝 ToDo List App
An interactive task management app featuring:

- **MainActivity**: Displays the task list
- **AddTaskActivity**: Allows adding/editing tasks
- UI designed with **LinearLayout**, margins, and clean design principles
- Dynamic update of list via `RecyclerView` or `ListView`

---

### 3. 💾 Persistent ToDo List
An enhanced version of the ToDo app with:

- **SQLite** database integration
- Task "Completed" status toggle
- Data stored persistently across sessions
- Follows Android best practices for **DAO** and **SQLiteOpenHelper**

---

## 🛠️ Tools & Technologies

- **Language:** Java
- **IDE:** Android Studio
- **Database:** SQLite (for Exercise 3)
- **UI:** XML Layouts, Material Design components
- **Version Control:** Git + GitHub

---

## 📄 Coursework Requirements

✔ GUI designed with usability in mind  
✔ Proper use of views (Spinners, Buttons, TextViews)  
✔ App functionality aligned with brief  
✔ Appropriate use of styles, themes, and resources  
✔ Clean, commented, and functional Java code  
✔ Logbook reflects app development and decisions made  

---

## 📥 How to Run the Apps

1. Clone the repo or download the ZIP.
2. Open each app folder individually in **Android Studio**.
3. Run on an **emulator** or **physical Android device** (API 28+).
4. For the SQLite version, test by adding tasks and toggling status.

---
