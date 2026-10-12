# Contributing

Thanks for helping with Herald. Its design is shared with the
[Android](https://github.com/MkhytarMkhoian/herald),
[Flutter](https://github.com/MkhytarMkhoian/herald-flutter) and
[iOS](https://github.com/MkhytarMkhoian/herald-ios) SDKs. herald-kmp wraps the Android and iOS
ones, so a change to how Herald behaves usually belongs there. Open an issue first.

## Setting up

You need a Mac with Xcode, for the iOS parts, and the Android SDK. Tell Gradle where the Android SDK
is in `local.properties`, as `sdk.dir=/path/to/Android/sdk`.

```bash
SIMULATOR_ID=$(scripts/simulator.sh) ./gradlew check   # tests on the JVM, Android and iOS
./gradlew publishToMavenLocal                          # the artifacts Maven Central needs
```

The iOS sample builds with the shared module from this repository:

```bash
xcodebuild build -project sample/iosApp/KmpSample.xcodeproj -scheme KmpSample \
    -destination "id=$(scripts/simulator.sh)"
```

## What every change needs

- **The same API as the native SDKs.** On Android, a shared type is the Android SDK's own, so its
  shape must match exactly. On iOS, the Kotlin copy must match too.
- **Plain Kotlin and few comments, in simple words.** Document what a reader can't guess from the
  name: a Kotlin limit, or why a type differs from Android.
- **No accidental API changes.** `check` compares the public API with the records in each module's
  `api/` folder. When a change is meant, run `./gradlew apiDump` and commit the new records.
- **Tests:** on the JVM or Android for the Android side, and on iOS for the iOS side.
- **A line in `CHANGELOG.md`** under the next, unreleased version, starting with `New:`, `Fix:`,
  `Upgrade:` or `Breaking:`.
