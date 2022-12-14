<h1 align="center">Cars App</h1>
 
A simple Cars app shows available cars on map and list with modern Android tech-stacks and MVVM architecture. Fetching data from the network and the data via repository pattern.


## Download
Go to the [Download Link](https://drive.google.com/file/d/1F9n12t3PJDWuy4H_hSF2KQmxCUsP3xBr/view?usp=sharing) to download the latest APK.

## Screenshots
<p align="center">
<img src="/preview/preview.png" width="32%"/>
<img src="/preview/preview1.png" width="32%"/>
<img src="/preview/preview2.png" width="32%"/>
<img src="/preview/preview3.png" width="32%"/>
<img src="/preview/preview4.png" width="32%"/>
</p>

## Tech stack & Open-source libraries
- Minimum SDK level 23
- 100% [Kotlin](https://kotlinlang.org/) based + [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- Hilt for dependency injection.
- JetPack
  - Lifecycle - dispose observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
- Architecture
  - MVVM Architecture (View - ViewModel - Model)
  - Repository pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging network data.
