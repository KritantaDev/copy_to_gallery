import 'dart:async';

import 'package:flutter/services.dart';

class CopyToGallery {
  static const MethodChannel _channel =
      const MethodChannel('com.clragon/copy_to_gallery');

  /// create a new album
  static Future createAlbum(String albumName) {
    return _channel.invokeMethod('createAlbum', {
      'albumName': albumName,
    });
  }

  /// copies all files to an album called [albumName].
  /// if the album does not exist, it will be created.
  ///
  /// [files] are passed as a List<String> of file paths.
  /// file names will be auto-generated.
  static Future copyPictures(String albumName, List<String> filepaths) {
    Map<String, String> files =
        Map.fromIterable(filepaths, key: (v) => v, value: (v) => null);
    return _channel.invokeMethod('saveAlbum', {
      'albumName': albumName,
      'files': files,
    });
  }

  /// copies all files to an album called [albumName].
  /// if the album does not exist, it will be created.
  ///
  /// [files] are passed as a Map<String, String>, filepath and filename respectively.
  /// the file name can be null, which will result in an auto-generated name.
  static Future copyNamedPictures(String albumName, Map<String, String> files) {
    return _channel.invokeMethod('saveAlbum', {
      'albumName': albumName,
      'files': files,
    });
  }
}
