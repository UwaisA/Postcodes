# Postcodes

Postcodes is an efficient library for validating and formatting postcodes.
The library's interface is UI focused - providing tools for common UI tasks
such as formatting postcodes that have been typed into a text field and 
validating as a user types their postcode. While it is has been created with
user interfaces in mind the library can be used in any project that supports
Java. The library supports 180 countries across the world and works 100%
locally - no internet required.

### Usage
A simple use case (written in Kotlin) is as follows:
```kotlin
val postcodes = PostcodeUtil(Locale.US)
val isValid = postcodes.isValidPostcode("12345") // returns true

//returns a PostcodeResult object containing a formatted postcode string - "123" and other information
val formattedPostcode: PostcodeResult = postcodes.format("12 3")
```
The library can also handle a cursor allowing the user's typing to be unaffected
by changes to the postcode string before or after their cursor. The `PostcodeResult`
object contains information such as if it was not possible to format the
postcode or where in the postcode string the cursor should be after formatting.

### Reporting issues
To report a bug or request a feature please raise a GitHub issue
[here](https://github.com/UwaisA/postcodes/issues)

### Contributing
Building Postcodes with Gradle is quite simple:
``` 
git clone https://github.com/UwaisA/Postcodes.git
cd Postcodes
./gradlew jar
```
Editing and viewing the source code can be done in any editor that supports
Kotlin for example IntelliJ IDEA available [here](https://www.jetbrains.com/idea/download). 
Please fork the GitHub repository and make Pull Requests to contribute improvements.
