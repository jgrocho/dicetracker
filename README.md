# Dice Tracker

The Dice Tracker app keeps track of die rolls. Specifically, it allows you
to record the results of the roll of two six-sided dice and keep track of
them over several rolls. Furthermore, you can also save the rolls to a
cumulative list, to view trends over time. This is useful for games that use
such dice when you want to observe the roll distribution. The app shows a
bar chart of the current rolls, which can be saved and are then added to the
cumulative bar chart.

This repository contains the source code for the Dice Tracker Android app.

Please see the [issues](https://github.com/jgrocho/dicetracker/issues)
section to report any bugs or feature requests and to see the list of known
issues.

## Building

The build requires Maven and the Android SDK to be installed. You also need
to set the `ANDROID_HOME` environment variable to the location of the SDK:

    export ANDROID_HOME=/opt/android-sdk

Once the tools are configured correctly, running `mvn clean package` will
create the APK under `target/`. If you have a connected device or running
emulator, `mvn android:{deploy,run}` will install and run the app on all
devices.

## Acknowledgements

This app makes use of at least the following libraries:

* [ActionBarSherlock](http://actionbarsherlock.com/index.html)
* [Google Android Support Library](http://developer.android.com/tools/extras/support-library.html)

## License

    Copyright 2012 Jonathan Grochowski

    Licensed under the Apache License, Version 2.0 (the "License"); you may
    not use this file except in compliance with the License.  You may obtain
    a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[Apache Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

## Contributing

Please fork this repository and make contributions using
[pull requests](https://github.com/jgrocho/dicetracker/pulls).
