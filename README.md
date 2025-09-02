# 📱 Mentora – Social Learning App  

Mentora is a **social learning app** that combines **Quizzes, Task Management, Real-time Chat, Profile, Ads, and Onboarding** into one cohesive learning experience.  
It helps users stay productive, test their knowledge, and collaborate with peers in real-time.  

---

## ✨ Features  

### 🔐 Authentication & Onboarding  
- Firebase Authentication (Login & Register).  
- User profile stored in Firebase Realtime Database (**name, email, avatar URL**).  
- **3-screen onboarding flow** shown only on first launch:  
  1. Welcome screen  
  2. Features overview  
  3. Privacy & Terms with **Finish** button  
- **Banner Ads** integrated at the bottom of onboarding screens.  
- **Interstitial Ad** displayed when entering the main app.  

### 🎯 Quiz Module  
- **5 MCQs per quiz**, one question at a time.  
- **10-second timer** per question with auto-skip on timeout.  
- Displays **final score summary** at the end.  
- Stores quiz scores under each user’s profile in Firebase.  
- Dedicated **Quiz History** screen to review past attempts.  

### 📝 Task Manager Module  
- Add, edit, and delete tasks (**title, description, priority, status**).  
- **Filter tasks** by Pending / Done.  
- **Priority shown with color codes**: High (red), Normal (yellow), Low (green).  
- Tasks are **stored in Firebase Realtime Database** per user.  
- Updates in **real-time across devices**.  

### 💬 Chat Module  
- Real-time **group chatroom** using Firebase Realtime Database.  
- Send and receive text messages instantly.  
- (Optional bonus) Image sending and online/offline user status.  

### 🧭 App Navigation  
- Built using **Jetpack Compose Navigation**.  
- Home Screen includes bottom navigation for:  
  - **Quiz**  
  - **Tasks**  
  - **Chat**  
  - **Profile**  
- Profile shows **name, email, quiz history, and task count**.

## 📸 Screenshots  

Below are the key screens of the Mentora app.  

### 🟣 Onboarding Flow  
|Splash Screen| Screen 1 – Welcome | Screen 2 – Features | Screen 3 – Privacy & Terms |
|-----------------|--------------------|----------------------|----------------------------|
|![Splash](screenshot/Splash.png)| ![Onboarding1](screenshots/onboarding1.png) | ![Onboarding2](screenshots/onboarding2.png) | ![Onboarding3](screenshots/onboarding3.png) |

---

### 🟢 Authentication  
| Login Screen | Register Screen |
|--------------|-----------------|
| ![Login](screenshots/login.png) | ![Register](screenshots/register.png) |

---

### 🏠 Dashboard  
| Dashboard |
|-----------|
| ![Dashboard](screenshots/dashboard.png) |

---

### 🎯 Quiz Module  
| Quiz Question | Quiz Result | Quiz History |
|---------------|-------------|---------------|
| ![Quiz](screenshots/quiz.png) | ![Result](screenshots/result.png) | ![History](screenshots/quiz_history.png) |

---

### 📝 Task Manager  
| Task List | Add/Edit Task | Filtered Tasks |
|-----------|---------------|----------------|
| ![Tasks](screenshots/tasks.png) | ![AddTask](screenshots/add_task.png) | ![Filtered](screenshots/filtered.png) |

---

### 💬 Chat  
| Chat Screen |
|-------------|
| ![Chat](screenshots/chat.png) |

---

### 👤 Profile  
| Profile Screen |
|----------------|
| ![Profile](screenshots/profile.png) |


---

## 🛠️ Tech Stack  
- **Language**: Kotlin  
- **UI**: Jetpack Compose + Material 3  
- **Database**: Firebase Realtime Database  
- **Authentication**: Firebase Authentication  
- **Ads**: Google AdMob (Banner + Interstitial)  
- **Architecture**: MVVM (ViewModel + StateFlow)  
- **Navigation**: Jetpack Navigation Compose  

---

## ⚡ Setup Instructions  

### 🔹 Firebase Setup  
1. Go to [Firebase Console](https://console.firebase.google.com/).  
2. Create a Firebase project and connect it to your Android app.  
3. Enable **Authentication** → Email/Password.  
4. Enable **Realtime Database** (in test mode or with rules).  
5. Add `google-services.json` file to your `app/` folder.  

### 🔹 AdMob Setup  
1. Go to [Google AdMob](https://admob.google.com/).  
2. Create a new app and generate:  
   - **Banner Ad Unit ID**  
   - **Interstitial Ad Unit ID**  
3. Replace the placeholder IDs in your project with real AdMob IDs.  

---

## 🚀 Getting Started  
1. Clone the repository:  
   ```bash
   git clone https://github.com/Neha-Qasim/Mentora.git
   cd Mentora
   Open in Android Studio.

Connect your Firebase project and AdMob.

Run on emulator or physical device.
