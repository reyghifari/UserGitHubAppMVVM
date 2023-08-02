<h1 align="center">UserGithubAppMVVM</h1>

<p align="center">
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a>
  <a href="https://app.circleci.com/pipelines/github/reyghifari/UserGitHubAppMVVM"><img alt="Build Status" src="https://github.com/skydoves/Pokedex/workflows/Android%20CI/badge.svg"/></a> <br>
</p>

<p align="center">  
UserGithubAppMVVM demonstrates modern Android development with Multi Modular, Koin, Coroutines, Flow, Jetpack (Room, ViewModel), and Material Design based on MVVM architecture.
</p>
</br>

## Open API

UserGithubAppMVVM using the [GithubApi]([https://pokeapi.co/](https://api.github.com/)) for constructing RESTful API.<br>
GithubApi provides a RESTful API interface to highly detailed objects built from thousands of lines of data related to Github.

### Continuous Integration

UserGithubAppMVVM using Circle ci, Is a service to create continuous integration. Interestingly, apart from using your own server, you can also use the hosting that has been provided. For configuration you can also use YAML. In addition, there is also a ready-to-use docker image. This service can also be directly integrated with Github and Bitbucket quickly

![architecture](readphoto/circleci.png)

## Architecture
**UserGithubAppMVVM** is based on the MVVM architecture and the Repository pattern, which follows the [Google's official architecture guidance](https://developer.android.com/topic/architecture).

![architecture](readphoto/figure0.png)

### UI Layer

![architecture](readphoto/figure2.png)

### Data Layer

![architecture](readphoto/figure3.png)

### Encryption

Implement encryption on Room Database with SQLCipher on UserGithubAppMVVM.

![architecture](readphoto/encription.png)

### Multi Module

The use of multi-modules in software development provides advantages in separation of functionality, dependency management, reusability, team collaboration, testing and scalability.

-Module Favorite User: This module is responsible for managing the list of user favorites in the application. <br>
-Module Core: This module serves as the core of the application and contains components that can be used by other modules.<br>
-Module App: This module is the main application module that combines and uses the functionality of the Favorite User and Core modules.<br>

![architecture](readphoto/modular.png)




