# Encryption Decryption Project in Java (Caesar cipher algorithm)

August 2024

This project was build to learn basics of encryption and decryption in Java
with [Hyperskill](https://hyperskill.org/).

Project uses *command-line-arguments*, passed by a user. These arguments determine the behaviour of the program.
The encryption and decryption will be controlled by a key, a special parameter that controls the behavior of our
encryption algorithm.

By passing command-line-arguments, user can:

* choose between encryption or decryption
* pass the message as an argument or inside text file
* output encrypted/decrypted message in console or write to a text file
* set a key for encryption-decryption algorithm
* choose between different encryption-decryption algorithms

## Arguments

The program must parse arguments: `-mode`, `-data`, `-key`, `-alg`, `-in`, `-out`.

Arguments have to be passes like key-value pairs, for example:
`-mode enc -data "Text to encrypt" -key 5`

User **must** pass at least the `-data` argument, otherwise program will print
`"Error in read data"` message in a console.

### Arguments description and argument options:
Arguments can be passed in different order than shown below. 
Argument and its parameter has to be separated by a single space.

+ `-mode` argument allows to choose between encryption and decryption mode of the program
  and can accept parameter `enc` or `dec`:
    + `enc` - encryption mode
    + `dec` - decryption mode
    + **default** value for `-mode` is `enc`
+ `-data` argument accepts the string to encrypt/decrypt
    + if string is longer than one word, it must be inside double quotes - `"long message to encrypt"`
+ `-key` is an integer key to modify the message
+ `-alg` arguments allows to choose between two different encryption-decryption algorithms and
  can accept parameter `shift` or `unicode`:
    + `shift` uses only **english alphabet** for encryption-decryption
        + this parameter will encrypt/decrypt only characters from english alphabet
    + `unicode` uses Unicode Table for encryption-decryption
+ `-in` argument accepts the file name to read text from
    + file should be a text file
    + if both `-data` and `-in` arguments are passed, program reads text from `-data` **as default**
    + if file doesn't exist, prints `Error in read data` message to the console
+ `-out` argument accepts the file name to write text to
    + if file with given file name already exists, overrides this file
    + if `-out` argument is **NOT** passed, program will print encrypted/decrypted
      text to the console

### **Examples**:

**Example 1:** 

encryption; the arguments are: `-mode enc -key 5 -data "Welcome to hyperskill!"`

    Output to the console: \jqhtrj%yt%m~ujwxpnqq&

**Example 2:** 

decryption; the arguments are: `-key 5 -data "\jqhtrj%yt%m~ujwxpnqq&" -mode dec`

    Output to the console: Welcome to hyperskill!

**Example 3:** 

reading and writing to files; the arguments are: `-mode enc -in road_to_treasure.txt -out protected.txt -key 5 -alg unicode`

    This command must get data from road_to_treasure.txt, encrypt the data with the key of 5, 
    create protected.txt, and write ciphertext into it.

## How to run a program
1. Install [Java JDK](https://www.oracle.com/java/technologies/downloads/#java21) version 11 or later
2. Clone project from GitHub `git clone https://github.com/ArMazur/Encryption-Decryption-Java`
3. Change directory to project folder `cd Encryption-Decryption-Java`
4. Change directory to src folder `cd src`
5. Compile Main class `javac encryptdecrypt/Main.java`
6. Run program with arguments (example with args) 
   `java encryptdecrypt/Main -key 5 -data "Welcome to Encryption-Decryption Project!" -mode enc -alg unicode`
    
        Console output: aovmywo*~y*Oxm|z~syx7Nom|z~syx*Z|ytom~+


## Links
Here's the link to the project: https://hyperskill.org/projects/46

Check out my hyperskill profile: https://hyperskill.org/profile/614203111

