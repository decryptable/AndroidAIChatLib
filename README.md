# AndroidAIChatLib

AndroidAIChatLib is a Java library designed to integrate AI-based chat functionality into Android
applications. This library provides an easy-to-use interface for developers to add AI chat
capabilities to their apps.

## Installation

To use the library in your Android project, download the latest version of `chatai.jar` from
the [releases page](https://github.com/decryptable/AndroidAIChatLib/releases/latest).

Once downloaded, add the `chatai.jar` file to your Android project as follows:

1. Place the `chatai.jar` file in the `libs/` directory of your Android project.
2. In your `build.gradle` file, add the following to the `dependencies` block:

```gradle
dependencies {
    implementation files('libs/chatai.jar')
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