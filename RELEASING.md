# Releasing

A release is a `vX.Y.Z` tag. Pushing it runs the **Publish** workflow, which uploads every module
to Maven Central.

herald-kmp wraps the Android and iOS SDKs, so release them first when a release needs something new
from them.

## Steps

1. **Update `CHANGELOG.md`.** Under `## Version X.Y.Z`, replace `_Not released yet_` with the
   release date, `_YYYY-MM-DD_`.
2. **Update the versions shown to readers** in `README.md`: the install snippet and the version
   table, with the Android and iOS SDK versions this release uses.
3. **Check that everything passes:**

    ```bash
    SIMULATOR_ID=$(scripts/simulator.sh) ./gradlew check publishToMavenLocal
    xcodebuild build -project sample/iosApp/KmpSample.xcodeproj -scheme KmpSample \
        -destination "id=$(scripts/simulator.sh)"
    ```

    `check` includes the API check. If the public API changed on purpose, run `./gradlew apiDump`
    and commit the new records in each module's `api/` folder.
4. **Release:**

    ```bash
    git commit -am "Release X.Y.Z"
    git tag vX.Y.Z
    git push origin main vX.Y.Z
    ```

5. **Verify:** the Publish workflow passes, and the modules appear on
   [Maven Central](https://central.sonatype.com/namespace/io.github.mkhytarmkhoian) (it can take
   half an hour).

A tag must never move once pushed. When a release is wrong, fix it forward with the next version.

## One-time setup

Four repository secrets, the same as in the Android repo: `MAVEN_CENTRAL_USERNAME`,
`MAVEN_CENTRAL_PASSWORD` (a Central Portal user token), `SIGNING_KEY` (an ASCII-armored GPG private
key) and `SIGNING_KEY_PASSWORD`.
