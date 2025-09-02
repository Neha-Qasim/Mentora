📱 Mentora – Social Learning App

Mentora is a social learning app that combines Quizzes, Task Management, Real-time Chat, Profile, Ads, and Onboarding into one cohesive learning experience.
It helps users stay productive, test their knowledge, and collaborate with peers in real-time.

✨ Features
🔐 Authentication & Onboarding

Firebase Authentication (Login & Register).

User profile stored in Firebase Realtime Database (name, email, avatar URL).

3-screen onboarding flow shown only on first launch:

Welcome screen

Features overview

Privacy & Terms with Finish button

Banner Ads integrated at the bottom of onboarding screens.

Interstitial Ad displayed when entering the main app.

🎯 Quiz Module

5 MCQs per quiz, one question at a time.

10-second timer per question with auto-skip on timeout.

Displays final score summary at the end.

Stores quiz scores under each user’s profile in Firebase.

Dedicated Quiz History screen to review past attempts.

📝 Task Manager Module

Add, edit, and delete tasks (title, description, priority, status).

Filter tasks by Pending / Done.

Priority shown with color codes: High (red), Normal (yellow), Low (green).

Tasks are stored in Firebase Realtime Database per user.

Updates in real-time across devices.

💬 Chat Module

Real-time group chatroom using Firebase Realtime Database.

Send and receive text messages instantly.

(Optional bonus) Image sending and online/offline user status.

🧭 App Navigation

Built using Jetpack Compose Navigation.

Home Screen includes bottom navigation for:

Quiz

Tasks

Chat

Profile

Profile shows name, email, quiz history, and task count.

🛠️ Tech Stack

Language: Kotlin

UI: Jetpack Compose + Material 3

Database: Firebase Realtime Database

Authentication: Firebase Authentication

Ads: Google AdMob (Banner + Interstitial)

Architecture: MVVM (ViewModel + StateFlow)

Navigation: Jetpack Navigation Compose

📂 Project Structure
📦 Mentora/
 ┣ 📂 features/
 ┃ ┣ 📂 auth/        # Login, Register, Onboarding
 ┃ ┣ 📂 quiz/        # Quiz screens + history
 ┃ ┣ 📂 tasks/       # Task manager (CRUD + filters)
 ┃ ┣ 📂 chat/        # Real-time chat
 ┃ ┗ 📂 profile/     # Profile screen & task/quiz summary
 ┣ 📂 navigation/    # App navigation graph
 ┣ MainActivity.kt   # Entry point
 ┗ README.md

⚡ Setup Instructions
🔹 Firebase Setup

Go to Firebase Console
.

Create a Firebase project and connect it to your Android app.

Enable Authentication → Email/Password.

Enable Realtime Database (in test mode or with rules).

Add google-services.json file to your app/ folder.

🔹 AdMob Setup

Go to Google AdMob
.

Create a new app and generate:

Banner Ad Unit ID

Interstitial Ad Unit ID

Replace the placeholder IDs in your project with real AdMob IDs.

🚀 Getting Started

Clone the repository:

git clone https://github.com/Neha-Qasim/Mentora.git
cd Mentora


Open in Android Studio.

Connect your Firebase project and AdMob.

Run on emulator or physical device.

📸 Screenshots

Create a folder in your repo called screenshots/ and place your app images inside.
Update the table below with real paths:

Onboarding	Dashboard	Tasks	Quiz	Profile	Chat

	
	
	
	
	
📌 Deliverables

✅ Clean, modular GitHub repository.

✅ Firebase integration (Auth + Realtime DB).

✅ AdMob integration (Banner + Interstitial).

✅ APK build ready for demonstration.

👩‍💻 Author

Developed by Neha Qasim 🌸
