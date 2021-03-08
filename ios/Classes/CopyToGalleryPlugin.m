#import "CopyToGalleryPlugin.h"
#if __has_include(<copy_to_gallery/copy_to_gallery-Swift.h>)
#import <copy_to_gallery/copy_to_gallery-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "copy_to_gallery-Swift.h"
#endif

@implementation CopyToGallerylugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftCopyToGallerylugin registerWithRegistrar:registrar];
}
@end
