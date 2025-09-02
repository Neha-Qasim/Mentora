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
|![Splash](https://github.com/Neha-Qasim/Mentora/blob/3332a39d7cbf3a859beaed175142a8a05721aa93/Image%201.jpeg )| ![Onboarding1](https://github.com/Neha-Qasim/Mentora/blob/3332a39d7cbf3a859beaed175142a8a05721aa93/Image%20A.jpeg ) | ![Onboarding2](https://github.com/Neha-Qasim/Mentora/blob/3332a39d7cbf3a859beaed175142a8a05721aa93/Image%20B.jpeg ) | ![Onboarding3](https://github.com/Neha-Qasim/Mentora/blob/3332a39d7cbf3a859beaed175142a8a05721aa93/Image%20C.jpeg ) |

---

### 🟢 Authentication  
| Login Screen | Register Screen |
|--------------|-----------------|
| ![Login](https://github.com/Neha-Qasim/Mentora/blob/3332a39d7cbf3a859beaed175142a8a05721aa93/Image%202.jpeg ) | ![Register](https://github.com/Neha-Qasim/Mentora/blob/3332a39d7cbf3a859beaed175142a8a05721aa93/Image%203.jpeg ) |

---

### 🏠 Dashboard  
| Dashboard |
|-----------|
| ![Dashboard](https://github.com/Neha-Qasim/Mentora/blob/3332a39d7cbf3a859beaed175142a8a05721aa93/Dashboard%20Screen.png ) |

---

### 🎯 Quiz Module  
|Category Screen| Quiz Question | Quiz Result | Quiz History |
|----------------|---------------|-------------|---------------|
|![Category](https://github.com/Neha-Qasim/Mentora/blob/3332a39d7cbf3a859beaed175142a8a05721aa93/Quiz%20Screen.png )| ![Quiz](https://github.com/Neha-Qasim/Mentora/blob/3332a39d7cbf3a859beaed175142a8a05721aa93/Image%206.jpeg ) | ![Result](https://github.com/Neha-Qasim/Mentora/blob/3332a39d7cbf3a859beaed175142a8a05721aa93/Quiz%20Result.png ) | ![History](https://github.com/Neha-Qasim/Mentora/blob/3332a39d7cbf3a859beaed175142a8a05721aa93/Quiz%20History.png ) |

---

### 📝 Task Manager  
| Task List | Add/Edit Task | 
|-----------|---------------|
| ![Tasks](https://github.com/Neha-Qasim/Mentora/blob/89d28e2fa794ee9e268519f265f45c2482dd029d/Tasks%20Screen.png ) | ![AddTask](https://github.com/Neha-Qasim/Mentora/blob/89d28e2fa794ee9e268519f265f45c2482dd029d/Add%20Task.png ) |

---

### 💬 Chat  
| Chat Screen |
|-------------|
| ![Chat](https://github.com/Neha-Qasim/Mentora/blob/b33f38304aa2259af658037d8eba041814ac9a65/Chat%20Screen.jpeg ) |

---

### 👤 Profile  
| Profile Screen |
|----------------|
| ![Profile](https://github.com/Neha-Qasim/Mentora/blob/89d28e2fa794ee9e268519f265f45c2482dd029d/Profile%20Screen.png ) |


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
