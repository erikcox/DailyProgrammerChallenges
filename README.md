# *Daily Programmer Challenges*

**Daily Programmer Challenges** is an Android app that allows to browse the [/r/dailyprogrammer](https://www.reddit.com/r/dailyprogrammer) subreddit.

## User Stories

The following **required** functionality is completed:

### Core Platform Development
* [ ] App integrates a third-party library.

* [ ] App validates all input from servers and users. If data does not exist or is in the wrong format, the app logs this fact and does not crash.

* [ ] App includes support for accessibility. That includes content descriptions, navigation using a D-pad, and, if applicable, non-audio versions of audio cues.

* [ ] App keeps all strings in a strings.xml file and enables RTL layout switching on all layouts.

* [ ] App provides a widget to provide relevant information to the user on the home screen.

### Google Play Services

* [ ] App integrates two or more Google services. Google service integrations can be a part of Google Play Services or Firebase.

* [ ] Each service imported in the build.gradle is used in the app.

* [ ] If Location is used, the app customizes the user’s experience by using the device's location.

* [ ] If Admob is used, the app displays test ads. If Admob was not used, student meets specifications.

* [ ] If Analytics is used, the app creates only one analytics instance. If Analytics was not used, student meets specifications.

* [ ] If Maps is used, the map provides relevant information to the user. If Maps was not used, student meets specifications.

* [ ] If Identity is used, the user’s identity influences some portion of the app. If Identity was not used, student meets specifications.

### Material Design

* [ ] App theme extends AppCompat.

* [ ] App uses an app bar and associated toolbars.

* [ ] App uses standard and simple transitions between activities.

### Building

* [ ] App builds from a clean repository checkout with no additional configuration.

* [ ] App builds and deploys using the installRelease Gradle task.

* [ ] App is equipped with a signing configuration, and the keystore and passwords are included in the repository. Keystore is referred to by a relative path.

* [ ] All app dependencies are managed by Gradle.

### Data Persistence

* [ ] App stores data locally either by implementing a ContentProvider OR using Firebase Realtime Database. No third party frameworks may be used.

* [ ] Must implement at least one of the three

* [ ] If it regularly pulls or sends data to/from a web service or API, app updates data in its cache at regular intervals using a SyncAdapter or JobDispacter.

**OR**

* [ ] If it needs to pull or send data to/from a web service or API only once, or on a per request basis (such as a search application), app uses an IntentService to do so.

**OR**

* [ ] It it performs short duration, on-demand requests(such as search), app uses an AsyncTask.

* [ ] App uses a Loader to move its data to its views.

The following **optional** features are implemented:
* [ ] 
* [ ] 
* [ ] 

## Video Walkthrough 

Here's a walkthrough of implemented user stories:
!['Video Walkthrough'](walkthrough.gif)

## Notes

* 
* 
* 
* 

## Mockups
!['All challenges'](01_All_Challenges.png)
!['Navigation drawer'](02_Navigation_Drawer.png)
!['Sort options'](03_Sort_Options.png)
!['Share'](04_Share.png)
!['Detail problem'](05_Detail_Problem.png)
!['Detail solution'](06_Detail_Solution.png)
!['Saved'](07_Saved.png)
!['Completed'](08_Completed.png)
!['About'](09_About.png)
!['Widget'](10_Widget.png)

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
