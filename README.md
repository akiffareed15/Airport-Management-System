
# ✈️ AirportApp Management System

A Java-based desktop application designed to manage airport operations such as flight passenger bookings, check-in, and reporting. The system includes an animated loading screen and a clean, user-friendly JavaFX GUI.

---

## 📌 Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Installation & Running](#installation--running)
- [User Interface](#user-interface)
  - [Animated Loading Screen](#animated-loading-screen)
  - [Flight Management Tab](#flight-management-tab)
  - [Booking Tab](#booking-tab)
  - [Check-In Tab](#check-in-tab)
  - [Reports Tab](#reports-tab)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

---

## ✅ Features

- Smooth fade-in animated loading screen
- Manage Flights (Add, Update, Delete)
- Book and manage passengers
- Passenger check-in functionality
- Report viewing and export (planned)
- Simple, intuitive GUI built with JavaFX, javaAwt
- Executable `.exe` and `.jar` versions included

---

## 🛠 Technologies Used

- **Java 11+**
- **JavaFX** (GUI)
- **Launch4j** (for `.exe` generation)
- **Eclipse IDE** (for development)
- **FXML / CSS** (for GUI design and styling)

---

## 🗂 Project Structure

```bash
AirportApp Management System/
├── AMS.exe                     # Executable file for Windows
├── AirportApp.jar             # Main Java app file
├── AMS_config.xml             # Launch4j config
├── create_jar.bat             # Script to build .jar
├── run.bat                    # Run script
├── /bin                       # Compiled class files
│   └── com/airport/...        # Java packages
├── /settings                  # Eclipse settings
├── style.css                  # GUI styling
└── .project, .classpath       # Eclipse config files
```

---

## 🚀 Installation & Running

### Prerequisites

- Java JDK 11+ installed
- (Optional) Eclipse IDE if editing or running the source code

### Running the App

#### Option 1: Using `.jar`
```bash
java -jar AirportApp.jar
```

#### Option 2: Using `.exe` (Windows)
- Just double-click `AMS.exe`

#### Option 3: From Source (Eclipse)
1. Import project into Eclipse
2. Configure JavaFX library
3. Run `FlightApp.java` or the designated main class

---

## 🖥️ User Interface


### 🎬 Animated Loading Screen

The app starts with a beautiful loading screen to enhance user experience.



![Loading Screen](https://raw.githubusercontent.com/akiffareed15/Airport-Management-System/1c9f823c7702766022aa3e52989ce0ac31348fad/1.png)
```
> Fade-in Animated Login Interface
```
![2](https://github.com/user-attachments/assets/41daa74f-f532-44dd-8ba2-936f22b39b10)
```
---

### ✈️ Flight Management Tab

- Add new flights (Flight No., Source, Destination,)
- Edit or delete flights
- View all flights in a table


```
![3](https://github.com/user-attachments/assets/fdf1a4d7-40b9-424e-bf9a-2b3934b206b6)

```

---

### 🎫 Booking Tab

- Register passengers
- Detail
- Book flights
- View past bookings

```
![4](https://github.com/user-attachments/assets/78d04539-d96b-45f7-bc8d-aeccea1c1335)

```

---

### ✅ Check-In Tab

- Select a booked ticket
- Confirm check-in
- View passenger and flight info
- Mark status as "checked-in"

```
![5](https://github.com/user-attachments/assets/696b4af3-1ce1-4bd7-aee3-27e899a3ab36)

```

---

### 📊 Reports Tab

- View completed bookings per flight
- Prepare data for admin reports

```
![6](https://github.com/user-attachments/assets/9fee27b5-6f5c-45c9-8b4d-09c6bbdafb3c)

```

---

## 💡 Usage

1. Launch the app via `.jar` or `.exe`
2. Wait for the animated loading screen to finish
3. Use tabs for:
   - Adding Flights
   - Adding Passenger Detail (tickets)
   - Performing Check-Ins
   - Viewing Reports
4. Exit and relaunch anytime

---

## 🤝 Contributing

Pull requests are welcome!

Steps:
1. Fork the repository
2. Create a branch: `git checkout -b feature/yourFeature`
3. Commit your changes: `git commit -m "Added new feature"`
4. Push to the branch: `git push origin feature/yourFeature`
5. Open a pull request

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).
 
---

## 📬 Contact

**akif-dev**  
📧 akiffareed15@gmail.com  
🔗 GitHub: [github.com/akiffareed15](https://github.com/akiffareed15)


