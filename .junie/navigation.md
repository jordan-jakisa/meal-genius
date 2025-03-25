Navigation and routing﻿
Edit pageLast modified: 20 March 2025
Navigation is a key part of UI applications that allows users to move between different application
screens. Compose Multiplatform adopts the Jetpack Compose approach to navigation.

The navigation library is currently in Alpha. You're welcome to try it in your Compose Multiplatform
projects. Remember to track potential migration issues in future releases. We would appreciate your
feedback in YouTrack.

Setup﻿
To use the navigation library, add the following dependency to your commonMain source set:

kotlin {
// ...
sourceSets {
// ...
commonMain.dependencies {
// ...
implementation("org.jetbrains.androidx.navigation:navigation-compose:2.8.0-alpha10")
}
// ...
}
}
Type-safe navigation﻿
Starting with 1.7.0, Compose Multiplatform supports type-safe navigation in common code as described
in the Jetpack documentation.

Sample project﻿
To see the Compose Multiplatform navigation library in action, check out the nav_cupcake project,
which was converted from the Navigate between screens with Compose Android codelab.

Just as with Jetpack Compose, to implement navigation, you should:

List routes that should be included in the navigation graph. Each route must be a unique string that
defines a path.

Create a NavHostController instance as your main composable property to manage navigation.

Add a NavHost composable to your app:

Choose the starting destination from the list of routes you defined earlier.

Create a navigation graph, either directly, as part of creating a NavHost, or programmatically,
using the NavController.createGraph() function.

Each back stack entry (each navigation route included in the graph) implements the LifecycleOwner
interface. A switch between different screens of the app makes it change its state from RESUMED to
STARTED and back. RESUMED is also described as "settled": navigation is considered finished when the
new screen is prepared and active. See the Lifecycle page for details of the current implementation
in Compose Multiplatform.

Limitations﻿
Current limitations of navigation in Compose Multiplatform, compared to Jetpack Compose:

Deep links (handling or following them) are not supported.

The BackHandler function and predictive back gestures are not supported on any platform besides
Android.

Compose Multiplatform 1.8.0-beta01
Support for browser navigation in web apps﻿
Compose Multiplatform for web fully supports the common Navigation library APIs, and on top of that
allows your apps to receive navigational input from the browser. Users can use the Back and Forward
buttons in the browser to move between navigation routes reflected in the browser history, as well
as use the address bar to understand where they are and get to a destination directly.

To bind the web app to the navigation graph defined in your common code, call the
window.bindToNavigation() method in your Kotlin/Wasm or Kotlin/JS code:

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalBrowserHistoryApi
fun main() {
val body = document.body ?: return

    ComposeViewport(body) {

        val navController = rememberNavController()
        // Assumes that your main composable function in common code is App()
        App(navController)
        LaunchedEffect(Unit) {
            window.bindToNavigation(navController)
        }
    }

}
After a window.bindToNavigation(navController) call:

The URL displayed in the browser reflects the current route (in the URL fragment, after the #
character).

The app parses URLs entered manually to translate them into destinations within the app.

By default, when using type-safe navigation, a destination is translated into a URL fragment
according to the kotlinx.serialization default with appended
arguments: <app package>.<serializable type>/<argument1>/<argument2>. For example,
example.org#org.example.app.StartScreen/123/Alice%2520Smith.

Customize translating routes into URLs and back﻿
As Compose Multiplatform apps are single-page apps, the framework manipulates the address bar to
imitate usual web navigation. If you would like to make your URLs more readable and isolate
implementation from URL patterns, you can assign the name to a screen directly or develop fully
custom processing for destination routes:

To simply make a URL readable, use the @SerialName annotation to explicitly set the serial name for
a serializable object or class:

// Instead of using the app package and object name,
// this route will be translated to the URL simply as "#start"
@Serializable @SerialName("start") data object StartScreen
To fully construct every URL, you can use the optional getBackStackEntryRoute lambda.

Full URL customization﻿
To implement a fully custom route to URL transformation:

Pass the optional getBackStackEntryRoute lambda to the window.bindToNavigation() function to specify
how routes should be converted into URL fragments when necessary.

