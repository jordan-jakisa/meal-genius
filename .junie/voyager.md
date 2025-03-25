Setup¶
Add Maven Central to your repositories if needed

repositories {
    mavenCentral()
}
2. Add the desired dependencies to your module’s build.gradle file

Dependencies
Version Catalog
dependencies {
    val voyagerVersion = "1.1.0-beta02"

    // Multiplatform

    // Navigator
    implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")

    // Screen Model
    implementation("cafe.adriel.voyager:voyager-screenmodel:$voyagerVersion")

    // BottomSheetNavigator
    implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:$voyagerVersion")

    // TabNavigator
    implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyagerVersion")

    // Transitions
    implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")

    // Koin integration
    implementation("cafe.adriel.voyager:voyager-koin:$voyagerVersion")

    // Android

    // Hilt integration
    implementation("cafe.adriel.voyager:voyager-hilt:$voyagerVersion")

    // LiveData integration
    implementation("cafe.adriel.voyager:voyager-livedata:$voyagerVersion")

    // Desktop + Android

    // Kodein integration
    implementation("cafe.adriel.voyager:voyager-kodein:$voyagerVersion")

    // RxJava integration
    implementation("cafe.adriel.voyager:voyager-rxjava:$voyagerVersion")
}

Current version here.

Platform compatibility¶
Multiplatform targets: Android, iOS, Desktop, Mac Native, Web.

Android	Desktop	Multiplatform
voyager-navigator	✅	✅	✅
voyager-screenModel	✅	✅	✅
voyager-bottom-sheet-navigator	✅	✅	✅
voyager-tab-navigator	✅	✅	✅
voyager-transitions	✅	✅	✅
voyager-koin	✅	✅	✅
voyager-kodein	✅	✅	✅
voyager-lifecycle-kmp	✅	✅	✅
voyager-hilt	✅		
voyager-rxjava	✅	✅	
voyager-livedata	✅		

Navigation¶
Success

To use the Navigator you should first import cafe.adriel.voyager:voyager-navigator (see Setup).

Screen¶
On Voyager, screens are just classes with a composable function as the entrypoint. To create one, you should implement the Screen interface and override the Content() composable function.

You can use data class (if you need to send params), class (if no param is required).

class PostListScreen : Screen {

    @Composable
    override fun Content() {
        // ...
    }
}

data class PostDetailsScreen(val postId: Long) : Screen {

    @Composable
    override fun Content() {
        // ...
    }
}
Navigator¶
Navigator is a composable function deeply integrated with Compose internals. It’ll manage the lifecyle, back press, state restoration and even nested navigation for you.

To start using it, just set the initial Screen.

class SingleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Navigator(HomeScreen)
        }
    }
}
Use the LocalNavigator to navigate to other screens. Take a look at the Stack API for the available operations.

class PostListScreen : Screen {

    @Composable
    override fun Content() {
        // ...
    }

    @Composable
    private fun PostCard(post: Post) {
        val navigator = LocalNavigator.currentOrThrow

        Card(
            modifier = Modifier.clickable { 
                navigator.push(PostDetailsScreen(post.id))
                // Also works:
                // navigator push PostDetailsScreen(post.id)
                // navigator += PostDetailsScreen(post.id)
            }
        ) {
            // ...
        }
    }
}
If part of your UI is shared between screens, like the TopAppBar or BottomNavigation, you can easily reuse them with Voyager.

@Composable
override fun Content() {
    Navigator(HomeScreen) { navigator ->
        Scaffold(
            topBar = { /* ... */ },
            content = { CurrentScreen() },
            bottomBar = { /* ... */ }
        )
    }
}
{% hint style=”warning” %} You should use CurrentScreen() instead of navigator.lastItem.Content(), because it will save the Screen’s subtree for you (see SaveableStateHolder). {% endhint %}

Sample¶


Info

Source code here.

Made with Material for MkDocs

