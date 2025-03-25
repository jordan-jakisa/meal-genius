DataStore   
Part of Android Jetpack.

bookmark_border
Jetpack DataStore is a data storage solution that allows you to store key-value pairs or typed objects with protocol buffers. DataStore uses Kotlin coroutines and Flow to store data asynchronously, consistently, and transactionally.

If you're currently using SharedPreferences to store data, consider migrating to DataStore instead.

Note: If you need to support large or complex datasets, partial updates, or referential integrity, consider using Room instead of DataStore. DataStore is ideal for small, simple datasets and does not support partial updates or referential integrity.
Preferences DataStore and Proto DataStore
DataStore provides two different implementations: Preferences DataStore and Proto DataStore.

Preferences DataStore stores and accesses data using keys. This implementation does not require a predefined schema, and it does not provide type safety.
Proto DataStore stores data as instances of a custom data type. This implementation requires you to define a schema using protocol buffers, but it provides type safety.
Using DataStore correctly
In order to use DataStore correctly always keep in mind the following rules:

Never create more than one instance of DataStore for a given file in the same process. Doing so can break all DataStore functionality. If there are multiple DataStores active for a given file in the same process, DataStore will throw IllegalStateException when reading or updating data.

The generic type of the DataStore must be immutable. Mutating a type used in DataStore invalidates any guarantees that DataStore provides and creates potentially serious, hard-to-catch bugs. It is strongly recommended that you use protocol buffers which provide immutability guarantees, a simple API, and efficient serialization.

Never mix usages of SingleProcessDataStore and MultiProcessDataStore for the same file. If you intend to access the DataStore from more than one process, always use MultiProcessDataStore.

Setup
To use Jetpack DataStore in your app, add the following to your Gradle file depending on which implementation you want to use:

Preferences DataStore
Groovy
Kotlin

    // Preferences DataStore (SharedPreferences like APIs)
    dependencies {
        implementation "androidx.datastore:datastore-preferences:1.1.3"

        // optional - RxJava2 support
        implementation "androidx.datastore:datastore-preferences-rxjava2:1.1.3"

        // optional - RxJava3 support
        implementation "androidx.datastore:datastore-preferences-rxjava3:1.1.3"
    }

    // Alternatively - use the following artifact without an Android dependency.
    dependencies {
        implementation "androidx.datastore:datastore-preferences-core:1.1.3"
    }
    
Proto DataStore
Groovy
Kotlin

    // Typed DataStore (Typed API surface, such as Proto)
    dependencies {
        implementation "androidx.datastore:datastore:1.1.3"

        // optional - RxJava2 support
        implementation "androidx.datastore:datastore-rxjava2:1.1.3"

        // optional - RxJava3 support
        implementation "androidx.datastore:datastore-rxjava3:1.1.3"
    }

    // Alternatively - use the following artifact without an Android dependency.
    dependencies {
        implementation "androidx.datastore:datastore-core:1.1.3"
    }
    
Note: If you use the datastore-preferences-core artifact with Proguard, you must manually add Proguard rules to your proguard-rules.pro file to keep your fields from being deleted. You can view the necessary rules.
Store key-value pairs with Preferences DataStore
The Preferences DataStore implementation uses the DataStore and Preferences classes to persist simple key-value pairs to disk.

Create a Preferences DataStore
Use the property delegate created by preferencesDataStore to create an instance of DataStore<Preferences>. Call it once at the top level of your kotlin file, and access it through this property throughout the rest of your application. This makes it easier to keep your DataStore as a singleton. Alternatively, use RxPreferenceDataStoreBuilder if you're using RxJava. The mandatory name parameter is the name of the Preferences DataStore.

Kotlin
Java

// At the top level of your kotlin file:
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
Read from a Preferences DataStore
Because Preferences DataStore does not use a predefined schema, you must use the corresponding key type function to define a key for each value that you need to store in the DataStore<Preferences> instance. For example, to define a key for an int value, use intPreferencesKey(). Then, use the DataStore.data property to expose the appropriate stored value using a Flow.