If needed, add code that catches URL fragments in the address bar (when someone clicks or pastes
your app's URL) and translates URLs into routes to navigate users accordingly.

Here's an example of a simple type-safe navigation graph to use with the following samples of web
code (commonMain/kotlin/org.example.app/App.kt):

NavHost(navController = navController, startDestination = StartScreen)
{...}
In wasmJsMain/kotlin/main.kt, add the lambda to the .bindToNavigation() call:

@OptIn(
ExperimentalComposeUiApi::class,
ExperimentalBrowserHistoryApi::class,
ExperimentalSerializationApi::class
)
fun main() {
val body = document.body ?: return
ComposeViewport(body) {
val navController = rememberNavController()
App(navController)
LaunchedEffect(Unit) {
window.bindToNavigation(navController) { entry ->
val route = entry.destination.route.orEmpty()
when {
// Identifies the route using its serial descriptor
route.startsWith(StartScreen.serializer().descriptor.serialName) -> {
// Sets the corresponding URL fragment to "#start"
// instead of "#org.example.app.StartScreen"
//
// This string must always start with the `#` character to keep
// the processing at the front end
"#start"
}
route.startsWith(Id.serializer().descriptor.serialName) -> {
// Accesses the route arguments
val args = entry.toRoute<Id>()

                        // Sets the corresponding URL fragment to "#find_id_222"
                        // instead of "#org.example.app.ID%2F222"
                        "#find_id_${args.id}"
                    }
                    route.startsWith(Patient.serializer().descriptor.serialName) -> {
                        val args = entry.toRoute<Patient>()
                        // Sets the corresponding URL fragment to "#patient_Jane%20Smith-Baker_33"
                        // instead of "#org.company.app.Patient%2FJane%2520Smith-Baker%2F33"
                        "#patient_${args.name}_${args.age}"
                    }
                    // Doesn't set a URL fragment for all other routes
                    else -> ""
                }
            }
        }
    }

}
Make sure that every string that corresponds to a route starts with the # character to keep the data
within URL fragments. Otherwise, when users copy and paste the URL, the browser will try to access a
wrong endpoint instead of passing the control to your app.

If your URLs have custom formatting, you should add the reverse processing to match manually entered
URLs to destination routes. The code that does the matching needs to run before the
window.bindToNavigation() call binds window.location to the navigation graph:

@OptIn(
ExperimentalComposeUiApi::class,
ExperimentalBrowserHistoryApi::class,
ExperimentalSerializationApi::class
)
fun main() {
val body = document.body ?: return
ComposeViewport(body) {
val navController = rememberNavController()
App(navController)
LaunchedEffect(Unit) {
// Accesses the fragment substring of the current URL
val initRoute = window.location.hash.substringAfter('#', "")
when {
// Identifies the corresponding route and navigates to it
initRoute.startsWith("start") -> {
navController.navigate(StartScreen)
}
initRoute.startsWith("find_id") -> {
// Parses the string to extract route parameters before navigating to it
val id = initRoute.substringAfter("find_id_").toLong()
navController.navigate(Id(id))
}
initRoute.startsWith("patient") -> {
val name = initRoute.substringAfter("patient_").substringBefore("_")
val id = initRoute.substringAfter("patient_").substringAfter("_").toLong()
navController.navigate(Patient(name, id))
}
}

            window.bindToNavigation(navController) { ... }
        }
    }

}

Type safety in Kotlin DSL and Navigation Compose

bookmark_border
You can use built-in type safe APIs to provide compile-time type safety for your navigation graph.
These APIs are available when your app uses the Navigation Compose or Navigation Kotlin DSL. They
are available as of Navigation 2.8.0.

These APIs are equivalent to what Safe Args provides to navigation graphs built using XML.

Define routes
To use type-safe routes in Compose, you first need to define serializable classes or objects that
represent your routes.

To define serializable objects use @Serializable annotation provided by the Kotlin Serialization
plugin. This plugin can be added to your project by adding these dependencies.

Use the following rules to decide what type to use for your route:

Object: Use an object for routes without arguments.
Class: Use a class or data class for routes with arguments.
KClass<T>: Use if you don't need to pass arguments, such as a class without parameters, or a class
where all parameters have default values
For example: Profile::class
In all cases the object or class must be serializable.

For example:

// Define a home route that doesn't take any arguments
@Serializable
object Home

// Define a profile route that takes an ID
@Serializable
data class Profile(val id: String)
Build your graph
Next, you need to define your navigation graph. Use the composable() function to define composables
as destinations in your navigation graph.

NavHost(navController, startDestination = Home) {
composable<Home> {
HomeScreen(onNavigateToProfile = { id ->
navController.navigate(Profile(id))
})
}
composable<Profile> { backStackEntry ->
val profile: Profile = backStackEntry.toRoute()
ProfileScreen(profile.id)
}
}
Observe the following in this example:

composable() takes a type parameter. That is, composable<Profile>.
Defining the destination type is a more robust approach than passing a route string as in
composable("profile").
The route class defines the type of each navigation argument, as in val id: String, so there's no
need for NavArgument.
For the profile route, the toRoute() extension method recreates the Profile object from the
NavBackStackEntry and its arguments.
For more information on how to design your graph in general, see the Design your Navigation graph
page.

Navigate to type safe route
Finally, you can navigate to your composable using the navigate() function by passing in the
instance of the route:

navController.navigate(Profile(id = 123))
This navigates the user to the composable<Profile> destination in the navigation graph. Any
navigation arguments, such as id, can be obtained by reconstructing Profile using
NavBackStackEntry.toRoute and reading its properties.

Important: Because the parameters of the data class are typed, when you pass an instance of that
class to navigate(), the arguments are necessarily type safe.

Design your navigation graph

bookmark_border
The Navigation component uses a navigation graph to manage your app's navigation. The navigation
graph is a data structure that contains each destination within your app and the connections between
them.

Note: The navigation graph is distinct from the back stack, which is a stack within the
NavController that holds destinations the user has recently visited.
Destination types
There are three general types of destinations: hosted, dialog, and activity. The following table
outlines these three destination types and their purposes.

Type

Description

Use cases

Hosted

Fills the entire navigation host. That is, the size of a hosted destination is the same as the size
of the navigation host and previous destinations are not visible.

Main and detail screens.

Dialog

Presents overlay UI components. This UI is not tied to the location of the navigation host or its
size. Previous destinations are visible underneath the destination.

Alerts, selections, forms.

Activity

Represents unique screens or features within the app.

Serve as an exit point to the navigation graph that starts a new Android activity that is managed
separately from the Navigation component.

In modern Android development, an app consists of a single activity. Activity destinations are
therefore best used when interacting with third party activities or as part of the migration
process.

This document contains examples of hosted destinations, which are the most common and fundamental
destinations. See the following guides for information on the other destinations:

Dialog destinations
Activity destinations
Frameworks
Although the same general workflow applies in every case, how exactly you create a navigation host
and graph depends on the UI framework you use.

Compose: Use the NavHost composable. Add a NavGraph to it using the Kotlin DSL. You can create the
graph in two ways:
As part of the NavHost: Construct the navigation graph directly as part of adding the NavHost.
Programmatically: Use the NavController.createGraph() method to create a NavGraph and pass it to the
NavHost directly.
Fragments: When using fragments with the views UI framework, use a NavHostFragment as the host.
There are several ways to create a navigation graph:
Programmatically: Use the Kotlin DSL to create a NavGraph and directly apply it on the
NavHostFragment.
The createGraph() function used with the Kotlin DSL for both fragments and Compose is the same.
XML: Write your navigation host and graph directly in XML.
Android Studio editor: Use the GUI editor in Android Studio to create and adjust your graph as an
XML resource file.
Note: How you interact with the graph through the NavController is similar between frameworks. See
the Navigate to a destination guide for more details.
Compose
In Compose, use a serializable object or class to define a route. A route describes how to get to a
destination, and contains all the information that the destination requires.

Use the @Serializable annotation to automatically create the necessary serialization and
deserialization methods for your route types. This annotation is provided by the Kotlin
Serialization plugin. Follow these instructions to add this plugin.

Once you have defined your routes, use the NavHost composable to create your navigation graph.
Consider the following example:

@Serializable
object Profile
@Serializable
object FriendsList

val navController = rememberNavController()

NavHost(navController = navController, startDestination = Profile) {
composable<Profile> { ProfileScreen( /* ... */ ) }
composable<FriendsList> { FriendsListScreen( /* ... */ ) }
// Add more destinations similarly.
}
A serializable object represents each of the two routes, Profile and FriendsList.
The call to the NavHost composable passes a NavController and a route for the start destination.
The lambda passed to the NavHost ultimately calls NavController.createGraph() and returns a
NavGraph.
Each route is supplied as a type argument to NavGraphBuilder.composable<T>() which adds the
destination to the resulting NavGraph.
The lambda passed to composable is what the NavHost displays for that destination.
Caution: Instead of passing a type to composable(), you can pass a route string or an integer id.
However, this makes it much more difficult to manage passing additional arguments to the
destination.
Understand the lambda
To better understand the lambda that creates the NavGraph, consider that to build the same graph as
in the preceding snippet, you could create the NavGraph separately using NavController.createGraph()
and pass it to the NavHost directly:

val navGraph by remember(navController) {
navController.createGraph(startDestination = Profile)) {
composable<Profile> { ProfileScreen( /* ... */ ) }
composable<FriendsList> { FriendsListScreen( /* ... */ ) }
}
}
NavHost(navController, navGraph)
Important: A NavController is associated with a single NavHost composable. The NavHost provides the
NavController access to its navigation graph. When you use the NavController to navigate to a
destination, you cause the NavController to interact with its associated NavHost.
Pass arguments
If you need to pass data to a destination, define the route with a class that has parameters. For
example, the Profile route is a data class with a name parameter.

