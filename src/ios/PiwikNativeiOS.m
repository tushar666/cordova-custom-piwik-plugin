#import "PiwikNativeiOS.h"

@implementation PiwikNativeiOS
  
}
- (void)pluginInitialize {

  NSLog(@" Push io ^^^^^^^^^^^  pluginInitialize ");

  #pragma mark Piwik Initialise
    // PIWIK INTIALISE
    [PiwikTracker sharedInstanceWithSiteID:PIWIK_PROJECT_ID baseURL:[NSURL URLWithString:PIWIK_DATA_POST_URL]];
    [[PiwikTracker sharedInstance] setIncludeDefaultCustomVariable:NO];
    [PiwikTracker sharedInstance].userID = [[UIDevice currentDevice] identifierForVendor].UUIDString;
    [PiwikTracker sharedInstance].isPrefixingEnabled = NO;
    [PiwikTracker sharedInstance].appName = [[[NSBundle mainBundle] infoDictionary] objectForKey:@"CFBundleIdentifier"];
    // Print events to the console
    [PiwikTracker sharedInstance].debug = NO;
    
    /*
     // Switch to manual dispatch
     [PiwikTracker sharedInstance].dispatchInterval = -1;
     
     // Manual dispatch
     [[PiwikTracker sharedInstance] dispatch];
     */
    
    [self piwikPushScreenViewCustomMethodWithName:@"view_controller 1" andAction:@"Page"];


}


- (void)start_track:(CDVInvokedUrlCommand *)command {
    NSLog(@" Piwik -> start_track Method");
}
- (void)page:(CDVInvokedUrlCommand *)command {
    NSLog(@" Piwik -> page Method");
}
- (void)link:(CDVInvokedUrlCommand *)command{
    NSLog(@" Piwik -> link Method");
}


// PIWIK METHOD TO TRACK EVENTS LIKE PAGE VISIT & BUTTON CLICKS

// --------- This method will call only for Screen Visit / Page Visit
- (void)piwikPushScreenViewCustomMethodWithName:(NSString *)name andAction:(NSString *)action {
    //[[PiwikTracker sharedInstance] sendViews:name, nil];
    
    // Adding Custom Variables
    [self addCustomVariableForPiwik];
    [[PiwikTracker sharedInstance]sendView:name];
    NSLog(@"SEND PIWIK SCREEN VIEWS Name -> %@  and Action -> %@", name, action);
    
    
    // Switch to manual dispatch
    [PiwikTracker sharedInstance].dispatchInterval = -1;
    
    // Manual dispatch
    [[PiwikTracker sharedInstance] dispatch];
}

// ---------  This method will call only for Button Click and
- (void)piwikPushEventCustomMethodWithCategory:(NSString *)category andName:(NSString *)name {
    
    
    //NSString * strUUID = [UIDevice currentDevice].identifierForVendor.UUIDString;
    NSString * action   = @"hyperlink";
    // Adding Custom Variables
    [self addCustomVariableForPiwik];
    [[PiwikTracker sharedInstance] sendEventWithCategory:category action:action name:name value:0];
    
    // Switch to manual dispatch
    [PiwikTracker sharedInstance].dispatchInterval = -1;
    
    // Manual dispatch
    [[PiwikTracker sharedInstance] dispatch];
    
}

-(void)addCustomVariableForPiwik
{
    NSLog(@"Adding Custom Variables");
    CustomVariableScope scope = ScreenCustomVariableScope; // ScreenCustomVariableScope | VisitCustomVariableScope
    
    [[PiwikTracker sharedInstance] setCustomVariableForIndex:1 name:@"imei no" value:@"7727727727277773" scope:scope];
    [[PiwikTracker sharedInstance] setCustomVariableForIndex:2 name:@"name" value:@"Tushar" scope:scope];
    [[PiwikTracker sharedInstance] setCustomVariableForIndex:3 name:@"gender" value:@"Male" scope:scope];
    [[PiwikTracker sharedInstance] setCustomVariableForIndex:4 name:@"date of birth" value:@"March 24 9188" scope:scope];
    
}



- (void)init:(CDVInvokedUrlCommand *)command {

  NSLog(@" Push io Pre login init ^^^^^^^^^^^  pluginInitialize ");
    NSString* phrase = [command.arguments objectAtIndex:0];
    NSLog(@"%@", phrase);
}

- (void)register:(CDVInvokedUrlCommand *)command {

  NSLog(@" Push io Post login init ^^^^^^^^^^^  pluginInitialize ");

  // NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
  // NSLocale *enUSPOSIXLocale = [NSLocale localeWithLocaleIdentifier:@"en_US_POSIX"];
  // [dateFormatter setLocale:enUSPOSIXLocale];
  // [dateFormatter setDateFormat:@"yyyy-MM-dd'T'HH:mm:ssZZZZZ"];

  // NSDate *now = [NSDate date];
  // NSString *iso8601String = [dateFormatter stringFromDate:now];

  // CDVPluginResult *result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:iso8601String];
  // [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

@end
