#import <UIKit/UIKit.h>
#import <Cordova/CDVAvailability.h>
#import <Cordova/CDVPlugin.h>


@interface PiwikNativeiOS : CDVPlugin {
}

// The hooks for our plugin commands
- (void)start_track:(CDVInvokedUrlCommand *)command;
- (void)page:(CDVInvokedUrlCommand *)command;
- (void)link:(CDVInvokedUrlCommand *)command;

// Native Function
- (void)piwikPushScreenViewCustomMethodWithName:(NSString *)name andAction:(NSString *)action;
- (void)piwikPushEventCustomMethodWithCategory:(NSString *)category andName:(NSString *)name;

@end