@Serializable
data class Profile(val name: String)
Whenever you need to pass arguments to that destination, you create an instance of your route class,
passing the arguments to the class constructor.

Note: Use a data class for a route with arguments, and an object or data object for a route with no
arguments.
For optional arguments, create nullable fields with a default value.

@Serializable
data class Profile(val nickname: String? = null)
Obtain route instance
You can obtain the route instance with NavBackStackEntry.toRoute() or SavedStateHandle.toRoute().
When you create a destination using composable(), the NavBackStackEntry is available as a parameter.

@Serializable
data class Profile(val name: String)

val navController = rememberNavController()

NavHost(navController = navController, startDestination = Profile(name="John Smith")) {
composable<Profile> { backStackEntry ->
val profile: Profile = backStackEntry.toRoute()
ProfileScreen(name = profile.name) }
}
Note the following in this snippet:

The Profile route specifies the starting destination in the navigation graph, with "John Smith" as
the argument for name.
The destination itself is the composable<Profile>{} block.
The ProfileScreen composable takes the value of profile.name for its own name argument.
As such, the value "John Smith" passes through to ProfileScreen.
Minimal example
A complete example of a NavController and NavHost working together:

@Serializable
data class Profile(val name: String)

@Serializable
object FriendsList

// Define the ProfileScreen composable.
@Composable
fun ProfileScreen(
profile: Profile
onNavigateToFriendsList: () -> Unit,
) {
Text("Profile for ${profile.name}")
Button(onClick = { onNavigateToFriendsList() }) {
Text("Go to Friends List")
}
}