Kotlin
Java

val EXAMPLE_COUNTER = intPreferencesKey("example_counter")
val exampleCounterFlow: Flow<Int> = context.dataStore.data
  .map { preferences ->
    // No type safety.
    preferences[EXAMPLE_COUNTER] ?: 0
}
Write to a Preferences DataStore
Preferences DataStore provides an edit() function that transactionally updates the data in a DataStore. The function's transform parameter accepts a block of code where you can update the values as needed. All of the code in the transform block is treated as a single transaction.

Kotlin
Java

suspend fun incrementCounter() {
  context.dataStore.edit { settings ->
    val currentCounterValue = settings[EXAMPLE_COUNTER] ?: 0
    settings[EXAMPLE_COUNTER] = currentCounterValue + 1
  }
}
Store typed objects with Proto DataStore
The Proto DataStore implementation uses DataStore and protocol buffers to persist typed objects to disk.

Define a schema
Proto DataStore requires a predefined schema in a proto file in the app/src/main/proto/ directory. This schema defines the type for the objects that you persist in your Proto DataStore. To learn more about defining a proto schema, see the protobuf language guide.


syntax = "proto3";

option java_package = "com.example.application.proto";
option java_multiple_files = true;

message Settings {
  int32 example_counter = 1;
}
Note: The class for your stored objects is generated at compile time from the message defined in the proto file. Make sure you rebuild your project.
Create a Proto DataStore
There are two steps involved in creating a Proto DataStore to store your typed objects:

Define a class that implements Serializer<T>, where T is the type defined in the proto file. This serializer class tells DataStore how to read and write your data type. Make sure you include a default value for the serializer to be used if there is no file created yet.
Use the property delegate created by dataStore to create an instance of DataStore<T>, where T is the type defined in the proto file. Call this once at the top level of your kotlin file and access it through this property delegate throughout the rest of your app. The filename parameter tells DataStore which file to use to store the data, and the serializer parameter tells DataStore the name of the serializer class defined in step 1.
Kotlin
Java

object SettingsSerializer : Serializer<Settings> {
  override val defaultValue: Settings = Settings.getDefaultInstance()

  override suspend fun readFrom(input: InputStream): Settings {
    try {
      return Settings.parseFrom(input)
    } catch (exception: InvalidProtocolBufferException) {
      throw CorruptionException("Cannot read proto.", exception)
    }
  }

  override suspend fun writeTo(
    t: Settings,
    output: OutputStream) = t.writeTo(output)
}

val Context.settingsDataStore: DataStore<Settings> by dataStore(
  fileName = "settings.pb",
  serializer = SettingsSerializer
)
Read from a Proto DataStore
Use DataStore.data to expose a Flow of the appropriate property from your stored object.

Kotlin
Java

val exampleCounterFlow: Flow<Int> = context.settingsDataStore.data
  .map { settings ->
    // The exampleCounter property is generated from the proto schema.
    settings.exampleCounter
  }
Write to a Proto DataStore
Proto DataStore provides an updateData() function that transactionally updates a stored object. updateData() gives you the current state of the data as an instance of your data type and updates the data transactionally in an atomic read-write-modify operation.

Kotlin
Java

suspend fun incrementCounter() {
  context.settingsDataStore.updateData { currentSettings ->
    currentSettings.toBuilder()
      .setExampleCounter(currentSettings.exampleCounter + 1)
      .build()
    }
}
Use DataStore in synchronous code
Caution: Avoid blocking threads on DataStore data reads whenever possible. Blocking the UI thread can cause ANRs or UI jank, and blocking other threads can result in deadlock.
One of the primary benefits of DataStore is the asynchronous API, but it may not always be feasible to change your surrounding code to be asynchronous. This might be the case if you're working with an existing codebase that uses synchronous disk I/O or if you have a dependency that doesn't provide an asynchronous API.

