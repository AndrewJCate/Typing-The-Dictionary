<a name="readme-top"></a>


<!-- PROJECT TITLE -->
<br />
<div align="center">

<h3 align="center">Typing the Dictionary</h3>

  <p align="center">
    A game that measures typing speed and accuracy while you type random dictionary entries.
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
        <li><a href="#uninstallation">Uninstallation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

Typing the Dictionary is a small JavaFX program where you type random dictionary entries. While typing your 
words-per-minute, accuracy and number of words typed are tracked and displayed.


### Built With

* [![Java][Java.com]][Java-url]
* [![JavaFX][JavaFX.com]][JavaFX-url]
* [![IntelliJIdea][IntelliJIdea.com]][IntelliJIdea-url]
* [![Notepad++][Notepad++.com]][Notepad++-url]
* [![GitHub][GitHub.com]][GitHub-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started


To get a local copy up and running follow these simple example steps.

### Prerequisites

You will need the following software installed to use this program.
* ***Java***
  
    From the command line, enter java –version to see if you have Java installed on your computer.

    If Java is installed, you will see something like:
    ```
    java version "17.0.5" 2022-10-18 LTS
    Java(TM) SE Runtime Environment (build 17.0.5+9-LTS-191)
    Java HotSpot(TM) 64-Bit Server VM (build 17.0.5+9-LTS-191, mixed mode, sharing)
    ```
    
    Java may not be installed if you see something like:
    ```
    'java' is not recognized as an internal or external command, operable program or batch file.
    ```

    You can follow the instructions [here](https://www.java.com/en/download/help/download_options.html) to download and install it.


* ***JavaFX***
    
    Most users don't have JavaFX installed. You might not have it installed if you see the following message when 
  you try to run the program from the ***command line***.
    ```
  JavaFX runtime components are missing, and are required to run this application
  ```

    Instructions to install JavaFX can be found [here](https://openjfx.io/openjfx-docs/).

### Installation

1. From the Git repo, download the [TypingTheDictionary.jar](https://github.com/AndrewJCate/Typing-The-Dictionary/blob/master/out/artifactsTypingTheDictionary_jar/TypingTheDictionary.jar) file located in the
   `out/artifacts/TypingTheDictionary_jar` folder.
2. There are two ways to run this file:
   1. Run the .jar file directly.
   2. Using the ***command line***
      1. Navigate to the directory where you downloaded the file.
      2. Type `java -jar TypingTheDictionary.jar` and press Enter.


### Uninstallation

To uninstall this program:
* Delete the .jar file.

_If you want to keep your global data in the event that you may later reinstall ***Typing the Dictionary***, do not 
delete the saved user data._

To delete the saved user data:
1. Navigate to your User home directory:
   * Windows: `\Users\<username>`
   * Mac: `/Users/<username>`
   * Linux: `/home/<username>` or `/usr/home/<username>`
2. Delete the folder `.typingthedictionary`.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

### Session Data
 
Session data is the data from the current play session. When you are typing dictionary entries, you will see session 
data.

### Global Data

Global data is the data from all your play sessions combined. On the starting screen of the program, global data is displayed, so you can see your overall statistics.

### WPM Calculation
Words-per-minute is calculated using an average word length of 5 letters, not your actual word count. The 
calculation also factors in your accuracy. The less accurate you are, the lower the WPM.
This is typically how WPM calculations are done.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ROADMAP -->
## Roadmap

- [ ] Scalable, resizable GUI.
- [ ] Confirmation before resetting user data.
- [ ] Reviewing instructions does not reset current displayed entry.
- [ ] Button to flag dictionary entry for review, with notes feature.
- [ ] Function to upload own dictionary files and select between dictionaries (checkbox, to use multiple 
  dictionaries simultaneously).

See the [open issues](https://github.com/AndrewJCate/Typing-The-Dictionary/issues) for a full list of proposed features (and known issues).

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Andrew Cate - [LinkedIn](https://www.linkedin.com/in/andrewjcate/) - [Website](https://www.andrewjcate.com)


Project Link: [https://github.com/AndrewJCate/Typing-The-Dictionary](https://github.com/AndrewJCate/Typing-The-Dictionary)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

* [README Template](https://github.com/othneildrew/Best-README-Template/blob/master/BLANK_README.md)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
[contributors-shield]: https://img.shields.io/github/contributors/github_username/repo_name.svg?style=for-the-badge
[contributors-url]: https://github.com/github_username/repo_name/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/github_username/repo_name.svg?style=for-the-badge
[forks-url]: https://github.com/github_username/repo_name/network/members
[stars-shield]: https://img.shields.io/github/stars/github_username/repo_name.svg?style=for-the-badge
[stars-url]: https://github.com/github_username/repo_name/stargazers
[issues-shield]: https://img.shields.io/github/issues/github_username/repo_name.svg?style=for-the-badge
[issues-url]: https://github.com/github_username/repo_name/issues
[license-shield]: https://img.shields.io/github/license/github_username/repo_name.svg?style=for-the-badge
[license-url]: https://github.com/github_username/repo_name/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/andrewjcate/

[AWS-url]: https://aws.amazon.com/
[AWS.com]: https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white
[Anaconda-url]: https://anaconda.org/conda-forge/nodejs
[Anaconda.com]: https://img.shields.io/badge/Anaconda-%2344A833.svg?style=for-the-badge&logo=anaconda&logoColor=white
[AndroidStudio-url]: https://developer.android.com/
[AndroidStudio.com]: https://img.shields.io/badge/android%20studio-346ac1?style=for-the-badge&logo=android%20studio&logoColor=white
[Angular-url]: https://angular.io/
[Angular.io]: https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white
[Bootstrap-url]: https://getbootstrap.com
[Bootstrap.com]: https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white
[CSS3-url]: https://www.w3.org/
[CSS3.com]: https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white
[Eclipse-url]: https://www.eclipse.org/
[Eclipse.com]: https://img.shields.io/badge/Eclipse-FE7A16.svg?style=for-the-badge&logo=Eclipse&logoColor=white
[Gimp-url]: https://www.gimp.org/
[Gimp.com]: https://img.shields.io/badge/Gimp-657D8B?style=for-the-badge&logo=gimp&logoColor=FFFFFF
[GitHub-url]: https://github.com/
[GitHub.com]: https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white
[GitHubPages-url]: https://pages.github.com/
[GitHubPages.com]: https://img.shields.io/badge/github%20pages-121013?style=for-the-badge&logo=github&logoColor=white
[HTML5-url]: https://whatwg.org/
[HTML5.com]: https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white
[Hibernate-url]: https://hibernate.org/
[Hibernate.com]: https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white
[IntelliJIdea-url]: https://www.jetbrains.com/idea/
[IntelliJIdea.com]: https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white
[Invision-url]: https://www.invisionapp.com/
[Invision.com]: https://img.shields.io/badge/invision-FF3366?style=for-the-badge&logo=invision&logoColor=white
[JQuery-url]: https://jquery.com
[JQuery.com]: https://img.shields.io/badge/jQuery-0769AD?style=for-the-badge&logo=jquery&logoColor=white
[Jasmine-url]: https://jasmine.github.io/
[Jasmine.com]: https://img.shields.io/badge/jasmine-%238A4182.svg?style=for-the-badge&logo=jasmine&logoColor=white
[Java-url]: https://java.com/
[Java.com]: https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white
[JavaFX-url]: https://openjfx.io/
[JavaFX.com]: https://img.shields.io/badge/javafx-%23FF0000.svg?style=for-the-badge&logo=javafx&logoColor=white
[JavaScript-url]: https://www.javascript.com/
[JavaScript.com]: https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E
[JupyterNotebook-url]: https://jupyter.org/
[JupyterNotebook.com]: https://img.shields.io/badge/jupyter-%23FA0F00.svg?style=for-the-badge&logo=jupyter&logoColor=white
[MySQL-url]: https://www.mysql.com/
[MySQL.com]: https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white
[NPM-url]: https://www.npmjs.com/
[NPM.com]: https://img.shields.io/badge/NPM-%23CB3837.svg?style=for-the-badge&logo=npm&logoColor=white
[NetBeans-url]: https://netbeans.apache.org/front/main/index.html
[NetBeans.com]: https://img.shields.io/badge/NetBeansIDE-1B6AC6.svg?style=for-the-badge&logo=apache-netbeans-ide&logoColor=white
[NodeJS-url]: https://nodejs.org
[NodeJS.com]: https://img.shields.io/badge/node.js-6DA55F?style=for-the-badge&logo=node.js&logoColor=white
[Notepad++-url]: https://notepad-plus-plus.org/
[Notepad++.com]: https://img.shields.io/badge/Notepad++-90E59A.svg?style=for-the-badge&logo=notepad%2b%2b&logoColor=black
[Postman-url]: https://www.postman.com/
[Postman.com]: https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white
[PyCharm-url]: https://www.jetbrains.com/pycharm/
[PyCharm.com]: https://img.shields.io/badge/pycharm-143?style=for-the-badge&logo=pycharm&logoColor=black&color=black&labelColor=green
[Python-url]: https://code.visualstudio.com/
[Python.com]: https://img.shields.io/badge/python-3670A0?style=for-the-badge&logo=python&logoColor=ffdd54
[React-url]: https://reactjs.org/
[React.js]: https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB
[RxJS-url]: https://rxjs.dev/
[RxJS.com]: https://img.shields.io/badge/rxjs-%23B7178C.svg?style=for-the-badge&logo=reactivex&logoColor=white
[Spring-url]: https://spring.io/
[Spring.com]: https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white
[Tailwind-url]: https://tailwindcss.com/
[Tailwind.com]: https://img.shields.io/badge/tailwindcss-%2338B2AC.svg?style=for-the-badge&logo=tailwind-css&logoColor=white
[Thymeleaf-url]: https://www.thymeleaf.org/
[Thymeleaf.com]: https://img.shields.io/badge/Thymeleaf-%23005C0F.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white
[TypeScript-url]: https://www.typescriptlang.org/
[TypeScript.com]: https://img.shields.io/badge/typescript-%23007ACC.svg?style=for-the-badge&logo=typescript&logoColor=white
[VSCode-url]: https://code.visualstudio.com/
[VSCode.com]: https://img.shields.io/badge/Visual%20Studio%20Code-0078d7.svg?style=for-the-badge&logo=visual-studio-code&logoColor=white