Nested navigation¶
Nested Navigators¶
Going a little further, it’s possible to have nested navigators. The Navigator has a level property (so you can check how deeper your are) and can have a parent navigator (if you need to interact with it).

setContent {
    Navigator(ScreenA) { navigator0 ->
        println(navigator.level)
        // 0
        println(navigator.parent == null)
        // true
        Navigator(ScreenB) { navigator1 ->
            println(navigator.level)
            // 1
            println(navigator.parent == navigator0)
            // true
            Navigator(ScreenC) { navigator2 ->
                println(navigator.level)
                // 2
                println(navigator.parent == navigator1)
                // true
            }
        }
    }
}
Another operation is the popUntilRoot(), it will recursively pop all screens starting from the leaf navigator until the root one.

Sample¶


Info

Source code here.

Made with Material for MkDocs

BottomSheet navigation¶
Success

To use the BottomSheetNavigator you should first import cafe.adriel.voyager:voyager-bottom-sheet-navigator (see Setup).

Voyager provides a specialized navigator for ModalBottomSheetLayout: the BottomSheetNavigator.

Call it and set the back layer content. The BottomSheet content (or the front layer) will be set on demand.

setContent {
    BottomSheetNavigator {
        BackContent()
    }
}
You can also use the default Navigator to navigate on your back layer content.

setContent {
    BottomSheetNavigator {
        Navigator(BackScreen())
    }
}
The BottomSheetNavigator accepts the same params as ModalBottomSheetLayout.

@Composable
public fun BottomSheetNavigator(
    modifier: Modifier = Modifier,
    hideOnBackPress: Boolean = true,
    scrimColor: Color = ModalBottomSheetDefaults.scrimColor,
    sheetShape: Shape = MaterialTheme.shapes.large,
    sheetElevation: Dp = ModalBottomSheetDefaults.Elevation,
    sheetBackgroundColor: Color = MaterialTheme.colors.surface,
    sheetContentColor: Color = contentColorFor(sheetBackgroundColor),
    // ...
)
Use the LocalBottomSheetNavigator to show and hide the BottomSheet.

class BackScreen : Screen {

    @Composable
    override fun Content() {
        val bottomSheetNavigator = LocalBottomSheetNavigator.current

        Button(
            onClick = { 
                bottomSheetNavigator.show(FrontScreen())
            }
        ) {
            Text(text = "Show BottomSheet")
        }
    }
}
Sample¶


Info

Source code here.

Made with Material for MkDocs

Tab navigation¶
Success

To use the TabNavigator you should first import cafe.adriel.voyager:voyager-tab-navigator (see Setup).

Voyager provides a specialized navigator for tabs : the TabNavigator.

The Tab interface, like the Screen, has a Content() composable function, but also requires a TabOptions.

object HomeTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(R.string.home_tab)
            val icon = rememberVectorPainter(Icons.Default.Home)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        // ...
    }
}
Info

Since tabs aren’t usually reused, its OK to create them as object.

The TabNavigator unlike the Navigator:

Doesn’t handle back presses, because the tabs are siblings
Doesn’t implement the Stack API, just provides a current property
You can use it with a Scaffold to easily create the UI for your tabs.

setContent {
    TabNavigator(HomeTab) {
        Scaffold(
            content = { 
                CurrentTab() 
            },
            bottomBar = {
                BottomNavigation {
                    TabNavigationItem(HomeTab)
                    TabNavigationItem(FavoritesTab)
                    TabNavigationItem(ProfileTab)
                }
            }
        )
    }
}
Warning

Like theCurrentScreen(), you should use CurrentTab instead of tabNavigator.current.Content(), because it will save the Tab’s subtree for you (see SaveableStateHolder).

Use the LocalTabNavigator to get the current TabNavigator, and current to get and set the current tab.

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    BottomNavigationItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = { Icon(painter = tab.icon, contentDescription = tab.title) }
    )
}
Sample¶