Kotlin coroutines provide the runBlocking() coroutine builder to help bridge the gap between synchronous and asynchronous code. You can use runBlocking() to read data from DataStore synchronously. RxJava offers blocking methods on Flowable. The following code blocks the calling thread until DataStore returns data:

Kotlin
Java

val exampleData = runBlocking { context.dataStore.data.first() }
Performing synchronous I/O operations on the UI thread can cause ANRs or UI jank. You can mitigate these issues by asynchronously preloading the data from DataStore:

Kotlin
Java

override fun onCreate(savedInstanceState: Bundle?) {
    lifecycleScope.launch {
        context.dataStore.data.first()
        // You should also handle IOExceptions here.
    }
}
This way, DataStore asynchronously reads the data and caches it in memory. Later synchronous reads using runBlocking() may be faster or may avoid a disk I/O operation altogether if the initial read has completed.

Use DataStore in multi-process code
Note: DataStore multi-process is currently available in the 1.1.0 release
You can configure DataStore to access the same data across different processes with the same data consistency guarantees as from within a single process. In particular, DataStore guarantees:

Reads only return the data that has been persisted to disk.
Read-after-write consistency.
Writes are serialized.
Reads are never blocked by writes.
Consider a sample application with a service and an activity:

The service is running in a separate process and periodically updates the DataStore


<service
  android:name=".MyService"
  android:process=":my_process_id" />
Important: To run the service in a different process, use the android:process attribute. Note that the process id is prefixed with a colon (':'). This makes the service run in a new process, private to the application.

override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
      scope.launch {
          while(isActive) {
              dataStore.updateData {
                  Settings(lastUpdate = System.currentTimeMillis())
              }
              delay(1000)
          }
      }
}
While the app would collect those changes and update its UI


val settings: Settings by dataStore.data.collectAsState()
Text(
  text = "Last updated: $${settings.timestamp}",
)
To be able to use DataStore across different processes, you need to construct the DataStore object using the MultiProcessDataStoreFactory.


val dataStore: DataStore<Settings> = MultiProcessDataStoreFactory.create(
   serializer = SettingsSerializer(),
   produceFile = {
       File("${context.cacheDir.path}/myapp.preferences_pb")
   }
)
serializer tells DataStore how to read and write your data type. Make sure you include a default value for the serializer to be used if there is no file created yet. Below is an example implementation using kotlinx.serialization:


@Serializable
data class Settings(
   val lastUpdate: Long
)

@Singleton
class SettingsSerializer @Inject constructor() : Serializer<Settings> {

   override val defaultValue = Settings(lastUpdate = 0)

   override suspend fun readFrom(input: InputStream): Settings =
       try {
           Json.decodeFromString(
               Settings.serializer(), input.readBytes().decodeToString()
           )
       } catch (serialization: SerializationException) {
           throw CorruptionException("Unable to read Settings", serialization)
       }

   override suspend fun writeTo(t: Settings, output: OutputStream) {
       output.write(
           Json.encodeToString(Settings.serializer(), t)
               .encodeToByteArray()
       )
   }
}
You can use Hilt dependency injection to make sure that your DataStore instance is unique per process:


@Provides
@Singleton
fun provideDataStore(@ApplicationContext context: Context): DataStore<Settings> =
   MultiProcessDataStoreFactory.create(...)
Handle file corruption
There are rare occasions where DataStore's persistent on-disk file could get corrupted. By default, DataStore doesn't automatically recover from corruption, and attempts to read from it will cause the system to throw a CorruptionException.

DataStore offers a corruption handler API that can help you recover gracefully in such a scenario, and avoid throwing the exception. When configured, the corruption handler replaces the corrupted file with a new one containing a pre-defined default value.

To set up this handler, provide a corruptionHandler when creating the DataStore instance in by dataStore() or in the DataStoreFactory factory method:


