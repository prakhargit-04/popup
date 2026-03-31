<div align="center">

<img src="https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white"/>
<img src="https://img.shields.io/badge/Language-Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"/>
<img src="https://img.shields.io/badge/Build-Gradle%20KTS-02303A?style=for-the-badge&logo=gradle&logoColor=white"/>
<img src="https://img.shields.io/badge/AndroidX-Enabled-4285F4?style=for-the-badge&logo=google&logoColor=white"/>
<img src="https://img.shields.io/badge/Status-In%20Development-yellow?style=for-the-badge"/>

<br/><br/>

# 📬 PRIION — Android

### AI-powered email prioritization. Now on Android.

PRIION for Android brings intelligent, real-time email triage to your phone — so you only ever see what actually matters.

[🌐 Web App](https://email-4m6b.vercel.app) · [📁 GitHub](https://github.com/prakhargit-04) · [💼 LinkedIn](https://linkedin.com/in/prakharbhardwajpb)

</div>

---

## 🧠 What is PRIION?

PRIION is an AI-powered email prioritization platform that uses **Google Gemini** to classify your inbox in real time — separating what's urgent from what's noise.

This repository is the **native Android app** for PRIION. It surfaces high-priority email alerts as smart popups directly on your phone, without you having to open your inbox.

> Built as part of the PRIION ecosystem alongside the [Next.js web platform](https://email-4m6b.vercel.app).

---

## ✨ Features

- 🔔 **Smart Popup Alerts** — Real-time overlays for AI-classified high-priority emails
- 🤖 **Gemini-Powered Filtering** — Same AI engine as the PRIION web app
- 📱 **Native Android Experience** — Lightweight, fast, built with AndroidX
- 🔗 **Seamless Handoff** — Deep links straight into the full PRIION web app
- 🔒 **Privacy First** — No email content stored locally on device

---

## 🏗️ Tech Stack

| Layer | Technology |
|-------|------------|
| Language | Java |
| Build System | Gradle (Kotlin DSL) |
| Architecture | AndroidX |
| Backend | PRIION API (Next.js + Google Gemini) |
| Min SDK | Android 8.0+ (API 26) |

---

## 🚀 Getting Started

### Prerequisites

- **Android Studio** Hedgehog (2023.1.1) or later
- **JDK 17+**
- A PRIION account → [email-4m6b.vercel.app](https://email-4m6b.vercel.app)

### Setup

```bash
git clone https://github.com/prakhargit-04/popup.git
cd popup
```

1. Open in **Android Studio**
2. Wait for Gradle sync to complete
3. Connect a device or start an emulator (API 26+)
4. Hit **▶ Run**

---

## 📁 Project Structure

```
PRIION (Android)/
├── app/
│   └── src/main/
│       └── java/com/example/priion/
│           └── MainActivity.java        # Core popup & notification logic
├── gradle/                              # Gradle wrapper configs
├── build.gradle.kts                     # Root build config
├── settings.gradle.kts                  # Project settings
└── gradle.properties                    # JVM & AndroidX flags
```

---

## 🌐 Full PRIION Ecosystem

| Platform | Description | Link |
|----------|-------------|------|
| 🌐 Web App | Next.js + Gemini AI email prioritization | [Live](https://email-4m6b.vercel.app) |
| 📱 Android | This repo — native popup notifications | You are here |
| ☕ Anti-Gravity Cafe | Full-stack cafe management system | [GitHub](https://github.com/prakhargit-04/internet-cafe) |

---

## 🛣️ Roadmap

- [ ] Firebase Cloud Messaging (FCM) for background push delivery
- [ ] OAuth login with Google — sync directly with PRIION account
- [ ] Priority threshold settings (e.g. only alert on Score ≥ 8/10)
- [ ] Notification grouping & snooze
- [ ] Home screen widget — glanceable priority inbox

---

## 🤝 Contributing

Pull requests are welcome. For major changes, open an issue first to discuss what you'd like to change.

---

## 👤 Author

**Prakhar Bhardwaj** — B.Tech CSE @ VIT Chennai

[![LinkedIn](https://img.shields.io/badge/LinkedIn-prakharbhardwajpb-0077B5?style=flat&logo=linkedin)](https://linkedin.com/in/prakharbhardwajpb)
[![GitHub](https://img.shields.io/badge/GitHub-prakhargit--04-181717?style=flat&logo=github)](https://github.com/prakhargit-04)

---

<div align="center">
  <sub>PRIION — because not every email deserves your attention.</sub>
</div>
