# FindMeaning
### Android Application- *Java*

![displayPic](https://github.com/momin-sana/FindMeaning/assets/92250920/449d73fa-2073-4029-abf4-99ce0f1d9832)

FindMeaning is a dictionary app that uses Retrofit to fetch data from an API (`https://dictionaryapi.dev/`) and display it in a RecyclerListView. The data includes a list of phonetics with audio. Here words can be bookmarked and the history of searched words will be stored in Database. An Internet connection is mandatory to access the meaning of words.

### Features:
* Splash screen with animation: gives users a visually appealing introduction to the app
* Navigation bar: allows users to switch between different sections of the app easily
* Search bar: lets users search for words and their meanings quickly
* Custom AlertDialog to exit the app: ensures that users can exit the app quickly and safely
* LottieAnimationView progress bar: lets users know when data is being loaded and displayed
* RecyclerListView: allows users to scroll through a list of words and their meanings easily
* FragmentTransaction: Multiple fragments are used, i.e. MainFragment, DefinitionFragment, and BottomFragment.
* Model class, Adapters: These are used in corresponding with Database and RecylcerView
* Database: Two databases are used, to store word and other for notes.

### How to use:
1. Clone this repository to your local machine
2. Open the project in Android Studio
3. Build and run the app on an emulator or physical device
4. Use the navigation bar to switch between different sections of the app
5. Use the search bar to search for words and their meanings/

### Contributing:
If you would like to contribute to this project, please follow these steps:

1. Fork this repository
2. Create a new branch with your changes
4. Make your changes and commit them
5. Push your changes to your forked repository
6. Submit a pull request to this repository with your changes