val dataStore: DataStore<Settings> = DataStoreFactory.create(
   serializer = SettingsSerializer(),
   produceFile = {
       File("${context.cacheDir.path}/myapp.preferences_pb")
   },
   corruptionHandler = ReplaceFileCorruptionHandler { Settings(lastUpdate = 0) }
)
Provide feedback
Share your feedback and ideas with us through these resources:

Local Preferences in Kotlin Multiplatform Using DataStore
Mohab erabi
Mohab erabi

·
Follow

5 min read
·
Nov 5, 2024
103


2





In modern mobile applications, managing user preferences and settings is essential for enhancing user experience. Jetpack DataStore is a powerful and efficient solution for storing key-value pairs or structured data. Unlike SharedPreferences, DataStore is built on Kotlin Coroutines, offering asynchronous data access and support for schema evolution. In this article, we’ll walk through the steps to integrate DataStore into a Kotlin Multiplatform project to create a simple theme preferences app.

First, you need to create a Compose Multiplatform project. Open the JetBrains wizard link https://kmp.jetbrains.com/ to create a new project template. Download it and open it in Android Studio
Step 2: Configure Gradle Files

Configure Gradle Files
Project Level gradle/libs.versions.toml
In your project-level gradle/libs.versions.toml, add the following line in the [versions] block to include DataStore:
datastore = "1.1.1"

should look like

[versions]
agp = "8.5.2"
android-compileSdk = "34"
android-minSdk = "24"
android-targetSdk = "34"
androidx-activityCompose = "1.9.3"
androidx-appcompat = "1.7.0"
androidx-constraintlayout = "2.1.4"
androidx-core-ktx = "1.13.1"
androidx-espresso-core = "3.6.1"
androidx-lifecycle = "2.8.3"
androidx-material = "1.12.0"
androidx-test-junit = "1.2.1"
compose-multiplatform = "1.7.0"
junit = "4.13.2"
kotlin = "2.0.21"
datastore = "1.1.1"
In the [libraries] block, add the following lines for datastore libs.

[libraries]
// other libs 
datastore = { module = "androidx.datastore:datastore", version.ref = "datastore" }
datastore-preferences = { module = "androidx.datastore:datastore-preferences", version.ref = "datastore" }
Compose App build.gradle.kts
In your composeapp/build.gradle.kts, add the following dependencies after syncing the Gradle:

commonMain.dependencies {
    implementation(compose.runtime)
    implementation(compose.foundation)
    implementation(compose.material3)
    implementation(libs.datastore.preferences)
    implementation(libs.datastore)
}
Create DataStore Implementation
In the commonMain source set, create a new file called CreateDataStore.kt:

typealias PrefsDataStore = DataStore<Preferences>
fun createDataStore(producePath: () -> String): PrefsDataStore =
 PreferenceDataStoreFactory.createWithPath(
 produceFile = { producePath().toPath() }
 )
const val dataStoreFileName = "cmp.preferences_pb"
@Composable
expect fun rememberDataStore(): PrefsDataStore
Android Implementation
In the Android main source set, create CreateDataStore.android.kt:

@Composable
actual fun rememberDataStore(): PrefsDataStore {
    val context = LocalContext.current
    return remember {
        createDataStore(
            producePath = {
                context.filesDir.resolve(dataStoreFileName).absolutePath
            },
        )
    }
}
iOS Implementation
In the iOS main source set, create CreateDataStore.ios.kt:

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun rememberDataStore(): PrefsDataStore {
    return remember {
        createDataStore(
            producePath = {
                val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                    directory = NSDocumentDirectory,
                    inDomain = NSUserDomainMask,
                    appropriateForURL = null,
                    create = false,
                    error = null,
                )
                requireNotNull(documentDirectory).path + "/$dataStoreFileName"
            },
        )
    }
}
Create Theme Management
We will create a simple theme preferences app. Visit the Material Theme Builder to generate your theme. Download it and create a folder named theme, then add the .kts files.

