# copy_to_gallery

A Flutter plugin to save images and videos to the gallery. 
Works for iOS and Android.


## Usage

1. add this in `pubspec.yaml`.

```dart
dependencies:
  copy_to_gallery:
    git:
      url: git://github.com/clragon/copy_to_gallery.git
```

2. import package

```dart
import 'package:copy_to_gallery/copy_to_gallery.dart';
```

3. (optional) create a new album.

```dart
await CopyToGallery.createAlbum("album name");
```

4. save image or video files in album.

```dart
await CopyToGallery.copyPictures("album name", ["file path 1", "file path2 "...]);
```

- or with names (are ignored on iOS):

```dart
await CopyToGallery.copyNamedPictures("album name", {"file path 1": "file name 1", "file path 2": "file name 2"...});
```

## Credit

- original code [r_album](https://github.com/rhymelph/r_album) by [rhymelph](https://github.com/rhymelph)

