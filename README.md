# *Daily Programmer Challenges*

**Daily Programmer Challenges** is an Android app that allows to browse the [/r/dailyprogrammer](https://www.reddit.com/r/dailyprogrammer) subreddit.

## User Stories

The following **required** functionality is completed:

### Core Platform Development
* [x] App integrates a third-party library.

* [x] App validates all input from servers and users. If data does not exist or is in the wrong format, the app logs this fact and does not crash.

* [x] App includes support for accessibility. That includes content descriptions, navigation using a D-pad, and, if applicable, non-audio versions of audio cues.

* [x] App keeps all strings in a strings.xml file and enables RTL layout switching on all layouts.

* [x] App provides a widget to provide relevant information to the user on the home screen.

### Google Play Services

* [x] App implements Google Analytics service

* [x] App implements Google Ads service

* [x] Each service imported in the build.gradle is used in the app.

### Material Design

* [x] App theme extends AppCompat.

* [x] App uses an app bar and associated toolbars.

* [x] App uses standard and simple transitions between activities.

### Building

* [x] App builds from a clean repository checkout with no additional configuration.

* [x] App builds and deploys using the installRelease Gradle task.

* [x] App is equipped with a signing configuration, and the keystore and passwords are included in the repository. Keystore is referred to by a relative path.

* [x] All app dependencies are managed by Gradle.

### Data Persistence

* [x] App stores data locally either by implementing a ContentProvider OR using Firebase Realtime Database. No third party frameworks may be used.

* [x] It it performs short duration, on-demand requests(such as search), app uses an AsyncTask.

* [x] App uses a Loader to move its data to its views.

The following **optional** features are implemented:

* [x] App uses `Fabric` & `Crashlytics` for metrics

* [x] App uses `Stetho` for db and network inspection
 
* [x] App uses `Timber` for logging and uses build variants to determine which logs to output 

* [x] App uses `JRAW` library and `gson` for reddit processing 

* [x] App uses `Active Android` for DB persistence 

* [x] App uses `Retrofit` for handling network requests 

## Notes

* In order to get Crashlytics to work, you need a `fabric.properties` file at the root of the project with your Fabric secret key

## Mockups
!['All challenges'](/mocks/01_All_Challenges.png)
!['Navigation drawer'](/mocks/02_Navigation_Drawer.png)
!['Sort options'](/mocks/03_Sort_Options.png)
!['Share'](/mocks/04_Share.png)
!['Detail problem'](/mocks/05_Detail_Problem.png)
!['Detail solution'](/mocks/06_Detail_Solution.png)
!['Saved'](/mocks/07_Saved.png)
!['Completed'](/mocks/08_Completed.png)
!['About'](/mocks/09_About.png)
!['Widget'](/mocks/10_Widget.png)

## License

    Copyright [2017] [Erik Cox]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