// Define the FriendsListScreen composable.
@Composable
fun FriendsListScreen(onNavigateToProfile: () -> Unit) {
Text("Friends List")
Button(onClick = { onNavigateToProfile() }) {
Text("Go to Profile")
}
}

// Define the MyApp composable, including the `NavController` and `NavHost`.
@Composable
fun MyApp() {
val navController = rememberNavController()
NavHost(navController, startDestination = Profile(name = "John Smith")) {
composable<Profile> { backStackEntry ->
val profile: Profile = backStackEntry.toRoute()
ProfileScreen(
profile = profile,
onNavigateToFriendsList = {
navController.navigate(route = FriendsList)
}
)
}
composable<FriendsList> {
FriendsListScreen(
onNavigateToProfile = {
navController.navigate(
route = Profile(name = "Aisha Devi")
)
}
)
}
}
}
As the snippet demonstrates, instead of passing the NavController to your composables, expose an
event to the NavHost. That is, your composables should have a parameter of type () -> Unit for which
the NavHost passes a lambda that calls NavController.navigate().

Note: By using the parameters of the route class you can pass data to the given destination with
full type safety. For example, in the previous code Profile.name ensures that name is always a
String.
Fragments
As outlined in the preceding sections, when using fragments you have the option to create a
navigation graph programmatically using the Kotlin DSL, XML, or the Android Studio editor.