Create ThemeMode Enum Class
Create a new enum class called ThemeMode:

enum class ThemeMode {
    Light,
    System,
    Dark
}
Create AppTheme.kt
Create a new file called AppTheme.kt:

@Composable
fun AppTheme(
    mode: ThemeMode = ThemeMode.System,
    content: @Composable () -> Unit,
) {
    val isDark = isSystemInDarkTheme()
    val colorScheme = remember(mode) {
        when (mode) {
            ThemeMode.Light -> lightScheme
            ThemeMode.Dark -> darkScheme
            ThemeMode.System -> if (isDark) darkScheme else lightScheme
        }
    }

    val typo = remember(mode) {
        getAppTypography(mode)
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typo,
        content = content
    )
}
Define your base typography and a function to get the appropriate typography based on the theme mode:

val BaseTypography = Typography(
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 24.sp
    ),
    headlineMedium = TextStyle(
        fontSize = 28.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 34.sp
    ),
    titleSmall = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 20.sp
    )
)

fun getAppTypography(mode: ThemeMode): Typography {
    val color = if (mode == ThemeMode.Dark) Color.White else Color.Black
    return BaseTypography.copy(
        bodyLarge = BaseTypography.bodyLarge.copy(color = color),
        headlineMedium = BaseTypography.headlineMedium.copy(color = color),
        titleSmall = BaseTypography.titleSmall.copy(color = color)
    )
}
In the common source set, create a class ThemeModeSource:

class ThemeModeSource(
    private val dataStore: PrefsDataStore
) {
    companion object {
        private const val THEME_KEY = "themeKey"
    }

    fun getThemeMode(): Flow<ThemeMode> {
        return dataStore.data.map {
            val theme = it[stringPreferencesKey(THEME_KEY)] ?: ThemeMode.System.name
            ThemeMode.valueOf(theme)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun changeTheme(
        mode: ThemeMode
    ) {
        return withContext(Dispatchers.IO) {
            try {
                dataStore.edit {
                    it[stringPreferencesKey(THEME_KEY)] = mode.name
                }
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                e.printStackTrace()
            }
        }
    }
}

@Composable
fun rememberThemeSource(
    prefs: PrefsDataStore
): ThemeModeSource {
    return remember { ThemeModeSource(prefs) }
}
In your App.kt, use the following code:

@Composable
@Preview
fun App() {
    val datastore = rememberDataStore()
    val themeSource = rememberThemeSource(prefs = datastore)

    val theme by themeSource.getThemeMode().collectAsStateWithLifecycle(ThemeMode.System)

    val scope = rememberCoroutineScope()
    AppTheme(
        mode = theme
    ) {
        Scaffold { padding ->
            Column(
                modifier = Modifier.fillMaxSize().padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Change Your Theme ",
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center
                )
                ThemeMode.entries.forEach { mode ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            mode.name,
                            style = MaterialTheme.typography.titleSmall
                        )
                        RadioButton(
                            selected = mode == theme,
                            onClick = {
                                scope.launch {
                                    themeSource.changeTheme(mode)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
Optional : remove swift ios safe area for more Good looking
in iosApp/iosApp/ContentView.swift add the following code to remove the safe area padding

struct ContentView: View {
    var body: some View {
        ComposeView() .ignoresSafeArea(edges: .all)
                .ignoresSafeArea(.keyboard)
    }
}



now lets run and test the app





Conclusion
In this article, we’ve walked through the steps to integrate Jetpack DataStore into a Kotlin Multiplatform application to manage user theme preferences. We explored creating a basic theme switcher app that leverages DataStore to persist user preferences seamlessly across Android and iOS platforms. Remember that DataStore has many more methods for handling data, such as getString(), getInt(), getBoolean(), and more, which you can explore in the official DataStore documentation.

With this setup, you can easily extend the functionality of your app to include more user preferences, providing a more personalized experience.

Github Repository : https://github.com/mohaberabi/local-prefs-kmp-datastore
