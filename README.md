<p align="center">
    <a>
    <img src="app/src/main/res/drawable/covid2.webp" width="200" height="200"/>
    </a>
    <h1 align="center">GoCorona Tracker</h1>
</p>

[![GitHub license](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

## About
First of all Stay safe from COVID. Spread awareness too!
This app loads data from 2 API providers (covid19india.org for India data, corona.ninja.lmao for World data) and stores it in persistence storage. Then shows the data in UI.

## Features
- India data (Overview, State wise, District wise)
- World data (Overview, Country wise)
- Night Mode
- Multiple language support (Kannada, English, Hindi)
- Screen support (any size, any orientation)
- Offline support

## ✨ Screenshots
| Main Screen | Statistics |  Preventions |
|:-:|:-:|:-:|
| ![Fist](media/screen_1.jpg?raw=true) | ![3](media/screen_2.jpg?raw=true) | ![3](media/screen_3.jpg?raw=true) |
| Main Screen Dark | Statistics Dark |  FAQ |
| ![4](media/screen_dark_1.jpg?raw=true) | ![5](media/screen_dark_2.jpg?raw=true) | ![6](media/screen_dark_3.jpg?raw=true) |

## Tech Stack
- Kotlin
- MVVM with a touch of MVI
- Dagger for DI
- Retrofit for API calls
- Room for DB
- Coroutines and flow for threading and state management
- Repository pattern
- Clean architecture (kind of :p)

## 🤝 How to Contribute
1.  Fork it
2.  Create your feature branch (git checkout -b my-new-feature)
3.  Commit, Push, Create new PR.

## 📝 License
This project is released under the MIT license.
See [LICENSE](./LICENSE) for details.

```
MIT License

Copyright (c) 2020 Hari Choudhary

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
