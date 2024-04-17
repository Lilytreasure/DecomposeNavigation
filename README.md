# DecomposeNavigation
Compose Multiplatform Bottom Navigation with Decompose<br>

This is a Kotlin Multiplatform project targeting Android, iOS.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…<br>

libraries
#Decompose<br>
What is Decompose?¶<br>
Decompose is a Kotlin Multiplatform library for breaking down your code into lifecycle-aware business logic components (aka BLoC), with routing functionality and pluggable UI (Jetpack Compose, Android Views, SwiftUI, JS React, etc.).<br>
https://arkivanov.github.io/Decompose/getting-started/quick-start/<br>
https://github.com/arkivanov/Decompose<br>
Also includes Depedency injection with Koin and examples of how to access Device Specific APIs<Br>
#Android

 <table>
  <tr>
   <td><img src="https://github.com/Lilytreasure/DecomposeNavigation/assets/78819932/bd46da3d-6953-4b5e-be84-653f6975d536.png" alt="Home" style="width:250px;height:500px;"></td>
   <td><img src="https://github.com/Lilytreasure/DecomposeNavigation/assets/78819932/c1a4567e-c22b-4553-b0ec-aea90278731e.png" alt="Buy" style="width:250px;height:500px;"></td>
  </tr>
    <tr> 
   <td><img src="https://github.com/Lilytreasure/DecomposeNavigation/assets/78819932/a109ae4c-158a-4946-9be7-7742d8c315dc.png" alt="About" style="width:250px;height:500px;"></td>
   <td><img src="https://github.com/Lilytreasure/DecomposeNavigation/assets/78819932/614aada6-a79b-46bb-907b-0dfe4137a9d0.png" alt="About" style="width:250px;height:500px;"></td>
  </tr>
      <tr> 
   <td><img src="https://github.com/Lilytreasure/DecomposeNavigation/assets/78819932/41ee27df-1d3a-4bde-a229-3c2690d69569.png" alt="Call" style="width:250px;height:500px;"></td>
  </tr>
</table><br>
<img width="434" alt="Screenshot 2024-04-17 at 16 00 28" src="https://github.com/Lilytreasure/DecomposeNavigation/assets/78819932/2aac792d-c034-4b8d-87ef-d6e8e5ce50dd">




#iOs
 <table>
  <tr>
   <td><img width="434" alt="Screenshot 2024-04-17 at 16 00 28" src="https://github.com/Lilytreasure/DecomposeNavigation/assets/78819932/2aac792d-c034-4b8d-87ef-d6e8e5ce50dd"></td>
  </tr>
</table><br>