Info

Source code here.

TabNavigator + Nested Navigator¶
For more complex use cases, when each tab should have its own independent navigation, like the Youtube app, you can combine the TabNavigator with multiple Navigators.

Let’s go back to the Tab navigation example.

setContent {
    TabNavigator(HomeTab) {
        // ...
    }
}
But now, the HomeTab will have its own Navigator.

object HomeTab : Screen {

    @Composable
    override fun Content() {
        Navigator(PostListScreen())
    }
}
That way, we can use the LocalNavigator to navigate deeper into HomeTab, or the LocalTabNavigator to switch between tabs.

class PostListScreen : Screen {

    @Composable
    private fun GoToPostDetailsScreenButton(post: Post) {
        val navigator = LocalNavigator.currentOrThrow

        Button(
            onClick = { navigator.push(PostDetailsScreen(post.id)) }
        )
    }

    @Composable
    private fun GoToProfileTabButton() {
        val tabNavigator = LocalTabNavigator.current

        Button(
            onClick = { tabNavigator.current = ProfileTab }
        )
    }
}
Made with Material for MkDocs

oin integration¶
Success

To use the getScreenModel you should first import cafe.adriel.voyager:voyager-koin (see Setup).

Warning

Since 1.1.0-alpha04 we have rename the getScreenModel to koinScreenModel, this is a change to follow Koin Compose naming schema. The previous getScreenModel is deprecated and will be removed on 1.1.0

Declare your ScreenModels using the factory component.

val homeModule = module {
    factory { HomeScreenModel() } 
}
Call getScreenModel() to get a new instance.

class HomeScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = getScreenModel<HomeScreenModel>()
        // ...
    }
}
Sample¶
Info

Sample code here.

Navigator scoped ScreenModel¶
class HomeScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.getNavigatorScreenModel<HomeScreenModel>()
        // ...
    }
}
Made with Material for MkDocs

iewModel KMP¶
Since 1.1.0-beta01 we have introduce a experimental API for ViewModel KMP. It is under the package cafe.adriel.voyager:voyager-lifecycle-kmp (see Setup).

You will need to call ProvideNavigatorLifecycleKMPSupport before all Navigator calls and it will be working out of the box.

@Composable
fun MainView() {
    ProvideNavigatorLifecycleKMPSupport {
        Navigator(...)
    }
}

class MyScreen : Screen {
    @Composable
    fun Content() {
        val myViewModel = viewModel { MyScreenViewModel() }
    }
}
Navigator scoped ViewModel¶
Voyager 1.1.0-beta01 also have introduced the support for Navigator scoped ViewModel and Lifecycle. This will make easy to share a ViewModel cross screen of the same navigator.

class MyScreen : Screen {
    @Composable
    fun Content() {
        val myViewModel = navigatorViewModel { MyScreenViewModel() }
    }
}
Lifecycle KMP¶
This version also brings the Lifecycle events for Screen lifecycle in KMP, now is possible to a generic third party API that listen to Lifecycle of a Screen in KMP.

Made with Material for MkDocs

Transitions¶
To use the transitions you should first import cafe.adriel.voyager:voyager-transitions (see Setup).

Voyager has built-in transitions! When initializing the Navigator you can override the default content and use, for example, the SlideTransition.

setContent {
    Navigator(HomeScreen) { navigator ->
        SlideTransition(navigator)
    }
}
Known bug

There is a known bug using any Transition APIs can leaky ScreenModels or ViewModels, this happens because Voyager by default dispose Screens in the next Composition tick after a pop or replace is called, but the transition only finish later, so the ScreenModel or ViewModel is re created or cleared to early. For this purpose since Voyager 1.1.0-beta02 we have introduce a new API to fix this issue. For more details on the issue see #106.

