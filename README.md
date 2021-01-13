# copy_to_gallery

![](screen/r_album.png)

A Flutter plugin to save images and videos to the gallery. 
Works for iOS and Android.


## Usage

1. add this in `pubspec.yaml`.

```dart
dependencies:
  r_album:
    git:
      url: git://github.com/clragon/r_album.git
      ref: master
```

2. import it

```dart
import 'package:r_album/r_album.dart';
```

3. (optional) create a new album.

```dart
await RAlbum.createAlbum("your album name");
```

4. save image or video files in album.

```dart
await RAlbum.saveAlbum("your album name", {"filename1": "filepath1", "filename2": "filepath2"...});
```
file names are ignored on iOS.

## Credit

- original code [r_album](https://github.com/rhymelph/r_album) by [rhymelph](https://github.com/rhymelph)

