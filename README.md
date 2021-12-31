# Weather Forecast Readme

## Software development principles, patterns & practices

### Software development principles:

### For app architecture, I applied Clean Architecture + MVVM:

- Reference: https://proandroiddev.com/mvvm-with-clean-architecture-c2c021e05c89

- Reason:
  - Modern architecture and much more produtive because of adaption with latest Android library
  - Suitable for any app size
  - Long live technology and code is easy to understand, develope and maintain

### Clean code guildeline:

- Variable name should be meaningful
- Function just do one thing
- Class just serve for one actor
- Shouldn't have a large source code file
- Format code, optimize import, check error and warning to fix.

### Follow SOLID principles to design classes:

- S – Single Responsibility Principle.
- O – Open/Closed Principle.
- L – Liskov Substitution Principle.
- I – Interface Segregation Principle.
- D – Dependency Inversion Principle.

### Using RoomDB to cache data to local
- When start app, it will try to get data from local database if it exist
- When user click "Get Weather" button, app will get data from API and if success the result will be saved to local database 

### Apply security practises:

- Secure app from revert engineering
- Not allow to backup
- Checking root

### Using Observer pattern for data flow:

- Using LiveData between ViewModel and Activity

### Using Dependency injection and scope:

- Apply dependency for database and network

### Apply data binding for save time and reduce code

- Data binding for activity
- Data binding for adapter

## Code structure

```

│
└───com.example.weatherapp
   │
   └───common: This package contains common data class and constants using in app
   │
   └───data: This package contains data related functionalities.
   │     │
   │     └───database: This package contains related coponents for local database
   │     │   │
   │     │   └───entities: This package contains all tables in database
   │     │
   │     └───network: This package contains API Fetching Services
   │     │
   │     └───repository: This package contains classes which act as data providers for other layers
   │
   └───di: This package contains modules and providers for Dependency Injection
   │
   └───domain: 
   │     └───model: This package contains data models.
   │     │    │
   │     │    └───local: Include model for local database
   │     │    │
   │     │    └───remote: Include model │     │   for back-end (remote) data
   │     │
   │     └───usecases: This packages contains all business uses cases or implementation
   │
   └───util: This package contains utilities functions.
   │
   └───presentation: This package contains the activity or fragments and it viewmodel based on features
```

## Java/Kotlin libraries and frameworks:

- Architecture: Clean + MVVM
- Dependency Injection: Hilt
- Network call: Retrofit + OkHttp
- Json parser: Gson
- Cache and local database: ROOM
- Multi-thread: Kotlin Coroutine
- Observer data: LiveData + Lifecycle
- Decompile apk and rooted detection: using code and proguard
- Binding: Data Binding

## How to run?

- Pull project
- Go to "Build -> Clean Project" to clean project 
- Build debug apk and install on device:
  ```
  Build -> Build Bundle(s)/APK(s) -> Build APK(s)
  ```
- Or build the APK and immediately install it on a running emulator or connected device by click run app.

## Checklist

- [x] Programming language: Kotlin is required, Java is optional.
- [x] Design app's architecture (Using MVVM)
- [x] Apply LiveData mechanism
- [x] UI should look like attachment
- [x] Write UnitTests
- [x] Secure Android app from:
  - [x] Decompile APK
  - [x] Rooted device
- [x] Exception handling
