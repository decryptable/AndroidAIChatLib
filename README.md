# gpt-lib

![JitPack](https://img.shields.io/jitpack/version/com.github.decryptable/gpt-lib?style=for-the-badge&label=com.github.decryptable%3Agpt-lib&link=https%3A%2F%2Fjitpack.io%2F%23decryptable%2Fgpt-lib%2F1.0.0) ![GitHub Actions Workflow Status](https://img.shields.io/github/actions/workflow/status/decryptable/gpt-lib/publish.yml?branch=master&style=for-the-badge&label=Publish%20Status&link=https%3A%2F%2Fgithub.com%2Fdecryptable%2Fgpt-lib%2Factions%2Fworkflows%2Fpublish.yml)


**`gpt-lib`** is a Java library designed to integrate AI-based chat functionality into Android
applications. This library provides an easy-to-use interface for developers to add AI chat
capabilities to their apps.

## Installation

To use the library in your Android project, download the latest version of `chatai.jar` from
the [releases page](https://github.com/decryptable/AndroidAIChatLib/releases/latest).

## Installation (recommended)
1. Add the JitPack repository to your build file

    ```gradle
    dependencyResolutionManagement {
        repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
        repositories {
            mavenCentral()
            maven { url 'https://jitpack.io' }
        }
    }
    ```

2. Add the dependency

    ```gradle
    dependencies {
        implementation 'com.github.decryptable:gpt-lib:1.0.0'
    }
    ```
   
## Usage

To use the library in your project, refer to the example code
in [`MainActivity.java`](app/src/main/java/com/decryptable/chatai/MainActivity.java).

In this example, `ChatAI` represents the main class that facilitates the chat functionality. You can
customize the usage according to your needs.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contributing

Feel free to fork the repository, make changes, and create pull requests. Contributions are welcome!
