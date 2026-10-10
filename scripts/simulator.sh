#!/usr/bin/env bash
#
# Prints the id of an available iPhone simulator, for xcodebuild's -destination "id=...". CI
# machines come with different simulators, so the tests don't name one.

set -euo pipefail

xcrun simctl list devices available iPhone |
  grep -oE '\(([0-9A-F]{8}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{12})\)' |
  head -n 1 |
  tr -d '()'
