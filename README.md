<div align="center">

<img src="https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white"/>
<img src="https://img.shields.io/badge/Language-Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"/>
<img src="https://img.shields.io/badge/Build-Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white"/>
<img src="https://img.shields.io/badge/Status-In%20Development-yellow?style=for-the-badge"/>

<br/><br/>

# 📬 PRIION — Android Companion

**Never miss a critical email again.**  
PRIION's Android companion delivers real-time popup notifications for your highest-priority emails — powered by the same AI engine that runs [PRIION Web](https://email-4m6b.vercel.app).

</div>

---

## 🧠 What is this?

This is the **native Android companion app** for [PRIION](https://email-4m6b.vercel.app) — an AI-powered email prioritization platform. While the web app handles classification and inbox management, this Android app surfaces **instant popup alerts** for emails that the AI marks as high priority, so you're always in the loop — even away from your desk.

> Think of it as: *Gmail notifications, but your AI decides what actually matters.*

---

## ✨ Features

- 🔔 **Smart Popup Notifications** — Real-time overlays triggered by PRIION's AI priority engine
- 🤖 **AI-Driven Filtering** — Only surfaces emails classified as urgent or important
- 🔗 **Deep Link to Web App** — Tap a notification to open the full email thread in PRIION
- ⚡ **Lightweight** — Minimal footprint; runs as a background service

---

## 🏗️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java |
| Build System | Gradle (Kotlin DSL) |
| Minimum SDK | Android 8.0+ (API 26) |
| Backend | PRIION API (Next.js + Gemini AI) |

---

## 🚀 Getting Started

### Prerequisites

- Android Studio **Hedgehog** or later
- JDK 17+
- A PRIION account at [email-4m6b.vercel.app](https://email-4m6b.vercel.app)

### Clone & Run

```bash
git clone https://github.com/prakhargit-04/popup.git
cd popup
```

1. Open the project in **Android Studio**
2. Let Gradle sync complete
3. Connect your Android device or start an emulator (API 26+)
4. Hit **Run ▶**

---

## 📁 Project Structure

```
popup/
├── app/
│   └── src/
│       └── main/
│           └── java/com/example/priion/
│               └── MainActivity.java       # Entry point & popup logic
├── gradle/                                 # Gradle wrapper
├── build.gradle.kts                        # Root build config
└── settings.gradle.kts
```

---

## 🔗 Related Projects

| Project | Description | Link |
|---------|-------------|------|
| PRIION Web | AI email prioritization platform (Next.js + Gemini) | [Live](https://email-4m6b.vercel.app) · [GitHub](https://github.com/prakhargit-04) |
| Anti-Gravity Cafe | Full-stack cafe management system | [GitHub](https://github.com/prakhargit-04/internet-cafe) |

---

## 🛣️ Roadmap

- [ ] Firebase Cloud Messaging (FCM) integration for push delivery
- [ ] Customizable priority threshold in app settings
- [ ] Biometric lock for sensitive email previews
- [ ] Widget support for home screen priority inbox

---

## 👤 Author

**Prakhar Bhardwaj**  
B.Tech CSE @ VIT Chennai

[![LinkedIn](https://img.shields.io/badge/LinkedIn-Connect-0077B5?style=flat&logo=linkedin)](https://linkedin.com/in/prakharbhardwajpb)
[![GitHub](https://img.shields.io/badge/GitHub-Follow-181717?style=flat&logo=github)](https://github.com/prakhargit-04)

---

<div align="center">
  <sub>Part of the PRIION ecosystem — built to make email less overwhelming.</sub>
</div>