The following sections detail these different approaches.

Note: The Navigation component is designed for apps that have one main activity with multiple
fragment destinations. The main activity is associated with a navigation graph and contains a
NavHostFragment that is responsible for swapping destinations as needed. In an app with multiple
activity destinations, each activity has its own navigation graph.
Programmatically
The Kotlin DSL provides a programmatic way of creating a navigation graph with fragments. In many
ways this is neater and more modern than using an XML resource file.

Consider the following example, which implements a two-screen navigation graph.

First it is necessary to create the NavHostFragment, which must not include an app:navGraph element:

<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:app="http://schemas.android.com/apk/res-auto"
android:layout_width="match_parent"
android:layout_height="match_parent">

    <androidx.fragment.app.FragmentContainerView
        android:id="@+id/nav_host_fragment"
        android:name="androidx.navigation.fragment.NavHostFragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</FrameLayout>
Next, pass the id of the NavHostFragment to NavController.findNavController. This associates the NavController with the NavHostFragment.

Subsequently, the call to NavController.createGraph() links the graph to the NavController and
consequently also to the NavHostFragment:

@Serializable
data class Profile(val name: String)

@Serializable
object FriendsList

// Retrieve the NavController.
val navController = findNavController(R.id.nav_host_fragment)

// Add the graph to the NavController with `createGraph()`.
navController.graph = navController.createGraph(
startDestination = Profile(name = "John Smith")
) {
// Associate each destination with one of the route constants.
fragment<ProfileFragment, Profile> {
label = "Profile"
}

    fragment<FriendsListFragment, FriendsList>() {
        label = "Friends List"
    }

    // Add other fragment destinations similarly.

}
Using the DSL in this way is very similar to the workflow outlined in the preceding section on
Compose. For example, both there and here, the NavController.createGraph() function generates the
NavGraph. Likewise, while NavGraphBuilder.composable() adds composable destinations to the graph,
here NavGraphBuilder.fragment() adds a fragment destination.

For more information on how to use the Kotlin DSL, see Build a graph with the NavGraphBuilder DSL.

XML
You can directly write the XML yourself. The following example mirrors and is equivalent to the
two-screen example from the preceding section.

First, create a NavHostFragment. This serves as the navigation host which contains the actual
navigation graph.

A minimal implementation of a NavHostFragment:

<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:app="http://schemas.android.com/apk/res-auto"
android:layout_width="match_parent"
android:layout_height="match_parent">

    <androidx.fragment.app.FragmentContainerView
        android:id="@+id/nav_host_fragment"
        android:name="androidx.navigation.fragment.NavHostFragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:navGraph="@navigation/nav_graph" />

</FrameLayout>
The NavHostFragment contains the attribute app:navGraph. Use this attribute to connect your navigation graph to the navigation host. The following is an example of how you might implement the graph:


<navigation xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:app="http://schemas.android.com/apk/res-auto"
android:id="@+id/nav_graph"
app:startDestination="@id/profile">

    <fragment
        android:id="@+id/profile"
        android:name="com.example.ProfileFragment"
        android:label="Profile">

        <!-- Action to navigate from Profile to Friends List. -->
        <action
            android:id="@+id/action_profile_to_friendslist"
            app:destination="@id/friendslist" />
    </fragment>

    <fragment
        android:id="@+id/friendslist"
        android:name="com.example.FriendsListFragment"
        android:label="Friends List" />

    <!-- Add other fragment destinations similarly. -->

</navigation>
You use actions to define the connections between different destinations. In this example, the profile fragment contains an action that navigates to friendslist. For more information, see Use Navigation actions and fragments.

Note: The DSL example does not define actions because they don't apply in that context. When using
the DSL, use NavController.navigate() directly.
Editor
You can manage your app's navigation graph using the Navigation Editor in Android Studio. This is
essentially a GUI you can use to create and edit your NavigationFragment XML, as seen in the
preceding section.

For more information, see Navigation editor.

Nested graphs
You can also use nested graphs. This involves using a graph as a navigation destination. For more
information, see Nested graphs.
