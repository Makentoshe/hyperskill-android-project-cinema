# project-android-template

This project is a template for any hyperskill android project you want to create. 
The wizard will guide you to initialize a your custom project.

## Getting Started



### Cloning repository

Cloning via SSH: ssh://git@git.jetbrains.space/hyperskill/ad/template-android-project.git

This requires a ssh credentials. It can be defined in the MyProfile -> Security -> Git Keys

Cloning via HTTP: https://git.jetbrains.space/hyperskill/ad/template-android-project.git



### Working with git

When you cloning a template repository, it will be linked with current template via `git remote origin`. 
It this moment, if template will be changed (update dependencies or fix template specific bugs), you can just call
`git pull origin master` and template will be upgraded.

For working with project specific repository you can just add additional remote for the git: 
`git remote add project <link_to_repository>`. 
And using, like `git push project master` or `git pull project master`.



### Defining project specific files

#### `settings.gradle`
Change the `rootProject.name = 'your-project-name'` 

#### `course-info.yaml`
Change the `title: hyperskill-project-template` line.
Change the `content`. It is a list and should contain a single value: [a title of your module](https://hyperskill.jetbrains.space/p/ad/repositories/template-android-project/files/your-project-name)

#### `lesson-info.yaml` in your project
Change the `content` value. This value is a set of stages that you project contains. 
You can set as much stages as you want. For this template there will be only 1 stage.
 
 ### `lesson-remote-info.yaml` in your project
 
 The `id` value is a task description id related to Stepik lesson.
 
 
### Defining stage specific files

You can define a new stage using EduTools plugin or copy-paste the previous stage and refactor some files.
Each stage should contain: 
- `task.html` is a stage description;
- `task-info.yaml` is a file that setup stage files;
- `build.gradle` is a file for stage building and testing. It have an installed robolectric dependency for tests.
- `src` is a folder with sources.

*Note*: Don't forget to refactor packages and `AndroidManifest.xml` as well. 
You can find all `hyperskill.android.template.project` occurrences by Find action in Intellij IDEA or Android Studio.