Navigator(
    screen = ...,
    disposeBehavior = NavigatorDisposeBehavior(disposeSteps = false),
) { navigator ->
    SlideTransition(
        navigator = navigator,
        ...
        disposeScreenAfterTransitionEnd = true
    )
}
Warning

Have encounter Screen was used multiple times crash? Provide a uniqueScreenKey for your Screens

class ScreenFoo : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        ...
    }
Available transitions¶
FadeTransition()	SlideTransition()
	
ScaleTransition()

Custom transitions¶
It’s simple to add your own transitions: call ScreenTransition with a custom transitionModifier. Use the available params (screen, transition and event) to animate as needed.

@Composable
fun MyCustomTransition(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    content: ScreenTransitionContent
) {
    ScreenTransition(
        navigator = navigator,
        modifier = modifier,
        content = content,
        transition = {
            val (initialScale, targetScale) = when (navigator.lastEvent) {
                StackEvent.Pop -> ExitScales
                else -> EnterScales
            }

            scaleIn(initialScale) with scaleOut(targetScale)
        }
    )
}

setContent {
    Navigator(HomeScreen) { navigator ->
        MyCustomTransition(navigator) { screen ->
            screen.Content()
        }
    }
}
Take a look at the source of the available transitions for working examples.

Per Screen transitions [Experimental]¶
If you want to define a Enter and Exit transition for a specific Screen, you have a lot of options to do starting from 1.1.0-beta01 Voyager have a new experimental API for this purpose. To animate the content, we use transitions of the target screen in the case of push navigation, otherwise we use transitions of the initial screen

class ExampleSlideScreen : Screen, ScreenTransition {
    override val key: ScreenKey
        get() = uniqueScreenKey

    @Composable
    override fun Content() {
        ...
    }

    override fun enter(lastEvent: StackEvent): EnterTransition {
        return slideIn { size ->
            val x = if (lastEvent == StackEvent.Pop) -size.width else size.width
            IntOffset(x = x, y = 0)
        }
    }

    override fun exit(lastEvent: StackEvent): ExitTransition {
        return slideOut { size ->
            val x = if (lastEvent == StackEvent.Pop) size.width else -size.width
            IntOffset(x = x, y = 0)
        }
    }
}
It’s convenient to use Kotlin delegates for per-Screen transitions. For example, you can create a SlideTransition and FadeTransition classes:

class FadeTransition : ScreenTransition {

    override fun enter(lastEvent: StackEvent): EnterTransition {
        return fadeIn(tween(500, delayMillis = 500))
    }

    override fun exit(lastEvent: StackEvent): ExitTransition {
        return fadeOut(tween(500))
    }
}

class SlideTransition : ScreenTransition {

    override fun enter(lastEvent: StackEvent): EnterTransition {
        return slideIn { size ->
            val x = if (lastEvent == StackEvent.Pop) -size.width else size.width
            IntOffset(x = x, y = 0)
        }
    }

    override fun exit(lastEvent: StackEvent): ExitTransition {
        return slideOut { size ->
            val x = if (lastEvent == StackEvent.Pop) size.width else -size.width
            IntOffset(x = x, y = 0)
        }
    }
}
Then you can use them as delegates in your Screens:

class SlideScreen : Screen, ScreenTransition by SlideTransition() {

    @Composable
    override fun Content() {
        ...
    }
}

class FadeScreen : Screen, ScreenTransition by FadeTransition() {

    @Composable
    override fun Content() {
        ...
    }
}
Also you can use can pass your custom ScreenTransition instance in ScreenTransition function, it will be used for default animation.

setContent {
    Navigator(FadeScreen) { navigator ->
        ScreenTransition(
            navigator = navigator,
            defaultTransition = SlideTransition()
        )
    }
}
The API works with any ScreenTransition API, you just need to provide one and the Per Screen transition should.

setContent {
    Navigator(HomeScreen) { navigator ->
        SlideTransition(navigator)
    }
}
CrossfadeTransition is not supported yet.

Made with Material for MkDocs