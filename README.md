
# GOD - Goal of the day

GOD - Goals of the day

### Problem Statement: 
People always face problems in finding their goals and keeping track over a period. Thoughts come and evaporate and forget instantly if we don’t write it immediately. But people don’t always carry pen and paper with them. Consider the case while you traveling somewhere and observe a problem that you can solve or the most important task that comes in your mind. If you don’t have a pen/paper to write it down you can use a mobile to write in a note. 

But not all mobile devices have preinstalled notes app. Preinstalled notes app are very simple. Just doing work of writing text. If you have more than one task then you must prioritize them to effectively use your day and complete the most important task first. 

### Proposed Solution : 
Goals of the day (GOD) app provides features to keep track of all your daily tasks and monthly tasks like the list of all tasks of the day, most important tasks from all your tasks, your monthly tasks/goals. Colors used in app emphasize the priority of all tasks. User can easily add their monthly tasks and daily tasks which are required to reach or complete monthly tasks.

Once the task is completed you can update the task as completed. After a month you can check how many goals you have achieved. Once the task is completed you can delete the task from monthly and daily tasks.


## Screenshots


| <img src = "https://github.com/TechTalkerShubh/GOD/blob/3b5cead14c8bbd519f81eeab7ed404356e3d9e8f/GOD%20-%20Assets/13.png" width="230" height="400" /> 
 <img src = "https://github.com/TechTalkerShubh/GOD/blob/3b5cead14c8bbd519f81eeab7ed404356e3d9e8f/GOD%20-%20Assets/14.png" width="230" height="400" /> 
<img src = "https://github.com/TechTalkerShubh/GOD/blob/3b5cead14c8bbd519f81eeab7ed404356e3d9e8f/GOD%20-%20Assets/15.png" width="230" height="400" />
<img src = "https://github.com/TechTalkerShubh/GOD/blob/3b5cead14c8bbd519f81eeab7ed404356e3d9e8f/GOD%20-%20Assets/16.png" width="230" height="400" />
<img src = "https://github.com/TechTalkerShubh/GOD/blob/3b5cead14c8bbd519f81eeab7ed404356e3d9e8f/GOD%20-%20Assets/17.png" width="230" height="400" />
<img src = "https://github.com/TechTalkerShubh/GOD/blob/3b5cead14c8bbd519f81eeab7ed404356e3d9e8f/GOD%20-%20Assets/18.png" width="230" height="400" />
<img src = "https://github.com/TechTalkerShubh/GOD/blob/3b5cead14c8bbd519f81eeab7ed404356e3d9e8f/GOD%20-%20Assets/19.png" width="230" height="400" />|




### Functionality & Concepts used : 
The App has a very simple and interactive interface that helps the user to add their daily and monthly tasks.
Following are few android concepts used to achieve the functionalities in the app :

* Kotlin : This App is using kotlin as a primary language and used the android studio to build it.

* Navigation Library : Bottom navigation in this app is built using a navigation library. Which navigates to different fragments with ease.

* Room database : To store all the tasks created by the user we used Room database. Room database stores all the data locally. So, users don’t need to active internet connection to fetch all their task from the server. This saves time and internet both as it is not going to distract the user by tuning on data and start getting notifications.

* Live data & View models : Live model and live data are used while the user is updating any task or deleting. Due to this even if the user device changes any configuration like screen rotation, keyboard then it responds to it and updates live as it constantly observes the lifecycle of the app and maintains the scope throughout the lifecycle.

* Material Design : Look and feel is as important as the working of app. By keeping this in mind we have defined all the colors which we used in this app in colors value. The app also uses material design library and follows material guides to design layouts.

* View binding : Instead of find view by id, view binding is used in-app as recommended by Google to use view or data binding and also it is easy to access all the layout components. 

* Fragments : To navigate between different views navigation view with fragments is used. Fragments are a lightweight and efficient way to navigate between different views.

* Recycler view : To present the list of different tasks recycler view is used. The Item layout and adapter is also created to make it more dynamic and more interactive.

* Res : Project is well designed and defines all values at proper places like string, fonts, colors, styles for future maintainability and updates in-app.

* Other dependencies used in this project are:
    E.hdodenhof:circleimageview, intuit.ssp:ssp, intuit.sdp:sdp, theartofdev.edmodo, makeramen:roundedimageview, devrel:easypermissions, kotlinx-coroutines

### Application Link & Future Scope : 
You can access the app from [here](https://play.google.com/store/apps/details?id=com.shubhamjitiya.god) or [here](https://drive.google.com/drive/folders/1AcYWmQtLbLGVgYKjgatai8cArRv6QUFH?usp=sharing)

Because of the very short time span, we are not able to develop a fully fledged app but we have shortlisted our future updates.
Future updates will include some improvement like user can delete note by swipe left or right, while adding task top - 3 goals will be automatically identified by asking few questions to them. So, app will identify the top 3 most important tasks of the day based on the questions asked to them. The pending task will be added so user can easily see the remaining task separately which will be shown in the different fragment. Also thinking to schedule the tasks using job scheduling algorithm. Adding the feature to add an image.


### Authors

- [Shubham Jitiya](https://gtu-everything-for-students.netlify.app/)

### Download other apps

- [GTU - Everything for students](https://play.google.com/store/apps/details?id=com.shubhjitiya.gecgandhinagar)
- [WhatsInsta](https://play.google.com/store/apps/details?id=com.shubhjitiya.whatsinstasaver)
- [Team OPS Gujarat](https://play.google.com/store/apps/details?id=com.gujarat.ops.team)





