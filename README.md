# Fridge

Fridge is an Android library that eliminates the boilerplate of saving and restoring instance state.


## Features
- Easy save and restore
- Tiny footprint (works in runtime)


## Gradle

Add this in your root `build.gradle` file.

```
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
Then, add the library to your module `build.gradle`
```
dependencies {
    compile 'com.github.SaikoRobot:Fridge:0.2.0'
}
```

## Sample

```

public class MainActivity extends Activity {

    private int nonStateValue;

    @State
    private int stateValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fridge.restoreInstanceState(this, savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Fridge.saveInstanceState(this, outState);
    }
}


```

## License

MIT License
